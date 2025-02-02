package net.iessochoa.carlosarroyogalan.tareasv01.ui.screens.tarea

import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.CoroutineScope
import net.iessochoa.carlosarroyogalan.tareasv01.data.db.entities.Tarea
import net.iessochoa.carlosarroyogalan.tareasv01.data.tempmodel.TempModelTareas


data class UiStateTarea(
val categoria: String = "",
val prioridad: String = "",
val pagado: Boolean = false,
val estado: String = "",
val valoracion: Int = 0,
val tecnico: String = "",
val descripcion: String = "",
val colorFondo: Color = Color.Transparent,
    val esFormularioValido: Boolean = false,
    val mostrarDialogo: Boolean = false,
    val esTareaNueva: Boolean = true,
    val listaTareas: ArrayList<Tarea> = TempModelTareas.listaTareas,
    val uriImagen: String = "",
    val snackbarHostState: SnackbarHostState,
    val scope: CoroutineScope,
)

