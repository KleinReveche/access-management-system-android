package org.access.managementsystem.data.source

import androidx.room.Database
import androidx.room.RoomDatabase
import org.access.managementsystem.data.source.dao.PreferenceDao
import org.access.managementsystem.data.source.dao.ProductCategoryDao
import org.access.managementsystem.data.source.dao.ProductDao
import org.access.managementsystem.domain.models.Preference
import org.access.managementsystem.domain.models.Product
import org.access.managementsystem.domain.models.ProductCategory

@Database(
    entities = [Preference::class, ProductCategory::class, Product::class],
    version = 1
)
abstract class AmsDatabase : RoomDatabase() {
    abstract fun preferenceDao(): PreferenceDao
    abstract fun productCategoryDao(): ProductCategoryDao
    abstract fun productDao(): ProductDao

    companion object {
        const val DATABASE_NAME = "ams_database"
    }
}