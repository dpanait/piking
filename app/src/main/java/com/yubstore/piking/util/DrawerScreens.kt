package com.yubstore.piking.util

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.yubstore.piking.R
import com.yubstore.piking.service.APP_DATA

sealed class DrawerScreens(val title: String, val route: String, var icon: Int, var param: String) {
    //val painter = painterResource(id = R.drawable.ic_logo)
    //object Home : DrawerScreens("Home", "home/{idcliente}", Icons.Filled.Home, "1")
    //object Account : DrawerScreens("Account", "account", Icons.Filled.AccountCircle,"")
    //object Products : DrawerScreens("Products", "products", Icons.Filled.ShoppingCart,"")
    //object Login : DrawerScreens( "Login", "login", Icons.Filled.Close, "")
    object Home : DrawerScreens("Home", "home/{idcliente}", R.mipmap.home, "1")
    object Account : DrawerScreens("Account", "account", R.mipmap.user,"")
    object Piking : DrawerScreens("Piking", "piking/{idcliente}", R.mipmap.piking,APP_DATA.IDCLIENTE)
    object Products : DrawerScreens("Inventario", "products", R.mipmap.envio/*Icons.Filled.ShoppingCart*/,"")
    object MoveProducts : DrawerScreens("Mover productos", "moveProducts", R.mipmap.envio/*Icons.Filled.ShoppingCart*/,"")
    object Login : DrawerScreens( "Login", "login", R.mipmap.login, "")
}
private val screens = listOf(
    DrawerScreens.Home,
    //DrawerScreens.Account,
    DrawerScreens.Piking,
    DrawerScreens.Products,
    DrawerScreens.MoveProducts,
    DrawerScreens.Login

)

@Composable
fun Drawer(
    modifier: Modifier = Modifier,
    selectedScreen: String,
    onDestinationClicked: (route: String, param: String) -> Unit,
    onMenuSelected: ((drawerScreen: DrawerScreens) -> Unit)? = null
) {
    Column(
        modifier
            //.fillMaxSize()
            .padding(start = 24.dp, top = 48.dp)
            .width(200.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.ic_iluminashop),
            contentDescription = "App icon"
        )
        //Icon(Icons.Rounded.Menu, contentDescription = "Localized description")
        screens.forEach { screen ->
            println("Screen. ${screen.route}")
            Spacer(Modifier.height(24.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ){
                //Image(imageVector = screen.icon, contentDescription = screen.title)
                Row(modifier = Modifier.height(40.dp)) {
                    ImageResourceLoad(screen.icon)
                    Box(
                        Modifier.height(40.dp)
                            //.background(Color.Green),
                                ,
                        Alignment.Center
                    ){
                        Text(
                            text = screen.title,
                            textAlign = TextAlign.Right,
                            style = MaterialTheme.typography.h6,
                            color = if (screen.route == selectedScreen) Color.Blue else Color.Black,
                            modifier = Modifier.clickable {
                                onDestinationClicked(screen.route, screen.param)
                                onMenuSelected?.invoke(screen)
                            }
                        )
                    }

                }
            }

        }
    }
}


@Composable
fun ImageResourceLoad(icon: Int) {
    val image: Painter = painterResource(id = icon)
    Box(
        Modifier
            .wrapContentSize()
            .width(40.dp)
            .height(40.dp)
            //.background(Color.Red),

        ){
        Image(
            painter = image,
            contentDescription = "",
            alignment = Alignment.Center,
            modifier = Modifier
                .width(20.dp)
                .aspectRatio(0.5F,false)
        )
    }

}
