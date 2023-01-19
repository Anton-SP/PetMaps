package com.example.petmaps.data.repo

import com.example.petmaps.data.db.MarkEntity

interface MarkRepo {

    fun getMarkers(): List<MarkEntity>
}