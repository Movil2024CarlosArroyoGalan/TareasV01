package net.iessochoa.carlosarroyogalan.tareasv01.ui.screens.listatareas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import net.iessochoa.carlosarroyogalan.tareasv01.data.db.entities.Tarea
import net.iessochoa.carlosarroyogalan.tareasv01.data.repository.Repository
import net.iessochoa.carlosarroyogalan.tareasv01.data.tempmodel.TempModelTareas

class ListaTareasViewModel() : ViewModel() {
    val listaTareasUiState = Repository.getAllTareas().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
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

}

