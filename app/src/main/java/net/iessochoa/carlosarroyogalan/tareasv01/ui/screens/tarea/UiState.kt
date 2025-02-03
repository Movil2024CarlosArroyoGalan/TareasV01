package net.iessochoa.carlosarroyogalan.tareasv01.ui.screens.tarea

import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.CoroutineScope
import net.iessochoa.carlosarroyogalan.tareasv01.data.db.entities.Tarea
import net.iessochoa.carlosarroyogalan.tareasv01.data.tempmodel.TempModelTareas


data class UiStateTarea(
val categoria: String = "", //Categoría de la tarea
val prioridad: String = "", // Prioridad de la tarea
val pagado: Boolean = false, // Estado de si la tarea ha sido pagada o no
val estado: String = "", //Estado de la taréa
val valoracion: Int = 0, //Valoracion del ratingBar
val tecnico: String = "", //String técnico de la taréa
val descripcion: String = "", //String descripción de la taréa
val colorFondo: Color = Color.Transparent, //Color de fondo de la tarea
    val esFormularioValido: Boolean = false, //Estado en caso de que el formulario sea válido
    val mostrarDialogo: Boolean = false, //Diálogo de confirmacion
    val esTareaNueva: Boolean = true, //Taréa nueva o modificada
    val listaTareas: ArrayList<Tarea> = TempModelTareas.listaTareas, //Lista de taréas, cargadas desde un tempModel
    val uriImagen: String = "", //Uri de la imagen, direccion, si la tiene, claro está
    val snackbarHostState: SnackbarHostState, //Necesario para los mensajes temporales
    val scope: CoroutineScope, //Necesario para el uso de coorrutinas
)

