package com.yubstore.piking.views.products

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.yubstore.piking.data.AppData
import com.yubstore.piking.model.ProductsModel
import com.yubstore.piking.service.Inventory
import com.yubstore.piking.util.TopBar

@Composable
fun ProductsScreen(
    navController: NavHostController,
    openDrawer: () -> Unit
) {
    val context = LocalContext.current
    var envi = AppData(context).getEnvi.collectAsState(initial = "")
    val productsModel =  ProductsModel()
    println("Envi products: ${envi.value}")
    //val image_almacen: Painter = painterResource(id = R.mipmap.ic_products)
    var location by remember{ mutableStateOf(TextFieldValue("")) }
    var productsId by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var quantityProducts by remember {
        mutableStateOf(TextFieldValue(""))
    }
    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(
            title = "Inventario",
            buttonIcon = Icons.Rounded.Menu,
            onButtonClicked = { openDrawer() }
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            TextField(
                value = location,
                onValueChange = { location = it },
                label = { Text(text = "Localización") },
                modifier = Modifier.padding(10.dp)
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
            Button(onClick = {
                //your onclick code here
                Log.i("Clik", "Mover producto")
                var inventory = Inventory(location.text, productsId.text, quantityProducts.text)
                productsModel.saveInventory(inventory)
            }) {
                Text(text = "Guardar")
            }

        }
    }
}