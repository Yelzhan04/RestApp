package food.service
import food.data.models.Product
import food.data.models.Products
import food.db.DbSettings.dbQuery
import food.data.tables.User
import food.data.tables.Users
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll

class UserService{



    suspend fun getAllCustomers():List<User?> = dbQuery {
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