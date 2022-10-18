package com.yubstore.piking.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yubstore.piking.service.PostLogin
import com.yubstore.piking.service.SetLogin
//import com.yubstore.piking.service.postLogin
import com.yubstore.piking.views.login.setLoginData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginModel: ViewModel() {
    var _login by mutableStateOf( PostLogin(false, 0, listOf()))

    fun login(){
        var login: PostLogin = PostLogin(false, 0, listOf())
        //_login = postLogin(SetLogin("22G3", "22GH", "4H9Y", "login"))

        /*viewModelScope.launch {
            //_login =  postLogin(SetLogin("22G3", "22GH", "4H9Y", "login"))
            //println("Model1: $_login")
            kotlin.runCatching {
                postLogin(SetLogin("22G3", "22GH", "4H9Y", "login"))

            }.onSuccess {
                println("login: $it")
                _login = it
                setLoginData(it)

            }.onFailure {
                println("Error: $it")
            }
        }*/
        /*GlobalScope.launch(Dispatchers.Main) {
            val login = postLogin(SetLogin("22G3", "22GH", "4H9Y", "login"))
            //()
           // setLoginData(login)
        }*/

    }

}