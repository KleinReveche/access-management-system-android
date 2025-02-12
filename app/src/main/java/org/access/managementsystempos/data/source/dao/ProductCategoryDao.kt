package org.access.managementsystempos.data.source.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import org.access.managementsystempos.domain.models.ProductCategory

@Dao
interface ProductCategoryDao {
    @Query("SELECT * FROM product_categories")
    suspend fun getProductCategories(): List<ProductCategory>

    @Query("SELECT * FROM product_categories WHERE id = :id")
    suspend fun getProductCategory(id: Int): ProductCategory?

    @Query("SELECT * FROM product_categories WHERE name = :name")
    suspend fun getProductCategory(name: String): ProductCategory?

    @Upsert
    suspend fun saveProductCategory(productCategory: ProductCategory)

    @Delete
    suspend fun deleteProductCategory(productCategory: ProductCategory)
}