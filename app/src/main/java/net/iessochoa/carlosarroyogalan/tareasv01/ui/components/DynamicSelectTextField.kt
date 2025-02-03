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
import androidx.compose.ui.focus.focusModifier
//Menú desplegable con el dropdownmenu para seleccionar la opción
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DynamicSelectTextField(
    //Lista de opciones que se mostrarán en el menu
    options: Array<String>,
    //Label que se muestra en el texto
    label: String,
    //Funcion que se selecciona una opción del menu
    onSelectionChanged: (String) -> Unit,
    //Valor del campo de texto
    selectedValue: String,
    modifier: Modifier = Modifier
    ) {
    //Estado que controla si el menú está extendido o no
    val expanded = remember { mutableStateOf(false) }
    //Estado que almacena la opción seleccionada
    val selectedOption = remember { mutableStateOf(options.firstOrNull() ?: "") }
    //Menú desplegable
    ExposedDropdownMenuBox(
        //Menú abierto
        expanded = expanded.value,
        //Cambia si está expandido o no cuando se usa
        onExpandedChange = { expanded.value = !expanded.value },
        modifier = modifier
    ) {
        //Muestra la opción seleccionada
        OutlinedTextField(
            value = selectedValue,
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded.value) },
            modifier = Modifier
                .menuAnchor() // Necesario para posicionar el menú correctamente
                .fillMaxWidth()
        )
        //Menu que se muestra cuando el texto es tocado
        androidx.compose.material3.DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }
        ) {
            //Muestra todos los items del menu
            options.forEach { option ->
                androidx.compose.material3.DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        //Cuando se selecciona una opción se actualiza el estado
                        selectedOption.value = option
                        //Llama a la funcion para manejar el cambio de seleccion
                        onSelectionChanged(option)
                        //Cierra el menú despues de seleccionar
                        expanded.value = false
                    }
                )
            }
        }
    }
}