package dev.mirror.kt.kotoli.framework

import dev.mirror.kt.kotoli.framework.event.MessageEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import net.dv8tion.jda.api.requests.GatewayIntent
import kotlin.coroutines.CoroutineContext

class Bot private constructor(
    private val jda: JDA,
    val eventBus: EventBus
): CoroutineScope {
    override val coroutineContext: CoroutineContext = Dispatchers.Default

    companion object {
        fun create(token: String, intents: Collection<GatewayIntent> = listOf()): Bot {
            val eventbus = EventBus()

            val jda = JDABuilder.create(token, intents)
                .addEventListeners(EventListener(eventbus))
                .build()

            return Bot(jda, eventbus)
        }
    }

    init {
        register<GuildMessageReceivedEvent> {
            if (!it.message.contentRaw.startsWith("kt!")) return@register
            eventBus.dispatch(MessageEvent(it, it.message.contentRaw.substring("kt!".length), eventBus))
        }
    }

    inline fun <reified T> register(crossinline handler: (T) -> Unit) {
        launch {
            eventBus.getSubscription<T>()
                .collect {
                    handler(it)
                }
        }
    }

    fun start() {
        jda.awaitReady()
    }

    fun shutdown() {
        jda.shutdown()
        cancel()
    }
}
