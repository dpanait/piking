package com.yubstore.piking.model

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.yubstore.piking.service.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AlmacenModel: ViewModel() {
    private val _almacenesList = mutableStateListOf<Almacen>()//mutableStateListOf<Piking>()
    val almacenesList: SnapshotStateList<Almacen>
        get() = _almacenesList
    /*init {
        getAlmacenes(APP_DATA.IDCLIENTE)
    }*/
    fun getAlmacenes(idcliente: String){
        val setAlmacen =SetAlmacen(idcliente, "almacen")
        val response = ServiceBuilder.buildService(ApiInterface::class.java)
        response.postAlmacen(setAlmacen).enqueue(
            object : Callback<PostAlmacen> {
                override fun onResponse(
                    call: Call<PostAlmacen>,
                    response: Response<PostAlmacen>
                ) {
                    //Toast.makeText(this@MainActivity,response.message().toString(),Toast.LENGTH_LONG).show()
                    var alamcenes = response.body()
                    var alamcenesList = alamcenes?.body

                    if( response.isSuccessful){
                        _almacenesList.clear()
                        _almacenesList.addAll(alamcenesList!!)
                        //pikingListStatus.value = false

                    } //else {
                    response.errorBody()?.close()
                    //}
                    call.cancel()

                }

                override fun onFailure(call: Call<PostAlmacen>, t: Throwable) {
                    //Toast.makeText(this@MainActivity,t.toString(),Toast.LENGTH_LONG).show()

                    println("OnFailure Piking: ${t}, $call")


                    //errorPiking = t.message.toString()
                }

            }
        )
    }
}