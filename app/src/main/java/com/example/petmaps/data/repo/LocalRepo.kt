package com.example.petmaps.data.repo

import android.util.Log
import com.example.petmaps.data.Mark
import com.example.petmaps.data.db.MarkDao
import com.example.petmaps.utils.toEntity
import com.example.petmaps.utils.toMark
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalRepo(private val markDao: MarkDao) : MarkRepo {
    override suspend fun create(mark: Mark): Long {
        val markId = markDao.insertMark(mark = mark.toEntity())
        if (markId > 0) {
            return markId
        }
        return TEMPLATE_WAS_NOT_CREATED
    }


    override suspend fun getMarkers(): List<Mark> = withContext(Dispatchers.IO){
        markDao.getMarkerList().map { entity ->
            entity.toMark()
        }
    }


    companion object {
        const val TEMPLATE_WAS_NOT_CREATED = -1L
    }
}
