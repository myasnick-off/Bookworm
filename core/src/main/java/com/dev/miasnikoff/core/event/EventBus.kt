package com.dev.miasnikoff.core.event

import kotlinx.coroutines.flow.SharedFlow

interface EventBus {
    val events: SharedFlow<AppEvent>
    suspend fun emitEvent(event: AppEvent)
}