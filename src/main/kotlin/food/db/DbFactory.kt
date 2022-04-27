package food.db

import com.typesafe.config.ConfigFactory
import food.data.models.Orders
import food.data.models.Product
import food.data.models.Product_Orders
import food.data.tables.Users
import io.ktor.config.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object DbSettings {
    private val appConfig = HoconApplicationConfig(ConfigFactory.load())
    private var dbUrl = appConfig.property("jdbc.url").getString()
    private var dbUser = appConfig.property("jdbc.username").getString()
    private var dbPassword = appConfig.property("jdbc.password").getString()

    fun init(dbUrl: String, dbUser: String, dbPassword: String) {
        DbSettings.dbUrl = dbUrl
        DbSettings.dbUser = dbUser
        DbSettings.dbPassword = dbPassword
        pgConnection()
        transaction {
            addLogger(StdOutSqlLogger)
            SchemaUtils.create(Users,Product,Product_Orders,Orders)
        }
    }

    private fun pgConnection() = Database.connect(
        url = dbUrl,
        driver = "org.postgresql.Driver",
        user = dbUser,
        password = dbPassword
    )
    suspend fun <T> dbQuery(block: () -> T): T =
        withContext(Dispatchers.IO){
            transaction {
                block()
            }
        }
}