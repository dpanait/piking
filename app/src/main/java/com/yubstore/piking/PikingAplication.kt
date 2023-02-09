package com.yubstore.piking

import android.app.Application
import com.yubstore.piking.data.AppItemRoomDatabase

class PikingApplication: Application() {
    val appDataBase: AppItemRoomDatabase by lazy { AppItemRoomDatabase.getDatabase(this) }
}