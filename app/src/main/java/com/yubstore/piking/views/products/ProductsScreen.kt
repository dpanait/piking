package com.yubstore.piking.views.products

import android.graphics.drawable.Icon
import android.media.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.navigation.NavHostController
import com.yubstore.piking.R
import com.yubstore.piking.model.LoginModel
import com.yubstore.piking.util.TopBar
import io.ktor.http.*

@Composable
fun ProductsScreen(
    navController: NavHostController,
    openDrawer: () -> Unit
) {
    //val image_almacen: Painter = painterResource(id = R.mipmap.ic_products)
    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(
            title = "Productos",
            buttonIcon = Icons.Rounded.Menu,
            onButtonClicked = { openDrawer() }
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(text = "Products Page content here.")
            /*if(almacenes.value.body.isNotEmpty()){
                Text(almacenes.value.body[0].toString())
            }*/
        }
    }
}