package com.yubstore.piking.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities= [App::class], version=1)
abstract class DbDataSource: RoomDatabase() {
    abstract fun appDao(): AppDao
}