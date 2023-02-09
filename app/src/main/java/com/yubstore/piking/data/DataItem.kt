package com.yubstore.piking.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "app")
data class AppItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "environment")
    val itemEnvironment: String,
    @ColumnInfo(name = "version")
    val itemVersion: String
)
