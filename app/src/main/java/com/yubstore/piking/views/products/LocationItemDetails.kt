package com.yubstore.piking.views.products

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.yubstore.piking.model.ProductsModel
import com.yubstore.piking.service.Inventory
import com.yubstore.piking.util.TopBar

//@OptIn(ExperimentalComposeUiApi::class)
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LocationItemDetails(
    location: String,
    productsId: String,
    quantity: Int,
    openDrawer: () -> Unit,
){
    val context = LocalContext.current
    val productsModel = ProductsModel()
    var location = remember {mutableStateOf(TextFieldValue("111" + location.padStart(12, '0')))}
    var productsId = remember {
        mutableStateOf(TextFieldValue(productsId))
    }
    var quantity = remember {
        mutableStateOf(TextFieldValue(quantity.toString()))
    }
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val (a, b, c) = FocusRequester.createRefs()
        TextField(
            value = location.value,
            onValueChange = { location.value = it },
            label = { Text(text = "Localización") },
            modifier = Modifier
                .padding(10.dp)
                .focusRequester(a),
            singleLine = true,
            //keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions {
                focusManager.moveFocus(FocusDirection.Next)
            }
        )
        TextField(
            value = productsId.value,
            onValueChange = { productsId.value = it },
            label = { Text(text = "Id Producto") },
            modifier = Modifier
                .padding(10.dp)
                .focusRequester(a),
            singleLine = true,
            //keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions {
                focusManager.moveFocus(FocusDirection.Next)
            }
        )
        TextField(
            value = quantity.value,
            onValueChange = { quantity.value = it },
            label = { Text(text = "Cantidad") },
            modifier = Modifier
                .padding(10.dp)
                .focusRequester(a),
            singleLine = true,
            //keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions {
                focusManager.moveFocus(FocusDirection.Next)
            }
        )
        Button(onClick = {
            //your onclick code here
            var inventory = Inventory(location.value.text, productsId.value.text, quantity.value.text)
            productsModel.saveInventory(inventory, context)
            location.value = TextFieldValue("")
            productsId.value = TextFieldValue("")
            quantity.value = TextFieldValue("")
            //focusManager.moveFocus(FocusDirection.Up)
            a.requestFocus()
        }) {
            Text(text = "Guardar")
        }
    }

}