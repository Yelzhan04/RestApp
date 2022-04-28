package food.controller

import food.data.models.Product_Orders.customer_id
import food.data.models.Products
import food.data.tables.User
import food.service.ProductService
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

val productControl = ProductService()

fun Route.productRoutes(){
    route("api/products"){
        get {
            val allProducts = productControl.getAllProducts()
            call.response.status(HttpStatusCode.OK)
            call.respond(mapOf("Products" to allProducts))
        }

        post {
            val addProduct = call.receive<Products>()
            productControl.addProducts(addProduct)
            call.respond(HttpStatusCode.Accepted )
        }

        get("{id}") {

            val product_id = productControl.getProductById(call.parameters["id"]?.toInt()!!)
            if (product_id == null) {
                call.respond(HttpStatusCode.NotFound)
            } else {
                call.respond(product_id)
            }
        }

        delete("{id}") {
            val removed = productControl.deleteProductById(call.parameters["id"]?.toInt()!!)
            if (removed){
                call.respond(HttpStatusCode.OK)
            }
            else{
                call.respond(HttpStatusCode.NotFound)
            }
        }

        put("{id}"){
            val id =  call.parameters["id"]?.toInt()!!
            val product= call.receive<Products>()
            productControl.updateProductById(product,id)
            call.respond(HttpStatusCode.OK)
        }



    }
}