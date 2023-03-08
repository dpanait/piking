package com.yubstore.piking.views.products

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yubstore.piking.model.AlmacenModel
import com.yubstore.piking.model.ProductsModel
import com.yubstore.piking.service.MoveProducts


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MoveProductsScreen(
    navController: NavHostController,
    almacenModel: AlmacenModel,
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
    var alertMsg = remember{ mutableStateOf("") }
    var alertMsgStatus = remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current
    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(
            title = "Productos",
            buttonIcon = Icons.Rounded.Menu,
            almacenModel = almacenModel,
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
                if( location.text.isNotEmpty()
                    && productsId.text.isNotEmpty()
                    && quantityProducts.text.isNotEmpty() ) {
                    val moveProducts = MoveProducts(
                        location.text,
                        productsId.text,
                        quantityProducts.text,
                        newLocation.text
                    )
                    productsModel.saveMoveProducts(moveProducts, context)
                    location = TextFieldValue("")
                    productsId = TextFieldValue("")
                    quantityProducts = TextFieldValue("")
                    newLocation = TextFieldValue("")
                    a.requestFocus()
                } else {
                    alertMsg.value = "Tienes que rellenar todos los campos"
                    alertMsgStatus.value = true
                }
            }) {
                Text(text = "Mover")
            }
            if(alertMsgStatus.value) {
                AlertDialog(
                    onDismissRequest = { alertMsgStatus.value = false },
                    title = {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "Producto", style = TextStyle(color = Color.DarkGray,fontWeight = FontWeight.Bold, fontSize = 18.sp))
                        }
                    },
                    text = {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Text(alertMsg.value)

                        }
                    },
                    buttons = {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ){
                            Button(
                                modifier = Modifier.width(100.dp),
                                onClick = { alertMsgStatus.value = false }
                            ) {
                                Text("Cerrar")
                            }
                        }
                    }
                )
            }
        }
    }
}