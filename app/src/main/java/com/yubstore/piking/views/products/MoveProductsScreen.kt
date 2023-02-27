package com.yubstore.piking.views.products

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.yubstore.piking.model.ProductsModel
import com.yubstore.piking.service.MoveProducts


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MoveProductsScreen(
    navController: NavHostController,
    openDrawer: () -> Unit
){
    val productsModel =  ProductsModel()
    val context = LocalContext.current

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
    val focusManager = LocalFocusManager.current
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
            val (a, b, c, d) = FocusRequester.createRefs()
            /*if(almacenes.value.body.isNotEmpty()){
                Text(almacenes.value.body[0].toString())
            }*/
            TextField(
                value = location,
                onValueChange = { location = it },
                label = { Text(text = "Localización actual") },
                modifier = Modifier.padding(10.dp)
                    .focusRequester(a),
                singleLine = true,
                keyboardActions = KeyboardActions {
                    focusManager.moveFocus(FocusDirection.Next)
                }
            )
            TextField(
                value = productsId,
                onValueChange = { productsId = it },
                label = { Text(text = "Producto") },
                modifier = Modifier.padding(10.dp)
                    .focusRequester(b),
                singleLine = true,
                keyboardActions = KeyboardActions {
                    focusManager.moveFocus(FocusDirection.Next)
                }
            )
            TextField(
                value = quantityProducts,
                onValueChange = { quantityProducts = it },
                label = { Text(text = "Cantidad") },
                modifier = Modifier.padding(10.dp)
                    .focusRequester(c),
                singleLine = true,
                keyboardActions = KeyboardActions {
                    focusManager.moveFocus(FocusDirection.Next)
                }
            )
            TextField(
                value = newLocation,
                onValueChange = { newLocation = it },
                label = { Text(text = "Localización nueva") },
                modifier = Modifier.padding(10.dp)
                    .focusRequester(d),
                singleLine = true,
                keyboardActions = KeyboardActions {
                    focusManager.moveFocus(FocusDirection.Next)
                }
            )
            Button(onClick = {
                //your onclick code here
                Log.i("Clik", "Mover producto")
                val moveProducts = MoveProducts(location.text, productsId.text, quantityProducts.text, newLocation.text)
                productsModel.saveMoveProducts(moveProducts, context)
                location = TextFieldValue("")
                productsId = TextFieldValue("")
                quantityProducts = TextFieldValue("")
                newLocation = TextFieldValue("")
                a.requestFocus()
            }) {
                Text(text = "Mover")
            }
        }
    }
}