package net.iessochoa.carlosarroyogalan.tareasv01.ui.screens.vistaTarea

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import net.iessochoa.carlosarroyogalan.tareasv01.data.tempmodel.TempModelTareas

class VistaTareaViewModel: ViewModel(){
    //Estado de la vista de la tarea desde mutablestateFlow uiStateVistaTarea
    private val _uiStateVistaTarea = MutableStateFlow(UiStateVistaTareas())
    // Exposición del estado como StateFlow para que se pueda observar desde la UI
    val uiStateVistaTarea: StateFlow<UiStateVistaTareas> = _uiStateVistaTarea.asStateFlow()
    //Encuentra y actualiza la tarea en el estado de la ui segun el id
    fun findTarea(desTarea: Long?){
        // Si desTarea no es nulo, obtiene la tarea desde el modelo temporal
        val tarea = desTarea?.let { TempModelTareas.getTarea(it) }
        // Actualiza el estado de la UI con la tarea obtenida
        _uiStateVistaTarea.update {
            //Copia del estado con la tarea actualizada
            it.copy(tarea = tarea)
        }
    }
}