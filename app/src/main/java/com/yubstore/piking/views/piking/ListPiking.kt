package com.yubstore.piking.views.piking

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.yubstore.piking.model.PikingModel
import com.yubstore.piking.service.Products
import com.yubstore.piking.util.PikingSelectAllBtn
import com.yubstore.piking.util.SearchView
import com.yubstore.piking.views.common.CircularIndeterminateProgressBar
import kotlinx.coroutines.launch


@SuppressLint("UnrememberedMutableState", "CoroutineCreationDuringComposition")
@Composable
fun ListPiking(
    navController: NavController,
    clickAllBtn: MutableState<Boolean>,
    listState: LazyListState,
    pikingItem: SnapshotStateList<Products>,
    pikingItemStatus: Boolean,
    btnSelectClicked: String,
    pikingModel: PikingModel,
    modificated_values: SnapshotStateList<Products>,
    textState: MutableState<TextFieldValue>,
    productsDataList: MutableList<ProductsData>,
    productsHeightList: MutableList<ProductsHeight>
) {
    var itemIndex by remember {
        mutableStateOf(0)
    }


    val btnSelectClicked = remember { mutableStateOf("none") }
    val bodyContent = remember { mutableStateOf("Select Action") }
    var isClickedIcon by remember{ mutableStateOf(false) }
    var filteredProducts: SnapshotStateList<Products>
    val searchedText = textState.value.text
    var scrollState = rememberScrollState()
    var coroutineScope = rememberCoroutineScope()


    Column(
        modifier = Modifier
            //.padding(top = 70.dp)
            //.padding(0.dp, 60.dp, 0.dp, 0.dp)
    ) {
        //Text("Your text here")
        Column() {
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
        }
        Column(
            modifier = Modifier
            .verticalScroll(scrollState)
                .padding(bottom = 60.dp)
        ) {

            filteredProducts = if (searchedText.isEmpty()) {
                //itemIndex = 0
                pikingItem
            } else {
                val resultList =
                    mutableStateListOf<Products>()//SnapshotStateList<Products> = SnapshotStateList<Products>()
                var scroll = 0
                pikingItem.forEachIndexed { index, product ->
                    //println("Check Filter: ${product.barcode?.lowercase()?.contains(searchedText.lowercase())}")
                    if (product.barcode?.lowercase()?.contains(searchedText.lowercase()) == true) {
                        // calculamos la altura del scroll
                        for ((indexHeight, value) in productsHeightList.withIndex()) {
                            println("HEIght: $index - $indexHeight - $value")
                            if (index == indexHeight) {
                                break
                            }
                            scroll += value.height.toInt() + 72
                            Log.w("ItemIndeScrool", "$itemIndex")

                        }

                        if (productsHeightList.size == 0) {
                            itemIndex = (index * (756 + 72))
                        }

                        product.piking = 1
                        product.status = true
                        //product.quantityProcessed = product.products_quantity.split(".")[0].toInt()
                        resultList.add(product)
                        pikingItem[index].piking = 1
                        pikingItem[index].status = true
                        //
                        //itemList[index].quantityProcessed = itemList[index].products_quantity.split(".")[0].toInt()
                    }
                }
                itemIndex = scroll
                //println("Size filteres: ${resultList.size} - ${resultList.first().barcode}")
                //println("Scrool: $itemIndex")
                pikingItem
                //resultList
            }
            /*filteredProducts.forEachIndexed { index, product ->
                Log.e("Num products", "${productsHeightList.size} - $product")
            }*/
            if (pikingItemStatus) {

                CircularIndeterminateProgressBar(pikingItemStatus, null)

            } else {

                filteredProducts.forEachIndexed { index, product ->
                    var checklist by remember { mutableStateOf(false) }
                    var maxValue =
                        product.products_quantity.split(".")[0].toInt() - product.quantityProcessed
                    // todas las aciones cuando se hace ckil en el checkbox
                    var onCheckBoxChecked: (Boolean) -> Unit = { it ->
                        //println("Status: $it - $itemList")
                        checklist = it
                        clickAllBtn.value = false
                        if (it) {

                            if (maxValue != 0) {
                                //println("PIKING1: ${quantity.toInt() == (quantity_prcesed_server + maxValue)}")
                                modificated_values.find { pro -> pro.products_id == product.products_id }?.piking =
                                    1
                                pikingItem.find { pro -> pro.products_id == product.products_id }?.piking =
                                    1
                                pikingItem.find { pro -> pro.products_id == product.products_id }?.status =
                                    true
                                //Log.e("SCROOL val: ", "${scrollState.value}")
                            }
                        } else {
                            modificated_values.find { pro -> pro.products_id == product.products_id }?.piking =
                                0
                            pikingItem.find { pro -> pro.products_id == product.products_id }?.piking =
                                0
                            pikingItem.find { pro -> pro.products_id == product.products_id }?.status =
                                false

                        }
                    }
                    //println("ClickAll btn: ${clickAllBtn.value}")

                    // si hemos echo click en los botones selectar todos o deselect

                    if (clickAllBtn.value) {
                        checklist = product.status//by mutableStateOf(product.status)
                        if (product.piking == 1) {
                            checklist = true
                        }
                        //Log.d("Bunton text", "Button: $btnSelectClicked")
                        // cuando hacemos check o uncheck movemos el scrool a la ultima fila o al principio dependiendo del caso
                        if (btnSelectClicked.value == "check") {
                            itemIndex = pikingItem.size * 1086
                        }
                        if (btnSelectClicked.value == "uncheck") {
                            itemIndex = 0
                        }

                    } else {
                        checklist = product.status
                    }

                    //println("Status remember: $checklist - $product")
                    //println("Scroll Value: ${itemIndex.value}")

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

                Column(
                    modifier = Modifier.fillMaxWidth()
                        .padding(top = 0.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Button(onClick = {
                        //println("Guardar: $itemsList")
                        var pikingList = arrayListOf<Products>()
                        filteredProducts.forEachIndexed { index, item ->
                            //println("Guardar item_$index: $item")
                            val productsQuantity =
                                item.products_quantity.split(".")[0].toInt() - item.quantityProcessed
                            //println("productsQuantity: $productsQuantity")
                            var productsDataStatus =
                                productsDataList.find { pro -> pro.productsId == item.products_id }
                            if (productsDataStatus == null) {
                                if (item.status) {
                                    item.quantityProcessed = productsQuantity
                                } else {
                                    item.quantityProcessed = 0
                                }
                            } else {
                                if (item.status) {
                                    item.quantityProcessed = productsDataStatus.quantity
                                } else {
                                    item.quantityProcessed = 0
                                }
                            }
                            //item.quantityProcessed = productsQuantity - item.quantityProcessed.toInt()
                            pikingList.add(item)
                            Log.w("Item", "${pikingList[index]}")
                            var productChange =
                                productsDataList.find { pro -> pro.productsId == item.products_id }
                            //Log.e("Products change", "${productChange}")
                        }
                        pikingModel.savePiking(pikingList)
                        navController.popBackStack()

                    }) {
                        Text(text = "Guardar")

                    }
                }

            }
            //println("Scroll Value: $itemIndex")
            coroutineScope.launch {
                scrollState.scrollTo(itemIndex)
            }
        }
        /*productsHeightList.forEach { item ->
            Log.e("Element height", "${item.productsId} -  ${item.height}")
        }*/



    }
}