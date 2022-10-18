package com.yubstore.piking.views.piking

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavController
import com.yubstore.piking.model.PikingModel
import com.yubstore.piking.service.*
import io.ktor.client.call.*
import io.ktor.client.statement.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

@Composable
fun PikingList(navController: NavController, state: MutableState<TextFieldValue>, idCliente: String?) {
    val coroutineScope = rememberCoroutineScope()
    val pikingModel = PikingModel()
    pikingModel.getPiking(coroutineScope, idCliente)
    val orders = pikingModel.pikingList//getListOfCountries(coroutineScope, idCliente)
    println("orders: $orders")
    var filteredCountries: SnapshotStateList<Piking>
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        val searchedText = state.value.text
        filteredCountries = if (searchedText.isEmpty()) {
            orders
        } else {
            val resultList: SnapshotStateList<Piking> = SnapshotStateList<Piking>()
            for (order in orders) {
                if(order.city?.lowercase() == searchedText.lowercase()){
                    resultList.addAll(listOf(order))
                }

                //for (product in products) {
                    /*if (products.lines_products.forEach(item {  })find(it.barcode == searchedText.lowercase())
                    ) {
                        resultList.plus(products)
                    }*/
                //}
            }
            resultList
        }
        this.items(filteredCountries) { piking ->
            OrdersListItem(
                piking = piking,
                onItemClick = { item ->
                    println("Item: $item")
                    var ordersId = item.orders_id
                    //var idCliente = item.IDCLIENTE
                    navController.currentBackStackEntry?.savedStateHandle?.set("product_search", ProductSearch(ordersId, idCliente))
                    navController.navigate("products/$idCliente/$ordersId")
                    //PikingDetail(idCliente, ordersId)
                }
            )
        }
    }
}

fun getListOfCountries(coroutineScope: CoroutineScope, idCliente: String?): ArrayList<Piking>{
    println("ID CLIENTE: $idCliente")
    val products = arrayListOf(Products("","", "", "", "", "", "", "" ))
    var ordersList = arrayListOf(Piking("", "", "", "", "", "", ""))
    //var postOrders =  PostPiking(false , ordersList)//ArrayList<String>()


    coroutineScope.launch {
        //postOrders = postPiking(SetPiking(idCliente, "get_picking"))//ArrayList<String>()
        //println("PostOrders: $postOrders")
        //ordersList = postOrders.body
        kotlin.runCatching {
            postPiking(SetPiking(idCliente, "get_picking"))//ArrayList<String>()
            //Test(SetPiking(idCliente, "get_picking"))

        }.onSuccess {
            println("it: ${it.bodyAsText()}")
            val response = it.body<PostPiking>()
            println("Piking: $response")
            //postOrders = it
            //println("PostOrders: $postOrders")
            ordersList = response.body

            //println("size: ${it.body.size}")
            coroutineScope.cancel()
        }.onFailure {
            println("Error: $it")
        }
    }
    /*var result = testFuel()
    println("REsult; $result")*/
    val requestModel = SetPiking(idCliente, "get_picking")

    /*val response = ServiceBuilder.buildService(ApiInterface::class.java)
    response.sendReq(requestModel).enqueue(
        object : Callback<PostPiking> {
            override fun onResponse(
                call: Call<PostPiking>,
                response: Response<PostPiking>
            ) {
                //Toast.makeText(this@MainActivity,response.message().toString(),Toast.LENGTH_LONG).show()
                println("Response: ${response.message().toString()}")
            }

            override fun onFailure(call: Call<PostPiking>, t: Throwable) {
                //Toast.makeText(this@MainActivity,t.toString(),Toast.LENGTH_LONG).show()
                println("OnFailure: ${t}")
            }

        }
    )*/
    println("ordersList: $ordersList")
    return ordersList
}
