package com.example.uas_pam.ui.view.tanaman

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.uas_pam.R
import com.example.uas_pam.ui.viewmodel.tanaman.InsertTanamanEvent


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInputTanaman(
    insertTanamanEvent: InsertTanamanEvent,
    modifier: Modifier = Modifier,
    onValueChange:(InsertTanamanEvent)->Unit = {},
    enabled: Boolean = true
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = insertTanamanEvent.namatanaman,
            onValueChange = {onValueChange(insertTanamanEvent.copy(namatanaman = it))},
            label = { Text("Nama Tanaman") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(R.color.primary)
            )
        )
        OutlinedTextField(
            value = insertTanamanEvent.periodetanam,
            onValueChange = {onValueChange(insertTanamanEvent.copy(periodetanam = it))},
            label = { Text("Periode Tanam (Bulan)") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(R.color.primary)
            )
        )
        OutlinedTextField(
            value = insertTanamanEvent.deskripsitanaman,
            onValueChange = {onValueChange(insertTanamanEvent.copy(deskripsitanaman = it))},
            label = { Text("Deskripsi Tanaman") },
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