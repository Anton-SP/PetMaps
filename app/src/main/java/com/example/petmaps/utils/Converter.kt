package com.example.petmaps.utils

import com.example.petmaps.data.Mark
import com.example.petmaps.data.db.MarkEntity
import com.google.android.gms.maps.model.LatLng

fun MarkEntity.toMark() = Mark(
    id = id,
    coordinates = LatLng(lat,lng),
    note = note
)

fun Mark.toEntity() = MarkEntity(
    id = id,
    lat= coordinates.latitude,
    lng = coordinates.longitude,
    note = note
)
