package com.example.uas_pam.ui.viewmodel.aktivitas

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uas_pam.model.Aktivitas
import com.example.uas_pam.model.Tanaman
import com.example.uas_pam.repository.AktivitasRepository
import com.example.uas_pam.repository.PekerjaRepository
import com.example.uas_pam.repository.TanamanRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class UpdateAktivitasViewModel(
    private val akt: AktivitasRepository,
    private val tnmn: TanamanRepository,
    private val pkrj: PekerjaRepository,
) : ViewModel() {

    var updateAktivitastate by mutableStateOf(UpdateAktivitastate())
        private set

    val tanamanList: StateFlow<List<Tanaman>> = flow {
        emit(tnmn.getTanaman())
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    // Tanaman
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
                    .map { it.idtanaman }
            } catch (e: Exception) {
                idTanamanOptions = emptyList()
            }
        }
    }

    // Pekerja
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
                    .map { it.idpekerja }
            } catch (e: Exception) {
                idPekerjaOptions = emptyList()
            }
        }
    }

    fun updateUpdatePekerjaState(updateAktivitaEvent: UpdateAktivitasEvent) {
        updateAktivitastate = updateAktivitastate.copy(updateAktivitaEvent = updateAktivitaEvent)
    }

    fun getAktivitasId(id_aktivitas: String) {
        viewModelScope.launch {
            try {
                val aktivitas = akt.getAktivitasById(id_aktivitas)
                updateAktivitastate = UpdateAktivitastate(updateAktivitaEvent = aktivitas.toUpdateAktivitasEvent())
            } catch (e: Exception) {
                e.printStackTrace()
                updateAktivitastate = UpdateAktivitastate(isError = true, errorMessage = e.message)
            }
        }
    }

    fun updateAktivitas() {
        viewModelScope.launch {
            try {
                val aktivitas = updateAktivitastate.updateAktivitaEvent.toAkt()
                aktivitas.idaktivitas?.let { id ->
                    akt.updateAktivitas(id, aktivitas)
                    updateAktivitastate = updateAktivitastate.copy(isSuccess = true)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                updateAktivitastate = updateAktivitastate.copy(isError = true, errorMessage = e.message)
            }
        }
    }
}

data class UpdateAktivitastate(
    val updateAktivitaEvent: UpdateAktivitasEvent = UpdateAktivitasEvent(),
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null
)

data class UpdateAktivitasEvent(
    val idaktivitas: String = "",
    val idtanaman: String = "",
    val idpekerja: String = "",
    val tanggalaktivitas: String = "",
    val deskripsiaktivitas: String = "",
)

fun UpdateAktivitasEvent.toAkt(): Aktivitas = Aktivitas(
    idaktivitas = idaktivitas,
    idtanaman = idtanaman,
    idpekerja = idpekerja,
    tanggalaktivitas = tanggalaktivitas,
    deskripsiaktivitas = deskripsiaktivitas
)

fun Aktivitas.toUpdateAktivitasEvent(): UpdateAktivitasEvent = UpdateAktivitasEvent(
    idaktivitas = idaktivitas,
    idtanaman = idtanaman,
    idpekerja = idpekerja,
    tanggalaktivitas = tanggalaktivitas,
    deskripsiaktivitas = deskripsiaktivitas
)
