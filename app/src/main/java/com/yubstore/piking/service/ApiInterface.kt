package com.yubstore.piking.service

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {

    @POST("/piking_api")
    fun sendReq(@Body requestModel: SetPiking) : Call<PostPiking>
}