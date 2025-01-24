package com.example.uas_pam.ui.viewmodel.aktivitas

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uas_pam.model.Aktivitas
import com.example.uas_pam.model.Pekerja
import com.example.uas_pam.model.Tanaman
import com.example.uas_pam.repository.AktivitasRepository
import com.example.uas_pam.repository.PekerjaRepository
import com.example.uas_pam.repository.TanamanRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class InsertAktivitasViewModel (
    private val akt: AktivitasRepository,
    private val tnmn: TanamanRepository,
    private val pkrj: PekerjaRepository

): ViewModel(){
    var insertAktivitasState by mutableStateOf(InsertAktivitasState())
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

    // COBA COBA
    val pekerjaList: StateFlow<List<Pekerja>> = flow {
        emit(pkrj.getPekerja())
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    var idPekerjaOptions by mutableStateOf<List<String>>(emptyList())
        private set
    init {
        fetchPekerjaList()
    }

    private fun fetchPekerjaList() {
        viewModelScope.launch {
            try {
                val pekerjaData = pkrj.getPekerja()
                idPekerjaOptions = pekerjaData
                    .mapNotNull { it.idpekerja.toIntOrNull() }
                    .sorted()
                    .map { it.toString() }
            } catch (e: Exception) {
                idPekerjaOptions = emptyList()
            }
        }
    }

    fun updateInsertAktivitasState(insertAktivitasEvent: InsertAktivitasEvent){
        insertAktivitasState = InsertAktivitasState(insertAktivitasEvent = insertAktivitasEvent)
    }

    suspend fun inserAktivitas(){
        viewModelScope.launch {
            try {
                akt.insertAktivitas(insertAktivitasState.insertAktivitasEvent.toAktivitas())
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
}

data class InsertAktivitasState(
    val insertAktivitasEvent : InsertAktivitasEvent = InsertAktivitasEvent()
)

data class InsertAktivitasEvent(
    val idaktivitas: String ="",
    val idtanaman: String = "",
    val idpekerja: String = "",
    val tanggalaktivitas: String = "",
    val deskripsiaktivitas: String = ""
)

fun InsertAktivitasEvent.toAktivitas(): Aktivitas = Aktivitas(
    idaktivitas = idaktivitas,
    idtanaman = idtanaman,
    idpekerja = idpekerja,
    tanggalaktivitas = tanggalaktivitas,
    deskripsiaktivitas = deskripsiaktivitas
)

fun Aktivitas.toUiStateAktivitas(): InsertAktivitasState = InsertAktivitasState(
    insertAktivitasEvent = toInsertAktivitasEvent()
)

fun Aktivitas.toInsertAktivitasEvent():InsertAktivitasEvent = InsertAktivitasEvent(
    idaktivitas = idaktivitas,
    idtanaman = idtanaman,
    idpekerja = idpekerja,
    tanggalaktivitas = tanggalaktivitas,
    deskripsiaktivitas = deskripsiaktivitas
)