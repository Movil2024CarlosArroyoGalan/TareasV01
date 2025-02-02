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
    private val _uiStateSinPagar = MutableStateFlow(false)
    val uiStateSinPagar: StateFlow<Boolean> = _uiStateSinPagar.asStateFlow()

    val listaFiltroEstado =
        context.resources.getStringArray(R.array.filtro_estado_tarea).toList()
    val _uiStateFiltro= MutableStateFlow(
        UiStateFiltro(
            filtroEstado = listaFiltroEstado[3],
            )
    )
    val uiStatelistaTareas: StateFlow<ListaUiState> = combine(_uiStateFiltro, uiStateSinPagar) { uiStateFiltro, sinPagar ->
        when {
            sinPagar -> Repository.getTareasByPayment().map { lista ->
                lista.filter { it.estado == listaFiltroEstado.indexOf(uiStateFiltro.filtroEstado) }
            }
            uiStateFiltro.filtroEstado == listaFiltroEstado[3] -> Repository.getAllTareas()
            else -> Repository.getTareasByEstado(listaFiltroEstado.indexOf(uiStateFiltro.filtroEstado))
        }
    }.flatMapLatest { flowDeListaTareas ->
        flowDeListaTareas.map { listaTareas ->
            ListaUiState(listaPalabra = listaTareas)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ListaUiState()
    )

    private val _uiStateDialogo = MutableStateFlow(UiStateDialogo())
    val uiStateDialogo: StateFlow<UiStateDialogo> = _uiStateDialogo.asStateFlow()
    //Inicializacion de la lista, sin este nos encontrariamos la lista vacia y no podriamos trabajar con ella
    init {
        if (TempModelTareas.listaTareas.isEmpty()) {
            TempModelTareas.iniciaPruebaTareas()
        }
    }
    fun delTarea(tarea: Tarea) {
        viewModelScope.launch (Dispatchers.IO) {
            Repository.delTarea(tarea)
        }
    }
    fun onMostrarDialogoBorrar(tarea: Tarea){
        _uiStateDialogo.value = UiStateDialogo(
            mostrarDialogoBorrar = true,
            tareaBorrar = tarea)
    }
    fun onAceptarDialogo(){
        _uiStateDialogo.value.tareaBorrar?.let {
            delTarea(it)
        }
        _uiStateDialogo.value = UiStateDialogo()
    }
    fun onCancerlarDialogo(){
        _uiStateDialogo.value = UiStateDialogo()
    }
    fun onCheckedChangeFiltroEstado(nuevoFiltroEstado: String) {
        _uiStateFiltro.value=_uiStateFiltro.value.copy(
            filtroEstado = nuevoFiltroEstado
        )
    }
    fun onCheckedChangeFiltroSinPagar(checked: Boolean) {
        _uiStateSinPagar.value = checked
    }
}