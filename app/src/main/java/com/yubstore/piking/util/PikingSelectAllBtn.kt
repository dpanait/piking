package com.yubstore.piking.util

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.yubstore.piking.service.Products
import kotlinx.coroutines.launch

@Composable
fun PikingSelectAllBtn(
    bodyContent: MutableState<String>,
    pikingItem: SnapshotStateList<Products>,
    clickAllBtn: MutableState<Boolean>,
    btnSelectClicked: MutableState<String>
) {
    val expanded = remember { mutableStateOf(false) } // 1

    Box(
        Modifier
            .wrapContentSize(Alignment.TopEnd)
    ) {
        IconButton(onClick = {
            expanded.value = true // 2
            bodyContent.value =  "Menu Opening"
        }) {
            Icon(
                Icons.Filled.MoreVert,
                contentDescription = "More Menu"
            )
        }
    }

    DropdownMenu(
        expanded = expanded.value,
        onDismissRequest = { expanded.value = false },
    ) {
        DropdownMenuItem(
            onClick = {
            expanded.value = false // 3
            //isClickedIcon = false
            bodyContent.value = "First Item Selected"  // 4
            clickAllBtn.value = true
            btnSelectClicked.value = "check"

            println("first")
            var piking = mutableStateListOf<Products>()
            pikingItem.forEachIndexed { index, item ->
                //println("Item checkAll before: $item")
                item.piking = 1
                item.status = true
                //item.quantityProcessed = item.products_quantity.split(".")[0].toInt()
                piking.add(item)
                //println("Item checkAll: $item")
            }

            //println("Check all: $pikingItem")
            pikingItem.clear()
            pikingItem.addAll(piking)

        }) {
            //Text("Selecionar todos los productos")
            Icon(
                modifier = Modifier.size(60.dp),
                imageVector = Icons.Filled.CheckCircle,
                contentDescription = "Localized description",
                tint = Color.Green
            )
        }

        Divider()

        DropdownMenuItem(onClick = {
            expanded.value = false
            bodyContent.value = "Second Item Selected"
            println("second")
            clickAllBtn.value = true
            btnSelectClicked.value = "uncheck"

            var piking = mutableStateListOf<Products>()
            pikingItem.forEachIndexed { index, item ->

                item.piking = 0
                item.status = false
                //item.quantityProcessed = 0
                piking.add(item)
                //println("Item checkAll: $item")
            }

            //println("Deselecionar todos: $pikingItem")
            pikingItem.clear()
            pikingItem.addAll(piking)

        }) {
            //Text("Second item")
            Icon(
                modifier = Modifier.size(60.dp),
                imageVector = Icons.Filled.CheckCircle,
                contentDescription = "Localized description",
                tint = Color.LightGray
            )
        }
    }
}