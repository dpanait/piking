package com.yubstore.piking.service

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiInterface {

    @POST("piking_api")
    fun postOrders(@Body requestModel: SetPiking) : Call<PostPiking>
    @POST("piking_api")
    fun postProducts(@Body requestModel: SetProduct) : Call<PostProducts>

    @GET("version.txt")
    fun getVersion(): Call<String>

    @POST("piking_api")
    fun savePiking(@Body requestModel: SavePiking) : Call<SavePostPiking>
    @POST("piking_api")
    fun saveInventory(@Body requestModel: SaveInventory) : Call<ResponsePostInventory>
    @POST("piking_api")
    fun saveMoveProducts(@Body requestModel: PostMoveProducts) : Call<ResponsePostMoveProducts>
}
