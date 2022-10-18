package com.yubstore.piking.service

import android.content.ContentValues.TAG
import android.os.Parcel
import android.os.Parcelable
import android.provider.SyncStateContract.Helpers.update
import android.util.Log
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.ResponseResultOf
import com.github.kittinunf.fuel.core.extensions.cUrlString
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.core.requests.CancellableRequest
import com.yubstore.piking.service.KtorClient.httpClient
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import kotlinx.serialization.Serializable
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.client.utils.EmptyContent.contentType
import io.ktor.http.*
import io.ktor.util.*
import kotlinx.coroutines.isActive
import kotlinx.serialization.*
import kotlinx.serialization.SerialName

@Serializable
data class SetLogin (
    //@SerialName("code1")
    val code1: String,
    //@SerialName("code2")
    val code2: String,
    //@SerialName("code3")
    val code3: String,
    //@SerialName("action")
    val action: String
)

// response login data
@Serializable
data class PostLogin(
    //@SerializedName("status")
    val status: Boolean,
    //@SerializedName("idcliente")
    val idcliente: Int,
    //@SerializedName("body")
    val body: List<String>
)

// login fucntion
@OptIn(InternalAPI::class)
suspend fun postLogin(setlogin: SetLogin): HttpResponse {
    /*return KtorClient.httpClient.use {
        it.post("https://yuubbb.com/pre/dani_bugs/yuubbbshop/piking_api"){
            contentType(ContentType.Application.Json)//Application.Json
            body = setlogin
        }
    }*/
    val response: HttpResponse =  httpClient.post("https://yuubbb.com/pre/dani_bugs/yuubbbshop/piking_api"){
        //contentType(ContentType.Application.Json)//Application.Json
        header(HttpHeaders.ContentType,ContentType.Application.Json)
        setBody(setlogin)

    }
    return response
}

@Serializable
data class SetAlmacen(
    val idcliente: String?,
    val action: String
)

// response almacen
@Serializable
data class PostAlmacen(
    val status: Boolean,
    val post: String,
    val body: ArrayList<Almacen>
)

// objeto almacen
@Serializable
data class Almacen(
    val cajas_id: String,
    val cajas_name: String,
    val cajas_name_Y: String
)

// almacen funcion
@OptIn(InternalAPI::class)
suspend fun postAlmacen(setAlmacen: SetAlmacen): HttpResponse {
    /*return KtorClient.httpClient.use {
        it.post("https://yuubbb.com/pre/dani_bugs/yuubbbshop/piking_api"){
            contentType(ContentType.Application.Json)//Application.Json
            body = setAlmacen
        }

    }*/
    val response: HttpResponse =  httpClient.post("https://yuubbb.com/pre/dani/yuubbbshop/piking_api"){
        header(HttpHeaders.ContentType,ContentType.Application.Json)
        setBody(setAlmacen)
    }
    return response

}
@Serializable
data class SetPiking(
    val idcliente: String?,
    val action: String
)
// response piking
@Serializable
data class PostPiking(
    val status: Boolean,
    val post: String,
    val body: ArrayList<Piking>
)
@Serializable
data class Piking(
    val orders_id: String,
    val date_purchased: String,
    val orders_type_id: String,
    val IDCLIENTE: String,
    val envios_estados_id: String,
    val city: String?,
    val postcode: String?
    //val lines_products: ArrayList<Products>
)

// objeto Productos
@Serializable
data class SetProduct(
    val idcliente: String,
    val orders_id: String,
    val action: String
)

@Serializable
data class PostProducts(
    val status: Boolean,
    val post: String,
    val body: ArrayList<Products>
)

@Serializable
data class Products(
    val orders_id: String,
    val orders_products_id: String,
    val products_id: String,
    val products_sku: String,
    val barcode: String,
    val products_name: String,
    val products_quantity: String,
    val image: String?
)
// almacen funcion
@OptIn(InternalAPI::class)
suspend fun postPiking(setPiking: SetPiking): HttpResponse {
    /*val response = httpClient.use {
        it.post("https://yuubbb.com/pre/dani_bugs/yuubbbshop/piking_api"){
            header(HttpHeaders.ContentType,ContentType.Application.Json)
            setBody(setPiking)
        }

    }*/
    val response: HttpResponse = httpClient.post("https://yuubbb.com/pre/dani/yuubbbshop/piking_api") {
        contentType(ContentType.Application.Json)//Application.Json
        setBody(setPiking)
        timeout {
            requestTimeoutMillis = 6000
        }

    }
    return response
}
// productos
suspend fun postProducts(setProducts: SetProduct): HttpResponse{
    val response: HttpResponse = httpClient.post("https://yuubbb.com/pre/dani/yuubbbshop/piking_api") {
        contentType(ContentType.Application.Json)//Application.Json
        setBody(setProducts)

    }
    return response
}
/*suspend fun Test(setPiking: SetPiking){
    var meals: PostPiking? = null
    try{
        meals = httpClient.post("https://yuubbb.com/pre/dani/yuubbbshop/piking_api") {
            contentType(ContentType.Application.Json)//Application.Json
            body = setPiking

        }
    } catch (e: NoTransformationFoundException){
        val mealsString: String = httpClient.post("https://yuubbb.com/pre/dani/yuubbbshop/piking_api") {
            contentType(ContentType.Application.Json)//Application.Json
            body = setPiking

        }
           /* KtorClient.httpClient.use {
                it.post("https://yuubbb.com/pre/dani/yuubbbshop/piking_api"){
                    contentType(ContentType.Application.Json)//Application.Json
                    body = setPiking
                }

            }*/
        println("mealsString: $mealsString")
        val json = kotlinx.serialization.json.Json {
            ignoreUnknownKeys = true
            isLenient = true
            encodeDefaults = false
            prettyPrint = true
        }
        /*meals = json.decodeFromString<PostPiking>(mealsString)
        println("response: $meals")*/
    } finally {
        println("Meals: $meals")
    }

}*/
fun testFuel(): CancellableRequest {
    /*val (_, _, result) = "https://yuubbb.com/pre/dani_bugs/yuubbbshop/piking_api"
        .httpPost(listOf("action" to "", "idcliente" to "192")
            .responseString()*/
    val json = kotlinx.serialization.json.Json {
        ignoreUnknownKeys = true
        /*isLenient = true
        encodeDefaults = false
        prettyPrint = true*/
    }
    //var (_, _, result) =
        return Fuel.post("https://yuubbb.com/pre/dani/yuubbbshop/piking_api", listOf("action" to "get_picking", "idcliente" to "192"))
        //.jsonBody(listOf("action" to "", "idcliente" to "192"))
        .also { Log.d(TAG, it.cUrlString()) }
        .responseString{ _, _, result ->
            //println(result)
            //return result.toString()
        }
    //println("Result: $result")
    //var meals = json.decodeFromString<PostPiking>(result.toString())
    //println("response: $meals")
}
