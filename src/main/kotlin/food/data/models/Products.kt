package food.data.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Product : IntIdTable("products") {
    val title = varchar("product_title",50)
    val description = varchar("product_description",255)

}
class ProductsEntity(id: EntityID<Int>): IntEntity(id){
    companion object:IntEntityClass<ProductsEntity>(Product)
    val title by Product.title
    val description by Product.description
    override fun toString(): String = "Product($title,$description)"



}

@Serializable
data class Products(
    val id:Int,
    val title:String,
    val description:String
)