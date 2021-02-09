package dev.mirror.kt.kotoli.register

import dev.kord.common.annotation.KordPreview
import dev.kord.common.entity.Snowflake
import dev.kord.core.Kord
import dev.kord.core.behavior.createApplicationCommand

@OptIn(KordPreview::class)
suspend fun main() {
    val client = Kord(System.getenv("DISCORD_TOKEN"))
    client.getGuild(Snowflake(System.getenv("GUILD_ID")))
        ?.run {
            createApplicationCommand("roleinfo", "Get role information") {
                role("role", "The role which you want to get information") {
                    required = true
                }
            }

            createApplicationCommand("rolerank", "Get count of roles rankings")
        }

    client.login()
}