package com.example.uas_pam.ui.view.pekerja

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.uas_pam.R
import com.example.uas_pam.ui.customwidget.TopAppBarr
import com.example.uas_pam.ui.navigasi.DestinasiNavigasi
import com.example.uas_pam.ui.viewmodel.pekerja.DetailPekerjaState
import com.example.uas_pam.ui.viewmodel.pekerja.DetailPekerjaViewModel
import com.example.uas_pam.ui.viewmodel.penyediamodel.PenyediaViewModel


object DestinasiDetailPekerja : DestinasiNavigasi {
    override val route = "detail_pekerja"
    override val titleRes = "Detail Pekerja"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPekerjaScreen(
    navigateBack: () -> Unit,
    id_pekerja: String,
    modifier: Modifier = Modifier,
    viewModel: DetailPekerjaViewModel = viewModel(factory = PenyediaViewModel.Factory),
    navController: NavHostController
) {
    val pekerjaState by viewModel.detailPekerjaState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(Unit) {
        viewModel.getPekerja(id_pekerja)
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBarr(
                title = DestinasiDetailPekerja.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("update_pekerja/$id_pekerja")
                },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp),
                containerColor = colorResource(R.color.primary),
                contentColor = Color.White
            ) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Update pekerja")
            }
        }
    ) { innerPadding ->
        DetailBodyPekerja(
            detailPekerjaState = pekerjaState,
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun DetailBodyPekerja(
    detailPekerjaState: DetailPekerjaState,
    modifier: Modifier = Modifier
) {
    when (detailPekerjaState) {
        is DetailPekerjaState.Loading -> {
            CircularProgressIndicator(modifier = modifier.fillMaxSize())
        }
        is DetailPekerjaState.Error -> {
            Text(
                text = detailPekerjaState.message,
                color = Color.Red,
                modifier = modifier.fillMaxSize().wrapContentSize(Alignment.Center)
            )
        }
        is DetailPekerjaState.Success -> {
            val pekerja = detailPekerjaState.pekerja
            Column(
                verticalArrangement = Arrangement.spacedBy(18.dp),
                modifier = modifier.padding(12.dp)
            ) {
                ComponentDetailPekerja(judul = "Nama Pekerja", isinya = pekerja.namapekerja)
                ComponentDetailPekerja(judul = "Jabatan", isinya = pekerja.jabatan)
                ComponentDetailPekerja(judul = "Kontak Pekerja", isinya = pekerja.kontakpekerja)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun ComponentDetailPekerja(
    modifier: Modifier = Modifier,
    judul: String,
    isinya: String,
){
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "$judul : ",
            fontSize = 19.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )

        Text(
            text = isinya,
            fontSize = 19.sp,
            fontWeight = FontWeight.Bold
        )
    }
}