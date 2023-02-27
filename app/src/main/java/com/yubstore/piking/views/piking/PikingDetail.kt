package com.yubstore.piking.views.piking

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.yubstore.piking.model.PikingModel
import com.yubstore.piking.service.*
import com.yubstore.piking.views.common.CircularIndeterminateProgressBar
import io.ktor.client.call.*
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
data class ProductsData (
    var productsId: String,
    var quantity: Int
)
@Serializable
data class ProductsHeight (
    var productsId: String,
    var height: Float
)
@SuppressLint("UnrememberedMutableState", "RememberReturnType")
@Composable
fun PikingDetail(
    navController: NavController,
    idCliente: String,
    ordersId: String,
    product_search: ProductSearch?
){
    val coroutineScope = rememberCoroutineScope()
    println("IdCliente: $idCliente OrdersId: $ordersId")
    val cajasId = product_search?.cajasId
    val pikingModel = PikingModel()
    val pikingItemStatus =  remember{ pikingModel.pikingItemStatus }//mutableStateOf(true)
    val pikingItem = remember { pikingModel.pikingItem }
    val modificated_values = remember { pikingModel.pikingItemCopy }
        if(pikingItem.size == 0) {
        pikingModel.getPikingItem(coroutineScope, idCliente, ordersId, cajasId.toString())
    }

    val btnSelectClicked = remember { mutableStateOf("none") }
    val textState = remember { mutableStateOf(TextFieldValue("")) }
    var filteredProducts =  mutableStateListOf<Products>()///mutableStateListOf<Products>()
    val listState = rememberLazyListState()


    //scrollState.scrollTo(scrollState.value - 1000)
    var clickAllBtn = remember { mutableStateOf(false) }
    val bodyContent = remember { mutableStateOf("Select Action") }
    var isClickedIcon by remember{ mutableStateOf(false) }


    val searchedText = textState.value.text

    /*filteredProducts = if (searchedText.isEmpty()) {
        pikingItem
    } else {
        val resultList = mutableStateListOf<Products>()//SnapshotStateList<Products> = SnapshotStateList<Products>()

        pikingItem.forEachIndexed { index, product ->
            println("Check Filter: ${product.barcode?.lowercase()?.contains(searchedText.lowercase())}")
            if (product.barcode?.lowercase()?.contains(searchedText.lowercase()) == true) {

                product.piking = 1
                product.status = true
                product.quantityProcessed = product.products_quantity.split(".")[0].toInt()
                resultList.add(product)
            }
        }
        println("Size filteres: ${resultList.size} - ${resultList.first().barcode}")
        resultList
    }*/
    /*Column() {
        Row(){
            SearchView(state = textState, Modifier.fillMaxWidth(0.9f))
            IconButton(
                modifier = Modifier
                    .size(60.dp),

                onClick = {
                    isClickedIcon = true
                }
            ){

                PikingSelectAllBtn(bodyContent, pikingItem, clickAllBtn, btnSelectClicked)
            }
        }
    }*/
    //println("filteredProducts: ${filteredProducts.size}")
    // creamos la lista de los productos
    /*BuildLazyColumn(
        navController,
        clickAllBtn,
        listState,
        pikingItem,
        pikingItemStatus.value,
        btnSelectClicked.value,
        pikingModel,
        modificated_values,
        textState
    )*/
    var productsDataList = remember {
        listOf<ProductsData>()
    }.toMutableList()
    var productsHeightList = remember {
        listOf<ProductsHeight>()
    }.toMutableList()

    ListPiking(
        navController,
        clickAllBtn,
        listState,
        pikingItem,
        pikingItemStatus.value,
        btnSelectClicked.value,
        pikingModel,
        modificated_values,
        textState,
        productsDataList,
        productsHeightList
    )



}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun BuildLazyColumn(
    navController: NavController,
    clickAllBtn: MutableState<Boolean>,
    listState: LazyListState,
    itemList: SnapshotStateList<Products>,
    pikingItemStatus: Boolean,
    btnSelectClicked: String,
    pikingModel: PikingModel,
    modificated_values: SnapshotStateList<Products>,
    textState: MutableState<TextFieldValue>
){
    var coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    //listState.animateScrollToItem(index = 10)
    var itemIndex = remember {
        mutableStateOf(0)
    }
    var productsDataList = remember {
        listOf<ProductsData>()
    }.toMutableList()

    var productsHeightList = remember {
        listOf<ProductsHeight>()
    }.toMutableList()

    LazyColumn(
        modifier = Modifier.padding(0.dp, 60.dp, 0.dp, 0.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        state = listState
    ) {

        //println("pikingItemStatus: $pikingItemStatus - ${itemList.size}")
        var filteredProducts =  mutableStateListOf<Products>()
        val searchedText = textState.value.text


        if(pikingItemStatus) {
            item {
                CircularIndeterminateProgressBar(pikingItemStatus)
            }
        } else {
            // hacemos scroll en la lista dependiendo del botton pulsado
            /*if(clickAllBtn.value){
                var indexItem = itemList.size
                if(btnSelectClicked == "uncheck"){
                    indexItem = 0
                }
                coroutineScope.launch {
                    // Animate scroll to the 10th item
                    listState.animateScrollToItem(index = indexItem)
                }
            }*/

            filteredProducts = if (searchedText.isEmpty()) {
                itemList
            } else {
                val resultList = mutableStateListOf<Products>()//SnapshotStateList<Products> = SnapshotStateList<Products>()

                itemList.forEachIndexed { index, product ->
                    //println("Check Filter: ${product.barcode?.lowercase()?.contains(searchedText.lowercase())}")
                    if (product.barcode?.lowercase()?.contains(searchedText.lowercase()) == true) {
                        itemIndex.value = index
                        product.piking = 1
                        product.status = true
                        //product.quantityProcessed = product.products_quantity.split(".")[0].toInt()
                        resultList.add(product)
                        itemList[index].piking = 1
                        itemList[index].status = true
                        //itemList[index].quantityProcessed = itemList[index].products_quantity.split(".")[0].toInt()
                    }
                }
                //println("Size filteres: ${resultList.size} - ${resultList.first().barcode}")
                itemList
                //resultList
            }

            //Log.d("antes", "${filteredProducts.size}")
            //itemsIndexed(items = filteredProducts){ index, product ->
            //this.items(items = itemList, key = { product -> product.products_id}) { product ->
            this.items(filteredProducts){ product->
                //productsDataList.add(ProductsData(product.products_id, product.products_quantity.split(".")[0].toInt()))
                //var product by remember{ mutableStateOf(product) }
                var checklist by remember { mutableStateOf(false)}
                var maxValue = 0
                //Log.d("Item", "List: add data ${product.piking} Lazy status: ${product.status}")
                //var product = itemsList[index]//modificated_values[index]
                //val quantity = product.products_quantity.split(".")[0]
                var quantityProcessedToShow = product.products_quantity.split(".")[0].toInt() - product.quantityProcessed
                //var quantity_prcesed_server = quantityProcessedToShow

                //var quantityProcessed = quantityProcessedToShow
                /*if(product.quantityProcessed == 0){
                    maxValue = quantity.toInt()
                } else {*/
                    maxValue = quantityProcessedToShow
                //}

                // todas las aciones cuando se hace ckil en el checkbox
                var onCheckBoxChecked: (Boolean)->Unit = { it->
                    //println("Status: $it - $itemList")
                    checklist = it
                    clickAllBtn.value = false
                    if (it) {

                        if(maxValue != 0){
                            //println("PIKING1: ${quantity.toInt() == (quantity_prcesed_server + maxValue)}")
                            modificated_values.find { pro -> pro.products_id == product.products_id }?.piking = 1
                            itemList.find { pro -> pro.products_id == product.products_id }?.piking = 1
                            itemList.find { pro -> pro.products_id == product.products_id }?.status = true

                        }
                    } else {
                        modificated_values.find { pro -> pro.products_id == product.products_id }?.piking = 0
                        itemList.find { pro -> pro.products_id == product.products_id }?.piking = 0
                        itemList.find { pro -> pro.products_id == product.products_id }?.status = false

                    }
                }
                //println("ClickAll btn: ${clickAllBtn.value}")

                // si hemos echo click en los botones selectar todos o deselect

                if(clickAllBtn.value){
                    checklist = product.status//by mutableStateOf(product.status)
                    if(product.piking == 1){
                        checklist = true
                    }
                    //Log.d("Bunton text", "Button: $btnSelectClicked")
                    // cuando hacemos check o uncheck movemos el scrool a la ultima fila o al principio dependiendo del caso
                    if(btnSelectClicked == "check"){
                        itemIndex.value = filteredProducts.size
                    }
                    if(btnSelectClicked == "uncheck"){
                        itemIndex.value = 0
                    }

                } else {
                    //checklist = product.status
                }

                //println("Status remember: $checklist - $product")
                // generamos cada linea de producto en esta orders
                ProductsDisplay(
                    item = product,
                    iconStatus = checklist,
                    itemsList = filteredProducts,
                    onCheckBoxChecked = onCheckBoxChecked,
                    modificated_values = modificated_values,
                    productsDataList = productsDataList,
                    productsHeightList = productsHeightList
                )


            }
            coroutineScope.launch {

                listState.scrollToItem(itemIndex.value,0)
            }
            item {
                Button(onClick = {
                    //println("Guardar: $itemsList")
                    var pikingList = arrayListOf<Products>()
                    filteredProducts.forEachIndexed { index, item ->
                        println("Guardar item_$index: $item")
                        val productsQuantity = item.products_quantity.split(".")[0].toInt() - item.quantityProcessed
                        //println("productsQuantity: $productsQuantity")
                        var productsDataStatus =  productsDataList.find { pro -> pro.productsId == item.products_id }
                        if(productsDataStatus == null){
                            if(item.status){
                                item.quantityProcessed = productsQuantity
                            } else {
                                item.quantityProcessed = 0
                            }
                        } else {
                            if(item.status) {
                                item.quantityProcessed = productsDataStatus.quantity
                            } else {
                                item.quantityProcessed = 0
                            }
                        }
                        //item.quantityProcessed = productsQuantity - item.quantityProcessed.toInt()
                        pikingList.add(item)
                        Log.w("Item", "${pikingList[index]}")
                        var productChange = productsDataList.find { pro -> pro.productsId == item.products_id }
                        Log.e("Products change", "${productChange}")
                    }
                    //pikingModel.savePiking(pikingList)
                    //navController.popBackStack()

                }) {
                    Text(text = "Guardar")

                }

            }
        }
    }
}


@Composable
fun ProductsDisplay(
    item: Products,
    iconStatus: Boolean,
    itemsList: SnapshotStateList<Products>,
    onCheckBoxChecked: (Boolean) -> Unit,
    modificated_values: SnapshotStateList<Products>,
    productsDataList: MutableList<ProductsData>,
    productsHeightList: MutableList<ProductsHeight>

){
    //println("Product: $item - Icon Status: $iconStatus")
    var checkboxState = rememberSaveable { mutableStateOf(false) }
    var quantityProcessedToShow = item.products_quantity.split(".")[0].toInt() - item.quantityProcessed
    var piking by rememberSaveable { mutableStateOf(item.piking) }
    var orders_id by rememberSaveable { mutableStateOf(item.orders_id) }
    var quantityProcessed by rememberSaveable { mutableStateOf(quantityProcessedToShow.toString()) }
    var products_quantity by rememberSaveable { mutableStateOf(item.products_quantity) }
    var products_id by rememberSaveable { mutableStateOf(item.products_id) }
    var products_name by rememberSaveable { mutableStateOf(item.products_name) }
    var barcode by rememberSaveable { mutableStateOf(item.barcode) }
    var image by rememberSaveable { mutableStateOf(item.image) }
    var products_sku by rememberSaveable { mutableStateOf(item.products_sku) }
    var location by rememberSaveable { mutableStateOf(item.location) }
    var status by rememberSaveable { mutableStateOf(item.status) }

    var isError by remember { mutableStateOf(false) }

    val quantity = products_quantity.split(".")[0]
    // maximo que se puede poner en el input de las cantidades
    //var quantity_prcesed_server = quantityProcessedToShow
    var maxValue = 0
    if(item.quantityProcessed == 0){
        maxValue = quantity.toInt()
    } else {
        maxValue = quantityProcessedToShow
    }
    checkboxState.value = piking == 1
    var height by remember {
        mutableStateOf(0f)
    }
    val localDensity = LocalDensity.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            //.height(300.dp)
            .onGloballyPositioned { coordinates ->
                height = coordinates.size.height.toFloat()
                productsHeightList.add(ProductsHeight(products_id, coordinates.size.height.toFloat()))
                // Set column height using the LayoutCoordinates
                // columnHeightPx = coordinates.size.height.toFloat()
                // columnHeightDp = with(localDensity) { coordinates.size.height.toDp() }
            }
        ,
        elevation = 10.dp,

    ) {
        Column(
            modifier = Modifier.padding(10.dp, 15.dp)
        ) {
            Row() {
                Text(
                    products_sku,
                    style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
                )
            }

            Spacer(modifier = Modifier.padding(3.dp))
            Row() {
                if(barcode != null){
                    Text(barcode!!)
                } else {
                    Text("")
                }


            }
            Row() {
                Text(String(products_name.toByteArray(), Charsets.UTF_8))
            }
            Row() {
                Text(
                    (quantity).toString() + " Ud",
                    style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Bold)
                )
            }
            Row() {
                if(location == "null"){
                    Text("")
                } else {
                    Text(location.toString())
                }

            }
            Row() {
                if (image != null) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data("https://yuubbb.com/images/" + image)
                            .build(),
                        contentDescription = null,
                        modifier = Modifier
                            .width(150.dp)
                            .height(100.dp)
                            .padding(10.dp)
                    )
                }
            }
            Row(){

                OutlinedTextField(
                    placeholder = { Text("UD")},
                    value = quantityProcessed,
                    onValueChange = { it->
                        //println("it: $it")
                        var number = it.filter { it.isDigit() }
                        //println("Number: $number")
                        var it_value = it
                        /*if(it == ""){
                            it_value = ""
                        }*/
                        isError = false

                        /*println("quantity: $quantity, restantes: $quantityProcessedToShow - server: ${item.quantityProcessed}")*/
                        //println("Numero teclado: $number,max value: $maxValue, quantityProcessed: $quantityProcessedToShow")
                        if(number != "" && number.toInt() > maxValue){
                            number = 0.toString()
                            isError = true
                        }
                        quantityProcessed = number//manageLength(it_value, maxValue.toString().length)

                        if(quantityProcessed != "" && quantityProcessedToShow != 0) {
                            modificated_values.find { pro -> pro.products_id == products_id }?.quantityProcessed =
                                number.toInt()
                            //itemsList.find { pro -> pro.products_id == products_id }?.quantityProcessed =
                                //number.toInt()

                            var productDataStatus = productsDataList.find { pro -> pro.productsId == products_id }
                            //Log.e("productDataStatus:", "${productDataStatus}")
                            if(productDataStatus != null) {
                                productsDataList.find { pro -> pro.productsId == products_id }?.quantity = number.toInt()
                            } else {
                                productsDataList.add(ProductsData(products_id, number.toInt()))
                            }

                            //var productChange = productsDataList.find { pro -> pro.productsId == products_id }
                            //Log.e("Products change teclado", "${productChange}")

                        }
                    },
                    isError = isError,
                    keyboardOptions = KeyboardOptions(/*keyboardType = KeyboardType.Number,*/ imeAction = ImeAction.Next),
                    maxLines = 1,
                    modifier = Modifier.size(60.dp),
                    /*keyboardActions = KeyboardActions(
                        onNext = {
                            println(this.)
                        }
                    )*/
                )

                if(isError){
                    Text(
                        text = "La cantidad introducida no puede ser mayor a la que se tiene que enviar, o esta cantidad esta ya enviada",
                        color = MaterialTheme.colors.error,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End.also { Arrangement.SpaceAround }
            ){
                Checkbox(
                    checked = iconStatus,
                    onCheckedChange = onCheckBoxChecked
                )
            }
        }
    }
    
}



private fun manageLength(input: String, maxLength: Int): String = if (input.length > maxLength ) input.substring(0,maxLength) else input

