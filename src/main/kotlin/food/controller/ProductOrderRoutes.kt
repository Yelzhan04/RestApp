package food.controller


import food.data.models.Product_Order
import food.data.models.Products
import food.service.ProductOrderService
import food.service.ProductService
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

val productOrderControl = ProductOrderService()

fun Route.productOrderRoutes(){
    route("api/products/order"){
        get {
            val allProductsOrder = productOrderControl.getAllProductsOrder()
            call.response.status(HttpStatusCode.OK)
            call.respond(mapOf("TotalOrder" to allProductsOrder))
        }

        post {
            val addProductOrder = call.receive<Product_Order>()
            productOrderControl.addProductsOrder(addProductOrder)
            call.respond(HttpStatusCode.Accepted )
        }

        get("{id}") {

            val productOrderId = food.controller.productOrderControl.getProductsOrderById(call.parameters["id"]?.toInt()!!)
            if (productOrderId == null) {
                call.respond(HttpStatusCode.NotFound)
            } else {
                call.respond(productOrderId)
            }
        }

        delete("{id}") {
            val removed = productOrderControl.deleteProductsOrder(call.parameters["id"]?.toInt()!!)
            if (removed){
                call.respond(HttpStatusCode.OK)
            }
            else{
                call.respond(HttpStatusCode.NotFound)
            }
        }

        put("{id}"){
            val id =  call.parameters["id"]?.toInt()!!
            val product= call.receive<Product_Order>()
            productOrderControl.updateProductsOrder(product,id)
            call.respond(HttpStatusCode.OK)
        }



    }
}