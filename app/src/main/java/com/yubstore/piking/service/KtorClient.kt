package com.yubstore.piking.service

import android.util.Log
import com.yubstore.piking.service.KtorClient.json
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.cache.*
/*import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.json.serializer.*
//import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.**/
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.http.ContentType.Application.Json
import io.ktor.http.HttpHeaders.ContentEncoding
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

private const val TIME_OUT = 60_000
private const val TIME_OUT_MILI: Long = 1000 * 60 * 5
object KtorClient {
    private val json = kotlinx.serialization.json.Json {
        prettyPrint = true
        ignoreUnknownKeys = true
        isLenient = true
        encodeDefaults = false
    }
    val httpClient = HttpClient(Android){
        engine {
            // this: AndroidEngineConfig
            connectTimeout = TIME_OUT
            socketTimeout = TIME_OUT

        }
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
        install(Logging){
            logger = object : Logger {
                override fun log(message: String) {
                    Napier.v("HTTP Client", null, message)
                    Log.d("xapp-ktor", message )
                }
            }
            level = LogLevel.ALL
        }
        install(HttpTimeout) {
            socketTimeoutMillis = TIME_OUT_MILI
            requestTimeoutMillis = TIME_OUT_MILI
            connectTimeoutMillis = TIME_OUT_MILI
        }
        //install(HttpCache)


    }//.also { Napier.base(DebugAntilog()) }
    /*val httpClient = HttpClient {
        followRedirects = true

        install(JsonFeature){
            serializer = /*GsonSerializer {
                enableComplexMapKeySerialization()
                setPrettyPrinting()
                disableHtmlEscaping()
                serializeNulls()
                setLenient()
            }*/KotlinxSerializer(json)
            /*engine {
                connectTimeout = TIME_OUT
                socketTimeout = TIME_OUT
            }*/
        }
        install(Logging){
            logger = object : Logger {
                override fun log(message: String) {
                    Log.d("xapp-ktor", message )
                }
            }
            level = LogLevel.INFO
        }
        install(HttpTimeout){
            socketTimeoutMillis = 1000 * 60 * 5
            requestTimeoutMillis = 1000 * 60 * 5
            connectTimeoutMillis = 1000 * 60 * 5
        }

        defaultRequest {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }*/
}