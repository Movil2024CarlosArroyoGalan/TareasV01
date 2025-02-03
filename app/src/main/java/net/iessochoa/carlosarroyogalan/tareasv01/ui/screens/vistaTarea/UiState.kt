package net.iessochoa.carlosarroyogalan.tareasv01.ui.screens.vistaTarea

import net.iessochoa.carlosarroyogalan.tareasv01.data.db.entities.Tarea
data class UiStateVistaTareas(
    // Representa una tarea que puede ser nula si no hay tarea seleccionada o cargada
    val tarea: Tarea?= null
)