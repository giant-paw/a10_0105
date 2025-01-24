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
import androidx.compose.runtime.LaunchedEffect
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
import com.example.uas_pam.ui.viewmodel.pekerja.UpdatePekerjaEvent
import com.example.uas_pam.ui.viewmodel.pekerja.UpdatePekerjaState
import com.example.uas_pam.ui.viewmodel.pekerja.UpdatePekerjaViewModel
import com.example.uas_pam.ui.viewmodel.penyediamodel.PenyediaViewModel
import kotlinx.coroutines.launch

object DestinasiUpdatePekerja: DestinasiNavigasi {
    override val route = "update_pekerja"
    override val titleRes = "Update Pekerja"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatePekerjaScreen(
    navigateBack: () -> Unit,
    id_pekerja: String,
    modifier: Modifier = Modifier,
    viewModel: UpdatePekerjaViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val pekerjaState = viewModel.updatePekerjaState
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(id_pekerja) {
        viewModel.getPekerjaId(id_pekerja)
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBarr(
                DestinasiUpdatePekerja.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        UpdatePekerja(
            updatePekerjaState = pekerjaState,
            onPekerjaValueChange = viewModel::updateUpdatePekerjaState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updatePekerja()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}


@Composable
fun UpdatePekerja(
    updatePekerjaState: UpdatePekerjaState,
    onPekerjaValueChange: (UpdatePekerjaEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputPekerja(
            updatePekerjaEvent = updatePekerjaState.updatePekerjaEvent,
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
            Text(text = "Update Pekerja")
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInputPekerja(
    updatePekerjaEvent: UpdatePekerjaEvent,
    modifier: Modifier = Modifier,
    onValueChange: (UpdatePekerjaEvent) -> Unit = {},
    enabled: Boolean = true,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = updatePekerjaEvent.namapekerja,
            onValueChange = { onValueChange(updatePekerjaEvent.copy(namapekerja = it)) },
            label = { Text("Nama Pekerja") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(R.color.primary)
            )
        )
        OutlinedTextField(
            value = updatePekerjaEvent.jabatan,
            onValueChange = { onValueChange(updatePekerjaEvent.copy(jabatan = it)) },
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
            value = updatePekerjaEvent.kontakpekerja,
            onValueChange = { onValueChange(updatePekerjaEvent.copy(kontakpekerja = it)) },
            label = { Text("Kontak Pekerja") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(R.color.primary)
            )
        )
    }
}