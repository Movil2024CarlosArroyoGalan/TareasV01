package net.iessochoa.carlosarroyogalan.tareasv01.ui.screens.tarea

import android.app.Application
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import net.iessochoa.carlosarroyogalan.tareasv01.R
import net.iessochoa.carlosarroyogalan.tareasv01.ui.theme.ColorPrioridadAlta

class TareaViewModel(application: Application): AndroidViewModel(application) {
    private val context = application.applicationContext
    val listaPrioridad = context.resources.getStringArray(R.array.prioridad)
    val listaCategory = context.resources.getStringArray(R.array.categoria)
    val listaEstados = context.resources.getStringArray(R.array.estado_tarea)
    private val PRIORIDAD_ALTA = listaPrioridad[2]

    private val _uiStateTarea = MutableStateFlow(
        UiStateTarea(
            categoria = listaCategory[0],
            prioridad = listaPrioridad[0],
            estado = listaEstados[0]
        )
    )
    val uiStateTarea: StateFlow<UiStateTarea> = _uiStateTarea.asStateFlow()

    fun onValueChangeCategoria(nuevaCategoria: String) {
        _uiStateTarea.value = _uiStateTarea.value.copy(categoria = nuevaCategoria)
    }

    fun onValueChangePrioridad(nuevaPrioridad: String) {
        val colorFondo = if (PRIORIDAD_ALTA == nuevaPrioridad) ColorPrioridadAlta else Color.Transparent
        _uiStateTarea.value = _uiStateTarea.value.copy(
            prioridad = nuevaPrioridad,
            colorFondo = colorFondo
        )
    }

    fun onValueChangePagado(pagado: Boolean) {
        _uiStateTarea.value = _uiStateTarea.value.copy(pagado = pagado)
    }

    fun onValueChangeEstado(nuevoEstado: String) {
        _uiStateTarea.value = _uiStateTarea.value.copy(estado = nuevoEstado)
    }

    fun onValueChangeValoracion(nuevaValoracion: Int) {
        _uiStateTarea.value = _uiStateTarea.value.copy(valoracion = nuevaValoracion)
    }

    fun onValueChangeTecnico(nuevoTecnico: String) {
        _uiStateTarea.value = _uiStateTarea.value.copy(
            tecnico = nuevoTecnico,
            esFormularioValido = nuevoTecnico.isNotBlank() && _uiStateTarea.value.descripcion.isNotBlank())
    }

    fun onValueChangeDescripcion(nuevaDescripcion: String) {
        _uiStateTarea.value = _uiStateTarea.value.copy(
            descripcion = nuevaDescripcion,
            esFormularioValido = nuevaDescripcion.isNotBlank() && _uiStateTarea.value.tecnico.isNotBlank())
    }
    fun onGuardar() {
        _uiStateTarea.value = _uiStateTarea.value.copy(
            mostrarDialogo = true
        )
    }
    fun onConfirmarDialogoGuardar() {
        _uiStateTarea.value = _uiStateTarea.value.copy(
            mostrarDialogo = false
        )
    }
    fun onCancelarDialogoGuardar() {
        _uiStateTarea.value = _uiStateTarea.value.copy(
            mostrarDialogo = false
        )
    }
}

