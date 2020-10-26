package dev.mirror.kt.kotoli

import dev.mirror.kt.kotoli.command.pingPong
import dev.mirror.kt.kotoli.command.roleInfo
import dev.mirror.kt.kotoli.framework.Bot
import net.dv8tion.jda.api.requests.GatewayIntent

fun main() {
    val bot = Bot.create(
        System.getenv("DISCORD_TOKEN"), listOf(
            GatewayIntent.GUILD_MESSAGES,
            GatewayIntent.GUILD_MEMBERS
        )
    ).apply {
        pingPong()
        roleInfo()
    }

    bot.start()
}
