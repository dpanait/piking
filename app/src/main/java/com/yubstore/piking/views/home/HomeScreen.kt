package com.yubstore.piking.views.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.yubstore.piking.service.*
import com.yubstore.piking.util.TopBar
import io.ktor.client.call.*
import io.ktor.client.statement.*
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun HomeScreen(navController: NavHostController, idcliente: String?, openDrawer: () -> Unit){
    println("Idcliente: $idcliente")
    val coroutineScope = rememberCoroutineScope()

    /*val almacenes = produceState(
        initialValue = PostAlmacen(false , listOf(Almacen("","", ""))),
        producer = {
            value = postAlmacen(SetAlmacen("1", "almacen"))
        }
    )*/
    /*var alm = remember {
        PostAlmacen(false , listOf(Almacen("","", "")))
    }
    coroutineScope.launch {
        try {
            alm = postAlmacen(SetAlmacen(idcliente, "almacen"))
        } catch (e: Exception) {
            // handle exception
            println("Error: $e")
        } finally {
            //someState.endProgress()
        }

    }*/
    val getAlmacenes: () -> Unit = {
        //var alm = PostAlmacen(false , listOf(Almacen("","", "")))
        //println("ALM fun: $alm")
        coroutineScope.launch {
            println("Id: $idcliente")

            kotlin.runCatching {
                postAlmacen(SetAlmacen(idcliente, "almacen"))

            }.onSuccess {
                val response = it.body<PostAlmacen>()
                println("Almacen: $response")
                println("almacen response: ${it.bodyAsText()}")
                //alm = it

                println("size: ${it}")
                coroutineScope.cancel()
            }.onFailure {
                println("Error: $it")
            }
        }
    }
    println("getAlmacenes: ${getAlmacenes}")
    getAlmacenes()
    //println("Almacenes: ${almacenes}")
    //println("Alm: $alm")
    /*println("Almacenes: ${almacenes.value.body}")
    println("Al: $alm")*/
    /*val vm by remember { mutableStateOf(LoginModel()) }

    var alma = vm.almacen()*/
    /*var almModel = LoginModel()
    var al = almModel.almacen()
    //var alma = LoginModel().almacen()
    println("Alma: $al")*/
    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(
            title = "Home",
            buttonIcon = Icons.Filled.Menu,
            onButtonClicked = { openDrawer() }
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Home Page content here.")
            /*if(almacenes.value.body.isNotEmpty()){
                Text(almacenes.value.body[0].toString())
            }*/
        }
    }
}