package com.example.petmaps.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.petmaps.utils.CoordinatesConverter
import com.google.android.gms.maps.model.LatLng

@Entity(tableName = "markers")
data class MarkEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long,

    var lat: Double,

    var lng: Double,

    var note: String?
)
