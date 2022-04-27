package food.service

import food.data.models.Order
import food.data.models.Orders
import food.data.models.Product
import food.data.models.Products
import food.data.tables.User
import food.data.tables.Users
import food.db.DbSettings
import food.db.DbSettings.dbQuery
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll

class OrderService {

    suspend fun getAllOrders():List<Order?> = dbQuery {
        Orders.selectAll().map { rowToOrder(it) }
    }

    suspend fun addOrders(order: Order,id:Product){
        dbQuery{
            Orders.insert { or ->
                or[Orders.id] = order.id
                or[Orders.product] = id.id

            }

        }
    }

    private fun rowToOrder(row: ResultRow?): Order?{
        if (row==null){
            return null
        }
        return Order(
            id = row[Orders.id].value,
            product = row[Orders.product].toString()
        )
    }
}