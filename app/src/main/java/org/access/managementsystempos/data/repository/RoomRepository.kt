package org.access.managementsystempos.data.repository

import org.access.managementsystempos.data.source.AmsDatabase
import org.access.managementsystempos.domain.models.Preference
import org.access.managementsystempos.domain.models.PreferenceKey
import org.access.managementsystempos.domain.models.Product
import org.access.managementsystempos.domain.models.ProductCategory
import org.access.managementsystempos.domain.repository.LocalRepository

class RoomRepository(database: AmsDatabase) : LocalRepository {
    private val preferenceDao = database.preferenceDao()
    private val productDao = database.productDao()
    private val productCategoryDao = database.productCategoryDao()

    override suspend fun getPreferences(): List<Preference> {
        return preferenceDao.getPreferences()
    }

    override suspend fun getPreference(key: PreferenceKey): Preference? {
        return preferenceDao.getPreference(key.name)
    }

    override suspend fun savePreference(preference: Preference) {
        preferenceDao.savePreference(preference)
    }

    override suspend fun deletePreference(preference: Preference) {
        preferenceDao.deletePreference(preference)
    }

    override suspend fun getProducts(): List<Product> {
        return productDao.getProducts()
    }

    override suspend fun getProduct(id: Int): Product? {
        return productDao.getProduct(id)
    }

    override suspend fun getProduct(name: String): Product? {
        return productDao.getProduct(name)
    }

    override suspend fun getProductsByCategory(categoryId: Int): List<Product> {
        return productDao.getProductsByCategory(categoryId)
    }

    override suspend fun saveProduct(product: Product) {
        productDao.saveProduct(product)
    }

    override suspend fun deleteProduct(product: Product) {
        productDao.deleteProduct(product)
    }

    override suspend fun getProductCategories(): List<ProductCategory> {
        return productCategoryDao.getProductCategories()
    }

    override suspend fun getProductCategory(id: Int): ProductCategory? {
        return productCategoryDao.getProductCategory(id)
    }

    override suspend fun getProductCategory(name: String): ProductCategory? {
        return productCategoryDao.getProductCategory(name)
    }

    override suspend fun saveProductCategory(productCategory: ProductCategory) {
        productCategoryDao.saveProductCategory(productCategory)
    }

    override suspend fun deleteProductCategory(productCategory: ProductCategory) {
        productCategoryDao.deleteProductCategory(productCategory)
    }


}