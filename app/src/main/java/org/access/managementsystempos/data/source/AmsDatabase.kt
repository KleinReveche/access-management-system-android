package org.access.managementsystempos.data.source

import androidx.room.Database
import androidx.room.RoomDatabase
import org.access.managementsystempos.data.source.dao.PreferenceDao
import org.access.managementsystempos.data.source.dao.ProductCategoryDao
import org.access.managementsystempos.data.source.dao.ProductDao
import org.access.managementsystempos.domain.models.db.Preference
import org.access.managementsystempos.domain.models.db.Product
import org.access.managementsystempos.domain.models.db.ProductCategory

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