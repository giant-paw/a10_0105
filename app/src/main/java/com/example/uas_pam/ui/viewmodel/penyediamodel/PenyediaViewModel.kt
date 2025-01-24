package com.example.uas_pam.ui.viewmodel.penyediamodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.uas_pam.FarmApplication
import com.example.uas_pam.ui.viewmodel.pekerja.HomePekerjaViewModel
import com.example.uas_pam.ui.viewmodel.pekerja.InsertPekerjaViewModel
import com.example.uas_pam.ui.viewmodel.tanaman.DetailTanamanViewModel
import com.example.uas_pam.ui.viewmodel.tanaman.HomeTanamanViewModel
import com.example.uas_pam.ui.viewmodel.tanaman.InsertTanamanViewModel
import com.example.uas_pam.ui.viewmodel.tanaman.UpdateTanamanViewModel

object PenyediaViewModel {
    val Factory = viewModelFactory {

        // Home Tanaman
        initializer { HomeTanamanViewModel(farmapp().container.tanamanRepository) }
        // Insert Tanaman
        initializer { InsertTanamanViewModel(farmapp().container.tanamanRepository) }
        // Detail Tanaman
        initializer { DetailTanamanViewModel(createSavedStateHandle(),
            farmapp().container.tanamanRepository) }
        // Update Tanaman
        initializer { UpdateTanamanViewModel(farmapp().container.tanamanRepository) }

        // Home Pekerja
        initializer { HomePekerjaViewModel(farmapp().container.pekerjaRepository) }
        // Insert Pekerja
        initializer { InsertPekerjaViewModel(farmapp().container.pekerjaRepository) }
    }
}

fun CreationExtras.farmapp(): FarmApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]as FarmApplication)