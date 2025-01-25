package com.example.uas_pam.ui.viewmodel.aktivitas

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.uas_pam.model.Aktivitas
import com.example.uas_pam.repository.AktivitasRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

sealed class DetailAktivitasState {
    data class Success(val aktivitas: Aktivitas) : DetailAktivitasState()
    data class Error(val message: String) : DetailAktivitasState()
    object Loading : DetailAktivitasState()
}

class DetailAktivitasViewModel(
    savedStateHandle: SavedStateHandle,
    private val akt: AktivitasRepository
) : ViewModel() {
    private val idaktivitas: String = checkNotNull(savedStateHandle["id_aktivitas"])

    private val _detailUiState = MutableStateFlow<DetailAktivitasState>(DetailAktivitasState.Loading)
    val detailAktivitasState: StateFlow<DetailAktivitasState> = _detailUiState.asStateFlow()

    fun getAktivitas(idaktivitas: String) {
        viewModelScope.launch {
            _detailUiState.value = DetailAktivitasState.Loading
            _detailUiState.value = try {
                val aktivitas = akt.getAktivitasById(idaktivitas)
                DetailAktivitasState.Success(aktivitas)
            } catch (e: IOException) {
                DetailAktivitasState.Error("Terjadi kesalahan jaringan")
            } catch (e: HttpException) {
                DetailAktivitasState.Error("Terjadi kesalahan server")
            }
        }
    }
}