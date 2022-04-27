package food.controller

import food.data.models.Order
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
            orderControl.addOrders(addOrder, Product)
        }
    }
}