package com.example.uas_pam.ui.view.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.uas_pam.R
import com.example.uas_pam.ui.navigasi.DestinasiNavigasi

object MainScreen : DestinasiNavigasi {
    override val route = "menu_farm"
    override val titleRes = ""
}

@Composable
fun MainMenuScreen(
    onNavigateToTanaman: () -> Unit,
    onNavigateToPekerja: () -> Unit,
    onNavigateToAktivitas: () -> Unit,
    onNavigateToPanen: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        // Menambahkan background
        Image(
            painter = painterResource(id = R.drawable.bgg),
            contentDescription = "Background Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Giant Farm",
                color = Color.Red,
                fontSize = 40.sp,
                modifier = Modifier.padding(20.dp)
            )

            Row(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CardItem(
                    title = "Tanaman",
                    iconRes = R.drawable.logo1,
                    onClick = onNavigateToTanaman
                )
                CardItem(
                    title = "Pekerja",
                    iconRes = R.drawable.employee,
                    onClick = onNavigateToPekerja
                )
            }

            Row(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CardItem(
                    title = "Aktivitas Pertanian",
                    iconRes = R.drawable.work,
                    onClick = onNavigateToAktivitas
                )
                CardItem(
                    title = "Catatan Panen",
                    iconRes = R.drawable.writing,
                    onClick = onNavigateToPanen
                )
            }
        }
    }
}

@Composable
fun CardItem(
    title: String,
    iconRes: Int,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.size(150.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0x99000000)
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = title,
                tint = Color.White,
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.padding(top = 8.dp))
            Text(
                text = title,
                color = Color.White,
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}
