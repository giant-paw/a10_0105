package com.example.uas_pam.ui.view.panen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uas_pam.R
import com.example.uas_pam.ui.customwidget.TopAppBarr
import com.example.uas_pam.ui.navigasi.DestinasiNavigasi
import com.example.uas_pam.ui.viewmodel.panen.UpdatePanenEvent
import com.example.uas_pam.ui.viewmodel.panen.UpdatePanenState
import com.example.uas_pam.ui.viewmodel.panen.UpdatePanenViewModel
import com.example.uas_pam.ui.viewmodel.penyediamodel.PenyediaViewModel
import kotlinx.coroutines.launch

object DestinasiUpdatePanen: DestinasiNavigasi {
    override val route = "update_panen"
    override val titleRes = "Update Panen"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatePanenScreen(
    navigateBack: () -> Unit,
    id_panen: String,
    modifier: Modifier = Modifier,
    viewModel: UpdatePanenViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val panenState = viewModel.updatePanenState
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(id_panen) {
        viewModel.getPanenId(id_panen)
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBarr(
                DestinasiUpdatePanen.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        UpdatePanen(
            updatePanenState = panenState,
            onPanenValueChange = viewModel::updateUpdatePanenState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updatePanen()
                    navigateBack()
                }
            },
            idTanamanOptions = viewModel.idTanamanOptions,
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}


@Composable
fun UpdatePanen(
    updatePanenState: UpdatePanenState,
    onPanenValueChange: (UpdatePanenEvent) -> Unit,
    onSaveClick: () -> Unit,
    idTanamanOptions: List<String>,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputPanen(
            updatePanenEvent = updatePanenState.updatePanenEvent,
            onValueChange = onPanenValueChange,
            idTanamanOptions = idTanamanOptions,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.primary),
                contentColor = Color.White
            )
        ) {
            Text(text = "Update")
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInputPanen(
    updatePanenEvent: UpdatePanenEvent,
    modifier: Modifier = Modifier,
    onValueChange: (UpdatePanenEvent) -> Unit = {},
    enabled: Boolean = true,
    idTanamanOptions: List<String> = emptyList(),
) {
    var tanamanDropdownExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        // DROPDOWN TANAMAN
        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = updatePanenEvent.idtanaman,
                onValueChange = {},
                label = { Text("Id Tanaman") },
                modifier = Modifier.fillMaxWidth().clickable { tanamanDropdownExpanded = true },
                enabled = false,
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Dropdown Icon"
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorResource(R.color.primary)
                )
            )
            DropdownMenu(
                expanded = tanamanDropdownExpanded,
                onDismissRequest = { tanamanDropdownExpanded = false }
            ) {
                idTanamanOptions.forEach { id ->
                    DropdownMenuItem(
                        text = { Text(id) },
                        onClick = {
                            onValueChange(updatePanenEvent.copy(idtanaman = id))
                            tanamanDropdownExpanded = false
                        }
                    )
                }
            }
        }

        OutlinedTextField(
            value = updatePanenEvent.tanggalpanen,
            onValueChange = { onValueChange(updatePanenEvent.copy(tanggalpanen = it)) },
            label = { Text("Tanggal Panen") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(R.color.primary)
            )
        )

        OutlinedTextField(
            value = updatePanenEvent.jumlahpanen,
            onValueChange = { onValueChange(updatePanenEvent.copy(jumlahpanen = it)) },
            label = { Text("Jumlah Panen") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(R.color.primary)
            )
        )

        OutlinedTextField(
            value = updatePanenEvent.keterangan,
            onValueChange = { onValueChange(updatePanenEvent.copy(keterangan = it)) },
            label = { Text("Keterangan") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(R.color.primary)
            )
        )
    }
}