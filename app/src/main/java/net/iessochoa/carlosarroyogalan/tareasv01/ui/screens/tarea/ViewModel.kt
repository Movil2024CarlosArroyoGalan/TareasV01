package net.iessochoa.carlosarroyogalan.tareasv01.ui.screens.tarea

import android.app.Application
import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import net.iessochoa.carlosarroyogalan.tareasv01.R
import net.iessochoa.carlosarroyogalan.tareasv01.data.db.entities.Tarea
import net.iessochoa.carlosarroyogalan.tareasv01.data.repository.Repository
import net.iessochoa.carlosarroyogalan.tareasv01.ui.theme.ColorPrioridadAlta

class TareaViewModel(application: Application): AndroidViewModel(application) {
    //Contexto de la aplicacion heredado de application
    private val context = application.applicationContext
    //Opciones, obtenidas desde los recursos xml
    val listaPrioridad = context.resources.getStringArray(R.array.prioridad)
    val listaCategory = context.resources.getStringArray(R.array.categoria)
    val listaEstados = context.resources.getStringArray(R.array.estado_tarea)
    //Constante de prioridad alta
    private val PRIORIDAD_ALTA = listaPrioridad[2]
    //Objeto de tarea, null en caso de no haberse asignado
    var tarea: Tarea? = null
    //Mutable state que contiene la informacion de la tarea, se inicializa todo
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
    //Estado de la tarea desde la uiState
    val uiStateTarea: StateFlow<UiStateTarea> = _uiStateTarea.asStateFlow()
    //Actualizacion de la categoría de la tarea
    fun onValueChangeCategoria(nuevaCategoria: String) {
        _uiStateTarea.value = _uiStateTarea.value.copy(categoria = nuevaCategoria)
    }
    //Actualizacion de la prioridad de la tarea
    fun onValueChangePrioridad(nuevaPrioridad: String) {
        val colorFondo = if (PRIORIDAD_ALTA == nuevaPrioridad) ColorPrioridadAlta else Color.Transparent
        _uiStateTarea.value = _uiStateTarea.value.copy(
            prioridad = nuevaPrioridad,
            colorFondo = colorFondo
        )
    }
    //Actualizacion de el pago de la tarea
    fun onValueChangePagado(pagado: Boolean) {
        _uiStateTarea.value = _uiStateTarea.value.copy(pagado = pagado)
    }
    //Actualizacion de el estado de la tarea
    fun onValueChangeEstado(nuevoEstado: String) {
        _uiStateTarea.value = _uiStateTarea.value.copy(estado = nuevoEstado)
    }
    //Actualizacion de la valoracion de la tarea
    fun onValueChangeValoracion(nuevaValoracion: Int) {
        _uiStateTarea.value = _uiStateTarea.value.copy(valoracion = nuevaValoracion)
    }
    //Actualizacion de el técnico de la tarea
    fun onValueChangeTecnico(nuevoTecnico: String) {
        _uiStateTarea.value = _uiStateTarea.value.copy(
            tecnico = nuevoTecnico,
            esFormularioValido = nuevoTecnico.isNotBlank() && _uiStateTarea.value.tecnico.isNotBlank())
    }
    //Actualizacion de la descripcion de la tarea
    fun onValueChangeDescripcion(nuevaDescripcion: String) {
        _uiStateTarea.value = _uiStateTarea.value.copy(
            descripcion = nuevaDescripcion,
            esFormularioValido = nuevaDescripcion.isNotBlank() && _uiStateTarea.value.descripcion.isNotBlank())
    }
    //Actualizacion de la guardar la tarea completa de la tarea
    fun onGuardar() {
        _uiStateTarea.value = _uiStateTarea.value.copy(
            mostrarDialogo = true
        )
    }
    //Confirmacion de el guardado de la tarea
    fun onConfirmarDialogoGuardar() {
        //cierra el dialogo
        _uiStateTarea.value = _uiStateTarea.value.copy(
            mostrarDialogo = false
        )
        //lanzamos la corrutina para guardar la tarea
        viewModelScope.launch(Dispatchers.IO) {
            Repository.addTarea(uiStateToTarea())
        }
    }
    //Cancelacion del guardado de la tarea
    fun onCancelarDialogoGuardar() {
        _uiStateTarea.value = _uiStateTarea.value.copy(
            mostrarDialogo = false
        )
    }
    /**
     *Carga los datos de la tarea en UiState,
     * que a su vez actualiza la interfaz de usuario *
     */
    //Actualiza toda la interfaz
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
                ColorPrioridadAlta else Color.Transparent,
            uriImagen = tarea.img
        )
    }
    fun uiStateToTarea(): Tarea {
        //Asigna un id autogenerado
        return if (uiStateTarea.value.esTareaNueva)
        //si es nueva, le asigna un id
            Tarea(
                //Valores de UIstate buscando los indices correctos
                categoria = listaCategory.indexOf(uiStateTarea.value.categoria),
                prioridad = listaPrioridad.indexOf(uiStateTarea.value.prioridad),
                img = uiStateTarea.value.uriImagen,
                pagado = uiStateTarea.value.pagado,
                estado = listaEstados.indexOf(uiStateTarea.value.estado),
                valoracionCliente = uiStateTarea.value.valoracion,
                tecnico = uiStateTarea.value.tecnico,
                descripcion = uiStateTarea.value.descripcion
            )
        //si no es nueva, actualiza la tarea
        else Tarea(
            tarea!!.id,
            categoria = listaCategory.indexOf(uiStateTarea.value.categoria),
            prioridad = listaPrioridad.indexOf(uiStateTarea.value.prioridad),
            img = uiStateTarea.value.uriImagen,
            pagado = uiStateTarea.value.pagado,
            estado = listaEstados.indexOf(uiStateTarea.value.estado),
            valoracionCliente = uiStateTarea.value.valoracion,
            tecnico = uiStateTarea.value.tecnico,
            descripcion = uiStateTarea.value.descripcion
        )
    }
    //Actualiza la URI de la imagen de la tarea
    fun setUri(uri: String){
        _uiStateTarea.value = _uiStateTarea.value.copy(
            uriImagen = uri
        )
    }
    //Obtiene la tarea desde la base de datos a partir de su Id
    fun getTarea(id: Long) {
        //lanzamos una corrutina que nos devuelve la tarea de la bd
        viewModelScope.launch (Dispatchers.IO) {
            tarea = Repository.getTarea(id)
            if (tarea != null) tareaToUiState(tarea!!)
        }
    }
}

