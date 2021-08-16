package com.example.myapplication.room.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.room.entity.PageEntity

@Dao
interface PageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(keys: PageEntity)

    @Query("SELECT * FROM ${PageEntity.TABLE_NAME} WHERE :page LIKE page")
    suspend fun remoteKey(page: Int): PageEntity

    @Query("DELETE FROM ${PageEntity.TABLE_NAME}")
    suspend fun delete()
}