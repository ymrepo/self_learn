package com.ym.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.datetime.Clock

typealias Middleware<State, Event> = (flow: MutableStateFlow<State>) -> ((event: Event) -> Unit) -> ((event: Event) -> Unit)

//send 参数是个方法类型参数，方法包括三个参数，flow，next的方法类型参数，event
//返回类型是个Middleware，
fun <State, Event> middleware(send: (state: MutableStateFlow<State>, next: (Event) -> Unit, event: Event) -> Unit): Middleware<State, Event> =
    { state ->
        { next ->
            { event ->
                send(state, next, event)
            }
        }
    }

interface BaseState
interface BaseEvent{
    val instant get()=Clock.System.now()
}
interface BaseAction

class MviBaseViewModel :ViewModel() {
}