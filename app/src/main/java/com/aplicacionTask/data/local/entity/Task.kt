package com.aplicacionTask.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task")
data class Task(
    @PrimaryKey(autoGenerate = true) val id : Long? = null,
    @ColumnInfo(name = "title") val title : String? = null,
    @ColumnInfo(name = "description") val description : String? = null
)
