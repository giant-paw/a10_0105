package com.example.uas_pam.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Aktivitas(
    @SerialName("id_aktivitas")
    val idaktivitas : String,
    @SerialName("id_tanaman")
    val idtanaman : String,
    @SerialName("id_pekerja")
    val idpekerja : String,
    @SerialName("tanggal_aktivitas")
    val tanggalaktivitas : String,
    @SerialName("deskripsi_aktivitas")
    val deskripsi_aktivitas : String
)
