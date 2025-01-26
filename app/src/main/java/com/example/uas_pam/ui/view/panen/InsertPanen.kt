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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uas_pam.R
import com.example.uas_pam.ui.customwidget.TopAppBarr
import com.example.uas_pam.ui.navigasi.DestinasiNavigasi
import com.example.uas_pam.ui.viewmodel.panen.InsertPanenEvent
import com.example.uas_pam.ui.viewmodel.panen.InsertPanenState
import com.example.uas_pam.ui.viewmodel.panen.InsertPanenViewModel
import com.example.uas_pam.ui.viewmodel.penyediamodel.PenyediaViewModel
import kotlinx.coroutines.launch
import kotlin.reflect.KFunction1


object DestinasiInsertPanen: DestinasiNavigasi {
    override val route = "item_panen"
    override val titleRes = "Form Isi Data Panen"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryPanenScreen(
    navigateBack:()->Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertPanenViewModel = viewModel(factory = PenyediaViewModel.Factory)
){

    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBarr(
                title = DestinasiInsertPanen.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerpadding->
        EntryBodyPanen(
            insertPanenState = viewModel.insertPanenState,
            onPanenValueChange = viewModel::updateInsertPanenState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.inserPanen()
                    navigateBack()
                }
            },
            idTanamanOptions = viewModel.idTanamanOptions,
            modifier = Modifier
                .padding(innerpadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun EntryBodyPanen(
    insertPanenState: InsertPanenState,
    onPanenValueChange: KFunction1<InsertPanenEvent, Unit>,
    onSaveClick:()->Unit,
    idTanamanOptions: List<String>,

    modifier: Modifier = Modifier
){
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputPanen(
            insertPanenEvent = insertPanenState.insertPanenEvent,
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
            Text(text = "Simpan")
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInputPanen(
    insertPanenEvent: InsertPanenEvent,
    modifier: Modifier = Modifier,
    onValueChange:(InsertPanenEvent)->Unit = {},
    enabled: Boolean = true,
    idTanamanOptions: List<String> = emptyList()
){
    var tanamanDropdownExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = insertPanenEvent.idtanaman,
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
                            onValueChange(insertPanenEvent.copy(idtanaman = id))
                            tanamanDropdownExpanded = false
                        }
                    )
                }
            }
        }

        OutlinedTextField(
            value = insertPanenEvent.tanggalpanen,
            onValueChange = {onValueChange(insertPanenEvent.copy(tanggalpanen = it))},
            label = { Text("Tanggal Panen") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(R.color.primary)
            )
        )
        OutlinedTextField(
            value = insertPanenEvent.jumlahpanen,
            onValueChange = {onValueChange(insertPanenEvent.copy(jumlahpanen = it))},
            label = { Text("Jumlah Panen") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(R.color.primary)
            )
        )

        OutlinedTextField(
            value = insertPanenEvent.keterangan,
            onValueChange = {onValueChange(insertPanenEvent.copy(keterangan = it))},
            label = { Text("Keterangan") },
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