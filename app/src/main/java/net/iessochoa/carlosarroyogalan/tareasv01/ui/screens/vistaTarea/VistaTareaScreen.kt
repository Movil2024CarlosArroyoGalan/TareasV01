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
    viewModel: VistaTareaViewModel = viewModel(),
    desTarea: Long?,
    onVolver: () -> Unit,
    onVolverToInicio: () -> Unit
){
    viewModel.findTarea(desTarea)
    val uiStateVistaTareas by viewModel.uiStateVistaTarea.collectAsState()
    Scaffold(
        topBar = {
            AppBar(
                tituloPantallaActual = R.string.ver_tarea.toString() + uiStateVistaTareas.tarea,
                puedeNavegarAtras = true,
                navegaAtras = onVolver
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(modifier = Modifier) {
                Button(onClick = onVolver) {
                    Text(text = stringResource(R.string.volver))
                }
                Spacer(modifier = Modifier.width(16.dp))
                Button(onClick = onVolverToInicio) {
                    Text(stringResource(R.string.volver_al_inicio))
                }
            }

        }
    }
}
