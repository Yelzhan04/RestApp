package food.data.tables

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Users : IntIdTable("user") {
    val firstName = varchar("firstName", 255)
    val lastName = varchar("lastName", 255)
    val address = varchar("Address",255)
    val city = varchar("City",255)
}

class UserEntity(id: EntityID<Int>):IntEntity(id){
    companion object: IntEntityClass<UserEntity>(Users)
    var firstName by Users.firstName
    var lastname by Users.lastName
    var address by Users.address
    var city by Users.city

    override fun toString(): String = "User($firstName,$lastname,$address,$city)"

}

@Serializable
data class User(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val address:String,
    val city:String
)




