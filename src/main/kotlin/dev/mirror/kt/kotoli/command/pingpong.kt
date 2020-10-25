package dev.mirror.kt.kotoli.command

import dev.mirror.kt.kotoli.framework.Bot
import dev.mirror.kt.kotoli.framework.event.MessageEvent

data class PingPongEvent(
    val messageEvent: MessageEvent
)

fun Bot.pingPong() {
    register(::pingPongDispatcher)
    register(::onPingPong)
}

private fun pingPongDispatcher(event: MessageEvent) {
    if(event.discordEvent.message.contentRaw == "kt!ping") {
        event.eventBus.dispatch(PingPongEvent(event))
    }
}

private fun onPingPong(event: PingPongEvent) {
    event.messageEvent.discordEvent.channel.sendMessage("Pong!").queue()
}
