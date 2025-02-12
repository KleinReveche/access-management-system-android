package org.access.managementsystempos.features.common

import kotlinx.serialization.json.Json
import org.access.managementsystempos.domain.models.Product
import org.access.managementsystempos.domain.models.ProductCategory
import org.access.managementsystempos.domain.repository.LocalRepository
import org.access.managementsystempos.domain.repository.RemoteRepository

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