package dev.mirror.kt.kotoli.framework.event

import dev.mirror.kt.kotoli.framework.EventBus
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent

data class MessageEvent(
    val discordEvent: GuildMessageReceivedEvent,
    val content: String,
    val eventBus: EventBus
)
