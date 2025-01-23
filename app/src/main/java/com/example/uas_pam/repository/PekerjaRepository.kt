package com.example.uas_pam.repository

import com.example.uas_pam.model.Pekerja
import com.example.uas_pam.service.PekerjaService
import java.io.IOException

interface PekerjaRepository{
    suspend fun getPekerja(): List<Pekerja>
    suspend fun insertPekerja(pekerja: Pekerja)
    suspend fun updatePekerja(idpekerja: String, pekerja: Pekerja)
    suspend fun deletePekerja(idpekerja: String)
    suspend fun getPekerjaById(idpekerja: String): Pekerja
}

class NetworkPekerjaRepository(private val pekerjaApiService: PekerjaService): PekerjaRepository{
    override suspend fun getPekerja(): List<Pekerja> = pekerjaApiService.getPekerja()

    override suspend fun getPekerjaById(idpekerja: String): Pekerja {
        return pekerjaApiService.getPekerjaById(idpekerja)
    }
    override suspend fun insertPekerja(pekerja: Pekerja){
        pekerjaApiService.insertPekerja(pekerja)
    }
    override suspend fun updatePekerja(idpekerja: String, pekerja: Pekerja){
        pekerjaApiService.updatePekerja(idpekerja, pekerja)
    }

    override suspend fun deletePekerja(idpekerja: String){
        try {
            val response = pekerjaApiService.deletePekerja(idpekerja)
            if (!response.isSuccessful){
                throw IOException("Failed to delete pekerja. HTTP Status Code: ${response.code()}")
            } else{
                response.message()
                println(response.message())
            }
        }catch (e: Exception){
            throw e
        }
    }
}