package com.example.uas_pam.repository

import com.example.uas_pam.model.Aktivitas
import com.example.uas_pam.service.AktivitasService
import okio.IOException


interface AktivitasRepository{
    suspend fun getAktivitas(): List<Aktivitas>
    suspend fun insertAktivitas(aktivitas: Aktivitas)
    suspend fun updateAktivitas(idaktivitas: String, aktivitas: Aktivitas)
    suspend fun deleteAktivitas(idaktivitas: String)
    suspend fun getAktivitasById(idaktivitas: String): Aktivitas
}

class NetworkAktivitasRepository(private val aktivitasApiService: AktivitasService): AktivitasRepository{
    override suspend fun insertAktivitas(aktivitas: Aktivitas){
        aktivitasApiService.insertAktivitas(aktivitas)
    }
    override suspend fun updateAktivitas(idaktivitas: String, aktivitas: Aktivitas){
        aktivitasApiService.updateAktivitas(idaktivitas, aktivitas)
    }

    override suspend fun deleteAktivitas(idaktivitas: String){
        try {
            val response = aktivitasApiService.deleteAktivitas(idaktivitas)
            if (!response.isSuccessful){
                throw IOException("Failed to delete Aktivitas. HTTP Status Code: ${response.code()}")
            } else{
                response.message()
                println(response.message())
            }
        }catch (e: Exception){
            throw e
        }
    }

    override suspend fun getAktivitas(): List<Aktivitas> = aktivitasApiService.getAktivitas()
    override suspend fun getAktivitasById(idaktivitas: String): Aktivitas {
        return aktivitasApiService.getAktivitasById(idaktivitas)
    }
}