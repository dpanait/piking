package com.yubstore.piking.views.piking

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavHostController
import com.yubstore.piking.model.AlmacenModel
import com.yubstore.piking.service.APP_DATA
import com.yubstore.piking.util.SearchView
import com.yubstore.piking.util.TopBar
import com.yubstore.piking.views.common.LoadingAnimation

@Composable
fun PikingScreen(
    navController: NavHostController,
    iDCliente: String?,
    almacenModel: AlmacenModel,
    openDrawer: () -> Unit
) {
    //val image_almacen: Painter = painterResource(id = R.mipmap.ic_products)
    val textState = remember { mutableStateOf(TextFieldValue("")) }
    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(
            title = "Piking",
            buttonIcon = Icons.Rounded.Menu,
            almacenModel = almacenModel,
            onButtonClicked = { openDrawer() }
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
        ){
            SearchView(state = textState, Modifier.fillMaxWidth())
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){

                var idCliente = APP_DATA.IDCLIENTE;
                PikingList(navController, textState, idCliente)
                //Text(text = "Piking Page content here.")

            /*if(almacenes.value.body.isNotEmpty()){
                Text(almacenes.value.body[0].toString())
            }*/
        }

    }
}