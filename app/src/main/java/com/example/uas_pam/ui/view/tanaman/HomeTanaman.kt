package com.example.uas_pam.ui.view.tanaman

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uas_pam.R
import com.example.uas_pam.model.Tanaman
import com.example.uas_pam.ui.customwidget.TopAppBarr
import com.example.uas_pam.ui.navigasi.DestinasiNavigasi
import com.example.uas_pam.ui.viewmodel.penyediamodel.PenyediaViewModel
import com.example.uas_pam.ui.viewmodel.tanaman.HomeTanamanState
import com.example.uas_pam.ui.viewmodel.tanaman.HomeTanamanViewModel

object DestinasiHomeTanaman: DestinasiNavigasi {
    override val route = "home_tanaman"
    override val titleRes = "Daftar Tanaman"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTanamanScreen(
    navigateBack: () -> Unit,
    navigateToItemEntry: () -> Unit,
    onDetailClick: (String) -> Unit = {},
    modifier: Modifier = Modifier,
    viewModel: HomeTanamanViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(Unit) {
        viewModel.getTnmn()
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBarr(
                title = DestinasiHomeTanaman.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack,
                onRefresh = {
                    viewModel.getTnmn()
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = navigateToItemEntry,
                icon = {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Tambah Tanaman"
                    )
                },
                text = {
                    Text(text = "Tambah Tanaman")
                },
                containerColor = colorResource(R.color.primary),
                contentColor = Color.White,
                modifier = Modifier.padding(16.dp)
            )
        }
    ) { innerPadding ->
        HomeStatusTanaman(
            homeTanamanState = viewModel.tanamanHomeState,
            retryAction = { viewModel.getTnmn() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onDeleteClick = { tanaman ->
                viewModel.deleteTnmn(tanaman.idtanaman)
            }
        )
    }
}

@Composable
fun HomeStatusTanaman(
    homeTanamanState: HomeTanamanState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit,
    onDeleteClick: (Tanaman) -> Unit
) {
    when (homeTanamanState) {
        is HomeTanamanState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is HomeTanamanState.Success -> {
            if (homeTanamanState.tanaman.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data Tanaman")
                }
            } else {
                TanamanLayout(
                    tanaman = homeTanamanState.tanaman,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = onDetailClick,
                    onDeleteClick = onDeleteClick
                )
            }
        }
        is HomeTanamanState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun OnLoading(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.connection_loading),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun OnError(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.connection_error), contentDescription = null
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
        Button(
            onClick = retryAction,
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.primary)
            )
        ) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun TanamanLayout(
    tanaman: List<Tanaman>,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit,
    onDeleteClick: (Tanaman) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(tanaman) { tnmn ->
            TanamanCard(
                tanaman = tnmn,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(tnmn.idtanaman) },
                onDeleteClick = onDeleteClick
            )
        }
    }
}

@Composable
fun TanamanCard(
    tanaman: Tanaman,
    onDeleteClick: (Tanaman) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(R.color.cardBackground))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Filled.Info, contentDescription = null, tint = colorResource(R.color.iconColor)) // Red for info icon
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = tanaman.namatanaman,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.cardText) // Dark text color for title
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Filled.DateRange, contentDescription = null, tint = colorResource(R.color.iconColor)) // Red for date icon
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = tanaman.periodetanam,
                    fontSize = 18.sp,
                    color = colorResource(R.color.textSecondary) // Secondary text color
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Filled.MailOutline, contentDescription = null, tint = colorResource(R.color.iconColor)) // Red for mail icon
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = tanaman.deskripsitanaman,
                    fontSize = 18.sp,
                    color = colorResource(R.color.textSecondary) // Secondary text color
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = { onDeleteClick(tanaman) }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Hapus", tint = colorResource(R.color.iconColor)) // Red for delete icon
                }
            }
        }
    }

}
