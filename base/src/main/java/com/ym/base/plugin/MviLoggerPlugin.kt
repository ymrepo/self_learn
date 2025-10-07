package com.ym.base.plugin

import com.ym.base.BaseAction
import com.ym.base.BaseEvent
import com.ym.base.BaseState

class MviLoggerPlugin<Event : BaseEvent, State : BaseState, Action : BaseAction> :
    MviPlugin<Event, State, Action>() {
    init {
        eventWrap = eventWrap { store, next, event -> next.invoke(event) }
        setStateWrap = stateWrap { store, next, newState -> next.invoke(newState) }
        sendActionWrap = actionWrap { store, next, action -> next(action) }
        sendCommonAction = commonActionWrap { store, next, action -> next.invoke(action) }
    }

}