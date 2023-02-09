package com.yubstore.piking.views.products

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavHostController
import com.yubstore.piking.util.TopBar
import androidx.compose.material.*
import androidx.compose.ui.unit.dp
import com.yubstore.piking.model.ProductsModel
import com.yubstore.piking.service.MoveProducts


@Composable
fun MoveProductsScreen(
    navController: NavHostController,
    openDrawer: () -> Unit
){
    val productsModel =  ProductsModel()
    var location by remember{ mutableStateOf(TextFieldValue("")) }
    var newLocation by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var productsId by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var quantityProducts by remember {
        mutableStateOf(TextFieldValue(""))
    }
    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(
            title = "Mover Productos",
            buttonIcon = Icons.Rounded.Menu,
            onButtonClicked = { openDrawer() }
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){

            /*if(almacenes.value.body.isNotEmpty()){
                Text(almacenes.value.body[0].toString())
            }*/
            TextField(
                value = location,
                onValueChange = { location = it },
                label = { Text(text = "Localización actual") },
                modifier = Modifier.padding(10.dp),
            )
            TextField(
                value = productsId,
                onValueChange = { productsId = it },
                label = { Text(text = "Producto") },
                modifier = Modifier.padding(10.dp)
            )
            TextField(
                value = quantityProducts,
                onValueChange = { quantityProducts = it },
                label = { Text(text = "Cantidad") },
                modifier = Modifier.padding(10.dp)
            )
            TextField(
                value = newLocation,
                onValueChange = { newLocation = it },
                label = { Text(text = "Localización nueva") },
                modifier = Modifier.padding(10.dp)
            )
            Button(onClick = {
                //your onclick code here
                Log.i("Clik", "Mover producto")
                val moveProducts = MoveProducts(location.text, productsId.text, quantityProducts.text, newLocation.text)
                productsModel.saveMoveProducts(moveProducts)
            }) {
                Text(text = "Mover")
            }
        }
    }
}