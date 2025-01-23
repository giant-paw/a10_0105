package com.example.uas_pam.ui.viewmodel.tanaman

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.uas_pam.model.Tanaman
import com.example.uas_pam.repository.TanamanRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

sealed class DetailTanamanState {
    data class Success(val tanaman: Tanaman) : DetailTanamanState()
    data class Error(val message: String) : DetailTanamanState()
    object Loading : DetailTanamanState()
}

class DetailTanamanViewModel(
    savedStateHandle: SavedStateHandle,
    private val tnmn: TanamanRepository
) : ViewModel() {
    private val idtanaman: String = checkNotNull(savedStateHandle["id_tanaman"])

    private val _detailUiState = MutableStateFlow<DetailTanamanState>(DetailTanamanState.Loading)
    val detailTanamanState: StateFlow<DetailTanamanState> = _detailUiState.asStateFlow()

    fun getTanaman(idtanaman: String) {
        viewModelScope.launch {
            _detailUiState.value = DetailTanamanState.Loading
            _detailUiState.value = try {
                val tanaman = tnmn.getTanamanById(idtanaman)
                DetailTanamanState.Success(tanaman)
            } catch (e: IOException) {
                DetailTanamanState.Error("Terjadi kesalahan jaringan")
            } catch (e: HttpException) {
                DetailTanamanState.Error("Terjadi kesalahan server")
            }
        }
    }
}