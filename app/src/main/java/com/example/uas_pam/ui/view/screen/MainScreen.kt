package com.example.uas_pam.ui.view.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.uas_pam.R
import com.example.uas_pam.ui.navigasi.DestinasiNavigasi


object MainScreen: DestinasiNavigasi {
    override val route = "menu_dormitory"
    override val titleRes = ""
}

@Composable
fun MainMenuScreen(
    onNavigateToTanaman: () -> Unit,
    onNavigateToPekerja: () -> Unit,
    onNavigateToAktivitas: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.primary))
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Text(
                text = "Giant Farm",
                color = Color.White,
                fontSize = 20.sp,
                modifier = Modifier.padding(20.dp)
            )

            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "",
                modifier = Modifier
                    .size(300.dp)
                    .padding(bottom = 24.dp)
            )

            // Ke Tanaman
            Button(
                onClick = { onNavigateToTanaman() },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.purple_700)
                )
            ) {
                Text(text = "Tanaman",
                    color = Color.White)
            }

            // Ke Pekerja
            Button(
                onClick = { onNavigateToPekerja() },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.purple_700)
                )
            ) {
                Text(text = "Pekerja",
                    color = Color.White)
            }

            // Ke Aktivitas Pertanian
            Button(
                onClick = { onNavigateToAktivitas() },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.purple_700)
                )
            ) {
                Text(text = "Aktivitas Pertanian",
                    color = Color.White)
            }
        }
    }
}
