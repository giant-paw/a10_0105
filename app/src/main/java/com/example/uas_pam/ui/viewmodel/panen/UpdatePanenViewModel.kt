package com.example.uas_pam.ui.viewmodel.panen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uas_pam.model.Panen
import com.example.uas_pam.model.Tanaman
import com.example.uas_pam.repository.PanenRepository
import com.example.uas_pam.repository.TanamanRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class UpdatePanenViewModel(
    private val pn: PanenRepository,
    private val tnmn: TanamanRepository,
) : ViewModel() {

    var updatePanenState by mutableStateOf(UpdatePanenState())
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

    fun updateUpdatePanenState(updatePanenEvent: UpdatePanenEvent) {
        updatePanenState = updatePanenState.copy(updatePanenEvent = updatePanenEvent)
    }

    fun getPanenId(idpanen: String) {
        viewModelScope.launch {
            try {
                val panen = pn.getPanenById(idpanen)
                updatePanenState = UpdatePanenState(updatePanenEvent = panen.toUpdatPanenEvent())
            } catch (e: Exception) {
                e.printStackTrace()
                updatePanenState = UpdatePanenState(isError = true, errorMessage = e.message)
            }
        }
    }

    fun updatePanen() {
        viewModelScope.launch {
            try {
                val panen = updatePanenState.updatePanenEvent.toPn()
                panen.idpanen?.let { id ->
                    pn.updatePanen(id, panen)
                    updatePanenState = updatePanenState.copy(isSuccess = true)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                updatePanenState = updatePanenState.copy(isError = true, errorMessage = e.message)
            }
        }
    }
}

data class UpdatePanenState(
    val updatePanenEvent: UpdatePanenEvent = UpdatePanenEvent(),
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null
)

data class UpdatePanenEvent(
    val idpanen: String = "",
    val idtanaman: String = "",
    val tanggalpanen: String = "",
    val jumlahpanen: String = "",
    val keterangan: String = "",
)

fun UpdatePanenEvent.toPn(): Panen = Panen(
    idpanen = idpanen,
    idtanaman = idtanaman,
    tanggalpanen = tanggalpanen,
    jumlahpanen = jumlahpanen,
    keterangan = keterangan
)

fun Panen.toUpdatPanenEvent(): UpdatePanenEvent = UpdatePanenEvent(
    idpanen = idpanen,
    idtanaman = idtanaman,
    tanggalpanen = tanggalpanen,
    jumlahpanen = jumlahpanen,
    keterangan = keterangan
)
