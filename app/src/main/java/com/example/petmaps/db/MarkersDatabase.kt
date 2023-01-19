package com.example.petmaps.db

import androidx.room.Database
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
}