package com.example.uas_pam.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class pekerja(
    @SerialName("id_pekerja")
    val idpekerja : String,
    @SerialName("nama_pekerja")
    val namapekerja : String,
    @SerialName("jabatan")
    val jabatan : String,
    @SerialName("kontak_pekerja")
    val kontakpekerja : String
)
