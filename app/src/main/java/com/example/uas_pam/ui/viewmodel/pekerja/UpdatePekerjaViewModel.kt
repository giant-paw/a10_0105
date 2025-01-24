package com.example.uas_pam.ui.viewmodel.pekerja


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uas_pam.model.Pekerja
import com.example.uas_pam.repository.PekerjaRepository
import kotlinx.coroutines.launch

class UpdatePekerjaViewModel(
    private val pkrj: PekerjaRepository
) : ViewModel() {

    var updatePekerjaState by mutableStateOf(UpdatePekerjaState())
        private set

    fun updateUpdatePekerjaState(updatePekerjaEvent: UpdatePekerjaEvent) {
        updatePekerjaState = updatePekerjaState.copy(updatePekerjaEvent = updatePekerjaEvent)
    }

    fun getPekerjaId(id_pekerja: String) {
        viewModelScope.launch {
            try {
                val pekerja = pkrj.getPekerjaById(id_pekerja)
                updatePekerjaState = UpdatePekerjaState(updatePekerjaEvent = pekerja.toUpdatePkrjEvent())
            } catch (e: Exception) {
                e.printStackTrace()
                updatePekerjaState = UpdatePekerjaState(isError = true, errorMessage = e.message)
            }
        }
    }

    fun updatePekerja() {
        viewModelScope.launch {
            try {
                val pekerja = updatePekerjaState.updatePekerjaEvent.toPkrj()
                pekerja.idpekerja?.let { id ->
                    pkrj.updatePekerja(id, pekerja)
                    updatePekerjaState = updatePekerjaState.copy(isSuccess = true)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                updatePekerjaState = updatePekerjaState.copy(isError = true, errorMessage = e.message)
            }
        }
    }
}
data class UpdatePekerjaState(
    val updatePekerjaEvent: UpdatePekerjaEvent = UpdatePekerjaEvent(),
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null
)

data class UpdatePekerjaEvent(
    val idpekerja: String = "",
    val namapekerja: String = "",
    val jabatan: String = "",
    val kontakpekerja: String = ""
)

fun UpdatePekerjaEvent.toPkrj(): Pekerja = Pekerja(
    idpekerja = idpekerja,
    namapekerja = namapekerja,
    jabatan = jabatan,
    kontakpekerja = kontakpekerja
)

fun Pekerja.toUpdatePkrjEvent(): UpdatePekerjaEvent = UpdatePekerjaEvent(
    idpekerja = idpekerja,
    namapekerja = namapekerja,
    jabatan = jabatan,
    kontakpekerja = kontakpekerja
)
