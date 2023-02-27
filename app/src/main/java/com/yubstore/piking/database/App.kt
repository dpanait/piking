package com.yubstore.piking.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Entity(tableName = "app")
data class App (
    @ColumnInfo(name = "enviroment") val enviroment: String,
    @ColumnInfo(name = "version") val version: String,
    @PrimaryKey(autoGenerate = true) var id: Int = 0
)

@Dao
interface AppDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(app: App)

    @Query("SELECT * FROM app ORDER BY id DESC")
    fun getAll(): LiveData<List<App>>

    @Delete
    fun delete(app: App)
}