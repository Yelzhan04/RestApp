package food.plugins

//import food.controller.orderRoute
//import food.controller.productOrderRoutes
import food.controller.orderRoute
import food.controller.productOrderRoutes
//import food.controller.productOrderRoutes
import food.controller.productRoutes
import food.controller.userRoutes
import io.ktor.routing.*
import io.ktor.application.*
import io.ktor.response.*

fun Application.configureRouting() {

    routing {
        get("/") {
            call.respondText("Hello World from routing!")
        }
        userRoutes()
        productRoutes()
        orderRoute()
        productOrderRoutes()

    }
}
