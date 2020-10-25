package dev.mirror.kt.kotoli.framework

import net.dv8tion.jda.api.events.ReadyEvent
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class EventListener(
    private val eventBus: EventBus
) : ListenerAdapter() {
    override fun onReady(event: ReadyEvent) {
        eventBus.dispatch(event)
    }

    override fun onGuildMessageReceived(event: GuildMessageReceivedEvent) {
        if(event.author.isBot) return
        eventBus.dispatch(event)
    }
}
