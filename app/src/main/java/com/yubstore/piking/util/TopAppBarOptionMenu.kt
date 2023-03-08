package com.yubstore.piking.util

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yubstore.piking.model.AlmacenModel
import com.yubstore.piking.service.*

@Composable
fun TopAppBarOptionMenu(
    bodyContent: MutableState<String>,
    almacenModel: AlmacenModel
) {
    val expanded = remember { mutableStateOf(false) } // 1
    val coroutineScope = rememberCoroutineScope()

    var clientId = APP_DATA.IDCLIENTE
    println("clientId: $clientId")

    val almacenes = remember { almacenModel.almacenesList }
    if(almacenes.size  == 0 ){
        clientId?.let { almacenModel.getAlmacenes(it) }
    }
    Box(
        Modifier
            .wrapContentSize(Alignment.TopEnd)
    ) {
        IconButton(onClick = {
            expanded.value = true // 2
            //bodyContent.value =  "Menu Opening"
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
        modifier = Modifier
            .width(200.dp)
    ) {
        almacenes.forEach { almacen ->
            DropdownMenuItem(onClick = {
                //mSelectedText = almacen.cajas_name
                println("Selected: ${almacen.cajas_name}")
                bodyContent.value = "${almacen.cajas_name} - ${almacen.cajas_id}"
                expanded.value = false
                APP_DATA.cajasId = almacen.cajas_id
                APP_DATA.storeName = "${almacen.cajas_name} - ${almacen.cajas_id}"
            }) {
                Text(text = "${almacen.cajas_name} - ${almacen.cajas_id}")
            }
        }
    }

    /*DropdownMenu(
        expanded = expanded.value,
        onDismissRequest = { expanded.value = false },
    ) {
        DropdownMenuItem(onClick = {
            expanded.value = false // 3
            bodyContent.value = "First Item Selected"  // 4
        }) {
            Text("First item")
        }

        Divider()

        DropdownMenuItem(onClick = {
            expanded.value = false
            bodyContent.value = "Second Item Selected"
        }) {
            Text("Second item")
        }

        Divider()

        DropdownMenuItem(onClick = {
            expanded.value = false
            bodyContent.value = "Third Item Selected"
        }) {
            Text("Third item")
        }

        Divider()

        DropdownMenuItem(onClick = {
            expanded.value = false
            bodyContent.value = "Fourth Item Selected"
        }) {
            Text("Fourth item")
        }
    }*/
}