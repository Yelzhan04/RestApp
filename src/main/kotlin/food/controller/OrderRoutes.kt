package food.controller

import food.data.models.Order
import food.data.models.Orders.product
import food.data.models.Product
import food.data.models.Products
import food.service.OrderService
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.sql.Table
import javax.swing.text.html.HTML.Tag.P

val orderControl = OrderService()

fun Route.orderRoute(){
    route("api/orders"){
        get {
            val allOrder = orderControl.getAllOrders()
            call.response.status(HttpStatusCode.OK)
            call.respond(mapOf("Orders" to allOrder))
        }

        post {
            val addOrder = call.receive<Order>()
            orderControl.addOrders(addOrder)
        }

        get("{id}") {

            val order_id = orderControl.getOrderById(call.parameters["id"]?.toInt()!!)
            if (order_id== null) {
                call.respond(HttpStatusCode.NotFound)
            } else {
                call.respond(order_id)
            }
        }

        delete("{id}") {
            val removed = orderControl.deleteOrderById(call.parameters["id"]?.toInt()!!)
            if (removed){
                call.respond(HttpStatusCode.OK)
            }
            else{
                call.respond(HttpStatusCode.NotFound)
            }
        }

        put("{id}"){
            val id =  call.parameters["id"]?.toInt()!!
            val order= call.receive<Order>()
            orderControl.updateOrderById(order,id)
            call.respond(HttpStatusCode.OK)
        }

    }
}