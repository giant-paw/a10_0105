package com.example.uas_pam.ui.view.tanaman

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
import com.example.uas_pam.ui.viewmodel.penyediamodel.PenyediaViewModel
import com.example.uas_pam.ui.viewmodel.tanaman.DetailTanamanState
import com.example.uas_pam.ui.viewmodel.tanaman.DetailTanamanViewModel


object DestinasiDetailTanaman : DestinasiNavigasi {
    override val route = "detail_tanaman"
    override val titleRes = "Detail_Tanaman"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTanamanScreen(
    navigateBack: () -> Unit,
    id_tanaman: String,
    modifier: Modifier = Modifier,
    viewModel: DetailTanamanViewModel = viewModel(factory = PenyediaViewModel.Factory),
    navController: NavHostController
) {
    val tanamanState by viewModel.detailTanamanState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(Unit) {
        viewModel.getTanaman(id_tanaman)
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBarr(
                title = DestinasiDetailTanaman.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("update_tanaman/$id_tanaman")
                },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp),
                containerColor = colorResource(R.color.primary),
                contentColor = Color.White
            ) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Update Tanaman")
            }
        }
    ) { innerPadding ->
        DetailBodyTanaman(
            detailTanamanState = tanamanState,
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun DetailBodyTanaman(
    detailTanamanState: DetailTanamanState,
    modifier: Modifier = Modifier
) {
    when (detailTanamanState) {
        is DetailTanamanState.Loading -> {
            CircularProgressIndicator(modifier = modifier.fillMaxSize())
        }
        is DetailTanamanState.Error -> {
            Text(
                text = detailTanamanState.message,
                color = Color.Red,
                modifier = modifier.fillMaxSize().wrapContentSize(Alignment.Center)
            )
        }
        is DetailTanamanState.Success -> {
            val tanaman = detailTanamanState.tanaman
            Column(
                verticalArrangement = Arrangement.spacedBy(18.dp),
                modifier = modifier.padding(12.dp)
            ) {
                ComponentDetailTanaman(judul = "Nama Tanaman", isinya = tanaman.namatanaman)
                ComponentDetailTanaman(judul = "Nomor Identitas", isinya = tanaman.periodetanam)
                ComponentDetailTanaman(judul = "Email", isinya = tanaman.deskripsitanaman)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun ComponentDetailTanaman(
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