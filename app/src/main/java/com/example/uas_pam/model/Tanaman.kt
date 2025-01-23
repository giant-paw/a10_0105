package com.example.uas_pam.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Tanaman(
    @SerialName("id_tanaman")
    val idtanaman : String,
    @SerialName("nama_tanaman")
    val namatanaman : String,
    @SerialName("periode_tanam")
    val periodetanam : String,
    val deskripsitanaman : String,
)
