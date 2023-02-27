package com.yubstore.piking.data

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.future.asCompletableFuture


class AppRepository(private val appDao: AppDao) {

    var searchResults: Flow<AppItem> = flowOf() //AppItem(1, "pre", "00")// = MutableLiveData<AppItem>()
    var Result: LiveData<List<AppItem>> = MutableLiveData()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    var allAppItems:  Flow<AppItem> = searchResults
    var allItems: LiveData<List<AppItem>> = appDao.getItems()

    fun insertItem(newAppItem: AppItem) {
        coroutineScope.launch(Dispatchers.IO) {
            println("insertItem: $newAppItem")
            appDao.insert(newAppItem)
        }
    }

    fun deleteItem(appItem: AppItem) {
        coroutineScope.launch(Dispatchers.IO) {
            appDao.delete(appItem)
        }
    }
    fun updateItem(appItem: AppItem) {
        coroutineScope.launch(Dispatchers.IO) {
            appDao.update(appItem)
        }
    }

    fun findProduct(id: Int) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults = asyncFind(id).await()
            Result = appDao.getItems()
        }
    }
    /*fun findAll(): Deferred<Flow<List<AppItem>>> =
        coroutineScope.async(Dispatchers.IO){
            return@async appDao.getItems()
        }*/


    private fun asyncFind(id: Int): Deferred<Flow<AppItem>> =//Deferred<Flow<AppItem>> =
        coroutineScope.async(Dispatchers.IO) {
            return@async appDao.getItem(id)
        }
}