package food.data.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Table

object OrderDetail: IntIdTable("Product_Order"){

    val orderID = reference("Order_Id",Orders.id)
    val productID = reference("products",Product.id)
    val productsQuantity = integer("product_amount")
    val unitPrice = integer("price")
}

class OrderDetailEntity(id: EntityID<Int>): IntEntity(id){

    companion object:IntEntityClass<OrderDetailEntity>(OrderDetail)

    var orderID by OrdersEntity referencedOn OrderDetail.orderID
    var productsQuantity by OrderDetail.productsQuantity
    val products by ProductsEntity via OrderDetail
    var unitPrice by OrderDetail.unitPrice
    override fun toString(): String = "OrderDetail($orderID,$productsQuantity,$products,$unitPrice)"

}

@Serializable
data class OrderDetails(
    val id:Int,
    val orderId:Int,
    val productsQuantity:Int,
    val products: List<Products>? = null,
    val unitPrice:Int

)

