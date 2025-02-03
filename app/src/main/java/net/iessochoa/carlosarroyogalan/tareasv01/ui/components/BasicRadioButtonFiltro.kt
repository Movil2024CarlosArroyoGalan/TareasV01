package net.iessochoa.carlosarroyogalan.tareasv01.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
@Composable
fun BasicRadioButtonFilter(
    //Opción seleccionada
    selectedOption: String,
    //Funcion a la que se llama cuando se selecciona la opcion
    onOptionSelected: (String) -> Unit,
    //Lista de opciones que se mostrarán como botones para seleccionar
    listaoptions: List<String>
) {
    Row  {
        //Itera sobre las opciones y crea un radioButton para cada una
        listaoptions.forEach { option ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 4.dp)
            ) {
                //Se marca como seleccionado si es la misma opcion que la seleccioanda
                RadioButton(
                    selected = selectedOption == option, //Se asegura de que la opción es la seleccionada
                    onClick = { onOptionSelected(option) } //Llama a la funcion para seleccionar
                )
                //Muestra el texto junto a radioButton
                Text(text = option)
            }
        }
    }
}