package net.iessochoa.carlosarroyogalan.tareasv01.ui.screens.listatareas

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import net.iessochoa.carlosarroyogalan.tareasv01.R
import net.iessochoa.carlosarroyogalan.tareasv01.data.db.entities.Tarea
import net.iessochoa.carlosarroyogalan.tareasv01.ui.components.AppBar
import net.iessochoa.carlosarroyogalan.tareasv01.ui.components.BasicRadioButton
import net.iessochoa.carlosarroyogalan.tareasv01.ui.components.BasicRadioButtonFilter
import net.iessochoa.carlosarroyogalan.tareasv01.ui.components.DialogoDeConfirmacion

@Composable
fun ListaTareasScreen(
    viewModel: ListaTareasViewModel = viewModel(),
    onClickNuevaTarea: () -> Unit = {},
    onClickModificarTarea: (pos: Long?) -> Unit = {},
    onClickVerTarea: (pos: Long?) -> Unit = {}
) {
    val uiStateTarea by viewModel.uiStatelistaTareas.collectAsState()
    val context = LocalContext.current
    val listaCategorias = context.resources.getStringArray(R.array.categoria).toList()
    val uiState by viewModel.uiStatelistaTareas.collectAsState()
    val dialogoState by viewModel.uiStateDialogo.collectAsState()
    val uiStateFiltro by viewModel._uiStateFiltro.collectAsState()
    val sinPagarState by viewModel.uiStateSinPagar.collectAsState()
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onClickNuevaTarea
            ) {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = stringResource(R.string.tarea_nueva)
                )
            }
        },
        topBar = {
            TopBarTareas()
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Row (
                verticalAlignment = Alignment.CenterVertically
            ){
                Switch(
                    checked = sinPagarState,
                    onCheckedChange = {viewModel.onCheckedChangeFiltroSinPagar(it)}
                )
                Text(text = stringResource(R.string.sin_pagar),
                    modifier = Modifier.padding(start = 16.dp))
            }
            BasicRadioButtonFilter(
                selectedOption = uiStateFiltro.filtroEstado,
                onOptionSelected = {
                    viewModel.onCheckedChangeFiltroEstado(it)
                },
                listaoptions = viewModel.listaFiltroEstado
            )
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                if (uiStateTarea.listaPalabra.isNotEmpty()) {
                    items(uiStateTarea.listaPalabra) { tarea ->
                        ItemCard(
                            tarea = tarea,
                            listaCategorias = listaCategorias,
                            onItemModificarClick = onClickModificarTarea,
                            onClickBorrar = { viewModel.onMostrarDialogoBorrar(tarea) }
                        )
                    }
                }
            }
        }
        if (dialogoState.mostrarDialogoBorrar) {
            DialogoDeConfirmacion(
                onDismissRequest = {
                    viewModel.onCancerlarDialogo()
                },
                onConfirmation = {
                    viewModel.onAceptarDialogo()
                    dialogoState.scope?.launch {
                        dialogoState.snackbarHostState?.showSnackbar(
                            message = context.getString(R.string.tarea_eliminada),
                            duration = SnackbarDuration.Short
                        )
                    }
                },
                dialogTitle = stringResource(R.string.atenci_n),
                dialogText = stringResource(R.string.desea_guardar_los_cambios),
                icon = Icons.Default.Info
            )
        }
    }
}