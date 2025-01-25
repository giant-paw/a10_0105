package com.example.uas_pam.ui.view.aktivitas

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
import com.example.uas_pam.ui.viewmodel.aktivitas.UpdateAktivitasEvent
import com.example.uas_pam.ui.viewmodel.aktivitas.UpdateAktivitasViewModel
import com.example.uas_pam.ui.viewmodel.aktivitas.UpdateAktivitastate
import com.example.uas_pam.ui.viewmodel.penyediamodel.PenyediaViewModel
import kotlinx.coroutines.launch

object DestinasiUpdateAktivitas: DestinasiNavigasi {
    override val route = "update_aktivitas"
    override val titleRes = "Update Aktivitas"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateAktivitasScreen(
    navigateBack: () -> Unit,
    id_aktivitas: String,
    modifier: Modifier = Modifier,
    viewModel: UpdateAktivitasViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val aktivitasState = viewModel.updateAktivitastate
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(id_aktivitas) {
        viewModel.getAktivitasId(id_aktivitas)
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBarr(
                DestinasiUpdateAktivitas.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        UpdateAktivitas(
            updateAktivitasState = aktivitasState,
            onAktivitasValueChange = viewModel::updateUpdatePekerjaState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateAktivitas()
                    navigateBack()
                }
            },
            idTanamanOptions = viewModel.idTanamanOptions,
            idPekerjaOptions = viewModel.idPekerjaOptions,
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}


@Composable
fun UpdateAktivitas(
    updateAktivitasState: UpdateAktivitastate,
    onAktivitasValueChange: (UpdateAktivitasEvent) -> Unit,
    onSaveClick: () -> Unit,
    idTanamanOptions: List<String>,
    idPekerjaOptions: List<String>,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputAktivitas(
            updateAktivitasEvent = updateAktivitasState.updateAktivitaEvent,
            onValueChange = onAktivitasValueChange,
            idTanamanOptions = idTanamanOptions,
            idPekerjaOptions = idPekerjaOptions,
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
fun FormInputAktivitas(
    updateAktivitasEvent: UpdateAktivitasEvent,
    modifier: Modifier = Modifier,
    onValueChange: (UpdateAktivitasEvent) -> Unit = {},
    enabled: Boolean = true,
    idTanamanOptions: List<String> = emptyList(),
    idPekerjaOptions: List<String> = emptyList()
) {
    var tanamanDropdownExpanded by remember { mutableStateOf(false) }
    var pekerjaDropdownExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        // DROPDOWN TANAMAN
        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = updateAktivitasEvent.idtanaman,
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
                            onValueChange(updateAktivitasEvent.copy(idtanaman = id))
                            tanamanDropdownExpanded = false
                        }
                    )
                }
            }
        }

        // DROPDOWN PEKERJA
        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = updateAktivitasEvent.idpekerja,
                onValueChange = {},
                label = { Text("Id Pekerja") },
                modifier = Modifier.fillMaxWidth().clickable { pekerjaDropdownExpanded = true },
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
                expanded = pekerjaDropdownExpanded,
                onDismissRequest = { pekerjaDropdownExpanded = false }
            ) {
                idPekerjaOptions.forEach { id ->
                    DropdownMenuItem(
                        text = { Text(id) },
                        onClick = {
                            onValueChange(updateAktivitasEvent.copy(idpekerja = id))
                            pekerjaDropdownExpanded = false
                        }
                    )
                }
            }
        }

        OutlinedTextField(
            value = updateAktivitasEvent.tanggalaktivitas,
            onValueChange = { onValueChange(updateAktivitasEvent.copy(tanggalaktivitas = it)) },
            label = { Text("Nama Aktivitas") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(R.color.primary)
            )
        )

        OutlinedTextField(
            value = updateAktivitasEvent.deskripsiaktivitas,
            onValueChange = { onValueChange(updateAktivitasEvent.copy(deskripsiaktivitas = it)) },
            label = { Text("Deskripsi Aktivitas") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(R.color.primary)
            )
        )
    }
}