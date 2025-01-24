package com.example.uas_pam.ui.viewmodel.aktivitas

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.uas_pam.model.Aktivitas
import com.example.uas_pam.repository.AktivitasRepository
import kotlinx.coroutines.launch
import okio.IOException

sealed class HomeAktivitasState{
    data class Success(val aktivitas: List<Aktivitas>): HomeAktivitasState()
    object Error: HomeAktivitasState()
    object Loading: HomeAktivitasState()
}

class HomeAktivitasViewModel (private val akt: AktivitasRepository): ViewModel(){
    var aktivitasHomeState: HomeAktivitasState by mutableStateOf(HomeAktivitasState.Loading)
        private set

    init {
        getAkt()
    }

    fun getAkt(){
        viewModelScope.launch {
            aktivitasHomeState = HomeAktivitasState.Loading
            aktivitasHomeState = try {
                HomeAktivitasState.Success(akt.getAktivitas())
            }catch (e: IOException){
                HomeAktivitasState.Error
            }catch (e: HttpException){
                HomeAktivitasState.Error
            }
        }
    }

    fun deleteAkt(id_aktivitas:String){
        viewModelScope.launch {
            try {
                akt.deleteAktivitas(id_aktivitas)
                getAkt()
            }catch (e: IOException){
                HomeAktivitasState.Error
            }catch (e: HttpException){
                HomeAktivitasState.Error
            }
        }
    }
}