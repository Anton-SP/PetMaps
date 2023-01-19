package com.example.petmaps.data.repo

import com.example.petmaps.data.db.MarkDao
import com.example.petmaps.data.db.MarkEntity

class LocalRepo(private val markDao: MarkDao) : MarkRepo {
    override fun getMarkers(): List<MarkEntity> =
        markDao.getMarkerList()
}
