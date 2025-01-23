package com.example.uas_pam.ui.viewmodel.tanaman

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uas_pam.model.Tanaman
import com.example.uas_pam.repository.TanamanRepository
import kotlinx.coroutines.launch

class InsertTanamanViewModel (
    private val tnmn: TanamanRepository,
): ViewModel(){
    var insertTanamanState by mutableStateOf(InsertTanamanState())
        private set


    fun updateInsertTanamanState(insertTanamanEvent: InsertTanamanEvent){
        insertTanamanState = InsertTanamanState(insertTanamanEvent = insertTanamanEvent)
    }

    suspend fun insertTanaman(){
        viewModelScope.launch {
            try {
                tnmn.insertTanaman(insertTanamanState.insertTanamanEvent.toTanaman())
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
}

data class InsertTanamanState(
    val insertTanamanEvent : InsertTanamanEvent = InsertTanamanEvent()
)

data class InsertTanamanEvent(
    val idtanaman: String ="",
    val namatanaman: String = "",
    val periodetanam: String = "",
    val deskripsitanaman: String = ""
)

fun InsertTanamanEvent.toTanaman(): Tanaman = Tanaman(
    idtanaman = idtanaman,
    namatanaman = namatanaman,
    periodetanam = periodetanam,
    deskripsitanaman = deskripsitanaman
)

fun Tanaman.toUiStateMahasiswa():InsertTanamanState = InsertTanamanState(
    insertTanamanEvent = toInsertTanamanEvent()
)

fun Tanaman.toInsertTanamanEvent():InsertTanamanEvent = InsertTanamanEvent(
    idtanaman = idtanaman,
    namatanaman = namatanaman,
    periodetanam = periodetanam,
    deskripsitanaman = deskripsitanaman
)