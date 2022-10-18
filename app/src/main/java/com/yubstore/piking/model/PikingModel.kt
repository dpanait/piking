package com.yubstore.piking.model

import androidx.annotation.MainThread
import androidx.annotation.NonNull
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yubstore.piking.service.*
import io.ktor.client.call.*
import io.ktor.client.statement.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.util.concurrent.CancellationException

class PikingModel: ViewModel() {

    private val _pikingList = mutableStateListOf<Piking>()
    val pikingList: SnapshotStateList<Piking>
        get() = _pikingList

    private val _pikingItem = mutableStateListOf<Products>()
    val pikingItem: SnapshotStateList<Products>
        get() = _pikingItem

    fun getPiking(coroutineScope: CoroutineScope, idCliente: String?) {
        try {
            viewModelScope.launch {
                //var response = postPiking(SetPiking(idCliente, "get_picking"))//ArrayList<String>()
                //println("PostOrders: ${response.bodyAsText()}")
                //_pikingList.addAll(response.body<PostPiking>())
                //ordersList = postOrders.body
                kotlin.runCatching {
                    postPiking(SetPiking(idCliente, "get_picking"))//ArrayList<String>()
                    //Test(SetPiking(idCliente, "get_picking"))

                }.onSuccess {
                    //println("it: ${it.bodyAsText()}")
                    val response = it.body<PostPiking>()
                    //println("Piking: $response")
                    //postOrders = it
                    //println("PostOrders: $postOrders")
                    _pikingList.clear()
                    _pikingList.addAll(response.body)

                    //println("size: ${it.body.size}")
                    coroutineScope.cancel()
                }.onFailure {
                    println("Error: $it")
                }
            }
        } catch (ex: CancellationException) {
            println("CancellationException: ${ex.message}, ${ex.cause}, ${ex.stackTrace}")
            throw ex // Must let the CancellationException propagate
        } catch (ex: Exception) {
            println("Exception: ${ex.message}, ${ex.cause}, ${ex.stackTrace}")
            // Handle all other exceptions here
        }
    }
    fun getPikingItem(coroutineScope: CoroutineScope, idCliente: String, ordersId: String){
        viewModelScope.launch {
            kotlin.runCatching {
                postProducts(SetProduct(idCliente, ordersId, "products"))//ArrayList<String>()
                //Test(SetPiking(idCliente, "get_picking"))

            }.onSuccess {
                //println("itModel: ${it.bodyAsText()}")
                val response = it.body<PostProducts>()
                println("PikingItemModel: $response")
                //postOrders = it
                //println("PostOrders: $postOrders")
                _pikingItem.clear()
                _pikingItem.addAll(response.body)

                coroutineScope.cancel()
            }.onFailure {
                println("Error: $it")
            }
        }
    }
}