package net.iessochoa.carlosarroyogalan.tareasv01.ui.screens.listatareas

import androidx.compose.material3.SnackbarHostState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import net.iessochoa.carlosarroyogalan.tareasv01.data.db.entities.Tarea

data class ListaUiState(
    val listaPalabra: List<Tarea> = emptyList()
)

data class UiStateDialogo(
    val mostrarDialogoBorrar: Boolean = false,
    val tareaBorrar: Tarea? = null,
    //Sin el scope o el snackbar no podriamos emitir el texto del mensaje
    val scope: CoroutineScope? = null,
    val snackbarHostState: SnackbarHostState? = null,
    )
data class UiStateFiltro(
    val filtroEstado: String
)

