package com.example.uas_pam.ui.navigasi

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.uas_pam.ui.view.pekerja.DestinasiHomePekerja
import com.example.uas_pam.ui.view.pekerja.HomePekerjaScreen
import com.example.uas_pam.ui.view.screen.MainMenuScreen
import com.example.uas_pam.ui.view.screen.MainScreen
import com.example.uas_pam.ui.view.tanaman.DestinasiDetailTanaman
import com.example.uas_pam.ui.view.tanaman.DestinasiHomeTanaman
import com.example.uas_pam.ui.view.tanaman.DestinasiInsertTanaman
import com.example.uas_pam.ui.view.tanaman.DestinasiUpdateTanaman
import com.example.uas_pam.ui.view.tanaman.DetailTanamanScreen
import com.example.uas_pam.ui.view.tanaman.EntryTanamanScreen
import com.example.uas_pam.ui.view.tanaman.HomeTanamanScreen
import com.example.uas_pam.ui.view.tanaman.UpdateTanamanScreen

@Composable
fun PengelolaHalaman(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = MainScreen.route,
        modifier = Modifier
    ) {

        // NAVIGASI MAIN SCREEN / HALAMAN UTAMA
        composable(MainScreen.route) {
            MainMenuScreen(
                onNavigateToTanaman = { navController.navigate(DestinasiHomeTanaman.route) }
            )
        }

        // NAVIGASI HOME UNTUK TANAMAN
        composable(DestinasiHomeTanaman.route) {
            HomeTanamanScreen(
                navigateBack = { navController.navigateUp() },
                navigateToItemEntry = { navController.navigate(DestinasiInsertTanaman.route) },
                onDetailClick = {id_tanaman ->
                    navController.navigate("${DestinasiDetailTanaman.route}/$id_tanaman")
                }
            )
        }

        // NAVIGASI INSERT TANAMAN
        composable(
            route = DestinasiInsertTanaman.route,
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

        // NAVIGASI DETAIL TANAMAN
        composable(
            route = "${DestinasiDetailTanaman.route}/{id_tanaman}",
            arguments = listOf(navArgument("id_tanaman") { type = NavType.StringType })
        ) { backStackEntry ->
            val id_tanaman = backStackEntry.arguments?.getString("id_tanaman") ?: return@composable
            DetailTanamanScreen(
                id_tanaman = id_tanaman,
                navigateBack = { navController.navigateUp() },
                navController = navController
            )
        }

        // NAVIGASI UPDATE TANAMAN
        composable(
            route = "${DestinasiUpdateTanaman.route}/{id_tanaman}",
            arguments = listOf(navArgument("id_tanaman") { type = NavType.StringType })
        ) { backStackEntry ->
            val id_tanaman = backStackEntry.arguments?.getString("id_tanaman") ?: return@composable
            UpdateTanamanScreen(
                id_tanaman = id_tanaman,
                navigateBack = { navController.navigateUp() }
            )
        }


        // NAVIGASI HOME PEKERJA
        composable(DestinasiHomePekerja.route) {
            HomePekerjaScreen(
                onDetailClick = { },
                navigateBack = { navController.navigateUp() },
                navigateToItemEntry = {  },
            )
        }
    }
}