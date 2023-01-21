package com.example.petmaps.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "markers")
data class MarkEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long,

    var lat: Double,

    var lng: Double,

    var note: String?
)
