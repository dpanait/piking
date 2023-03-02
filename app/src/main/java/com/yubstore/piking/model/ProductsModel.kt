package com.yubstore.piking.model

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yubstore.piking.service.*
import io.ktor.client.call.*
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductsModel: ViewModel() {

    val moveProductsStatus = mutableStateOf(false)
    var _multiLocation = mutableStateListOf<Inventory>()
    val multiLocation: SnapshotStateList<Inventory>
        get() = _multiLocation

    var locationListStatus = mutableStateOf(true)

    fun saveInventory(item: Inventory, context: Context){
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
                    if(savePikingObj!!.status){
                        Toast.makeText(context,"${savePikingObj!!.body}", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(context,"${savePikingObj!!.body}", Toast.LENGTH_LONG).show()
                    }
                    //_pikingList.addAll(listOrders)
                    response.errorBody()?.close()
                    call.cancel()
                }
                override fun onFailure(call: Call<ResponsePostInventory>, t: Throwable) {
                    println("Error: ${t.message} ${t.stackTraceToString()}")
                    println("OnFailure Piking: ${t}")
                    Toast.makeText(context,"Error: ${t.message} ${t.stackTraceToString()}", Toast.LENGTH_LONG).show()
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
    fun saveMoveProducts(item: MoveProducts, context: Context){
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
                    if(savePikingObj!!.status){
                        moveProductsStatus.value = true
                        Toast.makeText(context,"Producto cambiado de localización con éxito", Toast.LENGTH_LONG).show()
                    }


                    Log.w("Response move products", " $savePikingObj")
                    //_pikingList.addAll(listOrders)
                    response.errorBody()?.close()
                    call.cancel()
                }
                override fun onFailure(call: Call<ResponsePostMoveProducts>, t: Throwable) {
                    println("Error: ${t.message} ${t.stackTraceToString()}")
                    println("OnFailure move products ${t}")
                    Toast.makeText(context,"Error: ${t.message} ${t.stackTraceToString()}", Toast.LENGTH_LONG).show()
                }

            }
        )

        /*viewModelScope.launch {

            println("pikingItemStatus: ")
            kotlin.runCatching {
                postMoveProducts(postMoveProducts)//ArrayList<String>()
                //Test(SetPiking(idCliente, "get_picking"))

            }.onSuccess {
                println("itModel: ${it}")
                val response = it.body<ResponsePostMoveProducts>()//it.body<PostProducts>()
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

    fun checkMultiLocation(productsId: String, context: Context) {
        println("CajasID: ${APP_DATA.cajasId}")
        //return

        if(APP_DATA.cajasId.isEmpty()){
            Toast.makeText(context,"No se puede procesar peticion, no tienes configurado el Almacen", Toast.LENGTH_LONG).show()
            return
        }
        val postMultiLocation = PostMultiLocation("check_multi_location", APP_DATA.userSku, APP_DATA.IDCLIENTE, APP_DATA.cajasId, productsId)
        val response = ServiceBuilder.buildService(ApiInterface::class.java)
        response.saveMultiLocation(postMultiLocation).enqueue(
            object : Callback<ResponsePostMultiLocation> {
                override fun onResponse(
                    call: Call<ResponsePostMultiLocation>,
                    response: Response<ResponsePostMultiLocation>
                ) {
                    //Toast.makeText(this@MainActivity,response.message().toString(),Toast.LENGTH_LONG).show()
                    var saveMultiLocation = response.body()
                    if(saveMultiLocation!!.status){
                        _multiLocation.addAll(saveMultiLocation.body)
                        if(saveMultiLocation.body.size > 1) {
                            Toast.makeText(
                                context,
                                "Este producto: $productsId tien varias localizaciónes",
                                Toast.LENGTH_LONG
                            ).show()
                            locationListStatus.value = false
                        }

                    } else {
                        if(saveMultiLocation.body.isEmpty()){
                            Toast.makeText(
                                context,
                                "Este producto: $productsId no se encuentra en este almacen",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }


                    Log.w("Response multi location", " $saveMultiLocation")
                    //_pikingList.addAll(listOrders)
                    response.errorBody()?.close()
                    call.cancel()
                }
                override fun onFailure(call: Call<ResponsePostMultiLocation>, t: Throwable) {
                    println("Error: ${t.message} ${t.stackTraceToString()}")
                    println("OnFailure multi location ${t}")
                    Toast.makeText(context,"Error: ${t.message} ${t.stackTraceToString()}", Toast.LENGTH_LONG).show()
                }

            }
        )
        /*viewModelScope.launch {

            println("pikingItemStatus: ")
            kotlin.runCatching {
                postMultiLocation(postMultiLocation)//ArrayList<String>()
                //Test(SetPiking(idCliente, "get_picking"))

            }.onSuccess {
                println("itModel: ${it}")
                val response = it.body<ResponsePostMultiLocation>()//it.body<PostProducts>()
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


}