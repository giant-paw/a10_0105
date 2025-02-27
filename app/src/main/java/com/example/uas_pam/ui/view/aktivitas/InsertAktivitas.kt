package com.example.uas_pam.ui.view.aktivitas

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uas_pam.R
import com.example.uas_pam.ui.customwidget.TopAppBarr
import com.example.uas_pam.ui.navigasi.DestinasiNavigasi
import com.example.uas_pam.ui.viewmodel.aktivitas.InsertAktivitasEvent
import com.example.uas_pam.ui.viewmodel.aktivitas.InsertAktivitasState
import com.example.uas_pam.ui.viewmodel.aktivitas.InsertAktivitasViewModel
import com.example.uas_pam.ui.viewmodel.penyediamodel.PenyediaViewModel
import kotlinx.coroutines.launch

object DestinasiInsertAktivitas: DestinasiNavigasi {
    override val route = "item_aktivitas"
    override val titleRes = "Form Isi Data Aktivitas"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryAktivitasScreen(
    navigateBack:()->Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertAktivitasViewModel = viewModel(factory = PenyediaViewModel.Factory)
){

    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBarr(
                title = DestinasiInsertAktivitas.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerpadding->
        EntryBodyAktivitas(
            insertAktivitasState = viewModel.insertAktivitasState,
            onAktivitasValueChange = viewModel::updateInsertAktivitasState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.inserAktivitas()
                    navigateBack()
                }
            },
            idTanamanOptions = viewModel.idTanamanOptions,
            idPekerjaOptions = viewModel.idPekerjaOptions,
            modifier = Modifier
                .padding(innerpadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun EntryBodyAktivitas(
    insertAktivitasState: InsertAktivitasState,
    onAktivitasValueChange: (InsertAktivitasEvent)->Unit,
    onSaveClick:()->Unit,
    idTanamanOptions: List<String>,
    idPekerjaOptions: List<String>,

    modifier: Modifier = Modifier
){
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputAktivitas(
            insertAktivitasEvent = insertAktivitasState.insertAktivitasEvent,
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
            Text(text = "Simpan")
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInputAktivitas(
    insertAktivitasEvent: InsertAktivitasEvent,
    modifier: Modifier = Modifier,
    onValueChange:(InsertAktivitasEvent)->Unit = {},
    enabled: Boolean = true,
    idTanamanOptions: List<String> = emptyList(),
    idPekerjaOptions: List<String> = emptyList()
){
    var tanamanDropdownExpanded by remember { mutableStateOf(false) }
    var pekerjaDropdownExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = insertAktivitasEvent.idtanaman,
                onValueChange = {},
                label = { Text("ID Tanaman") },
                modifier = Modifier.fillMaxWidth().clickable { tanamanDropdownExpanded = true },
                enabled = false,
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
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
                            onValueChange(insertAktivitasEvent.copy(idtanaman = id))
                            tanamanDropdownExpanded = false
                        }
                    )
                }
            }
        }

        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = insertAktivitasEvent.idpekerja,
                onValueChange = {},
                label = { Text("ID Pekerja") },
                modifier = Modifier.fillMaxWidth().clickable { pekerjaDropdownExpanded = true },
                enabled = false,
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
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
                            onValueChange(insertAktivitasEvent.copy(idpekerja = id))
                            pekerjaDropdownExpanded = false
                        }
                    )
                }
            }
        }

        OutlinedTextField(
            value = insertAktivitasEvent.tanggalaktivitas,
            onValueChange = {onValueChange(insertAktivitasEvent.copy(tanggalaktivitas = it))},
            label = { Text("Tanggal Aktivitas") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(R.color.primary)
            )
        )
        OutlinedTextField(
            value = insertAktivitasEvent.deskripsiaktivitas,
            onValueChange = {onValueChange(insertAktivitasEvent.copy(deskripsiaktivitas = it))},
            label = { Text("Deskripsi Aktivitas") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(R.color.primary)
            )
        )

        if (enabled){
            Text(
                text = "Isi Semua Data Dengan Benar!!",
                modifier = Modifier.padding(12.dp)
            )
        }
    }
}