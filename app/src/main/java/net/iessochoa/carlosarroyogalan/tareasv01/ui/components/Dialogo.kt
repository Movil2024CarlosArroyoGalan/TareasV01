package net.iessochoa.carlosarroyogalan.tareasv01.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
//Dialogo de confirmación
@Composable
fun DialogoDeConfirmacion(
    // Función que se llama cuando el usuario cancela la seleccion
    onDismissRequest: () -> Unit,
    // Función que se llama cuando el usuario confirma la seleccion
    onConfirmation: () -> Unit,
    // Titulo del cuadro de diálogo
    dialogTitle: String,
    // Texto del cuadro de diálogo
    dialogText: String,
    // Icono del cuadro de diálogo
    icon: ImageVector,
) {
    //AlertDialog con los parámetros
    AlertDialog(
        //Icono en la parte superior del dialogo
        icon = {
            Icon(icon, contentDescription = null)
        },
        //Titulo del diálogo
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        //Descarta el diálogo
        onDismissRequest = {
            onDismissRequest()
        },
        //Boton de confirmacion para aceptar la accion
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text(stringResource(id = android.R.string.ok))
            }
        },
        //Boton de rechazo para cancelar la accion
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text(stringResource(id = android.R.string.cancel))
            }
        }
    )
}
