package com.yubstore.piking.views.piking

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.yubstore.piking.model.PikingModel
import com.yubstore.piking.service.*
import com.yubstore.piking.views.common.CircularIndeterminateProgressBar
import com.yubstore.piking.views.common.LoadingAnimation
import io.ktor.client.call.*
import io.ktor.client.statement.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

@Composable
fun PikingList(
    navController: NavController,
    state: MutableState<TextFieldValue>,
    idCliente: String?,
    pikingModel: PikingModel = PikingModel()
) {
    val coroutineScope = rememberCoroutineScope()
    //val pikingModel = PikingModel()
    val orders = remember { pikingModel.pikingList }//getListOfCountries(coroutineScope, idCliente)
    if(orders.size == 0) {
        pikingModel.getPiking(coroutineScope, idCliente)
    }

    println("orders: $orders")
    var filteredCountries: SnapshotStateList<Piking>
    var isLoading = remember { pikingModel.pikingListStatus }
    //LoadingAnimation(isLoading)

        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            if(isLoading.value) {
                item{
                    CircularIndeterminateProgressBar(isLoading.value)
                }

            } else {
            val searchedText = state.value.text
            filteredCountries = if (searchedText.isEmpty()) {
                orders
            } else {
                val resultList: SnapshotStateList<Piking> = SnapshotStateList<Piking>()

                for (order in orders) {
                    if (order.city?.lowercase() != null && order.city!!.lowercase().contains(searchedText.lowercase())) {
                        resultList.addAll(listOf(order))
                    } else if(order.orders_sku!!.lowercase().contains(searchedText.lowercase())){
                        resultList.addAll(listOf(order))
                    }
                }
                resultList
            }
            this.items(filteredCountries) { piking ->
                PikingListItem(
                    piking = piking,
                    onItemClick = { item ->
                        println("Item: $item")
                        var ordersId = item.orders_id
                        var cajasId = item.cajas_id
                        //var idCliente = item.IDCLIENTE
                        navController.currentBackStackEntry?.savedStateHandle?.set(
                            "product_search",
                            ProductSearch(idCliente.toString(), ordersId, cajasId)
                        )
                        navController.navigate("products/$idCliente/$ordersId")

                        //PikingDetail(idCliente, ordersId)
                    }
                )
            }
        }
    }
}


