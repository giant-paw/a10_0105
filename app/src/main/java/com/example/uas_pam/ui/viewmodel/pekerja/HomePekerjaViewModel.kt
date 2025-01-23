package com.example.uas_pam.ui.viewmodel.pekerja

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.uas_pam.model.Pekerja
import com.example.uas_pam.repository.PekerjaRepository
import kotlinx.coroutines.launch
import okio.IOException

sealed class HomePekerjaState{
    data class Success(val pekerja: List<Pekerja>): HomePekerjaState()
    object Error: HomePekerjaState()
    object Loading: HomePekerjaState()
}

class HomePekerjaViewModel (private val pkrj: PekerjaRepository): ViewModel(){
    var kamarHomeState: HomePekerjaState by mutableStateOf(HomePekerjaState.Loading)
        private set

    init {
        getPekerja()
    }

    fun getPekerja(){
        viewModelScope.launch {
            kamarHomeState = HomePekerjaState.Loading
            kamarHomeState = try {
                HomePekerjaState.Success(pkrj.getPekerja())
            }catch (e: IOException){
                HomePekerjaState.Error
            }catch (e: HttpException){
                HomePekerjaState.Error
            }
        }
    }
}