package com.yubstore.piking.data

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf


class AppRepository(private val appDao: AppDao) {

    var searchResults: Flow<AppItem> = flowOf() //AppItem(1, "pre", "00")// = MutableLiveData<AppItem>()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    var allAppItems:  Flow<AppItem> = searchResults

    fun insertItem(newAppItem: AppItem) {
        coroutineScope.launch(Dispatchers.IO) {
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
        }
    }

    private fun asyncFind(id: Int): Deferred<Flow<AppItem>> =//Deferred<Flow<AppItem>> =
        coroutineScope.async(Dispatchers.IO) {
            return@async appDao.getItem(id)
        }
}