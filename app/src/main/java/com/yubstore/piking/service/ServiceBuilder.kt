package com.yubstore.piking.service


import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object APP_DATA{
    val environment = "pre"
    var version = ""
    var userSku = "22G3"
    var IDCLIENTE = ""
    var cajasId = ""
    var storeName = ""
}

val urlVersion = "https://yuubbb.com/dev/version"
object URL_API_TEST {
    const val URL = "https://yuubbb.com/pre/dani/yuubbbshop/"

}
object URL_API {
    var URL = "https://yuubbb.com/pro/buy15.72/yuubbbshop/"

}
object URL_VERSION{
    const val URL = "https://yuubbb.com/dev/"
}

object ServiceBuilder {
    private val client = OkHttpClient.Builder()
        .callTimeout(20, TimeUnit.SECONDS)
        .build()



    var gson = GsonBuilder()
        .setLenient()
        .create()

    private val retrofit = Retrofit.Builder()
        .baseUrl(URL_API.URL) // change this IP for testing by your actual machine IP
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(client)
        .build()
    fun getRetrofit(url: String):Retrofit{
        return Retrofit.Builder()
            .baseUrl(url) // change this IP for testing by your actual machine IP
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
    }

    fun<T> buildService(service: Class<T>): T{

        if(APP_DATA.environment == "pro"){
            URL_API.URL = URL_API.URL.replace("00", "${APP_DATA.version}" )
        } else {
            URL_API.URL = URL_API_TEST.URL
        }
        println("Url: ${URL_API.URL}");
        val retrofit = Retrofit.Builder()
            .baseUrl(URL_API.URL) // change this IP for testing by your actual machine IP
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
        return retrofit.create(service)
    }


    fun<T> buildServiceUrl(service: Class<T>, url: String): T {
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
        return retrofit.create(service)
    }
}