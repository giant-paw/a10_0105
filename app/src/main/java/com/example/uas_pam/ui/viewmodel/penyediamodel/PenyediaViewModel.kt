package com.example.uas_pam.ui.viewmodel.penyediamodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.uas_pam.FarmApplication
import com.example.uas_pam.ui.viewmodel.tanaman.HomeTanamanViewModel

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer { HomeTanamanViewModel(farmapp().container.tanamanRepository) }
    }
}

fun CreationExtras.farmapp(): FarmApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]as FarmApplication)