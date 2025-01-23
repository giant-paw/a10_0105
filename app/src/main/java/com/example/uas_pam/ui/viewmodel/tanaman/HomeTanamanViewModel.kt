package com.example.uas_pam.ui.viewmodel.tanaman

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.uas_pam.model.Tanaman
import com.example.uas_pam.repository.TanamanRepository
import kotlinx.coroutines.launch
import okio.IOException

sealed class HomeTanamanState{
    data class Success(val tanaman: List<Tanaman>): HomeTanamanState()
    object Error: HomeTanamanState()
    object Loading: HomeTanamanState()
}

class HomeTanamanViewModel (private val tnmn: TanamanRepository): ViewModel(){
    var tanamanHomeState: HomeTanamanState by mutableStateOf(HomeTanamanState.Loading)
        private set

    init {
        getTnmn()
    }

    fun getTnmn(){
        viewModelScope.launch {
            tanamanHomeState = HomeTanamanState.Loading
            tanamanHomeState = try {
                HomeTanamanState.Success(tnmn.getTanaman())
            }catch (e: IOException){
                HomeTanamanState.Error
            }catch (e: HttpException){
                HomeTanamanState.Error
            }
        }
    }

    fun deleteTnmn(id_tanaman:String){
        viewModelScope.launch {
            try {
                tnmn.deleteTanaman(id_tanaman)
                getTnmn()
            }catch (e: IOException){
                HomeTanamanState.Error
            }catch (e: HttpException){
                HomeTanamanState.Error
            }
        }
    }
}