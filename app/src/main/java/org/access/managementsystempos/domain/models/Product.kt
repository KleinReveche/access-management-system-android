package org.access.managementsystempos.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity(tableName = "products")
@Serializable
data class Product(
    @PrimaryKey val id: Int,
    val name: String,
    val price: Float,
    @SerialName("added_by") val addedBy: String,
    @SerialName("category_id") val categoryId: Int,
    val image: String,
    @SerialName("created_at") val createdAt: String,
    @SerialName("updated_at") val updatedAt: String,
    @SerialName("updated_by") val updatedBy: String? = null,
    @SerialName("deleted_by") val deletedBy: String? = null,
    @SerialName("deleted_at") val deletedAt: String? = null
)