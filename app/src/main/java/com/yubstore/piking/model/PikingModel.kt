package com.yubstore.piking.model

import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yubstore.piking.service.*
import io.ktor.client.call.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PikingModel: ViewModel() {

    private val _pikingList = mutableStateListOf<Piking>()//mutableStateListOf<Piking>()
    val pikingList: SnapshotStateList<Piking>
        get() = _pikingList
    var errorPiking: String by mutableStateOf("")

    val pikingListStatus = mutableStateOf(true)

    private var _pikingItem = mutableStateListOf<Products>()
    val pikingItem: SnapshotStateList<Products>
        get() = _pikingItem
    private val _pikingItemCopy = mutableStateListOf<Products>()
    val pikingItemCopy: SnapshotStateList<Products>
        get() = _pikingItemCopy
    //var pikingItem = mutableStateListOf<Products>()


    var pikingItemStatus = mutableStateOf(true)// MutableStateFlow(false)
    /*val pikingItemStatus: Snapshot
        get() = _pikingItemStatus*/
    val loginModel = LoginModel()
    init{
        loginModel.getVersion()
    }

    fun getPiking(
        coroutineScope: CoroutineScope,
        idCliente: String?
    ) {
        val setPiking = SetPiking(idCliente, "get_picking")
        val response = ServiceBuilder.buildService(ApiInterface::class.java)
        response.postOrders(setPiking).enqueue(
            object : Callback<PostPiking> {
                override fun onResponse(
                    call: Call<PostPiking>,
                    response: Response<PostPiking>
                ) {
                    //Toast.makeText(this@MainActivity,response.message().toString(),Toast.LENGTH_LONG).show()
                    var pikingObj = response.body()
                    var listOrders = pikingObj?.body
                    /*println("Res: $response")
                    println("pikingObj: $pikingObj")
                    println("Size: ${listOrders?.size}")*/

                    //_pikingList.addAll(listOrders)

                    if( response.isSuccessful){
                        _pikingList.clear()
                        _pikingList.addAll(listOrders!!)
                        pikingListStatus.value = false

                    } //else {
                        response.errorBody()?.close()
                    //}
                    call.cancel()

                }

                override fun onFailure(call: Call<PostPiking>, t: Throwable) {
                    //Toast.makeText(this@MainActivity,t.toString(),Toast.LENGTH_LONG).show()

                    println("OnFailure Piking: ${t}, $call")


                    errorPiking = t.message.toString()
                }

            }
        )

        /*try {

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
                    val response = it.body<PostPiking>()//it.body<PostPiking>()
                    println("Piking: $response")
                    //postOrders = it
                    //println("PostOrders: $postOrders")

                    _pikingList.clear()
                    _pikingList.addAll(response.body)
                    //pikingList.addAll(response.body)
                    pikingListStatus.value = false
                    println("pikingListStatus: ${pikingListStatus.value}")
                    //println("size: ${it.body.size}")
                    viewModelScope.cancel()
                }.onFailure {
                    println("Error: ${it.message}, ${it.cause}, ${it.localizedMessage}")
                    viewModelScope.cancel()
                }
            }
            //viewModelScope.cancel()
        } catch (ex: CancellationException) {
            println("CancellationException: ${ex.message}, ${ex.cause}, ${ex.stackTrace}")
            throw ex // Must let the CancellationException propagate
        } catch (ex: Exception) {
            println("Exception: ${ex.message}, ${ex.cause}, ${ex.stackTrace}")
            // Handle all other exceptions here
        }*/
    }
    fun getPikingItem(
        coroutineScope: CoroutineScope,
        idCliente: String,
        ordersId: String,
        cajasId: String
    ){
        val setProduct = SetProduct(idCliente, ordersId, cajasId, "products")
        val response = ServiceBuilder.buildService(ApiInterface::class.java)
        response.postProducts(setProduct).enqueue(
            object : Callback<PostProducts> {
                override fun onResponse(
                    call: Call<PostProducts>,
                    response: Response<PostProducts>
                ) {
                    //Toast.makeText(this@MainActivity,response.message().toString(),Toast.LENGTH_LONG).show()
                    var productsObj = response.body()
                    var listProducts = productsObj?.body
                    var listProductsCopy = productsObj?.copy
                    /*println("Res: $response")
                    println("produtsObj: $productsObj")
                    println("listProducts: $listProducts, Size: ${listProducts?.size}")*/

                    _pikingItem.clear()
                    //pikingItem.addAll(listProducts!!)

                    _pikingItem.addAll(listProducts!!)
                    _pikingItemCopy.clear()
                    _pikingItemCopy.addAll(listProductsCopy!!)

                    pikingItemStatus.value = false
                    //_pikingList.addAll(listOrders)
                    response.errorBody()?.close()
                    call.cancel()
                }

                override fun onFailure(call: Call<PostProducts>, t: Throwable) {
                    //Toast.makeText(this@MainActivity,t.toString(),Toast.LENGTH_LONG).show()

                    println("OnFailure Piking: ${t}")

                    errorPiking = t.message.toString()
                }

            }
        )

        /*viewModelScope.launch {

            println("pikingItemStatus: ${pikingItemStatus.value}")
            kotlin.runCatching {
                postProducts(setProduct)//ArrayList<String>()
                //Test(SetPiking(idCliente, "get_picking"))

            }.onSuccess {
                println("itModel: ${it}")
                val response = it.body<PostProducts>()
                println("PikingItemModel: $response")
                //postOrders = it
                //println("PostOrders: $postOrders")

                _pikingItem.clear()
                _pikingItem.addAll(response.body)
                pikingItemStatus.value = false
                println("pikingItemStatus: ${pikingItemStatus.value}")

                viewModelScope.cancel()

            }.onFailure {
                println("Error: $it")
                viewModelScope.cancel()
            }
        }*/
    }
    fun checkAll(piking: SnapshotStateList<Products>) {

        piking.forEachIndexed{ index, item ->
            //item.piking = 1
            println("checkAll item: ${item}")
        }
        //println("checkAll: $piking, ${piking.size}")
        _pikingItem.clear()
        _pikingItem.addAll(piking)
    }
    fun savePiking(items: ArrayList<Products>){
        items.forEachIndexed{ index, item ->
            //item.piking = 1
            println("Piking item: ${item.piking}")
        }
        /*val savePostPiking = SavePiking("save_piking", APP_DATA.userSku, APP_DATA.IDCLIENTE, items)
        val response = ServiceBuilder.buildService(ApiInterface::class.java)
        response.savePiking(savePostPiking).enqueue(
            object : Callback<SavePostPiking> {
                override fun onResponse(
                    call: Call<SavePostPiking>,
                    response: Response<SavePostPiking>
                ) {
                    //Toast.makeText(this@MainActivity,response.message().toString(),Toast.LENGTH_LONG).show()
                    var savePikingObj = response.body()
                    var listProducts = savePikingObj?.body
                    println("Response Piking model: $savePikingObj")
                    //_pikingList.addAll(listOrders)
                    response.errorBody()?.close()
                    call.cancel()
                }
                override fun onFailure(call: Call<SavePostPiking>, t: Throwable) {
                    println("Error: ${t.message} ${t.stackTraceToString()}")
                    println("OnFailure Piking: ${t}")
                }

            }
        )*/

        /*viewModelScope.launch {

            println("pikingItemStatus: ${pikingItemStatus.value}")
            kotlin.runCatching {
                savePiking(savePostPiking)//ArrayList<String>()
                //Test(SetPiking(idCliente, "get_picking"))

            }.onSuccess {
                println("itModel: ${it}")
                val response = it.body<SavePostPiking>()//it.body<PostProducts>()
                println("PikingItemModel: $response")
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


