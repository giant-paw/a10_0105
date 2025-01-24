package com.example.uas_pam.ui.viewmodel.pekerja

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.uas_pam.model.Pekerja
import com.example.uas_pam.repository.PekerjaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

sealed class DetailPekerjaState {
    data class Success(val pekerja: Pekerja) : DetailPekerjaState()
    data class Error(val message: String) : DetailPekerjaState()
    object Loading : DetailPekerjaState()
}

class DetailPekerjaViewModel(
    savedStateHandle: SavedStateHandle,
    private val pkrj: PekerjaRepository
) : ViewModel() {
    private val idpekerja: String = checkNotNull(savedStateHandle["id_pekerja"])

    private val _detailUiState = MutableStateFlow<DetailPekerjaState>(DetailPekerjaState.Loading)
    val detailPekerjaState: StateFlow<DetailPekerjaState> = _detailUiState.asStateFlow()

    fun getPekerja(idpekerja: String) {
        viewModelScope.launch {
            _detailUiState.value = DetailPekerjaState.Loading
            _detailUiState.value = try {
                val pekerja = pkrj.getPekerjaById(idpekerja)
                DetailPekerjaState.Success(pekerja)
            } catch (e: IOException) {
                DetailPekerjaState.Error("Terjadi kesalahan jaringan")
            } catch (e: HttpException) {
                DetailPekerjaState.Error("Terjadi kesalahan server")
            }
        }
    }
}