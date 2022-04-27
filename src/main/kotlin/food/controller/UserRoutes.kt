package food.controller

import food.service.UserService
import food.data.tables.User
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

val userControl = UserService()
fun Route.userRoutes(){
    route("api/customers"){
        get {
            val allCustomers = userControl.getAllCustomers()
            call.response.status(HttpStatusCode.OK)
            call.respond(mapOf("Customers" to allCustomers))
        }
        post {
            val addCustomer = call.receive<User>()
            userControl.addUser(addCustomer)
            call.respond(HttpStatusCode.Accepted )
            val widget = call.receive<NewWidget>()
        }
    }
}