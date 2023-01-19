package com.example.petmaps.data.repo

import com.example.petmaps.data.Mark

interface MarkRepo {

   suspend fun create(mark:Mark): Long
   suspend fun getMarkers(): List<Mark>
}