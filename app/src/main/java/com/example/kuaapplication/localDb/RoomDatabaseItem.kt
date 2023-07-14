package com.example.kuaapplication.localDb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DatabasePermohonan::class, PengantinWanita::class, Wali::class, Saksi::class],
    version = 3, exportSchema = true)
abstract class RoomDatabaseItem: RoomDatabase() {

    abstract fun dao(): PetDao

    companion object{
        @Volatile
        private var INSTANCE: RoomDatabaseItem? = null

        fun getDatabase(context: Context): RoomDatabaseItem {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RoomDatabaseItem::class.java,
                    "database"
                )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}