package com.example.uas_pam.ui.viewmodel.pekerja

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uas_pam.model.Pekerja
import com.example.uas_pam.repository.PekerjaRepository
import kotlinx.coroutines.launch

class InsertPekerjaViewModel (
    private val pkrj: PekerjaRepository,
): ViewModel(){
    var insertPekerjaState by mutableStateOf(InsertPekerjaState())
        private set


    fun updateInsertPekerjaState(insertPekerjaEvent: InsertPekerjaEvent){
        insertPekerjaState = InsertPekerjaState(insertPekerjaEvent = insertPekerjaEvent)
    }

    suspend fun insertPekerja(){
        viewModelScope.launch {
            try {
                pkrj.insertPekerja(insertPekerjaState.insertPekerjaEvent.toPekerja())
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
}

data class InsertPekerjaState(
    val insertPekerjaEvent : InsertPekerjaEvent = InsertPekerjaEvent()
)

data class InsertPekerjaEvent(
    val idpekerja: String ="",
    val namapekerja: String = "",
    val jabatan: String = "",
    val kontakpekerja: String = ""
)

fun InsertPekerjaEvent.toPekerja(): Pekerja = Pekerja(
    idpekerja = idpekerja,
    namapekerja = namapekerja,
    jabatan = jabatan,
    kontakpekerja = kontakpekerja
)

fun Pekerja.toUiStatePekerja():InsertPekerjaState = InsertPekerjaState(
    insertPekerjaEvent = toInsertPekerjaEvent()
)

fun Pekerja.toInsertPekerjaEvent():InsertPekerjaEvent = InsertPekerjaEvent(
    idpekerja = idpekerja,
    namapekerja = namapekerja,
    jabatan = jabatan,
    kontakpekerja = kontakpekerja
)