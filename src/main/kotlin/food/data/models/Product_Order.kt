package food.data.models

import food.data.models.Product_Orders.customer_id
import food.data.tables.UserEntity
import food.data.tables.Users
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object Product_Orders: IntIdTable("Product_Order"){
    val customer_id = reference("Customer",Users)
    val order_id = reference("Order_Id",Orders)
}
class Product_OrdersEntity(id: EntityID<Int>): IntEntity(id){
    companion object:IntEntityClass<OrdersEntity>(Product_Orders)
    var customer_id by UserEntity referencedOn Product_Orders.customer_id
    var order_id by OrdersEntity referencedOn Product_Orders.order_id

    override fun toString(): String = "Product_Orders($customer_id,$order_id)"

}
@Serializable
data class Product_Order(
    val id:Int,
    val order_id:Int,
    val customer_id:Int,

)

