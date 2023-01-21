package com.example.petmaps.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    version = 1,
    exportSchema = true,
    entities = [
        MarkEntity::class
    ]
)

abstract class MarkersDatabase : RoomDatabase() {
    abstract fun getMarkDao(): MarkDao

    companion object {
        private var INSTANCE: MarkersDatabase? = null

        fun getDatabase(context: Context): MarkersDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MarkersDatabase::class.java,
                    "markers_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}

