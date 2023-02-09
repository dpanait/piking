package com.yubstore.piking.model

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yubstore.piking.data.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class AppModel(application: Application) : ViewModel() {
    private val repository: AppRepository
    var allAppItems: Flow<AppItem>
    var searchResults: Flow<AppItem>
    init {
        val appDb = AppItemRoomDatabase.getDatabase(application)
        val appDao = appDb.itemDao()
        repository = AppRepository(appDao)

        repository.findProduct(0)
        allAppItems = repository.allAppItems
        searchResults = repository.searchResults
    }
    //println("allAppItems: ${searchResults}")
    /**
     * Inserts the new Item into database.
     */
    fun getAppItems(){
        var appItem = repository.findProduct(0)
        println("AppItem: $appItem")

    }
    fun addNewItem(itemEnvironment: String, itemVersion: String) {
        val newItem = getNewItemEntry(itemEnvironment, itemVersion)
        insertItem(newItem)
    }

    /**
     * Launching a new coroutine to insert an item in a non-blocking way
     */
    private fun insertItem(item: AppItem) {
        viewModelScope.launch {
            repository.insertItem(item)
        }
    }

    /**
     * Returns true if the EditTexts are not empty
     */
    fun isEntryValid(itemEnvironment: String, itemVersion: String): Boolean {
        if (itemEnvironment.isBlank() || itemVersion.isBlank()) {
            return false
        }
        return true
    }

    /**
     * Returns an instance of the [Item] entity class with the item info entered by the user.
     * This will be used to add a new entry to the Inventory database.
     */
    private fun getNewItemEntry(itemEnvironment: String, itemVersion: String): AppItem {
        return AppItem(
            itemEnvironment = itemEnvironment,
            itemVersion = itemVersion
        )
    }
}

/**
 * Factory class to instantiate the [ViewModel] instance.
 */
/*class AppModelFactory(private val itemDao: ItemDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AppModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AppModel(itemDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}*/
/*class AppViewModelFactory(val application: Application) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AppModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AppModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
        //return AppModel(application) as T
    }
}*/