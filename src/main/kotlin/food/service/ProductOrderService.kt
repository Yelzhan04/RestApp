package food.service

import food.data.models.Product_Order
import food.data.models.Product_Orders
import food.db.DbSettings.dbQuery
import org.jetbrains.exposed.sql.*

class ProductOrderService {
    suspend fun getAllProductsOrder():List<Product_Order?> = dbQuery {
        Product_Orders.selectAll().map { rowToProductOrder(it) }
    }

    suspend fun addProductsOrder(products_order: Product_Order){
        dbQuery {
            Product_Orders.insert { pr ->
                pr[id] = products_order.id
                pr[order_id] = products_order.order_id
                pr[customer_id] = products_order.customer_id

            }
        }
    }

    suspend fun getProductsOrderById(id: Int): Product_Order? = dbQuery {
        Product_Orders.select {
            (Product_Orders.id eq (id))
        }.mapNotNull { rowToProductOrder(it) }
            .singleOrNull()
    }

    suspend fun deleteProductsOrder(id: Int) = dbQuery {
        Product_Orders.deleteWhere { Product_Orders.id eq id } > 0
    }

    suspend fun updateProductsOrder(productOrder: Product_Order, id:Int) = dbQuery {
        Product_Orders.update({ Product_Orders.id eq id }) {
            it[customer_id] = productOrder.customer_id
            it[order_id] = productOrder.order_id
        }
    }




    private fun rowToProductOrder(row: ResultRow?): Product_Order?{
        if (row==null){
            return null
        }
        return Product_Order(
            id = row[Product_Orders.id].value,
            order_id = row[Product_Orders.order_id].value ,
            customer_id = row[Product_Orders.customer_id].value
        )
    }
}