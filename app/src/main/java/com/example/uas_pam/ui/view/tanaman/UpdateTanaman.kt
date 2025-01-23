package com.example.uas_pam.ui.view.tanaman

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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uas_pam.R
import com.example.uas_pam.ui.customwidget.TopAppBarr
import com.example.uas_pam.ui.navigasi.DestinasiNavigasi
import com.example.uas_pam.ui.viewmodel.penyediamodel.PenyediaViewModel
import com.example.uas_pam.ui.viewmodel.tanaman.UpdateTanamanEvent
import com.example.uas_pam.ui.viewmodel.tanaman.UpdateTanamanState
import com.example.uas_pam.ui.viewmodel.tanaman.UpdateTanamanViewModel
import kotlinx.coroutines.launch

object DestinasiUpdateTanaman: DestinasiNavigasi {
    override val route = "update_tanaman"
    override val titleRes = "Update Tanaman"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateTanamanScreen(
    navigateBack: () -> Unit,
    id_tanaman: String,
    modifier: Modifier = Modifier,
    viewModel: UpdateTanamanViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val tanamanState = viewModel.updateTanamanState
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(id_tanaman) {
        viewModel.getTanamanId(id_tanaman)
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBarr(
                DestinasiUpdateTanaman.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        UpdateTanaman(
            updateTanamanState = tanamanState,
            onTanamanValueChange = viewModel::updateUpdateTanamanState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateTanaman()
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
fun UpdateTanaman(
    updateTanamanState: UpdateTanamanState,
    onTanamanValueChange: (UpdateTanamanEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputTanaman(
            updateTanamanEvent = updateTanamanState.updateTanamanEvent,
            onValueChange = onTanamanValueChange,
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
            Text(text = "Update Tanaman")
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInputTanaman(
    updateTanamanEvent: UpdateTanamanEvent,
    modifier: Modifier = Modifier,
    onValueChange: (UpdateTanamanEvent) -> Unit = {},
    enabled: Boolean = true,
    idKamarOptions: List<String> = emptyList()
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = updateTanamanEvent.namatanaman,
            onValueChange = { onValueChange(updateTanamanEvent.copy(namatanaman = it)) },
            label = { Text("Nama Tanaman") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(R.color.primary)
            )
        )
        OutlinedTextField(
            value = updateTanamanEvent.periodetanam,
            onValueChange = { onValueChange(updateTanamanEvent.copy(periodetanam = it)) },
            label = { Text("Periode Tanam") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(R.color.primary)
            )
        )
        OutlinedTextField(
            value = updateTanamanEvent.deskripsitanaman,
            onValueChange = { onValueChange(updateTanamanEvent.copy(deskripsitanaman = it)) },
            label = { Text("Deskripsi Tanaman") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(R.color.primary)
            )
        )
    }
}