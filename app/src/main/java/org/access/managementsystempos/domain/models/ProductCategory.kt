package org.access.managementsystempos.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity(tableName = "product_categories")
@Serializable
data class ProductCategory(
    @PrimaryKey val id: Int,
    val name: String,
    @SerialName("added_by") val addedBy: String,
    @SerialName("created_at") val createdAt: String,
    @SerialName("updated_at") val updatedAt: String,
    @SerialName("updated_by") val updatedBy: String? = null,
    @SerialName("deleted_by") val deletedBy: String? = null,
    @SerialName("deleted_at") val deletedAt: String? = null
)