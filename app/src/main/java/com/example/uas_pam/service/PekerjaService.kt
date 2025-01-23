package com.example.uas_pam.service

import com.example.uas_pam.model.Pekerja
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface PekerjaService {
    @Headers(
        "Accept: application/json",
        "Content-type: application/json"
    )
    @GET("readpekerja.php")
    suspend fun getPekerja(): List<Pekerja>

    @GET("read1Pekerja.php")
    suspend fun getPekerjaById(@Query("id_pekerja") id_pekerja: String): Pekerja

    @POST("insertpekerja.php")
    suspend fun insertPekerja(@Body pekerja: Pekerja)

    @PUT("updatepekerja.php")
    suspend fun updatePekerja(@Query("id_pekerja") id_pekerja: String, @Body pekerja: Pekerja)

    @DELETE("deletekamar.php")
    suspend fun deletePekerja(@Query("id_pekerja") id_pekerja: String): retrofit2.Response<Void>
}