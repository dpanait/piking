package com.yubstore.piking.views.products

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
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

    val focusManager = LocalFocusManager.current
    val mAppViewModel: AppModel = viewModel(
        factory = AppViewModelFactory(context.applicationContext as Application)
    )
    val coroutineScope = rememberCoroutineScope()
    /*val items = mAppViewModel.allItems.observeAsState(listOf()).value //.collectAsState( emptyList<AppItem>()).value//collectAsState(listOf<AppItem>(), coroutineScope.coroutineContext).value
    println("Enviroment list: $items")*/

    //var viewAppModel: com.yubstore.piking.database.AppModel = hiltViewModel()
    //val app by viewAppModel.app.observeAsState(listOf())

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
                    productsModel.checkMultiLocation(productsId.value.text, context)
                    println(productsModel.multiLocation)
                    println("ProductsSearch: ${productSearch}")
                    if(!isLoading.value) {
                        //navController.navigate("multiLocation")
                    }


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
                )
            )
            println("locationListStatus: ${isLoading.value}")
            if(!isLoading.value){
                Button(onClick = {
                    println("Show locations")
                    var productsId = productsId.value.text
                    isLoading.value = true
                    navController.navigate("multiLocation/${productsId}")

                }) {
                    Text("Ver localizaiones")
                }
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
                var inventory = Inventory(location.value.text, productsId.value.text, quantityProducts.value.text)
                productsModel.saveInventory(inventory, context)
                location.value = TextFieldValue("")
                productsId.value = TextFieldValue("")
                quantityProducts.value = TextFieldValue("")
                //focusManager.moveFocus(FocusDirection.Up)
                a.requestFocus()
            }) {
                Text(text = "Guardar")
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