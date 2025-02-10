package org.access.managementsystempos.domain.models.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "preferences")
data class Preference(
    @PrimaryKey val key: String,
    val value: String
)