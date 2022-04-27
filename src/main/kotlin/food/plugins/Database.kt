package food.plugins

import food.db.DbSettings
import io.ktor.application.*

fun Application.connectDatabase(){
    val url = environment.config.property("jdbc.url").getString()
    val username = environment.config.property("jdbc.username").getString()
    val password = environment.config.property("jdbc.password").getString()

    DbSettings.init(url,username,password)
}