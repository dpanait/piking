package com.yubstore.piking.views.products

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.sharp.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.yubstore.piking.data.AppData
import com.yubstore.piking.data.AppItem
import com.yubstore.piking.model.AlmacenModel
import com.yubstore.piking.model.AppModel
import com.yubstore.piking.model.AppViewModelFactory
import com.yubstore.piking.model.ProductsModel
import com.yubstore.piking.service.Inventory
import com.yubstore.piking.service.ProductSearch
import com.yubstore.piking.util.TopBar
import com.yubstore.piking.views.common.CircularIndeterminateProgressBar
import kotlin.coroutines.CoroutineContext

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ProductsScreen(
    navController: NavHostController,
    productsModel: ProductsModel,
    almacenModel: AlmacenModel,
    openDrawer: () -> Unit,
    productSearch: ProductSearch?
) {
    val context = LocalContext.current
    var envi = AppData(context).getEnvi.collectAsState(initial = "")
    //val productsModel =  ProductsModel()
    println("Envi products: ${envi.value}")
    //val image_almacen: Painter = painterResource(id = R.mipmap.ic_products)
    var location = remember{ mutableStateOf(TextFieldValue("")) }
    var productsId = remember {
        mutableStateOf(TextFieldValue(""))
    }
    var quantityProducts = remember {
        mutableStateOf(TextFieldValue(""))
    }
    var isLoading = remember { productsModel.locationListStatus }
    var isLoadinIcon = remember { productsModel.loadingIconStatus }
    var loadingIcon = remember{ mutableStateOf( Icons.Sharp.Close) }
    var alertMsg = remember{ mutableStateOf("") }
    var alertMsgStatus = remember { mutableStateOf(false) }
    //var loadingLabel = remember{ CircularIndeterminateProgressBar(isLoading.value) }

    val focusManager = LocalFocusManager.current
    val mAppViewModel: AppModel = viewModel(
        factory = AppViewModelFactory(context.applicationContext as Application)
    )
    val coroutineScope = rememberCoroutineScope()
    /*val items = mAppViewModel.allItems.observeAsState(listOf()).value //.collectAsState( emptyList<AppItem>()).value//collectAsState(listOf<AppItem>(), coroutineScope.coroutineContext).value
    println("Enviroment list: $items")*/

    //var viewAppModel: com.yubstore.piking.database.AppModel = hiltViewModel()
    //val app by viewAppModel.app.observeAsState(listOf())
    if(isLoadinIcon.value){
        loadingIcon.value = Icons.Default.Check
    }


    Column(modifier = Modifier.fillMaxSize()) {

        TopBar(
            title = "Inventario",
            buttonIcon = Icons.Rounded.Menu,
            almacenModel = almacenModel,
            onButtonClicked = { openDrawer() },

        )

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            val (a, b, c) = FocusRequester.createRefs()
            Row(
                modifier = Modifier
                    .padding(10.dp, 20.dp, 10.dp, 60.dp)
                    .fillMaxWidth()
            ){
                Text(
                    text = "Para comprobar si un producto tiene varias localizaciones, despues de scanear el codigo de bara del producto toca la lupa",
                    textAlign = TextAlign.Center
                )
            }

            TextField(
                value = location.value,
                onValueChange = { location.value = it },
                label = { Text(text = "Localización") },
                modifier = Modifier
                    .padding(10.dp)
                    .focusRequester(a)
                    ,
                singleLine = true,
                //keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions {
                    focusManager.moveFocus(FocusDirection.Next)
                }
            )
            TextField(
                value = productsId.value,
                onValueChange = {

                    productsId.value = it
                    //checkMultiLocation(productsModel, productsId, context)
                    //Thread.sleep(1_000)
                    //productsModel.checkMultiLocation(productsId.value.text, context)
                    //println(productsModel.multiLocation)
                    //println("ProductsSearch: ${productSearch}- ${isLoading.value}")
                    // if(!isLoading.value) {
                        //navController.navigate("multiLocation")

                    //}
                    //loadingIcon.value = Icons.Default.Refresh

                },
                label = { Text(text = "Producto") },
                modifier = Modifier
                    .padding(10.dp)
                    .focusRequester(b)
                    ,
                singleLine = true,
                keyboardActions = KeyboardActions (
                    onSearch = {
                        //Toast.makeText(context, "On Search Click: value = ${productsId.value.text}", Toast.LENGTH_SHORT).show()
                        //isLoading.value = false
                        //productsModel.locationListStatus = mutableStateOf(false)
                        //var productsId = productsId.value.text
                        //navController.navigate("multiLocation/${productsId}")
                        //LocationsItems(productModel = productsModel)
                    }
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Search
                ),
                trailingIcon = {
                    IconButton(
                        onClick = {
                            println("LoadingIcoon: ${loadingIcon.value.name}")
                            //productsId.value = TextFieldValue("")
                            productsModel.checkMultiLocation(productsId.value.text, context)
                            println(productsModel.multiLocation)
                        }
                    ) {
                        Icon(imageVector = Icons.Default.Search, contentDescription = null)
                    }
                }
                //leadingIcon = {  Icon(imageVector = loadingIcon.value, contentDescription = "emailIcon") },
                //trailingIcon = { Icon(imageVector = loadingIcon.value, contentDescription = null) }
            )
            println("locationListStatus: ${isLoading.value}")
            if(!isLoading.value){
                loadingIcon.value = Icons.Default.Check
                Button(onClick = {
                    println("Show locations")

                    var productsId = productsId.value.text
                    isLoading.value = true
                    navController.navigate("multiLocation/${productsId}")

                }) {
                    Text("Ver localizaiones")
                }
            } else {
                //loadingIcon.value = Icons.Default.Refresh
            }
            TextField(
                value = quantityProducts.value,
                onValueChange = { quantityProducts.value = it },
                label = { Text(text = "Cantidad") },
                modifier = Modifier
                    .padding(10.dp)
                    .focusRequester(c)
                    ,
                singleLine = true,
                keyboardActions = KeyboardActions {
                    focusManager.moveFocus(FocusDirection.Next)
                }
            )
            Button(onClick = {
                //your onclick code here
                if( location.value.text.isNotEmpty()
                    && productsId.value.text.isNotEmpty()
                    && quantityProducts.value.text.isNotEmpty() ){

                    var inventory = Inventory(location.value.text, productsId.value.text, quantityProducts.value.text)
                    productsModel.saveInventory(inventory, context)
                    location.value = TextFieldValue("")
                    productsId.value = TextFieldValue("")
                    quantityProducts.value = TextFieldValue("")
                    a.requestFocus()
                } else {
                    alertMsg.value = "Tienes que rellenar todos los campos"
                    alertMsgStatus.value = true

                }

            }) {
                Text(text = "Guardar")
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
                            Text(text = "Inventario", style = TextStyle(color = Color.DarkGray,fontWeight = FontWeight.Bold, fontSize = 18.sp))
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
            /*Button(onClick = {
                //your onclick code here
                insertDataEnv(mAppViewModel)
                //viewAppModel.addApp(App(enviroment = "pro", version = "16.40"))
                //println("app data: $app")
            }) {
                Text(text = "Save data")
            }
            Button(onClick = {
                //your onclick code here


                getDataEnv(mAppViewModel, coroutineScope.coroutineContext, items)
            }) {
                Text(text = "Get data")
            }*/

        }
    }
}

fun checkMultiLocation(
    productsModel: ProductsModel,
    //productsId: MutableState<TextFieldValue>,
    context: Context
) {
    //Log.e("check multi location","${productsId.value.text}" )
    //productsModel.checkMultiLocation(productsId.value.text, context)

}


fun getDataEnv(mAppViewModel: AppModel, context: CoroutineContext, items: List<AppItem>) {
    //context.launch {
    mAppViewModel.getAppItems(1)
    //val items = mAppViewModel.Result.observeAsState(listOf()).value
        Log.d("Items env", items.toString())
        println(items)
        items.forEach { appItem ->
            println("appItem: $appItem")
        }
    //}

}

private fun insertDataEnv(mAppViewModel: AppModel){
    val item = AppItem(
        id = 2,
        itemEnvironment = "pro",
        itemVersion = "16.43"
    )
    mAppViewModel.insertItem(item)
}