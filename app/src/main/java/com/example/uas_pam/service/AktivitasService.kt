package com.example.uas_pam.service

import com.example.uas_pam.model.Aktivitas
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface AktivitasService {
    @Headers(
        "Accept: application/json",
        "Content-type: application/json"
    )

    @GET("readaktivitas.php")
    suspend fun getAktivitas(): List<Aktivitas>

    @GET("read1aktivitas.php")
    suspend fun getAktivitasById(@Query("id_aktivitas") id_aktivitas: String): Aktivitas

    @POST("insertaktivitas.php")
    suspend fun insertAktivitas(@Body aktivitas: Aktivitas)

    @PUT("updateaktivitas.php/")
    suspend fun updateAktivitas(@Query("id_aktivitas") id_aktivitas: String, @Body aktivitas: Aktivitas)

    @DELETE("deleteaktivitas.php")
    suspend fun deleteAktivitas(@Query("id_aktivitas") id_aktivitas: String): retrofit2.Response<Void>
}