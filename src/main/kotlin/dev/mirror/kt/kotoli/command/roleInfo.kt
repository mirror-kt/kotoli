package dev.mirror.kt.kotoli.command

import dev.mirror.kt.kotoli.framework.Bot
import dev.mirror.kt.kotoli.framework.event.CommandEvent
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent

data class RoleInfoEvent(
    val discordEvent: GuildMessageReceivedEvent,
    val roleNamePattern: String
)

fun Bot.roleInfo() {
    register(::roleInfoDispatcher)
    register(::onRoleInfo)
}

private fun roleInfoDispatcher(event: CommandEvent) {
    if (event.content.toLowerCase().startsWith("roleinfo")) {
        val roleName = event.content.substring("roleinfo ".length)

        event.eventBus.dispatch(RoleInfoEvent(event.discordEvent, roleName))
    }
}

private fun onRoleInfo(event: RoleInfoEvent) {
    val roles = event.discordEvent.guild.roles
        .let {
            it.filter { role -> role.name == event.roleNamePattern }
                .ifEmpty { it.filter { role -> role.name.startsWith(event.roleNamePattern) } }
        }

    if (roles.isEmpty()) {
        event.discordEvent.channel.sendMessage("権限が見つかりませんでした").queue()
        return
    }
    if (roles.size > 1) {
        event.discordEvent.channel.sendMessage(
            """
            該当する権限が複数ありました
            ```
            ${roles.joinToString("\n")}
            ```
        """.trimIndent()
        )
        return
    }

    val role = roles.single()
    event.discordEvent.guild.findMembers { it.roles.contains(role) }
        .onSuccess {
            val embed = EmbedBuilder().apply {
                addField("メンバー", it.joinToString("\n") { it.nickname ?: it.effectiveName }, false)
            }
            event.discordEvent.channel.sendMessage(embed.build()).queue()
        }
}
