package food.service
import food.data.models.Product
import food.data.models.Products
import food.db.DbSettings.dbQuery
import food.data.tables.User
import food.data.tables.Users
import org.jetbrains.exposed.sql.*

class UserService{



    suspend fun getAllUsers():List<User?> = dbQuery {
        Users.selectAll().map { rowToUser(it) }
    }

    suspend fun addUser(user: User){
        dbQuery {
            Users.insert { ur->
                ur[Users.id] = user.id
                ur[Users.firstName] = user.firstName
                ur[Users.lastName] = user.lastName
                ur[Users.address] = user.address
                ur[Users.city] = user.city
            }
        }
    }
    suspend fun getUserById(id: Int): User? = dbQuery {
        Users.select {
            (Users.id eq (id))
        }.mapNotNull { rowToUser(it) }
            .singleOrNull()
    }

    suspend fun deleteUserById(id: Int) = dbQuery {
        Users.deleteWhere { Users.id eq id } > 0
    }

    suspend fun updateUserById(user: User,id:Int) = dbQuery {
        Users.update({Users.id eq id}){
            it[firstName] = user.firstName
            it[lastName] = user.lastName
            it[address] = user.address
            it[city]= user.city
        }
    }



    private fun rowToUser(row: ResultRow?):User?{
        if (row==null){
            return null
        }
        return User(
            id =row[Users.id].value,
            firstName = row[Users.firstName],
            lastName = row[Users.lastName],
            address = row[Users.address],
            city = row[Users.city]
        )
    }
}