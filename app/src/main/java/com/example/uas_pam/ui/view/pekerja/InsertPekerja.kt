package com.example.uas_pam.ui.view.pekerja

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.uas_pam.ui.viewmodel.pekerja.InsertPekerjaEvent
import com.example.uas_pam.ui.viewmodel.pekerja.InsertPekerjaState
import com.example.uas_pam.ui.viewmodel.pekerja.InsertPekerjaViewModel
import com.example.uas_pam.ui.viewmodel.penyediamodel.PenyediaViewModel
import kotlinx.coroutines.launch


object DestinasiInsertPekerja: DestinasiNavigasi {
    override val route = "item_pekerja"
    override val titleRes = "Tampilan Isi Data Pekerja"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryPekerjaScreen(
    navigateBack:()->Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertPekerjaViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBarr(
                title = DestinasiInsertPekerja.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerpadding->
        EntryBodyPekerja(
            insertPekerjaState = viewModel.insertPekerjaState,
            onPekerjaValueChange = viewModel::updateInsertPekerjaState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertPekerja()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerpadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun EntryBodyPekerja(
    insertPekerjaState: InsertPekerjaState,
    onPekerjaValueChange: (InsertPekerjaEvent)->Unit,
    onSaveClick:()->Unit,

    modifier: Modifier = Modifier
){
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputPekerja(
            insertPekerjaEvent = insertPekerjaState.insertPekerjaEvent,
            onValueChange = onPekerjaValueChange,
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
            Text(text = "Simpan Data")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInputPekerja(
    insertPekerjaEvent: InsertPekerjaEvent,
    modifier: Modifier = Modifier,
    onValueChange:(InsertPekerjaEvent)->Unit = {},
    enabled: Boolean = true
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = insertPekerjaEvent.namapekerja,
            onValueChange = {onValueChange(insertPekerjaEvent.copy(namapekerja = it))},
            label = { Text("Nama Pekerja") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(R.color.primary)
            )
        )
        OutlinedTextField(
            value = insertPekerjaEvent.jabatan,
            onValueChange = {onValueChange(insertPekerjaEvent.copy(jabatan = it))},
            label = { Text("Jabatan") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(R.color.primary)
            )
        )
        OutlinedTextField(
            value = insertPekerjaEvent.kontakpekerja,
            onValueChange = {onValueChange(insertPekerjaEvent.copy(kontakpekerja = it))},
            label = { Text("Kontak Pekerja") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(R.color.primary)
            )
        )
        if (enabled){
            Text(
                text = "Isi Semua Data Dengan Benar !!",
                modifier = Modifier.padding(12.dp)
            )
        }
    }
}