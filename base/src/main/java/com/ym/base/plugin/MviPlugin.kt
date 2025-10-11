package com.ym.base.plugin

import com.ym.base.BaseAction
import com.ym.base.BaseEvent
import com.ym.base.BaseState
import com.ym.base.CommonAction
import com.ym.base.MviBaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.serialization.PolymorphicSerializer
import kotlinx.serialization.modules.SerializersModule

interface MviPluginService {
    fun <Event : BaseEvent, State : BaseState, Action : BaseAction> MviBaseViewModel<Event, State, Action>.fetchPlugins(
        vmKey: String,
        eventSerializersModule: SerializersModule,
        stateSerializersModule: SerializersModule,
        actionSerializersModule: SerializersModule,
        commonActionSerializersModule: SerializersModule
    ): MutableList<MviPlugin<Event, State, Action>>
}

open class MviPlugin<Event : BaseEvent, State : BaseState, Action : BaseAction> {
    protected val pluginName = "${this::class.simpleName}"
    protected val eventSerialize = PolymorphicSerializer(BaseEvent::class)
    protected val stateSerializer = PolymorphicSerializer(BaseState::class)
    protected val actionSerializer = PolymorphicSerializer(BaseAction::class)
    protected val commonActionSerializer = PolymorphicSerializer(CommonAction::class)
    open var eventWrap: EventWrap<State, Event>? = null
    open var setStateWrap: StateWrap<State>? = null
    open var sendActionWrap: ActionWrap<State, Action>? = null
    open var sendCommonAction: CommonActionWrap<State>? = null
}

typealias EventWrap<State, Event> = (MutableStateFlow<State>) -> (((Event) -> Unit) -> ((Event) -> Unit))

fun <State, Event> eventWrap(send: (store: MutableStateFlow<State>, next: (Event) -> Unit, event: Event) -> Unit): EventWrap<State, Event> {
    return fun(store: MutableStateFlow<State>): (((Event) -> Unit) -> ((Event) -> Unit)) {
        return fun(next: (Event) -> Unit): (Event) -> Unit {
            return fun(event: Event) {
                send(store, next, event)
            }
        }

    }
}

typealias StateWrap<State> = (MutableStateFlow<State>) -> (((State) -> Unit) -> ((State) -> Unit))

fun <State> stateWrap(send: (store: MutableStateFlow<State>, next: (State) -> Unit, newState: State) -> Unit): StateWrap<State> =
    { store ->
        { next ->
            { newState ->
                {
                    send(store, next, newState)
                }
            }
        }
    }

typealias ActionWrap<State, Action> = (MutableStateFlow<State>) -> (((Action) -> Unit) -> ((Action) -> Unit))

fun <State, Action> actionWrap(send: (store: MutableStateFlow<State>, next: (Action) -> Unit, action: Action) -> Unit): ActionWrap<State, Action> =
    { store ->
        { next ->
            { action ->
                {
                    send(store, next, action)
                }
            }
        }
    }


typealias CommonActionWrap<State> = (MutableStateFlow<State>) -> (((CommonAction) -> Unit) -> ((CommonAction) -> Unit))

fun <State> commonActionWrap(send: (store: MutableStateFlow<State>, next: (CommonAction) -> Unit, newState: CommonAction) -> Unit): CommonActionWrap<State> =
    { store ->
        { next ->
            { newState ->
                {
                    send(store, next, newState)
                }
            }
        }
    }


/**
 * wrapGetter 相当于(MviPlugin<Event, State, Action>)为参数 ((MutableStateFlow<State>) -> (((Input) -> Unit) -> ((Input) -> Unit)))为返回值的high-order function
 * 这个返回值又是一个 (MutableStateFlow<State>)为参数 (((Input) -> Unit) -> ((Input) -> Unit)) 为返回值的high-order function
 * 这个返回又是一个((Input) -> Unit) -> ((Input) -> Unit) 参数是(Input) -> Unit) 返回值也是(Input) -> Unit) 的high-order function
 */
fun <Event : BaseEvent, State : BaseState, Action : BaseAction, Input> composeWrapChain(
    plugins: List<MviPlugin<Event, State, Action>>,
    store: MutableStateFlow<State>,
    wrapGetter: (MviPlugin<Event, State, Action>) -> ((MutableStateFlow<State>) -> (((Input) -> Unit) -> ((Input) -> Unit)))?,
    originalFunction: (Input) -> Unit
): (Input) -> Unit {
    val wraps = plugins.mapNotNull { plugin ->
        wrapGetter(plugin)?.let { it(store) }
    }
    return if (wraps.isEmpty()) originalFunction else compose(wraps)(originalFunction)
}

/**
 * 组合多个函数
 * 示例：
 *val functions = listof({x:String->x.uppercase()},{x:String -> "$x"})
 * val composed=compose(functions)
 * println(composed("hello))//输出 ”HELLO“
 */
fun <T> compose(functions: List<(T) -> T>): (T) -> T = { x ->
    functions.foldRight(x) { f, composed -> f(composed) }
}

fun <T> compose(vararg functions: (T) -> T): (T) -> T {
    return { x -> functions.foldRight(x) { function, acc -> function(acc) } }
}

