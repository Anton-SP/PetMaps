package com.example.petmaps.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng

@Entity(tableName = "markers")
data class MarkEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    var coordinates: LatLng,
    var note: String?
)
