package net.iessochoa.carlosarroyogalan.tareasv01.ui.screens.tarea

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.flow.MutableStateFlow
import net.iessochoa.carlosarroyogalan.tareasv01.R
import net.iessochoa.carlosarroyogalan.tareasv01.ui.theme.ColorPrioridadAlta

data class UiStateTarea(
val categoria: String = "",
val prioridad: String = "",
val pagado: Boolean = false,
val estado: String = "",
val valoracion: Int = 0,
val tecnico: String = "",
val descripcion: String = "",
val colorFondo: Color = Color.Transparent
)

