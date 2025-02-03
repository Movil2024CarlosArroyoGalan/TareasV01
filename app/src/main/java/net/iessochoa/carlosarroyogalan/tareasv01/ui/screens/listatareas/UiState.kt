package net.iessochoa.carlosarroyogalan.tareasv01.ui.screens.listatareas

import androidx.compose.material3.SnackbarHostState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import net.iessochoa.carlosarroyogalan.tareasv01.data.db.entities.Tarea
//Estado de la lista de tareas
data class ListaUiState(
    //Está inicialmente vacia
    val listaPalabra: List<Tarea> = emptyList()
)
//Estado del dialogo para borrar una tarea
data class UiStateDialogo(
    val mostrarDialogoBorrar: Boolean = false,
    //Tarea que queremos borrar
    val tareaBorrar: Tarea? = null,
    //Sin el scope o el snackbar no podriamos emitir el texto del mensaje
    val scope: CoroutineScope? = null,
    val snackbarHostState: SnackbarHostState? = null,
    )
//Estado para el filtro de tareas por estado
data class UiStateFiltro(
    val filtroEstado: String
)

