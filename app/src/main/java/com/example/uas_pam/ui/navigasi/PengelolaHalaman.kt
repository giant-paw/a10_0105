package com.example.uas_pam.ui.navigasi

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.uas_pam.ui.view.screen.MainMenuScreen
import com.example.uas_pam.ui.view.screen.MainScreen
import com.example.uas_pam.ui.view.tanaman.DestinasiHomeTanaman
import com.example.uas_pam.ui.view.tanaman.DestinasiInsertTanaman
import com.example.uas_pam.ui.view.tanaman.EntryTanamanScreen
import com.example.uas_pam.ui.view.tanaman.HomeTanamanScreen

@Composable
fun PengelolaHalaman(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = MainScreen.route,
        modifier = Modifier
    ) {

//        MAIN SCREEN / HALAMAN UTAMA
        composable(MainScreen.route) {
            MainMenuScreen(
                onNavigateToTanaman = { navController.navigate(DestinasiHomeTanaman.route) }
            )
        }

//        TAMPILAN HOME UNTUK TANAMAN
        composable(DestinasiHomeTanaman.route) {
            HomeTanamanScreen(
                navigateBack = { navController.navigateUp() },
                navigateToItemEntry = { navController.navigate(DestinasiInsertTanaman.route) },
                onDetailClick = {}
            )
        }

//        TAMPILAN INSERT TANAMAN
        composable(
            route = "${DestinasiInsertTanaman.route}",
        ) {
            EntryTanamanScreen(
                navigateBack = {
                    navController.navigate(DestinasiHomeTanaman.route) {
                        popUpTo(DestinasiHomeTanaman.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}
