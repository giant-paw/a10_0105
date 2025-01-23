package com.example.uas_pam.repository

import com.example.uas_pam.service.TanamanService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val tanamanRepository : TanamanRepository
}

class FarmContainer: AppContainer{
    private val baseUrl = "http://10.0.2.2/pamTA/"

    private val json = Json { ignoreUnknownKeys = true }

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl).build()

    private val tanamanService: TanamanService by lazy {
        retrofit.create(TanamanService::class.java)
    }

    override val tanamanRepository : TanamanRepository by lazy { NetworkTanamanRepository(tanamanService) }

}