package net.iessochoa.carlosarroyogalan.tareasv01.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DynamicSelectTextField(
    options: Array<String>,
    label: String,
    onSelectionChanged: (String) -> Unit
) {
    val expanded = remember { mutableStateOf(false) }
    val selectedOption = remember { mutableStateOf(options.firstOrNull() ?: "") }

    ExposedDropdownMenuBox(
        expanded = expanded.value,
        onExpandedChange = { expanded.value = !expanded.value }
    ) {
        OutlinedTextField(
            value = selectedOption.value,
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded.value) },
            modifier = Modifier
                .menuAnchor() // Necesario para posicionar el menú correctamente
                .fillMaxWidth()
        )
        androidx.compose.material3.DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }
        ) {
            options.forEach { option ->
                androidx.compose.material3.DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        selectedOption.value = option
                        onSelectionChanged(option)
                        expanded.value = false
                    }
                )
            }
        }
    }
}