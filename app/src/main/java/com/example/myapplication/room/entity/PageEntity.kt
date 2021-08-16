package com.example.myapplication.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myapplication.room.entity.PageEntity.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class PageEntity(
    @PrimaryKey @ColumnInfo(name = "page") val page: Int
) {
    companion object {
        const val TABLE_NAME = "page_table"
    }
}
