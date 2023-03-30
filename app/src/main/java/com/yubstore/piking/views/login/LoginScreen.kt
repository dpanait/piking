package com.yubstore.piking.views.login

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
//import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.yubstore.piking.model.LoginModel
import com.yubstore.piking.service.*
//import androidx.compose.runtime.produceState
import com.yubstore.piking.util.ResponseParser
import io.ktor.client.call.*
import io.ktor.client.statement.*
import io.ktor.util.Identity.decode
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun LoginScreen(
    navController: NavHostController,
    viewmodel: LoginModel = LoginModel()
){
    var loginData by remember { mutableStateOf(PostLogin(false, 0, listOf()) )}
    val vm by remember { mutableStateOf(LoginModel()) }
    val coroutineScope = rememberCoroutineScope()
    var textError by remember{ mutableStateOf("")}
    viewmodel.getVersion()
    //val coroutineScope1 = rememberCoroutineScope()
    //val coroutineScope2 = rememberCoroutineScope()

   // var args = viewmodel.login()
    //println("Screen: $args")
    /*coroutineScope.launch {
        //_login =  postLogin(SetLogin("22G3", "22GH", "4H9Y", "login"))
        //println("Model1: $_login")
        kotlin.runCatching {
            postLogin(SetLogin("22G3", "22GH", "4H9Y", "login"))

        }.onSuccess {
            println("login: $it")
            loginData = it
            coroutineScope.cancel("Fin")

        }.onFailure {
            println("Error: $it")
        }
    }*/
    /*coroutineScope1.launch{
        coroutineScope1.broadcast<> { "Hola" }
    }*/

    /*val args = produceState(
        producer = {
            value = postLogin(SetLogin("22G3", "22GH", "4H9Y", "login"))
        },//PostLogin(false, 0 , emptyList()),
        initialValue = PostLogin(false, 0, listOf()),
    )*/
    //println("Login: $loginData")
    //var url = "https://yuubbb.com/pre/dani_bugs/yuubbbshop/piking_api";
    /*val formBody = FormBody.Builder()
        .add("code1", "22G3")
        .add("code2", "22GH")
        .add("code3", "4H9Y")
        .add("action", "login")
        .build()
    var args = vm.postVolley(ctx, performAction(resultPost) )*/
    //println("Response $args")
    //var args = PostLogin(false, 0, listOf())
    /*coroutineScope.launch {
        args = postLogin(SetLogin("22G3", "22GH", "4H9Y", "login"))
        /*try {
            args = postLogin(SetLogin("22G3", "22GH", "4H9Y", "login"))

        } catch (e: Exception) {
            // handle exception
            println("Error: $e")
        } finally {
            //someState.endProgress()
        }*/

    }
    println("ARGS: $args")*/
    /*var paramUrl = args.idcliente.toString()
    DrawerScreens.Home.param = paramUrl*/


        Text(
            text = textError,
            style = TextStyle(color = Color.Red, fontSize = 20.sp),
            modifier = Modifier.fillMaxWidth()
                .padding(5.dp),
            textAlign = TextAlign.Center
        )
        /*if(args.value.body.isNotEmpty()){
            Text(args.value.body[0].toString())
        }*/

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val code1 = remember { mutableStateOf(TextFieldValue()) }
            val code2 = remember { mutableStateOf(TextFieldValue()) }
            val code3 = remember { mutableStateOf(TextFieldValue()) }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {

                TextField(
                    label = { Text(text = "CodeI") },
                    value = code1.value,
                    onValueChange = {
                        if(it.text.length <= 5){
                            code1.value = it
                        }
                    },
                    singleLine = true,
                    modifier = Modifier.width(100.dp)
                )

                Spacer(modifier = Modifier.width(20.dp))
                TextField(
                    label = { Text(text = "CodeII") },
                    value = code2.value,
                    onValueChange = {

                        if(it.text.length <= 5){
                            code2.value = it
                        }
                    },
                    modifier = Modifier.width(100.dp)
                )

                Spacer(modifier = Modifier.width(20.dp))
                TextField(
                    label = { Text(text = "CodeIII") },
                    value = code3.value,
                    onValueChange = {
                        if(it.text.length <= 5){
                            code3.value = it
                        }
                    },
                    modifier = Modifier.width(100.dp)
                )

            }

            Spacer(modifier = Modifier.height(20.dp))
            Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            //_login =  postLogin(SetLogin("22G3", "22GH", "4H9Y", "login"))
                            //println("Model1: $_login")
                            kotlin.runCatching {
                                var code1 = code1.value.text.uppercase()
                                var code2 = code2.value.text.uppercase()
                                var code3 = code3.value.text.uppercase()
                                println("Code1: ${code1} Code2: ${code2} Code3: ${code3}")
                                postLogin(SetLogin(code1, code2, code3, "login"))

                            }.onSuccess {
                                val response = it.body<PostLogin>()
                                println("login: ${response}")
                                //loginData = it
                                var idcliente = response.idcliente
                                var status = response.status
                                /// configuramos datos necesarios del cliente
                                APP_DATA.IDCLIENTE = idcliente.toString()
                                APP_DATA.userSku = code1.value.text.uppercase()

                                if(status){
                                    navController.navigate("home/$idcliente")
                                    coroutineScope.cancel("Fin")
                                } else {
                                    textError = "El codigo introducido no es correcto"
                                }


                            }.onFailure {
                                println("Error: $it")
                            }
                        }
                    },
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text(text = "Login")
                }
            }
            /*TextField(
                label = { Text(text = "Password") },
                value = password.value,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                onValueChange = { password.value = it })*/

            /*Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Login Page content here.")
            Button(onClick = { navController.navigate("home/1") }) {
                Text(text = "Login")
            }*/
        }


}

@JvmName("setLoginData1")
fun setLoginData(login: PostLogin){

}
