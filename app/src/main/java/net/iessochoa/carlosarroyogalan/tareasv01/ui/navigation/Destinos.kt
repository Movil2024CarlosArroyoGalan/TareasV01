package net.iessochoa.carlosarroyogalan.tareasv01.ui.navigation


import kotlinx.serialization.Serializable

object ListaTareasDestino

@Serializable
data class TareaDestino(val desTarea: Long? = null)
@Serializable
data class VistaTareasDestino(val desTarea: Long? = null)

const val LISTA_TAREAS_RUTA = "lista_tareas"
const val TAREA_RUTA = "tarea"
const val VISTA_TAREA_ROUTE = "vista_tarea"