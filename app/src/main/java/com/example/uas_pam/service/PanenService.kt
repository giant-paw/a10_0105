package com.example.uas_pam.service

import com.example.uas_pam.model.Panen
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface PanenService {
    @Headers(
        "Accept: application/json",
        "Content-type: application/json"
    )

    @GET("readpanen.php")
    suspend fun getPanen(): List<Panen>

    @GET("read1panen.php")
    suspend fun getPanenById(@Query("id_panen") id_panen: String): Panen

    @POST("insertpanen.php")
    suspend fun insertPanen(@Body panen: Panen)

    @PUT("updatepanen.php/")
    suspend fun updatePanen(@Query("id_panen") id_panen: String, @Body panen: Panen)

    @DELETE("deletepanen.php")
    suspend fun deletePanen(@Query("id_panen") id_panen: String): retrofit2.Response<Void>
}