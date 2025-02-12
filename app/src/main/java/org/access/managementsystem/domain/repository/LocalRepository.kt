package org.access.managementsystem.domain.repository

import org.access.managementsystem.domain.models.Preference
import org.access.managementsystem.domain.models.PreferenceKey
import org.access.managementsystem.domain.models.Product
import org.access.managementsystem.domain.models.ProductCategory

interface LocalRepository {
    suspend fun getPreferences(): List<Preference>
    suspend fun getPreference(key: PreferenceKey): Preference?
    suspend fun savePreference(preference: Preference)
    suspend fun deletePreference(preference: Preference)

    suspend fun getProducts(): List<Product>
    suspend fun getProduct(id: Int): Product?
    suspend fun getProduct(name: String): Product?
    suspend fun getProductsByCategory(categoryId: Int): List<Product>
    suspend fun saveProduct(product: Product)
    suspend fun deleteProduct(product: Product)

    suspend fun getProductCategories(): List<ProductCategory>
    suspend fun getProductCategory(id: Int): ProductCategory?
    suspend fun getProductCategory(name: String): ProductCategory?
    suspend fun saveProductCategory(productCategory: ProductCategory)
    suspend fun deleteProductCategory(productCategory: ProductCategory)
}