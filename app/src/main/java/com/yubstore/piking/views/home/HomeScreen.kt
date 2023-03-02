package com.yubstore.piking.views.home

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavHostController
import com.yubstore.piking.data.AppData
import com.yubstore.piking.model.AlmacenModel
import com.yubstore.piking.model.AppModel
import com.yubstore.piking.model.LoginModel
import com.yubstore.piking.service.*
import com.yubstore.piking.util.TopBar
import io.ktor.client.call.*
import io.ktor.client.statement.*
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun HomeScreen(
    navController: NavHostController,
    idcliente: String?,
    almacenModel: AlmacenModel,
    openDrawer: () -> Unit
){
    println("Idcliente: $idcliente")
    var loginModel = LoginModel()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    var envi = AppData(context).getEnvi.collectAsState(initial = "")
    //var environment = envi.getEnvi.collectAsState(initial = "")
    println("Envi: ${envi.value}")
    if(envi.value == ""){
        coroutineScope.launch {
            AppData(context).saveEnvi("pre")
        }
    }
    var envi2 = AppData(context).getEnvi.collectAsState(initial = "")
    println("Envi2: ${envi2.value}")
    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(
            title = "Home",
            buttonIcon = Icons.Filled.Menu,
            almacenModel = almacenModel,
            onButtonClicked = { openDrawer() }
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Inicio")
        }
    }

}


/*
var mExpanded by remember { mutableStateOf(false) }
    // Create a string value to store the selected city
    var mSelectedText by remember { mutableStateOf("") }

    var mTextFieldSize by remember { mutableStateOf(Size.Zero)}

    // Up Icon when expanded and down icon when collapsed
    val icon = if (mExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(
            title = "Home",
            buttonIcon = Icons.Filled.Menu,
            almacenModel = almacenModel,
            onButtonClicked = { openDrawer() }
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Home Page content here.")

            OutlinedTextField(
                value = mSelectedText,
                onValueChange = { mSelectedText = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        // This value is used to assign to
                        // the DropDown the same width
                        mTextFieldSize = coordinates.size.toSize()
                    },
                label = {Text("Almacen")},
                trailingIcon = {
                    Icon(icon,"contentDescription",
                        Modifier.clickable { mExpanded = !mExpanded })
                }
            )

            // Create a drop-down menu with list of cities,
            // when clicked, set the Text Field text as the city selected
            DropdownMenu(
                expanded = mExpanded,
                onDismissRequest = { mExpanded = false },
                modifier = Modifier
                    .width(with(LocalDensity.current){mTextFieldSize.width.toDp()})
            ) {
                almacenes.forEach { almacen ->
                    DropdownMenuItem(onClick = {
                        mSelectedText = almacen.cajas_name
                        mExpanded = false
                    }) {
                        Text(text = almacen.cajas_name)
                    }
                }
            }

        }
    }
*/