package org.access.managementsystempos.domain.models

data class Product(
    val name: String,
    val price: Float,
    val productCategory: ProductCategory,
    val imageId: Int? = null
)
