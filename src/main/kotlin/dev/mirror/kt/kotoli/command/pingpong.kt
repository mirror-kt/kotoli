package dev.mirror.kt.kotoli.command

import dev.mirror.kt.kotoli.framework.Bot
import dev.mirror.kt.kotoli.framework.event.MessageEvent
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent

data class PingPongEvent(
    val discordEvent: GuildMessageReceivedEvent
)

fun Bot.pingPong() {
    register(::pingPongDispatcher)
    register(::onPingPong)
}

private fun pingPongDispatcher(event: MessageEvent) {
    if (event.content == "ping") {
        event.eventBus.dispatch(PingPongEvent(event.discordEvent))
    }
}

private fun onPingPong(event: PingPongEvent) {
    event.discordEvent.channel.sendMessage("Pong!").queue()
}
