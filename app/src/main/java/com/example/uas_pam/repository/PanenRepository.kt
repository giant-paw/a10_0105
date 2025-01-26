package com.example.uas_pam.repository


import com.example.uas_pam.model.Aktivitas
import com.example.uas_pam.model.Panen
import com.example.uas_pam.service.PanenService
import okio.IOException

interface PanenRepository{
    suspend fun getPanen(): List<Panen>
    suspend fun insertPanen(panen: Panen)
    suspend fun updatePanen(idpanen: String, panen: Panen)
    suspend fun deletePanen(idpanen: String)
    suspend fun getPanenById(idpanen: String): Panen
}

class NetworkPanenRepository(private val panenApiService: PanenService): PanenRepository{
    override suspend fun insertPanen(panen: Panen){
        panenApiService.insertPanen(panen)
    }
    override suspend fun updatePanen(idpanen: String, panen: Panen){
        panenApiService.updatePanen(idpanen, panen)
    }

    override suspend fun deletePanen(idpanen: String){
        try {
            val response = panenApiService.deletePanen(idpanen)
            if (!response.isSuccessful){
                throw IOException("Failed to delete Panen. HTTP Status Code: ${response.code()}")
            } else{
                response.message()
                println(response.message())
            }
        }catch (e: Exception){
            throw e
        }
    }

    override suspend fun getPanen(): List<Panen> = panenApiService.getPanen()

    override suspend fun getPanenById(idpanen: String): Panen {
        return panenApiService.getPanenById(idpanen)
    }
}