package net.iessochoa.carlosarroyogalan.tareasv01.ui.screens.vistaTarea

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import net.iessochoa.carlosarroyogalan.tareasv01.R
import net.iessochoa.carlosarroyogalan.tareasv01.data.db.entities.Tarea
import net.iessochoa.carlosarroyogalan.tareasv01.ui.components.AppBar

@Composable
fun VistaTareaScreen(
    //ViewModel asociado
    viewModel: VistaTareaViewModel = viewModel(),
    //Id de la tarea a mostrar
    desTarea: Long?,
    //Funciones para volver
    onVolver: () -> Unit,
    onVolverToInicio: () -> Unit
){
    //Carga de la tarea conociendo el id
    viewModel.findTarea(desTarea)
    //Estado actualizado desde la vista del view model
    val uiStateVistaTareas by viewModel.uiStateVistaTarea.collectAsState()
    Scaffold(
        topBar = {
            // AppBar personalizada que incluye el título de la pantalla y el botón para volver atrás
            AppBar(
                tituloPantallaActual = R.string.ver_tarea.toString() + uiStateVistaTareas.tarea,
                puedeNavegarAtras = true,
                navegaAtras = onVolver
            )
        }
    ) { paddingValues ->
        // Contenedor de la pantalla, que se ajusta a todo el tamaño disponible
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Fila que contiene los botones para volver a la pantalla anterior o al inicio
            Row(modifier = Modifier) {
                // Botón para volver a la pantalla anterior
                Button(onClick = onVolver) {
                    Text(text = stringResource(R.string.volver))
                }
                Spacer(modifier = Modifier.width(16.dp))
                // Botón para volver al inicio de la aplicación
                Button(onClick = onVolverToInicio) {
                    Text(stringResource(R.string.volver_al_inicio))
                }
            }

        }
    }
}
