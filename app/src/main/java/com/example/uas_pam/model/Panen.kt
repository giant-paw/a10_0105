package com.example.uas_pam.model

import kotlinx.serialization.SerialName

data class Panen(
    @SerialName("id_panen")
    val idpanen : String,
    @SerialName("id_tanaman")
    val idtanaman : String,
    @SerialName("tanggal_panen")
    val tanggalpanen : String,
    @SerialName("jumlah_panen")
    val jumlahpanen : String,
    @SerialName("keterangan")
    val keterangan : String
)
