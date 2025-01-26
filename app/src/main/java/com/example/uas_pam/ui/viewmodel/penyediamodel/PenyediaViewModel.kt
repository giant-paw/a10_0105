package com.example.uas_pam.ui.viewmodel.penyediamodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.uas_pam.FarmApplication
import com.example.uas_pam.ui.viewmodel.aktivitas.DetailAktivitasViewModel
import com.example.uas_pam.ui.viewmodel.aktivitas.HomeAktivitasViewModel
import com.example.uas_pam.ui.viewmodel.aktivitas.InsertAktivitasViewModel
import com.example.uas_pam.ui.viewmodel.aktivitas.UpdateAktivitasViewModel
import com.example.uas_pam.ui.viewmodel.panen.DetailPanenViewModel
import com.example.uas_pam.ui.viewmodel.panen.HomePanenViewModel
import com.example.uas_pam.ui.viewmodel.panen.InsertPanenViewModel
import com.example.uas_pam.ui.viewmodel.pekerja.DetailPekerjaViewModel
import com.example.uas_pam.ui.viewmodel.pekerja.HomePekerjaViewModel
import com.example.uas_pam.ui.viewmodel.pekerja.InsertPekerjaViewModel
import com.example.uas_pam.ui.viewmodel.pekerja.UpdatePekerjaViewModel
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
            farmapp().container.tanamanRepository)
        }
        // Update Tanaman
        initializer { UpdateTanamanViewModel(farmapp().container.tanamanRepository) }


        // Home Pekerja
        initializer { HomePekerjaViewModel(farmapp().container.pekerjaRepository) }
        // Insert Pekerja
        initializer { InsertPekerjaViewModel(farmapp().container.pekerjaRepository) }
        // Update Pekerja
        initializer { UpdatePekerjaViewModel(farmapp().container.pekerjaRepository) }
        // Detail Pekerja
        initializer { DetailPekerjaViewModel(createSavedStateHandle(),
            farmapp().container.pekerjaRepository)
        }


        // Home Aktivitas
        initializer { HomeAktivitasViewModel(farmapp().container.aktivitasRepository) }
        // Insert Aktivitas
        initializer { InsertAktivitasViewModel(
            farmapp().container.aktivitasRepository, farmapp().container.tanamanRepository, farmapp().container.pekerjaRepository) }
        // Detail Aktivitas
        initializer { DetailAktivitasViewModel(createSavedStateHandle(),
            farmapp().container.aktivitasRepository)
        }
        // Update Aktivitas
        initializer { UpdateAktivitasViewModel(
            farmapp().container.aktivitasRepository, farmapp().container.tanamanRepository, farmapp().container.pekerjaRepository) }


        // Home Catatan Panen
        initializer { HomePanenViewModel(farmapp().container.panenRepository) }
        // Detail Catatan Panen
        initializer { DetailPanenViewModel(createSavedStateHandle(),
            farmapp().container.panenRepository)
        }
        // Insert Catatan Panen
        initializer { InsertPanenViewModel(
            farmapp().container.panenRepository,
            farmapp().container.tanamanRepository) }

    }
}

fun CreationExtras.farmapp(): FarmApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]as FarmApplication)