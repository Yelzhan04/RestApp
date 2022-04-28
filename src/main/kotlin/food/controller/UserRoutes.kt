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
            val allCustomers = userControl.getAllUsers()
            call.response.status(HttpStatusCode.OK)
            call.respond(mapOf("Customers" to allCustomers))
        }
        post {
            val addCustomer = call.receive<User>()
            userControl.addUser(addCustomer)
            call.respond(HttpStatusCode.Accepted )
        }

        get("{id}") {

            val customer_id = userControl.getUserById(call.parameters["id"]?.toInt()!!)
            if (customer_id == null) {
                call.respond(HttpStatusCode.NotFound)
            } else {
                call.respond(customer_id)
            }
        }

        delete("{id}") {
            val removed = userControl.deleteUserById(call.parameters["id"]?.toInt()!!)
            if (removed){
                call.respond(HttpStatusCode.OK)
            }
            else{
                call.respond(HttpStatusCode.NotFound)
            }
        }

        put("{id}"){
            val id =  call.parameters["id"]?.toInt()!!
            val users= call.receive<User>()
            userControl.updateUserById(users,id)
            call.respond(HttpStatusCode.OK)
        }


    }
}