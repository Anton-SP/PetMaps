package com.example.petmaps.data.db

import androidx.room.*

@Dao
interface MarkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMark(mark: MarkEntity):Long

    @Query("SELECT * FROM markers ORDER BY id")
    fun getMarkerList():List<MarkEntity>

    @Delete
    suspend fun delete(mark: MarkEntity): Int

    /*@Upsert
    suspend fun insertMark(mark:MarkEntity):Long*/
}