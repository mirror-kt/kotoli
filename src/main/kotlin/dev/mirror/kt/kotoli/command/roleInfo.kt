package dev.mirror.kt.kotoli.command

import dev.kord.common.annotation.KordPreview
import dev.kord.common.entity.snowflake
import dev.kord.core.behavior.channel.createEmbed
import dev.kord.core.behavior.respond
import dev.kord.core.event.interaction.InteractionCreateEvent
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.toList

@OptIn(KordPreview::class)
suspend fun InteractionCreateEvent.onRoleInfo() {
    val roleId = interaction.command
        .options["role"]
        ?.snowflake()

    val role = roleId?.let { id -> interaction.guild.getRoleOrNull(id) }
        ?: run {
            interaction.respond(true) {
                content = "権限が見つかりませんでした"
            }
            return
        }

    println("onRoleInfo")

    val members = interaction.guild
        .members
        .buffer(200)
        .filter { it.roleIds.contains(roleId) }
        .toList()

    interaction.channel.createEmbed {
        title = "${role.name}の詳細"
        color = role.color
        field("メンバー(${members.size}人)", false) {
            members.joinToString("\n") { it.displayName }
        }
    }
}