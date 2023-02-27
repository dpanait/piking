package com.yubstore.piking.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Delete
import androidx.room.Insert
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.launch
import javax.inject.Inject

//@ActivityScoped
@HiltViewModel
class AppModel @Inject constructor(
    private val appRepository: AppRepository
) : ViewModel(){

    val app: LiveData<List<App>>  by lazy{
        appRepository.getAllApp()
    }

    fun addApp(app: App){
        viewModelScope.launch {
            appRepository.newApp(app)
        }
    }
    fun deleteApp(toDelete: App){
        viewModelScope.launch {
            appRepository.deleteApp(toDelete)
        }
    }
}