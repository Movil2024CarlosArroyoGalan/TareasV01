package net.iessochoa.carlosarroyogalan.tareasv01.data.repository

import net.iessochoa.carlosarroyogalan.tareasv01.data.db.entities.Tarea
import net.iessochoa.carlosarroyogalan.tareasv01.data.tempmodel.TempModelTareas

object Repository {
    operator fun invoke(){
        TempModelTareas()
    }
    fun addTarea(tarea: Tarea)= TempModelTareas.addTarea(tarea)
    fun delTarea(tarea: Tarea)= TempModelTareas.delTarea(tarea)
    fun getAllTareas()=TempModelTareas.getAllTareas()
    fun getTarea(id:Long)=TempModelTareas.getTarea(id)

}