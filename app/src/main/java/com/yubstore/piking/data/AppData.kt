package com.yubstore.piking.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AppData(private val context: Context) {
    // to make sure theres only instance
    companion object{
        private val Context.dataStoree: DataStore<Preferences> by preferencesDataStore("environment")
        val ENVIROMENT_KEY = stringPreferencesKey("app_environment")
    }
    // get saved environmet
    val getEnvi : Flow<String?> = context.dataStoree.data
        .map{preferences ->
            preferences[ENVIROMENT_KEY] ?: "pre"
        }
    // save environment into datastore
    suspend fun saveEnvi(name: String){
        context.dataStoree.edit { preferences->
            preferences[ENVIROMENT_KEY] = name
        }
    }
}
