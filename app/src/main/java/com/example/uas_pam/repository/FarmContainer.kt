package com.example.uas_pam.repository

import com.example.uas_pam.service.AktivitasService
import com.example.uas_pam.service.PekerjaService
import com.example.uas_pam.service.TanamanService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val tanamanRepository : TanamanRepository
    val pekerjaRepository : PekerjaRepository
    val aktivitasRepository : AktivitasRepository
}

class FarmContainer: AppContainer{
    private val baseUrl = "http://10.0.2.2/UASPAM/"

    private val json = Json { ignoreUnknownKeys = true }

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl).build()

    // Tanaman
    private val tanamanService: TanamanService by lazy {
        retrofit.create(TanamanService::class.java)
    }
    // Pekerja
    private val pekerjaService: PekerjaService by lazy { retrofit.create(PekerjaService::class.java) }
    // Aktivitas
    private val aktivitasService: AktivitasService by lazy { retrofit.create(AktivitasService::class.java) }

    // Tanaman
    override val tanamanRepository : TanamanRepository by lazy { NetworkTanamanRepository(tanamanService) }
    // Pekerja
    override val pekerjaRepository: PekerjaRepository by lazy { NetworkPekerjaRepository(pekerjaService) }
    
    // Aktivitas
    override val aktivitasRepository : AktivitasRepository by lazy { NetworkAktivitasRepository(aktivitasService) }

}