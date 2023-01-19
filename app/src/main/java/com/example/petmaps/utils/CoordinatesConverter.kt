package com.example.petmaps.utils

import androidx.room.TypeConverter
import com.google.android.gms.maps.model.LatLng
import java.util.Arrays

class CoordinatesConverter {
    @TypeConverter
    fun fromLatLng(coordinates: LatLng):String {
        return coordinates.toString()
    }

    @TypeConverter
    fun toLatLng (coordinates: String):LatLng {
        var list =  listOf(coordinates.split(","))
        return LatLng(
            list[0] as Double,
            list[1] as Double
        )
    }
}