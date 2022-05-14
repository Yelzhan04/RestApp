package food.controller


import food.data.models.OrderDetails
import food.service.OrderDetailService
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

val productOrderControl = OrderDetailService()

fun Route.productOrderRoutes(){
    route("api/products/order"){
        get {
            val allProductsOrder = productOrderControl.getAllOrderDetails()
            call.response.status(HttpStatusCode.OK)
            call.respond(mapOf("TotalOrder" to allProductsOrder))
        }

        post {
            val addProductOrder = call.receive<OrderDetails>()
            productOrderControl.addOrdersDetails(addProductOrder)
            call.respond(HttpStatusCode.Accepted )
        }

        get("{id}") {

            val productOrderId = productOrderControl.getOrderDetailsById(call.parameters["id"]?.toInt()!!)
            if (productOrderId == null) {
                call.respond(HttpStatusCode.NotFound)
            } else {
                call.respond(productOrderId)
            }
        }

        delete("{id}") {
            val removed = productOrderControl.deleteOrderDetailsById(call.parameters["id"]?.toInt()!!)
            if (removed){
                call.respond(HttpStatusCode.OK)
            }
            else{
                call.respond(HttpStatusCode.NotFound)
            }
        }

//        put("{id}"){
//            val id =  call.parameters["id"]?.toInt()!!
//            val product= call.receive<Products>()
//            productOrderControl.updateProductsOrder(product,id)
//            call.respond(HttpStatusCode.OK)
//        }



    }
}