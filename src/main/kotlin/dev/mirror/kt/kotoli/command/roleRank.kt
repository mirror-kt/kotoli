package dev.mirror.kt.kotoli.command

import dev.mirror.kt.kotoli.framework.Bot
import dev.mirror.kt.kotoli.framework.event.MessageEvent
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.TextChannel

data class RoleRankEvent(val guild: Guild, val channel: TextChannel)

fun Bot.roleRank() {
    register(::roleInfoDispatcher)
    register(::onRoleRank)
}

private fun roleInfoDispatcher(event: MessageEvent) {
    if (event.content.toLowerCase() == "rolerank") {
        event.eventBus.dispatch(RoleRankEvent(event.discordEvent.guild, event.discordEvent.channel))
    }
}

private fun onRoleRank(event: RoleRankEvent) {
    event.guild.loadMembers()
        .onSuccess {
            println("onRoleRank")
            val embed = EmbedBuilder().apply {
                val rolesMap = it.sortedByDescending { member -> member.roles.size }
                    .take(10)
                    .map { member -> (member.nickname ?: member.effectiveName) to member.roles.size }
                    .toMap()

                addField("名前", rolesMap.keys.joinToString("\n"), true)
                addField("権限数", rolesMap.values.joinToString("\n"), true)
            }
            event.channel.sendMessage(embed.build()).queue()
        }
}
