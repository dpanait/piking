package com.yubstore.piking.model

import android.app.Application
import androidx.lifecycle.*
import com.yubstore.piking.data.*
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class AppModel(application: Application) : AndroidViewModel(application) {//ViewModel()
    private val repository: AppRepository
    var allAppItems: Flow<AppItem>
    var searchResults: Flow<AppItem>
    var allItems: LiveData<List<AppItem>>
    var Result: LiveData<List<AppItem>>
    init {
        val appDb = AppItemRoomDatabase.getDatabase(application)
        val appDao = appDb.itemDao()
        repository = AppRepository(appDao)

        repository.findProduct(0)
        allAppItems = repository.allAppItems
        searchResults = repository.searchResults
        allItems = repository.allItems
        Result = repository.Result
    }
    //println("allAppItems: ${searchResults}")
    /**
     * Inserts the new Item into database.
     */
    fun getAppItems(id: Int){
        var appItem = repository.findProduct(id)
        println("AppItem: $appItem")

    }
    /*fun getAll(): Deferred<Flow<List<AppItem>>> {
        return repository.findAll()
    }*/
    fun addNewItem(itemEnvironment: String, itemVersion: String) {
        val newItem = getNewItemEntry(itemEnvironment, itemVersion)
        insertItem(newItem)
    }

    /**
     * Launching a new coroutine to insert an item in a non-blocking way
     */
    fun insertItem(item: AppItem) {
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
class AppViewModelFactory(val application: Application) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(AppModel::class.java)) {
            return AppModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
        //return AppModel(application) as T
    }
}