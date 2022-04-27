package food.service


import food.data.models.Product
import food.data.models.Products
import food.db.DbSettings.dbQuery
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll

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