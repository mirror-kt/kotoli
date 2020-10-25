package dev.mirror.kt.kotoli

import dev.mirror.kt.kotoli.command.pingPong
import dev.mirror.kt.kotoli.framework.Bot
import net.dv8tion.jda.api.requests.GatewayIntent

fun main() {
    val bot = Bot.create(System.getenv("DISCORD_TOKEN"), listOf(GatewayIntent.GUILD_MESSAGES)).apply {
        pingPong()
    }

    bot.start()
}
