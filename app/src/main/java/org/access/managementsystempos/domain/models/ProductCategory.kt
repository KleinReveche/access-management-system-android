package org.access.managementsystempos.domain.models

data class ProductCategory(
    val name: String,
    val parentCategory: ProductCategory?
)
