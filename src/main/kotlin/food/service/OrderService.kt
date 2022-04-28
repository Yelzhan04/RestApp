package food.service

import food.data.models.Order
import food.data.models.Orders
import food.data.models.Orders.product
import food.data.models.Product
import food.data.models.Product.description
import food.data.models.Product.title
import food.data.models.Products
import food.db.DbSettings.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class OrderService {

    suspend fun getAllOrders():List<Order?> = dbQuery {
        Orders.selectAll().map { rowToOrder(it) }
    }

    suspend fun addOrders(order: Order){
        dbQuery{
            Orders.insert { or ->
                or[Orders.id] = order.id
                or[Orders.product] = order.product

            }

        }
    }

    suspend fun getOrderById(id: Int): Order? = dbQuery {
        Orders.select {
            (Orders.id eq (id))
        }.mapNotNull { rowToOrder(it) }
            .singleOrNull()
    }

    suspend fun deleteOrderById(id: Int) = dbQuery {
        Orders.deleteWhere { Orders.id eq id } > 0
    }

    suspend fun updateOrderById(order: Order, id:Int) = dbQuery {
        Orders.update({ Orders.id eq id}){
            it[product] =order.id

        }
    }

    private fun rowToOrder(row: ResultRow?): Order?{
        if (row==null){
            return null
        }
        return Order(
            id = row[Orders.id].value,
            product = row[Orders.product].value
        )
    }
}