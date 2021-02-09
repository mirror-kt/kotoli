package dev.mirror.kt.kotoli

import dev.kord.common.annotation.KordPreview
import dev.kord.core.Kord
import dev.kord.core.event.interaction.InteractionCreateEvent
import dev.kord.core.on
import dev.kord.gateway.Intent
import dev.kord.gateway.Intents
import dev.kord.gateway.PrivilegedIntent
import dev.mirror.kt.kotoli.command.onRoleInfo
import dev.mirror.kt.kotoli.command.onRoleRank
import kotlinx.coroutines.runBlocking

@OptIn(KordPreview::class, PrivilegedIntent::class)
suspend fun main() {
    val client = Kord(System.getenv("DISCORD_TOKEN")) {
        intents = Intents(
            Intent.GuildMembers
        )
    }

    client.on<InteractionCreateEvent> {
        when(interaction.command.name) {
            "roleinfo" -> onRoleInfo()
            "rolerank" -> onRoleRank()
        }
    }

    Runtime.getRuntime().addShutdownHook(Thread {
        runBlocking {
            client.logout()
        }
    })

    client.login()
}
