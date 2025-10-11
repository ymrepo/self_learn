package com.ym.base

import android.util.Log
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import com.ym.base.plugin.MviLoggerPlugin
import com.ym.base.plugin.MviPlugin
import com.ym.base.plugin.composeWrapChain
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

@Serializable
open class BaseState

@Serializable
open class BaseEvent

@Serializable
open class BaseAction

@Serializable
sealed class CommonAction {
    @Serializable
    data class ShortToastAction(val message: String = "") : CommonAction()

    @Serializable
    data class LongToastAction(val message: String = "") : CommonAction()

    @Serializable
    data class ShowNetworkLoadingAction(val message: String = "", val cancelable: Boolean = true) :
        CommonAction()

    @Serializable
    data object DismissNetworkLoadingAction : CommonAction()

    @Serializable
    data class FinishAction(val finish: Boolean) : CommonAction()

    @Serializable
    data class RouterAction(val routePath: String = "", val args: Map<String, String>) :
        CommonAction()

}

open abstract class MviBaseViewModel<Event : BaseEvent, State : BaseState, Action : BaseAction>(
    private val eventSerializersModule: SerializersModule? = null,
    private val stateSerializersModule: SerializersModule? = null,
    private val actionSerializersModule: SerializersModule? = null
) : ViewModel(), LifecycleObserver {

    init {
        installPlugin()
        subscribeToEvents()
    }

    val exceptionHandlerScope =
        CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate + CoroutineExceptionHandler { context, throwable ->
            Log.e(
                "MviBaseViewModel",
                "throw:${throwable.message}"
            )
        })

    private val commonActionSerializersModule = SerializersModule {
        polymorphic(CommonAction::class) {
            subclass(CommonAction.ShortToastAction::class)
            subclass(CommonAction.LongToastAction::class)
            subclass(CommonAction.ShowNetworkLoadingAction::class)
            subclass(CommonAction.DismissNetworkLoadingAction::class)
            subclass(CommonAction.FinishAction::class)
            subclass(CommonAction.RouterAction::class)
        }
    }
    private val initialState: State by lazy { createInitialState() }
    abstract fun createInitialState(): State

    private val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    private val _action = Channel<Action> { Channel.BUFFERED }
    private val action = _action.receiveAsFlow()

    /**
     * Toast事件 conflated 模式，覆盖旧事件
     * 默认容量：
     * 当溢出策略为Buffer Overflow.SUSPEND,默认容量为64
     * 当溢出策略为DROP——OLDEST 或 DROP_LAEST 默认容量为1
     * 适用于大多数需要缓冲的场景，平衡性能和资源
     */
    private val _toastChannel = Channel<CommonAction> { Channel.CONFLATED }
    val toastChannel = _toastChannel.receiveAsFlow()

    /**
     * RENDEZVOUS ,严格配对
     * 在CHannel()中常见rendezvous通道，无缓冲通道
     * 发送方和接收方必须同时主备好才能交换数据
     * send 挂起直到有接收方调用receive
     * receive挂起直到有发送方调用send
     * 适用于需要严格同步的场景，如一对一通信
     */
    private val _loadingChannel = Channel<CommonAction> { Channel.RENDEZVOUS }
    val loadingChannel = _loadingChannel.receiveAsFlow()

    private val _routerChannel = Channel<CommonAction> { Channel.BUFFERED }
    val routerChannel = _routerChannel.receiveAsFlow()

    private val _event = MutableSharedFlow<Event>(
        extraBufferCapacity = 64,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    var sendEvent = fun(event: Event) {
        exceptionHandlerScope.launch {
            _event.emit(event)
        }
    }

    var sendState = fun(state: State) {
        _state.update { state }
    }

    var sendAction = fun(action: Action) {
        exceptionHandlerScope.launch {
            _action.send(action)
        }
    }

    var sendCommonAction = fun(action: CommonAction) {
        when (action) {
            is CommonAction.ShortToastAction,
            is CommonAction.LongToastAction,
                -> {
                _toastChannel.trySend(action)
            }

            is CommonAction.ShowNetworkLoadingAction,
            is CommonAction.DismissNetworkLoadingAction -> {
                exceptionHandlerScope.launch {
                    _loadingChannel.send(action)
                }
            }

            is CommonAction.FinishAction,
            is CommonAction.RouterAction -> {
                exceptionHandlerScope.launch {
                    _routerChannel.send(action)
                }

            }
        }

    }

    private fun installPlugin() {
        val plugins = plugins()
        sendEvent = composeWrapChain(plugins, _state, { it.eventWrap }, sendEvent)
        sendState = composeWrapChain(plugins, _state, { it.setStateWrap }, sendState)
        sendAction = composeWrapChain(plugins, _state, { it.sendActionWrap }, sendAction)
        sendCommonAction =
            composeWrapChain(plugins, _state, { it.sendCommonAction }, sendCommonAction)

    }

    private fun plugins(): List<MviPlugin<Event, State, Action>> {
        val plugins: MutableList<MviPlugin<Event, State, Action>> = mutableListOf()
        plugins.add(MviLoggerPlugin())
        plugins += getExtensionPlugins()
        return plugins
    }

    open fun getExtensionPlugins(): List<MviPlugin<Event, State, Action>> {
        return emptyList()
    }

    private fun subscribeToEvents() {
        exceptionHandlerScope.launch {
            _event.collect { event ->
                processEvent(event)
            }
        }
    }

    private fun processEvent(event: Event) {
        exceptionHandlerScope.launch {
            handleEvent(event, _state.value)
        }
    }

    abstract fun handleEvent(event: Event, state: State)

    override fun onCleared() {
        super.onCleared()
        runCatching {
            _toastChannel.close()
            _routerChannel.close()
            _loadingChannel.close()
            _action.close()
        }.also {
            exceptionHandlerScope.cancel()
        }

    }
}