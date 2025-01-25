package com.example.uas_pam.ui.viewmodel.panen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.uas_pam.model.Panen
import com.example.uas_pam.repository.PanenRepository
import kotlinx.coroutines.launch
import okio.IOException

sealed class HomePanenState{
    data class Success(val panen: List<Panen>): HomePanenState()
    object Error: HomePanenState()
    object Loading: HomePanenState()
}

class HomePanenViewModel (private val pn: PanenRepository): ViewModel(){
    var panenHomeState: HomePanenState by mutableStateOf(HomePanenState.Loading)
        private set

    init {
        getPn()
    }

    fun getPn(){
        viewModelScope.launch {
            panenHomeState = HomePanenState.Loading
            panenHomeState = try {
                HomePanenState.Success(pn.getPanen())
            }catch (e: IOException){
                HomePanenState.Error
            }catch (e: HttpException){
                HomePanenState.Error
            }
        }
    }

    fun deletePn(id_panen:String){
        viewModelScope.launch {
            try {
                pn.deletePanen(id_panen)
                getPn()
            }catch (e: IOException){
                HomePanenState.Error
            }catch (e: HttpException){
                HomePanenState.Error
            }
        }
    }
}