package org.access.managementsystem.features.common

import kotlinx.serialization.json.Json
import org.access.managementsystem.domain.models.Product
import org.access.managementsystem.domain.models.ProductCategory
import org.access.managementsystem.domain.repository.LocalRepository
import org.access.managementsystem.domain.repository.RemoteRepository

suspend fun getProducts(remoteRepository: RemoteRepository, localRepository: LocalRepository) {
    val (fetchSuccess, products, productCategories) = remoteRepository.getProducts()

    if (fetchSuccess) {
        val productsList = Json.decodeFromString<List<Product>>(products.message)
        val productCategoriesList =
            Json.decodeFromString<List<ProductCategory>>(productCategories.message)

        for (product in productsList) {
            localRepository.saveProduct(product)
        }

        for (productCategory in productCategoriesList) {
            localRepository.saveProductCategory(productCategory)
        }
    }
}