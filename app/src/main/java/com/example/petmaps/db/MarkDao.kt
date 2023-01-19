package com.example.petmaps.db

import androidx.room.*

interface MarkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMark(mark:MarkEntity):Long

    @Query("SELECT * FROM markers ORDER BY id DESC")
    fun getMarkerList():List<MarkEntity>

    @Delete
    suspend fun delete(mark: MarkEntity): Int

    /*@Upsert
    suspend fun insertMark(mark:MarkEntity):Long*/
}