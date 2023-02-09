package com.yubstore.piking.model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yubstore.piking.service.*
import io.ktor.client.call.*
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductsModel: ViewModel() {
    fun saveInventory(item: Inventory){
        val savePostInventory = SaveInventory("save_inventory", APP_DATA.userSku, APP_DATA.IDCLIENTE, item)
        val response = ServiceBuilder.buildService(ApiInterface::class.java)
        response.saveInventory(savePostInventory).enqueue(
            object : Callback<ResponsePostInventory> {
                override fun onResponse(
                    call: Call<ResponsePostInventory>,
                    response: Response<ResponsePostInventory>
                ) {
                    //Toast.makeText(this@MainActivity,response.message().toString(),Toast.LENGTH_LONG).show()
                    var savePikingObj = response.body()


                    println("Response Piking model: $savePikingObj")
                    //_pikingList.addAll(listOrders)
                    response.errorBody()?.close()
                    call.cancel()
                }
                override fun onFailure(call: Call<ResponsePostInventory>, t: Throwable) {
                    println("Error: ${t.message} ${t.stackTraceToString()}")
                    println("OnFailure Piking: ${t}")
                }

            }
        )
        /*viewModelScope.launch {

            println("pikingItemStatus: ")
            kotlin.runCatching {
                postInventory(savePostInventory)//ArrayList<String>()
                //Test(SetPiking(idCliente, "get_picking"))

            }.onSuccess {
                println("itModel: ${it}")
                val response = it.body<ResponsePostInventory>()//it.body<PostProducts>()
                println("PikingItemModel: $response")
                Log.e("Response Name", "${response.body}")
                //postOrders = it
                //println("PostOrders: $postOrders")

                /* _pikingItem.clear()
                 _pikingItem.addAll(response.body)
                 pikingItemStatus.value = false
                 println("pikingItemStatus: ${pikingItemStatus.value}")*/

                //viewModelScope.cancel()

            }.onFailure {
                println("Error: ${it.stackTraceToString()} - ${it.stackTrace} -${it.message}")
                //viewModelScope.cancel()
            }
        }*/
    }
    fun saveMoveProducts(item: MoveProducts){
        val postMoveProducts = PostMoveProducts("save_move_products", APP_DATA.userSku, APP_DATA.IDCLIENTE, item)
        val response = ServiceBuilder.buildService(ApiInterface::class.java)
        response.saveMoveProducts(postMoveProducts).enqueue(
            object : Callback<ResponsePostMoveProducts> {
                override fun onResponse(
                    call: Call<ResponsePostMoveProducts>,
                    response: Response<ResponsePostMoveProducts>
                ) {
                    //Toast.makeText(this@MainActivity,response.message().toString(),Toast.LENGTH_LONG).show()
                    var savePikingObj = response.body()


                    Log.w("Response move products", " $savePikingObj")
                    //_pikingList.addAll(listOrders)
                    response.errorBody()?.close()
                    call.cancel()
                }
                override fun onFailure(call: Call<ResponsePostMoveProducts>, t: Throwable) {
                    println("Error: ${t.message} ${t.stackTraceToString()}")
                    println("OnFailure move products ${t}")
                }

            }
        )
    }
}