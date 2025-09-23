//package com.ym.base
//
//import kotlinx.coroutines.flow.MutableStateFlow
//
//typealias Event1<Event> = (Event) -> Unit
//typealias Event2<Event> = (event: Event) -> Unit
//
//typealias test1<Event, State> = (flow: MutableStateFlow<State>) -> (() -> Unit)
//typealias test2<Event, State> = (flow: MutableStateFlow<State>) -> (() -> (event: Event) -> Unit)
//typealias test3<Event, State> = (flow: MutableStateFlow<State>) -> (((event: Event) -> Unit) -> Unit)
//typealias test4<Event, State> = (flow: MutableStateFlow<State>) -> (() -> Unit) -> (() -> Unit)
//typealias Middleware<State, Event> = (flow: MutableStateFlow<State>) -> ((event: Event) -> Unit) -> ((event: Event) -> Unit)
//
////send 参数是个方法类型参数，方法包括三个参数，flow，next的方法类型参数，event
////返回类型是个Middleware，
//fun <State, Event> middleware(send: (state: MutableStateFlow<State>, next: (Event) -> Unit, event: Event) -> Unit): Middleware<State, Event> =
//    { state ->
//        { next ->
//            { event ->
//                send(state, next, event)
//            }
//        }
//    }
