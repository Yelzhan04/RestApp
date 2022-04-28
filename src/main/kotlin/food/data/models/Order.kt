package food.data.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Orders:IntIdTable("Order"){
    val product = reference("products",Product)
}
class OrdersEntity(id:EntityID<Int>):IntEntity(id){
    companion object:IntEntityClass<OrdersEntity>(Orders)
    var product by ProductsEntity referencedOn Orders.product
    override fun toString(): String = "Orders($product)"
}
@Serializable
data class Order(
    val id:Int,
    val product: Int

)
