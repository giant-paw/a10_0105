package com.example.uas_pam.ui.view.panen

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
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uas_pam.R
import com.example.uas_pam.model.Panen
import com.example.uas_pam.ui.customwidget.TopAppBarr
import com.example.uas_pam.ui.navigasi.DestinasiNavigasi
import com.example.uas_pam.ui.viewmodel.panen.HomePanenState
import com.example.uas_pam.ui.viewmodel.panen.HomePanenViewModel
import com.example.uas_pam.ui.viewmodel.penyediamodel.PenyediaViewModel


object DestinasiHomePanen: DestinasiNavigasi {
    override val route = "home_panen"
    override val titleRes = "Daftar Panen"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePanenScreen(
    navigateBack: () -> Unit,
    navigateToItemEntry: () -> Unit,
    navigateToUpdate: (Panen) -> Unit,
    onDetailClick: (String) -> Unit = {},
    modifier: Modifier = Modifier,
    viewModel: HomePanenViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(Unit) {
        viewModel.getPn()
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBarr(
                title = DestinasiHomePanen.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack,
                onRefresh = {
                    viewModel.getPn()
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = navigateToItemEntry,
                icon = {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Tambah Panen"
                    )
                },
                text = {
                    Text(text = "Tambah Panen")
                },
                containerColor = colorResource(R.color.primary),
                contentColor = Color.White,
                modifier = Modifier.padding(16.dp)
            )
        }
    ) { innerPadding ->
        HomeStatusPanen(
            homePanenState = viewModel.panenHomeState,
            retryAction = { viewModel.getPn() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onDeleteClick = { panen ->
                viewModel.deletePn(panen.idpanen)
            },
            onUpdateClick = navigateToUpdate
        )
    }
}

@Composable
fun HomeStatusPanen(
    homePanenState: HomePanenState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit,
    onDeleteClick: (Panen) -> Unit,
    onUpdateClick: (Panen) -> Unit
) {
    when (homePanenState) {
        is HomePanenState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is HomePanenState.Success -> {
            if (homePanenState.panen.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data Panen")
                }
            } else {
                PanenLayout(
                    panen = homePanenState.panen,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = onDetailClick,
                    onDeleteClick = onDeleteClick,
                    onUpdateClick = onUpdateClick
                )
            }
        }
        is HomePanenState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
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
fun PanenLayout(
    panen: List<Panen>,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit,
    onDeleteClick: (Panen) -> Unit,
    onUpdateClick: (Panen) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(panen) { pn ->
            PanenCard(
                panen = pn,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(pn.idpanen) },
                onDeleteClick = onDeleteClick,
                onUpdateClick = onUpdateClick
            )
        }
    }
}

@Composable
fun PanenCard(
    panen: Panen,
    onDeleteClick: (Panen) -> Unit,
    modifier: Modifier = Modifier,
    onUpdateClick: (Panen) -> Unit
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Filled.DateRange, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = panen.idtanaman,
                    fontSize = 18.sp
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Filled.Info, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = panen.tanggalpanen,
                    fontSize = 18.sp
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Filled.Info, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = panen.jumlahpanen,
                    fontSize = 18.sp
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = { onUpdateClick(panen) }) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
                }
                IconButton(onClick = { onDeleteClick(panen) }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Hapus")
                }
            }
        }
    }
}