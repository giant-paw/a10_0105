package com.example.uas_pam.service

import com.example.uas_pam.model.Tanaman
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface TanamanService {
    @Headers(
        "Accept: application/json",
        "Content-type: application/json"
    )

    @GET("readtanaman.php")
    suspend fun getTanaman(): List<Tanaman>

    @GET("read1tanaman.php")
    suspend fun getTanamanById(@Query("id_tanaman") id_tanaman: String): Tanaman

    @POST("inserttanaman.php")
    suspend fun insertTanaman(@Body tanaman: Tanaman)

    @PUT("updatetanaman.php/")
    suspend fun updateTanaman(@Query("id_tanaman") id_tanaman: String, @Body tanaman: Tanaman)

    @DELETE("deletetanaman.php")
    suspend fun deleteTanaman(@Query("id_tanaman") id_tanaman: String): retrofit2.Response<Void>
}