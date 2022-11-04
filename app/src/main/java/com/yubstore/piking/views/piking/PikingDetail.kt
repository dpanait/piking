package com.yubstore.piking.views.piking

import android.annotation.SuppressLint
import android.os.SystemClock.sleep
import android.util.Log
import android.widget.ImageButton
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.BeyondBoundsLayout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.yubstore.piking.model.PikingModel
import com.yubstore.piking.service.*
import com.yubstore.piking.util.DrawerScreens.Account.icon
import com.yubstore.piking.util.SearchView
import com.yubstore.piking.views.common.CheckBox
import com.yubstore.piking.views.common.CircularIndeterminateProgressBar
import io.ktor.client.call.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.time.delay
import java.util.concurrent.TimeUnit


@SuppressLint("UnrememberedMutableState", "RememberReturnType")
@Composable
fun PikingDetail(
    idCliente: String,
    ordersId: String,
    product_search: ProductSearch?
){
    val coroutineScope = rememberCoroutineScope()
    println("IdCliente: $idCliente OrdersId: $ordersId")
    val cajasId = product_search?.cajasId
    val pikingModel = PikingModel()
    var pikingItemStatus =  remember{ pikingModel.pikingItemStatus }//mutableStateOf(true)
    var pikingItem =  remember { pikingModel.pikingItem }
    if(pikingItem.size == 0) {
        pikingModel.getPikingItem(coroutineScope, idCliente, ordersId, cajasId.toString())
    }

     //getListOfCountries(coroutineScope, idCliente)
    //var pikingItem = remember{mutableStateListOf<Products>()} //mutableStateOf<List<Products>>(arrayListOf())
    //getPikingItem(coroutineScope, idCliente, ordersId, cajasId, pikingItem, pikingItemStatus)
    println("PikingItem details: ${pikingItem.size}")
    println("product_search: $product_search")
    var chekstatus = remember { mutableStateOf(false)}
    val textState = remember { mutableStateOf(TextFieldValue("")) }
    var filteredProducts =  mutableStateListOf<Products>()///mutableStateListOf<Products>()
    val listState = rememberLazyListState()

    val searchedText = textState.value.text
    filteredProducts = if (searchedText.isEmpty()) {
        pikingItem
    } else {
        val resultList = mutableStateListOf<Products>()//SnapshotStateList<Products> = SnapshotStateList<Products>()

        pikingItem.forEachIndexed { index, product ->
            if (product.barcode.lowercase().contains(searchedText.lowercase())) {
                product.piking = 1
                resultList.add(product)
            }
        }
        resultList
    }
    Column() {
        Row(){
            SearchView(state = textState)
        }
        Row (
            modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 0.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround.also { Alignment.CenterVertically }
        ){

            /*Button(
                onClick = {
                    println("cHEACK ALL: $pikingItem")

                    chekstatus.value = true

                    var piking = mutableStateListOf<Products>()
                    pikingItem.forEachIndexed { index, item ->

                        item.piking = 1
                        piking.add(item)
                        println("Item checkAll: $item")
                    }

                    println("Check all: $pikingItem")
                    pikingItem.clear()
                    pikingItem.addAll(piking)
                    //runBlocking {
                    //TimeUnit.SECONDS.sleep(2)
                    //}
                    if(filteredProducts.size > 2) {
                        coroutineScope.launch {
                            // Animate scroll to the 10th item
                            listState.animateScrollToItem(index = filteredProducts.size)
                        }
                    }

                }
            ) {
                Text(text = "Selecionar todos")

            }*/
            IconButton(
                modifier = Modifier
                    .size(60.dp),

                onClick = {
                    chekstatus.value = true

                    var piking = mutableStateListOf<Products>()
                    pikingItem.forEachIndexed { index, item ->

                        item.piking = 1
                        piking.add(item)
                        println("Item checkAll: $item")
                    }

                    println("Check all: $pikingItem")
                    pikingItem.clear()
                    pikingItem.addAll(piking)
                    if(filteredProducts.size > 2) {
                        coroutineScope.launch {
                            // Animate scroll to the 10th item
                            listState.animateScrollToItem(index = filteredProducts.size)
                        }
                    }

                }
            ) {

                Icon(
                    modifier = Modifier.size(60.dp),
                    imageVector = Icons.Filled.CheckCircle,
                    contentDescription = "Localized description",
                    tint = Color.Green
                )
            }
            //Spacer(modifier = Modifier.padding(10.dp))
            /*Button(
                onClick = {
                    println("Unchack ALL: $pikingItem")

                    chekstatus.value = false

                    var piking = mutableStateListOf<Products>()
                    pikingItem.forEachIndexed { index, item ->

                        item.piking = 0
                        piking.add(item)
                        println("Item checkAll: $item")
                    }

                    println("Deselecionar todos: $pikingItem")
                    pikingItem.clear()
                    pikingItem.addAll(piking)
                    //pikingModel.checkAll(pikingItem)
                    //runBlocking {
                    //TimeUnit.SECONDS.sleep(2)
                    //}
                    if(filteredProducts.size > 2){
                        coroutineScope.launch {
                            // Animate scroll to the 10th item
                            listState.animateScrollToItem(index = 0)
                        }
                    }

                    //isLoading = false
                }
            ) {
                Text(text = "Deselecionar todos")
            }*/
            IconButton(
                modifier = Modifier
                    .size(60.dp),

                onClick = {
                    chekstatus.value = false

                    var piking = mutableStateListOf<Products>()
                    pikingItem.forEachIndexed { index, item ->

                        item.piking = 0
                        piking.add(item)
                        //println("Item checkAll: $item")
                    }

                    //println("Deselecionar todos: $pikingItem")
                    pikingItem.clear()
                    pikingItem.addAll(piking)

                    if(filteredProducts.size > 2){
                        coroutineScope.launch {
                            // Animate scroll to the 10th item
                            listState.animateScrollToItem(index = 0)
                        }
                    }
                }
            ) {

                Icon(
                    modifier = Modifier.size(60.dp),
                    imageVector = Icons.Filled.CheckCircle,
                    contentDescription = "Localized description",
                    tint = Color.LightGray
                )
            }

        }
    }


    println("filteredProducts: ${filteredProducts.size}")
    BuildLazyColumn(listState, filteredProducts, pikingItemStatus.value, chekstatus)



}
@Composable
fun BuildLazyColumn(
    listState: LazyListState,
    items: SnapshotStateList<Products>,
    pikingItemStatus: Boolean,
    chekstatus: MutableState<Boolean>
){
    //var items = remember{ itemsList }

    LazyColumn(
        modifier = Modifier.padding(0.dp, 120.dp, 0.dp, 0.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        state = listState
    ) {

        println("pikingItemStatus: ${pikingItemStatus}")
        if(pikingItemStatus) {
            item {
                CircularIndeterminateProgressBar(pikingItemStatus)
            }
        } else {

            println("filteredProducts: $items")
            this.itemsIndexed(items) { index,product ->
                println("ProductsScreen: $product")
                //Text(String(product.products_name.toByteArray(), Charsets.UTF_8))

                val quantity = product.products_quantity.replace(".", ",")
                var checklist = false

                if(product.piking == 1 ){
                    checklist = true
                }
                var thumbIconLiked by remember { mutableStateOf(checklist) }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp),
                    elevation = 10.dp
                ) {
                    Column(
                        modifier = Modifier.padding(10.dp, 15.dp)
                    ) {
                        Row() {
                            Text(
                                product.products_sku,
                                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
                            )
                        }

                            Spacer(modifier = Modifier.padding(3.dp))
                        Row() {
                            Text(product.barcode)
                        }
                        Row() {
                            Text(String(product.products_name.toByteArray(), Charsets.UTF_8))
                        }
                        Row() {
                            Text((quantity).toString() + " Ud")
                        }
                        Row() {
                            Text(product.location.toString())
                        }
                        Row() {
                            if (product.image != null) {
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data("https://yuubbb.com/images/" + product.image)
                                        .build(),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .width(150.dp)
                                        .height(100.dp)
                                        .padding(10.dp)
                                )
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.End.also { Arrangement.SpaceAround }
                        ) {
                            IconButton(
                                onClick = {
                                    println("Nota")
                                }
                            ) {

                                Icon(
                                    modifier = Modifier.size(40.dp),
                                    imageVector = Icons.Filled.Clear,
                                    contentDescription = "Localized description"
                                )
                            }

                            Spacer(modifier = Modifier.width(10.dp))
                            IconButton(
                                modifier = Modifier.width(60.dp),
                                onClick = {
                                    Log.d("Icon", "Piking: ${product.products_id}")
                                    thumbIconLiked = !thumbIconLiked

                                    if (thumbIconLiked) {
                                        checklist = true
                                        items.find { pro -> pro.products_id == product.products_id }?.piking =
                                            1
                                    } else {
                                        checklist = false
                                        items.find { pro -> pro.products_id == product.products_id }?.piking =
                                            0
                                    }


                                    //checklist = true

                                    items.forEachIndexed { index, item ->
                                        println("pikingItem: $index, $item")
                                    }
                                }
                            ) {
                                println("checklist: $checklist, thumbIconLiked: $thumbIconLiked, chekstatus: ${chekstatus.value}")
                                if(checklist){
                                    Icon(
                                        modifier = Modifier.size(40.dp),
                                        imageVector = Icons.Rounded.Check,
                                        contentDescription = "Localized description",
                                        tint = Color.Green,
                                    )
                                } else {
                                    Icon(
                                        modifier = Modifier.size(40.dp),
                                        imageVector = Icons.Rounded.Check,
                                        contentDescription = "Localized description",
                                        tint = Color.LightGray
                                    )
                                }

                            }

                        }
                    }
                }
            }
            item {
                Button(onClick = {
                    println("Guardar: $items")
                    items.forEachIndexed { index, item ->
                        println("Guardar item: $item")
                    }
                }) {
                    Text(text = "Guardar")

                }

            }
        }
    }
}


fun getPikingItem(
    coroutineScope: CoroutineScope,
    idCliente: String,
    ordersId: String,
    cajasId: String,
    pikingItem: SnapshotStateList<Products>,
    pikingItemStatus: MutableState<Boolean>
){

    coroutineScope.launch {

        kotlin.runCatching {
            postProducts(SetProduct(idCliente, ordersId, cajasId,"products"))//ArrayList<String>()
            //Test(SetPiking(idCliente, "get_picking"))

        }.onSuccess {
            //println("itModel: ${it.bodyAsText()}")
            val response = it.body<PostProducts>()
            println("PikingItemModel: $response")
            //postOrders = it
            //println("PostOrders: $postOrders")

            //pikingItem.clear()
            //pikingItem.addAll(response.body)
            pikingItem.addAll(response.body!!)

            pikingItemStatus.value = false

            coroutineScope.cancel()
        }.onFailure {
            println("Error: $it")
            coroutineScope.cancel()
        }
    }
}



@Preview
@Composable
fun PreviewPikingDetail() {
    PikingDetail("192", "2263162", ProductSearch("192", "2263162", "294"))
}

