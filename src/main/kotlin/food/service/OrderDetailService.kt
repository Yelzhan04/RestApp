package food.service

import food.data.models.*
import food.data.models.OrderDetail.unitPrice
import food.db.DbSettings
import food.db.DbSettings.dbQuery
import org.jetbrains.exposed.sql.*

class OrderDetailService {
    suspend fun getAllOrderDetails():List<OrderDetails?> = dbQuery {
        OrderDetail.selectAll().map { rowToOrderDetail(it) }
    }

    suspend fun addOrdersDetails(orderDetail: OrderDetails){
        dbQuery {
            OrderDetail.insert { or ->
                or[OrderDetail.id] = orderDetail.id
                or[OrderDetail.orderID] = orderDetail.orderId
                or[OrderDetail.productsQuantity] = orderDetail.productsQuantity
                or[OrderDetail.unitPrice] = orderDetail.unitPrice
            }
        }
    }

    suspend fun getOrderDetailsById(id: Int): OrderDetails? = DbSettings.dbQuery {
        Orders.select {
            (Orders.id eq (id))
        }.mapNotNull { rowToOrderDetail(it) }
            .singleOrNull()
    }

    suspend fun deleteOrderDetailsById(id: Int) = dbQuery {
        OrderDetail.deleteWhere { OrderDetail.id eq id } > 0
    }

    suspend fun updateOrderById(order: Order, id:Int) = DbSettings.dbQuery {
        Orders.update({ Orders.id eq id }) {

        }
    }
    fun rowToOrderDetail(row:ResultRow?):OrderDetails?{
        if (row==null){ 
            return null
        }
        return OrderDetails(
            id = row[OrderDetail.id].value,
            orderId = row[OrderDetail.orderID].value,
            productsQuantity = row[OrderDetail.productsQuantity],
            unitPrice = row[OrderDetail.unitPrice]

        )
    }

}