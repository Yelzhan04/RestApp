package food.service


import food.data.models.Product
import food.data.models.Products
import food.data.tables.User
import food.data.tables.Users
import food.data.tables.Users.address
import food.data.tables.Users.city
import food.data.tables.Users.lastName
import food.db.DbSettings.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class ProductService {

    suspend fun getAllProducts():List<Products?> = dbQuery {
        Product.selectAll().map { rowToProduct(it) }
    }

    suspend fun addProducts(products:Products){
        dbQuery {
            Product.insert { pr ->
                pr[Product.id] = products.id
                pr[Product.title] = products.title
                pr[Product.description] = products.description

            }
        }
    }

    suspend fun getProductById(id: Int): Products? = dbQuery {
        Product.select {
            (Product.id eq (id))
        }.mapNotNull { rowToProduct(it) }
            .singleOrNull()
    }

    suspend fun deleteProductById(id: Int) = dbQuery {
        Product.deleteWhere { Product.id eq id } > 0
    }

    suspend fun updateProductById(product: Products,id:Int) = dbQuery {
        Product.update({Product.id eq id}){
            it[title] = product.title
            it[description] = product.description
        }
    }




    private fun rowToProduct(row: ResultRow?):Products?{
        if (row==null){
            return null
        }
        return Products(
            id = row[Product.id].value,
            title = row[Product.title] ,
            description = row[Product.description]
        )
    }
}