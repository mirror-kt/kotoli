package dev.mirror.kt.kotoli.framework

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class EventBus(
    override val coroutineContext: CoroutineContext = Dispatchers.Default
): CoroutineScope {
    @PublishedApi
    internal val flow = MutableSharedFlow<Any>()

    @Suppress("EXPERIMENTAL_API_USAGE")
    inline fun <reified T> getSubscription(): Flow<T> =
        flow.filterIsInstance()

    fun dispatch(event: Any) {
        launch {
            flow.emit(event)
        }
    }
}
