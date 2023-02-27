package com.yubstore.piking

import android.app.Application
import com.yubstore.piking.data.AppItemRoomDatabase
import dagger.hilt.android.HiltAndroidApp

//@HiltAndroidApp
class PikingApplication: Application() {
    val appDataBase: AppItemRoomDatabase by lazy { AppItemRoomDatabase.getDatabase(this) }
}