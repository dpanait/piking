package com.yubstore.piking.service

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {

    @POST("piking_api")
    fun postOrders(@Body requestModel: SetPiking) : Call<PostPiking>
    @POST("piking_api")
    fun postProducts(@Body requestModel: SetProduct) : Call<PostProducts>
}