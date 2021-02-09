package dev.mirror.kt.kotoli.command

import dev.kord.common.annotation.KordPreview
import dev.kord.core.behavior.respond
import dev.kord.core.event.interaction.InteractionCreateEvent
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList

@OptIn(KordPreview::class)
suspend fun InteractionCreateEvent.onRoleRank() {
    val ranking = interaction.guild
        .members
        .map { it to it.roleBehaviors.count() }
        .toList()
        .sortedByDescending { it.second }
        .take(10)

    interaction.respond {
        embed {
            title = "ロール数ランキング TOP10"
            field("名前", true) {
                ranking.joinToString("\n") { it.first.displayName }
            }
            field("ロール数", true) {
                ranking.joinToString("\n") { "${it.second}" }
            }
        }
    }
}