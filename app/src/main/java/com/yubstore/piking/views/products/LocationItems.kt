package com.yubstore.piking.views.products

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.yubstore.piking.model.ProductsModel
import com.yubstore.piking.views.common.CircularIndeterminateProgressBar

@SuppressLint("UnrememberedMutableState")
@Composable
fun LocationsItems(
    navController: NavController,
    productsId: String?,
    productModel: ProductsModel //= ProductsModel()
) {
    var context = LocalContext.current
    val productsLocation = remember { productModel.multiLocation }
    /*if (productsLocation.size == 0) {
        if (productsId != null) {
            productModel.checkMultiLocation(productsId, context)
        }
    }*/
    var isLoading = remember { productModel.locationListStatus }
    productsLocation.forEach { location ->
        println("ProductsLocations: $location")
    }
    var isClicked = remember {
        false
    }
    val onItemClicked: (Boolean) -> Unit = { item ->
        println("Item location ${item}")

    }
    Column(modifier = Modifier.fillMaxWidth()) {


        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            Log.e("IsLoading", "$isLoading")
            /*if(isLoading.value) {
                item{
                    CircularIndeterminateProgressBar(isLoading.value)
                }

            } else {*/
                item {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(10.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Text("Producto con varias localizaciónes")
                    }
                }
                this.items(productsLocation) { product ->

                    LocationItem(
                        productLocation = product,
                        onItemClick = { item ->
                            println("Item location ${product}")
                            //isClicked = true
                            navController.navigate("location/${product.location}/${product.productsId}/${product.quantityProducts}")
                        }

                    )
                    if (isClicked) {
                        //LocationItemDetails(location = product.location, productsId = product.productsId, quantity = product.quantityProducts.toInt())
                        isClicked = false
                        //productModel.locationListStatus = mutableStateOf(true)
                    }
                }
            //}
        }
    }
}