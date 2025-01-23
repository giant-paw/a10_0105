package com.example.uas_pam.ui.viewmodel.tanaman

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uas_pam.model.Tanaman
import com.example.uas_pam.repository.TanamanRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class UpdateTanamanViewModel(
    private val tnmn: TanamanRepository
) : ViewModel() {

    var updateTanamanState by mutableStateOf(UpdateTanamanState())
        private set

    fun updateUpdateTanamanState(updateTanamanEvent: UpdateTanamanEvent) {
        updateTanamanState = updateTanamanState.copy(updateTanamanEvent = updateTanamanEvent)
    }

    fun getTanamanId(id_tanaman: String) {
        viewModelScope.launch {
            try {
                val tanaman = tnmn.getTanamanById(id_tanaman)
                updateTanamanState = UpdateTanamanState(updateTanamanEvent = tanaman.toUpdateTnmnEvent())
            } catch (e: Exception) {
                e.printStackTrace()
                updateTanamanState = UpdateTanamanState(isError = true, errorMessage = e.message)
            }
        }
    }

    fun updateTanaman() {
        viewModelScope.launch {
            try {
                val tanaman = updateTanamanState.updateTanamanEvent.toTnmn()
                tanaman.idtanaman?.let { id ->
                    tnmn.updateTanaman(id, tanaman)
                    updateTanamanState = updateTanamanState.copy(isSuccess = true)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                updateTanamanState = updateTanamanState.copy(isError = true, errorMessage = e.message)
            }
        }
    }
}
data class UpdateTanamanState(
    val updateTanamanEvent: UpdateTanamanEvent = UpdateTanamanEvent(),
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null
)

data class UpdateTanamanEvent(
    val idtanaman: String = "",
    val namatanaman: String = "",
    val periodetanam: String = "",
    val deskripsitanaman: String = ""
)

fun UpdateTanamanEvent.toTnmn(): Tanaman = Tanaman(
    idtanaman = idtanaman,
    namatanaman = namatanaman,
    periodetanam = periodetanam,
    deskripsitanaman = deskripsitanaman
)

fun Tanaman.toUpdateTnmnEvent(): UpdateTanamanEvent = UpdateTanamanEvent(
    idtanaman = idtanaman,
    namatanaman = namatanaman,
    periodetanam = periodetanam,
    deskripsitanaman = deskripsitanaman
)
