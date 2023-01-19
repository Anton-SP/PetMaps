package com.example.petmaps.data.repo

import com.example.petmaps.data.Mark
import com.example.petmaps.data.db.MarkDao
import com.example.petmaps.utils.toMark

class LocalRepo(private val markDao: MarkDao) : MarkRepo {
    override suspend fun getMarkers(): List<Mark> =
        markDao.getMarkerList().map { entity ->
            entity.toMark()
        }
}
