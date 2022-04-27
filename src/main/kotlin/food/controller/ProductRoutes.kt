package food.controller

import food.data.models.Products
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
    }
}