package dev.mirror.kt.kotoli.framework

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class EventBus(
    override val coroutineContext: CoroutineContext = Dispatchers.Default
): CoroutineScope {
    @Suppress("EXPERIMENTAL_API_USAGE")
    val channel = ConflatedBroadcastChannel<Any>()

    @Suppress("EXPERIMENTAL_API_USAGE")
    inline fun <reified T> getSubscription(): Flow<T> = channel.openSubscription()
        .consumeAsFlow()
        .filter { it is T }
        .map { it as T }

    @Suppress("EXPERIMENTAL_API_USAGE")
    fun dispatch(event: Any) {
        launch {
            channel.send(event)
        }
    }
}
