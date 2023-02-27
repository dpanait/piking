package com.yubstore.piking.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/**
 * Database access object to access the Inventory database
 */
@Dao
interface AppDao {

    @Query("SELECT * from app ORDER BY environment ASC")
    fun getItems(): LiveData<List<AppItem>>

    @Query("SELECT * from app WHERE id = :id")
    fun getItem(id: Int): Flow<AppItem>

    // Specify the conflict strategy as IGNORE, when the user tries to add an
    // existing Item into the database.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: AppItem)

    @Update
    suspend fun update(item: AppItem)

    @Delete
    suspend fun delete(item: AppItem)
}