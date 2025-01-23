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
import com.example.uas_pam.ui.view.tanaman.HomeTanamanScreen

@Composable
fun PengelolaHalaman(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = MainScreen.route,
        modifier = Modifier
    ) {
        composable(MainScreen.route) {
            MainMenuScreen(
                onNavigateToTanaman = {navController.navigate(DestinasiHomeTanaman.route)}
            )
        }
    }
}
