package food.service

import food.data.models.Order
import food.data.models.Orders

import food.db.DbSettings.dbQuery
import org.jetbrains.exposed.sql.*
import java.time.LocalDateTime
import java.time.ZoneOffset

class OrderService {

    suspend fun getAllOrders():List<Order?> = dbQuery {
        Orders.selectAll().map { rowToOrder(it) }
    }

    suspend fun addOrders(order: Order){
        dbQuery{
            Orders.insert { or ->
                or[Orders.userID] = order.userId
                or[Orders.totalPrice] = order.totalPrice
                or[Orders.productQuantity] = order.productQuantity
                or[Orders.orderDate] = LocalDateTime.now()
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

        }
    }

    private fun rowToOrder(row: ResultRow?): Order?{
        if (row==null){
            return null
        }
        return Order(
            id = row[Orders.id].value,
            userId = row[Orders.userID].value,
            totalPrice = row[Orders.totalPrice],
            productQuantity = row[Orders.productQuantity],
            orderDate = row[Orders.orderDate].getLong(),
            )
    }
    fun LocalDateTime.getLong() =
        this.atZone(ZoneOffset.UTC)?.toInstant()?.toEpochMilli()

}

