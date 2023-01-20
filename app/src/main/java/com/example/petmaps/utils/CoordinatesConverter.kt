package com.example.petmaps.utils

import androidx.room.TypeConverter
import com.google.android.gms.maps.model.LatLng
import java.util.Arrays

class CoordinatesConverter {
    @TypeConverter
    fun fromLatLng(coordinates: LatLng): String {
        var lat = coordinates.latitude.toString()
        var lng = coordinates.longitude.toString()
        return lat+","+lng
    }

    @TypeConverter
    fun toLatLng(coordinates: String): LatLng {
        var list =(coordinates.split(","))

        return LatLng(
            list[0].toDouble(),
            list[1].toDouble()
        )
    }
}