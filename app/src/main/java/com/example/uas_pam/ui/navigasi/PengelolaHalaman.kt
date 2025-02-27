package com.example.uas_pam.ui.navigasi

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.uas_pam.ui.view.aktivitas.DestinasiDetailAktivitas
import com.example.uas_pam.ui.view.aktivitas.DestinasiHomeAktivitas
import com.example.uas_pam.ui.view.aktivitas.DestinasiInsertAktivitas
import com.example.uas_pam.ui.view.aktivitas.DestinasiUpdateAktivitas
import com.example.uas_pam.ui.view.aktivitas.DetailAktivitasScreen
import com.example.uas_pam.ui.view.aktivitas.EntryAktivitasScreen
import com.example.uas_pam.ui.view.aktivitas.HomeAktivitasScreen
import com.example.uas_pam.ui.view.aktivitas.UpdateAktivitasScreen
import com.example.uas_pam.ui.view.panen.DestinasiDetailPanen
import com.example.uas_pam.ui.view.panen.DestinasiHomePanen
import com.example.uas_pam.ui.view.panen.DestinasiInsertPanen
import com.example.uas_pam.ui.view.panen.DestinasiUpdatePanen
import com.example.uas_pam.ui.view.panen.DetailPanenScreen
import com.example.uas_pam.ui.view.panen.EntryPanenScreen
import com.example.uas_pam.ui.view.panen.HomePanenScreen
import com.example.uas_pam.ui.view.panen.UpdatePanenScreen
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

        // HALAMAN UTAMA
        // NAVIGASI MAIN SCREEN / HALAMAN UTAMA
        composable(MainScreen.route) {
            MainMenuScreen(
                onNavigateToTanaman = { navController.navigate(DestinasiHomeTanaman.route) },
                onNavigateToPekerja = { navController.navigate(DestinasiHomePekerja.route) },
                onNavigateToAktivitas = { navController.navigate(DestinasiHomeAktivitas.route) },
                onNavigateToPanen = { navController.navigate(DestinasiHomePanen.route) },
            )
        }



        // TANAMAN
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



        // PEKERJA
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



        // AKTIVITAS PERTANIAN
        // NAVIGASI HOME AKTIVITAS PERTANIAN
        composable(DestinasiHomeAktivitas.route) {
            HomeAktivitasScreen(
                navigateBack = { navController.navigateUp() },
                navigateToItemEntry = { navController.navigate(DestinasiInsertAktivitas.route) },
                navigateToUpdate = { aktivitas ->
                    navController.navigate("${DestinasiUpdateAktivitas.route}/${aktivitas.idaktivitas}")
                },
                onDetailClick = {id_aktivitas ->
                    navController.navigate("${DestinasiDetailAktivitas.route}/$id_aktivitas")
                }
            )
        }

        // NAVIGASI INSERT AKTIVITAS PERTANIAN
        composable(
            route = DestinasiInsertAktivitas.route,
        ) {
            EntryAktivitasScreen(
                navigateBack = {
                    navController.navigate(DestinasiHomeAktivitas.route) {
                        popUpTo(DestinasiHomeAktivitas.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        // NAVIGASI DETAIL AKTIVITAS PERTANIAN
        composable(
            route = "${DestinasiDetailAktivitas.route}/{id_aktivitas}",
            arguments = listOf(navArgument("id_aktivitas") { type = NavType.StringType })
        ) { backStackEntry ->
            val id_aktivitas = backStackEntry.arguments?.getString("id_aktivitas") ?: return@composable
            DetailAktivitasScreen(
                id_aktivitas = id_aktivitas,
                navigateBack = { navController.navigateUp() },
                navController = navController
            )
        }

        // NAVIGASI UPDATE AKTIVITAS PERTANIAN
        composable(
            route = "${DestinasiUpdateAktivitas.route}/{id_aktivitas}",
            arguments = listOf(navArgument("id_aktivitas") { type = NavType.StringType })
        ) { backStackEntry ->
            val id_aktivitas = backStackEntry.arguments?.getString("id_aktivitas") ?: return@composable
            UpdateAktivitasScreen(
                id_aktivitas = id_aktivitas,
                navigateBack = { navController.navigateUp() }
            )
        }



        // CATATAN PERTANIAN
        // NAVIGASI HOME CATATAN PERTANIAN
        composable(DestinasiHomePanen.route) {
            HomePanenScreen(
                navigateBack = { navController.navigateUp() },
                navigateToItemEntry = { navController.navigate(DestinasiInsertPanen.route) },
                navigateToUpdate = { panen ->
                    navController.navigate("${DestinasiUpdatePanen.route}/${panen.idpanen}")
                },
                onDetailClick = { id_panen ->
                    navController.navigate("${DestinasiDetailPanen.route}/$id_panen") }
            )
        }

        // NAVIGASI DETAIL CATATAN PERTANIAN
        composable(
            route = "${DestinasiDetailPanen.route}/{id_panen}",
            arguments = listOf(navArgument("id_panen") { type = NavType.StringType })
        ) { backStackEntry ->
            val id_panen = backStackEntry.arguments?.getString("id_panen") ?: return@composable
            DetailPanenScreen(
                id_panen = id_panen,
                navigateBack = { navController.navigateUp() },
                navController = navController
            )
        }

        // NAVIGASI INSERT CATATAN PANEN
        composable(
            route = DestinasiInsertPanen.route,
        ) {
            EntryPanenScreen(
                navigateBack = {
                    navController.navigate(DestinasiHomePanen.route) {
                        popUpTo(DestinasiHomePanen.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        // NAVIGASI UPDATE CATATAN PANEN
        composable(
            route = "${DestinasiUpdatePanen.route}/{id_panen}",
            arguments = listOf(navArgument("id_panen") { type = NavType.StringType })
        ) { backStackEntry ->
            val id_panen = backStackEntry.arguments?.getString("id_panen") ?: return@composable
            UpdatePanenScreen(
                id_panen = id_panen,
                navigateBack = { navController.navigateUp() }
            )
        }
    }
}