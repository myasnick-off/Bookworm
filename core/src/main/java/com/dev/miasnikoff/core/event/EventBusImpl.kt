package com.dev.miasnikoff.core.event

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

class EventBusImpl @Inject constructor(): EventBus {

    private val _events = MutableSharedFlow<AppEvent>()
    override val events = _events.asSharedFlow()

    override suspend fun emitEvent(event: AppEvent) {
        _events.emit(event)
    }
}