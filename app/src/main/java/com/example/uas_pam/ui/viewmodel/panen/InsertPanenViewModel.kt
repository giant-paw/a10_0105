package com.example.uas_pam.ui.viewmodel.panen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uas_pam.model.Panen
import com.example.uas_pam.model.Tanaman
import com.example.uas_pam.repository.PanenRepository
import com.example.uas_pam.repository.PekerjaRepository
import com.example.uas_pam.repository.TanamanRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class InsertPanenViewModel (
    private val akt: PanenRepository,
    private val tnmn: TanamanRepository,
    private val pkrj: PekerjaRepository

): ViewModel(){
    var insertPanenState by mutableStateOf(InsertPanenState())
        private set

    val tanamanList: StateFlow<List<Tanaman>> = flow {
        emit(tnmn.getTanaman())
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    var idTanamanOptions by mutableStateOf<List<String>>(emptyList())
        private set
    init {
        fetchTanamanList()
    }

    private fun fetchTanamanList() {
        viewModelScope.launch {
            try {
                val tanamanData = tnmn.getTanaman()
                idTanamanOptions = tanamanData
                    .mapNotNull { it.idtanaman.toIntOrNull() }
                    .sorted()
                    .map { it.toString() }
            } catch (e: Exception) {
                idTanamanOptions = emptyList()
            }
        }
    }


    fun updateInsertPanenState(insertPanenEvent: InsertPanenEvent){
        insertPanenState = InsertPanenState(insertPanenEvent = insertPanenEvent)
    }

    suspend fun inserPanen(){
        viewModelScope.launch {
            try {
                akt.insertPanen(insertPanenState.insertPanenEvent.toPanen())
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
}

data class InsertPanenState(
    val insertPanenEvent : InsertPanenEvent = InsertPanenEvent()
)

data class InsertPanenEvent(
    val idpanen: String ="",
    val idtanaman: String = "",
    val tanggalpanen: String = "",
    val jumlahpanen: String = "",
    val keterangan: String = ""
)

fun InsertPanenEvent.toPanen(): Panen = Panen(
    idpanen = idpanen,
    idtanaman = idtanaman,
    tanggalpanen = tanggalpanen,
    jumlahpanen = jumlahpanen,
    keterangan = keterangan
)

fun Panen.toUiStatePanen(): InsertPanenState = InsertPanenState(
    insertPanenEvent = toInsertPanenEvent()
)

fun Panen.toInsertPanenEvent():InsertPanenEvent = InsertPanenEvent(
    idpanen = idpanen,
    idtanaman = idtanaman,
    tanggalpanen = tanggalpanen,
    jumlahpanen = jumlahpanen,
    keterangan = keterangan
)