package food.data.models

import food.data.tables.UserEntity
import food.data.tables.Users
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.DateTimeException
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZonedDateTime

object Orders:IntIdTable("Order"){

    val userID = reference("user",Users.id)
    val orderDate: Column<LocalDateTime> = datetime("order_date")
    val totalPrice = integer("order_total_price")
    val productQuantity = integer("product_amount")

}

class OrdersEntity(id:EntityID<Int>):IntEntity(id){

    companion object:IntEntityClass<OrdersEntity>(Orders)
    var userID by UserEntity referencedOn Orders.userID
    var orderDate by Orders.orderDate
    var totalPrice by Orders.totalPrice
    var productQuantity by Orders.productQuantity
    override fun toString(): String = "Orders($userID,$orderDate,$totalPrice,$productQuantity)"

}

@Serializable
data class Order(
    val id:Int,
    val userId:Int,
    val orderDate:Long? = null,
    val totalPrice:Int,
    val productQuantity:Int,

)
