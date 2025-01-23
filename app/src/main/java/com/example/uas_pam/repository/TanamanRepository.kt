package com.example.uas_pam.repository

import com.example.uas_pam.model.Tanaman
import com.example.uas_pam.service.TanamanService
import okio.IOException

interface TanamanRepository {
    suspend fun getTanaman(): List<Tanaman>
    suspend fun insertTanaman(tanaman: Tanaman)
    suspend fun updateTanaman(idtanaman: String, tanaman: Tanaman)
    suspend fun deleteTanaman(idtanaman: String)
    suspend fun getTanamanById(idtanaman: String): Tanaman
}

class NetworkTanamanRepository(private val tanamanApiService: TanamanService): TanamanRepository{
    override suspend fun insertTanaman(tanaman: Tanaman){
        tanamanApiService.insertTanaman(tanaman)
    }
    override suspend fun updateTanaman(idtanaman: String, tanaman: Tanaman){
        tanamanApiService.updateTanaman(idtanaman, tanaman)
    }

    override suspend fun deleteTanaman(idtanaman: String){
        try {
            val response = tanamanApiService.deleteTanaman(idtanaman)
            if (!response.isSuccessful){
                throw IOException("Failed to delete tanaman. HTTP Status Code: ${response.code()}")
            } else{
                response.message()
                println(response.message())
            }
        }catch (e: Exception){
            throw e
        }
    }

    override suspend fun getTanaman(): List<Tanaman> = tanamanApiService.getTanaman()
    override suspend fun getTanamanById(idtanaman: String): Tanaman {
        return tanamanApiService.getTanamanById(idtanaman)
    }
}