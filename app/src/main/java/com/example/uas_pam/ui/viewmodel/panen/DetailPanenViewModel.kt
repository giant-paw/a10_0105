package com.example.uas_pam.ui.viewmodel.panen


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.uas_pam.model.Panen
import com.example.uas_pam.repository.PanenRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

sealed class DetailPanenState {
    data class Success(val panen: Panen) : DetailPanenState()
    data class Error(val message: String) : DetailPanenState()
    object Loading : DetailPanenState()
}

class DetailPanenViewModel(
    savedStateHandle: SavedStateHandle,
    private val pn: PanenRepository
) : ViewModel() {

    private val idpanen: String = checkNotNull(savedStateHandle["id_panen"])

    private val _detailUiState = MutableStateFlow<DetailPanenState>(DetailPanenState.Loading)
    val detailPanenState: StateFlow<DetailPanenState> = _detailUiState.asStateFlow()

    fun getPanen(idpanen: String) {
        viewModelScope.launch {
            _detailUiState.value = DetailPanenState.Loading
            _detailUiState.value = try {
                val panen = pn.getPanenById(idpanen)
                DetailPanenState.Success(panen)
            } catch (e: IOException) {
                DetailPanenState.Error("Terjadi kesalahan jaringan")
            } catch (e: HttpException) {
                DetailPanenState.Error("Terjadi kesalahan server")
            }
        }
    }
}