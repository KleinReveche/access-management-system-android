package org.access.managementsystem.data.source.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import org.access.managementsystem.domain.models.Product

@Dao
interface ProductDao {
    @Query("SELECT * FROM products")
    suspend fun getProducts(): List<Product>

    @Query("SELECT * FROM products WHERE id = :id")
    suspend fun getProduct(id: Int): Product?

    @Query("SELECT * FROM products WHERE name = :name")
    suspend fun getProduct(name: String): Product?

    @Query("SELECT * FROM products WHERE categoryId = :categoryId")
    suspend fun getProductsByCategory(categoryId: Int): List<Product>

    @Upsert
    suspend fun saveProduct(product: Product)

    @Delete
    suspend fun deleteProduct(product: Product)
}