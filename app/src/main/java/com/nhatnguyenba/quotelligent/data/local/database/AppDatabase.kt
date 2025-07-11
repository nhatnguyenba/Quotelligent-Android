package com.nhatnguyenba.quotelligent.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nhatnguyenba.quotelligent.data.local.dao.CollectionDao
import com.nhatnguyenba.quotelligent.data.local.dao.QuoteCollectionCrossRefDao
import com.nhatnguyenba.quotelligent.data.local.dao.QuoteDao
import com.nhatnguyenba.quotelligent.data.local.entities.CollectionEntity
import com.nhatnguyenba.quotelligent.data.local.entities.QuoteCollectionCrossRef
import com.nhatnguyenba.quotelligent.data.local.entities.QuoteEntity

@Database(
    entities = [
        QuoteEntity::class,
        CollectionEntity::class,
        QuoteCollectionCrossRef::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun quoteDao(): QuoteDao
    abstract fun collectionDao(): CollectionDao
    abstract fun crossRefDao(): QuoteCollectionCrossRefDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "quotelligent_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}