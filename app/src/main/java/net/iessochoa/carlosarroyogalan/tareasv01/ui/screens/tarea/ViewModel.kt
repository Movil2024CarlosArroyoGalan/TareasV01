package net.iessochoa.carlosarroyogalan.tareasv01.ui.screens.tarea

import android.app.Application
import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import net.iessochoa.carlosarroyogalan.tareasv01.R
import net.iessochoa.carlosarroyogalan.tareasv01.data.db.entities.Tarea
import net.iessochoa.carlosarroyogalan.tareasv01.data.repository.Repository
import net.iessochoa.carlosarroyogalan.tareasv01.ui.theme.ColorPrioridadAlta

class TareaViewModel(application: Application): AndroidViewModel(application) {
    private val context = application.applicationContext
    val listaPrioridad = context.resources.getStringArray(R.array.prioridad)
    val listaCategory = context.resources.getStringArray(R.array.categoria)
    val listaEstados = context.resources.getStringArray(R.array.estado_tarea)
    private val PRIORIDAD_ALTA = listaPrioridad[2]
    var tarea: Tarea? = null

    private val _uiStateTarea = MutableStateFlow(
        UiStateTarea(
            categoria = listaCategory[0],
            prioridad = listaPrioridad[0],
            estado = listaEstados[0],
            pagado = false,
            valoracion = 0,
            tecnico = "",
            descripcion = "",
            esFormularioValido = false,
            mostrarDialogo = false,
            snackbarHostState = SnackbarHostState(),
            scope = viewModelScope
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
            esFormularioValido = nuevoTecnico.isNotBlank() && _uiStateTarea.value.tecnico.isNotBlank())
    }

    fun onValueChangeDescripcion(nuevaDescripcion: String) {
        _uiStateTarea.value = _uiStateTarea.value.copy(
            descripcion = nuevaDescripcion,
            esFormularioValido = nuevaDescripcion.isNotBlank() && _uiStateTarea.value.descripcion.isNotBlank())
    }
    fun onGuardar() {
        _uiStateTarea.value = _uiStateTarea.value.copy(
            mostrarDialogo = true
        )
        Repository.addTarea(uiStateToTarea())
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
    /**
     *Carga los datos de la tarea en UiState,
     * que a su vez actualiza la interfaz de usuario *
     */
    fun tareaToUiState(tarea: Tarea) {
        _uiStateTarea.value = _uiStateTarea.value.copy(
            categoria = listaCategory[tarea.categoria],
            prioridad = listaPrioridad[tarea.prioridad],
            pagado = tarea.pagado,
            estado = listaEstados[tarea.estado],
            valoracion = tarea.valoracionCliente,
            tecnico = tarea.tecnico,
            descripcion = tarea.descripcion,
            esFormularioValido = tarea.tecnico.isNotBlank() &&
                    tarea.descripcion.isNotBlank(),
            esTareaNueva = false,
            colorFondo = if (PRIORIDAD_ALTA == listaPrioridad[tarea.prioridad])
                ColorPrioridadAlta else Color.Transparent
        )
    }
    fun uiStateToTarea(): Tarea {
        return if (uiStateTarea.value.esTareaNueva)
        //si es nueva, le asigna un id
            Tarea(
                categoria = listaCategory.indexOf(uiStateTarea.value.categoria),
                prioridad = listaPrioridad.indexOf(uiStateTarea.value.prioridad),
                img = R.drawable.foto3.toString(),
                pagado = uiStateTarea.value.pagado,
                estado = listaEstados.indexOf(uiStateTarea.value.estado),
                valoracionCliente = uiStateTarea.value.valoracion,
                tecnico = uiStateTarea.value.tecnico,
                descripcion = uiStateTarea.value.descripcion
            ) //si no es nueva, actualiza la tarea
        else Tarea(
            tarea!!.id,
            categoria = listaCategory.indexOf(uiStateTarea.value.categoria),
            prioridad = listaPrioridad.indexOf(uiStateTarea.value.prioridad),
            img = tarea!!.img,
            pagado = uiStateTarea.value.pagado,
            estado = listaEstados.indexOf(uiStateTarea.value.estado),
            valoracionCliente = uiStateTarea.value.valoracion,
            tecnico = uiStateTarea.value.tecnico,
            descripcion = uiStateTarea.value.descripcion
        )
    }
    fun getTarea(id: Long) {
        tarea = Repository.getTarea(id)
//si no es nueva inicia la UI con los valores de la tarea
        if (tarea != null) tareaToUiState(tarea!!)
    }





}

