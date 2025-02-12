package org.access.managementsystempos.data.source.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import org.access.managementsystempos.domain.models.Preference

@Dao
interface PreferenceDao {
    @Query("SELECT * FROM preferences")
    suspend fun getPreferences(): List<Preference>

    @Query("SELECT * FROM preferences WHERE `key` = :key")
    suspend fun getPreference(key: String): Preference?

    @Upsert
    suspend fun savePreference(preference: Preference)

    @Delete
    suspend fun deletePreference(preference: Preference)
}