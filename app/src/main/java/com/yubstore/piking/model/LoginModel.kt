package com.yubstore.piking.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
//import com.yubstore.piking.data.ItemDao
import com.yubstore.piking.service.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//import com.yubstore.piking.service.postLogin



class LoginModel: ViewModel(){
    var _login by mutableStateOf( PostLogin(false, 0, listOf()))


    init{
        getVersion()
    }
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
    fun getVersion(){
        if(APP_DATA.version.isEmpty()) {
            val responseBuilder =
                ServiceBuilder.buildServiceUrl(ApiInterface::class.java, URL_VERSION.URL) //ServiceBuilder.buildService(ApiInterface::class.java).getVersion(URL_VERSION.URL)

            responseBuilder.getVersion().enqueue(
                object : Callback<String> {
                    override fun onResponse(call: Call<String>, response: Response<String>) {

                        println("Response: $response")
                        if (response.code() == 200) {
                            val version = response.body()
                            println("Reponse version: $version")
                            APP_DATA.version = version!!
                        }

                    }

                    override fun onFailure(call: Call<String>, t: Throwable) {

                        println("Error: ${t.message}")
                    }

                }
            )
        }
    }

}