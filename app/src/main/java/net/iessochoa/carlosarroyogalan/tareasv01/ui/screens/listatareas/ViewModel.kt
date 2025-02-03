package net.iessochoa.carlosarroyogalan.tareasv01.ui.screens.listatareas

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import net.iessochoa.carlosarroyogalan.tareasv01.R
import net.iessochoa.carlosarroyogalan.tareasv01.data.db.entities.Tarea
import net.iessochoa.carlosarroyogalan.tareasv01.data.repository.Repository
import net.iessochoa.carlosarroyogalan.tareasv01.data.tempmodel.TempModelTareas

class ListaTareasViewModel(application: Application): AndroidViewModel(application){
    private val context = application.applicationContext
    //StateFlow para manejar si solo se deben mostrar tareas sin pagar
    private val _uiStateSinPagar = MutableStateFlow(false)
    val uiStateSinPagar: StateFlow<Boolean> = _uiStateSinPagar.asStateFlow()
    //Lista de estados disponibles para filtrar tareas
    val listaFiltroEstado =
        context.resources.getStringArray(R.array.filtro_estado_tarea).toList()
    //StateFlow que mantiene el estado del filtro de tareas
    val _uiStateFiltro= MutableStateFlow(
        UiStateFiltro(
            //Valor por defecto
            filtroEstado = listaFiltroEstado[3],
            )
    )
    val uiStatelistaTareas: StateFlow<ListaUiState> = combine(_uiStateFiltro, uiStateSinPagar) { uiStateFiltro, sinPagar ->
        when {
            //Si se filtra por tareas sin pagar, se obtiene solo ese conjunto de tareas
            sinPagar -> Repository.getTareasByPayment().map { lista ->
                lista.filter { it.estado == listaFiltroEstado.indexOf(uiStateFiltro.filtroEstado) }
            }
            //Si el filtro de estado es "Todos", se obtiene todas las tareas
            uiStateFiltro.filtroEstado == listaFiltroEstado[3] -> Repository.getAllTareas()
            //Si no, se filtra por el estado seleccionado
            else -> Repository.getTareasByEstado(listaFiltroEstado.indexOf(uiStateFiltro.filtroEstado))
        }
    }.flatMapLatest { flowDeListaTareas ->
        //Transforma la lista de tareas en el estado de la UI
        flowDeListaTareas.map { listaTareas ->
            ListaUiState(listaPalabra = listaTareas)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000), //Mantiene el estado mientras la vista está suscrita
        initialValue = ListaUiState() //Valor inicial vacío
    )

    private val _uiStateDialogo = MutableStateFlow(UiStateDialogo())
    val uiStateDialogo: StateFlow<UiStateDialogo> = _uiStateDialogo.asStateFlow()
    //Inicializacion de la lista, sin este nos encontrariamos la lista vacia y no podriamos trabajar con ella
    init {
        if (TempModelTareas.listaTareas.isEmpty()) {
            TempModelTareas.iniciaPruebaTareas()
        }
    }
    //Funcion de eliminacion de tarea
    fun delTarea(tarea: Tarea) {
        viewModelScope.launch (Dispatchers.IO) {
            Repository.delTarea(tarea)
        }
    }
    //Funcion en caso de querer eliminar una tarea que muestre un dialogo
    fun onMostrarDialogoBorrar(tarea: Tarea){
        _uiStateDialogo.value = UiStateDialogo(
            mostrarDialogoBorrar = true,
            tareaBorrar = tarea)
    }
    //En caso de que acepte elimina la tarea
    fun onAceptarDialogo(){
        _uiStateDialogo.value.tareaBorrar?.let {
            delTarea(it)
        }
        _uiStateDialogo.value = UiStateDialogo()
    }
    //En caso de que no resetea el estado
    fun onCancerlarDialogo(){
        _uiStateDialogo.value = UiStateDialogo()
    }
    //Funcion que actualia el filtro de estado selccionado
    fun onCheckedChangeFiltroEstado(nuevoFiltroEstado: String) {
        _uiStateFiltro.value=_uiStateFiltro.value.copy(
            filtroEstado = nuevoFiltroEstado
        )
    }
    //Funcion que actualiza el filtro de tareas sin pagar
    fun onCheckedChangeFiltroSinPagar(checked: Boolean) {
        _uiStateSinPagar.value = checked
    }
}