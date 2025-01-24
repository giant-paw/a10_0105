package com.example.uas_pam.ui.navigasi

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.uas_pam.ui.view.pekerja.DestinasiDetailPekerja
import com.example.uas_pam.ui.view.pekerja.DestinasiHomePekerja
import com.example.uas_pam.ui.view.pekerja.DestinasiInsertPekerja
import com.example.uas_pam.ui.view.pekerja.DestinasiUpdatePekerja
import com.example.uas_pam.ui.view.pekerja.DetailPekerjaScreen
import com.example.uas_pam.ui.view.pekerja.EntryPekerjaScreen
import com.example.uas_pam.ui.view.pekerja.HomePekerjaScreen
import com.example.uas_pam.ui.view.pekerja.UpdatePekerjaScreen
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
                onNavigateToTanaman = { navController.navigate(DestinasiHomeTanaman.route) },
                onNavigateToPekerja = { navController.navigate(DestinasiHomePekerja.route) }
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
                navigateBack = { navController.navigateUp() },
                navigateToItemEntry = { navController.navigate(DestinasiInsertPekerja.route) },
                navigateToUpdate = { pekerja ->
                    navController.navigate("${DestinasiUpdatePekerja.route}/${pekerja.idpekerja}")
                },
                onDetailClick = {id_pekerja ->
                    navController.navigate("${DestinasiDetailPekerja.route}/$id_pekerja")
                }
            )
        }

        // NAVIGASI INSERT PEKERJA
        composable(
            route = DestinasiInsertPekerja.route,
        ) {
            EntryPekerjaScreen(
                navigateBack = {
                    navController.navigate(DestinasiHomePekerja.route) {
                        popUpTo(DestinasiHomePekerja.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        // NAVIGASI UPDATE PEKERJA
        composable(
            route = "${DestinasiUpdatePekerja.route}/{id_pekerja}",
            arguments = listOf(navArgument("id_pekerja") { type = NavType.StringType })
        ) { backStackEntry ->
            val id_pekerja = backStackEntry.arguments?.getString("id_pekerja") ?: return@composable
            UpdatePekerjaScreen(
                id_pekerja = id_pekerja,
                navigateBack = { navController.navigateUp() }
            )
        }

        // NAVIGASI DETAIL PEKERJA
        composable(
            route = "${DestinasiDetailPekerja.route}/{id_pekerja}",
            arguments = listOf(navArgument("id_pekerja") { type = NavType.StringType })
        ) { backStackEntry ->
            val id_pekerja = backStackEntry.arguments?.getString("id_pekerja") ?: return@composable
            DetailPekerjaScreen(
                id_pekerja = id_pekerja,
                navigateBack = { navController.navigateUp() },
                navController = navController
            )
        }
    }
}