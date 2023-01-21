package com.example.petmaps.data

import com.google.android.gms.maps.model.LatLng

data class Mark(
    var id: Long,
    var coordinates: LatLng,
    var note: String?
)