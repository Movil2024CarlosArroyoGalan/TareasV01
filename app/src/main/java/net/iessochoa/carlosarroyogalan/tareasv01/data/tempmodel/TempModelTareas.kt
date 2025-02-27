package net.iessochoa.carlosarroyogalan.tareasv01.data.tempmodel

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import net.iessochoa.carlosarroyogalan.tareasv01.R
import net.iessochoa.carlosarroyogalan.tareasv01.data.db.entities.Tarea
import kotlin.random.Random

object TempModelTareas {
    //lista de tareas
    val listaTareas = ArrayList<Tarea>()
    //StateFlow observable
    private val _tareasStateFlow = MutableStateFlow<List<Tarea>>(listaTareas)
    //Obtiene todas las tareas con un Flow
    fun getAllTareas(): Flow<List<Tarea>> {
        return _tareasStateFlow
    }
    //Agrega una tarea una o actualiza la que ya existe
    fun addTarea(tarea: Tarea) {
        val pos = listaTareas.indexOf(tarea)
        if (pos < 0) {
            listaTareas.add(tarea)
        } else {
            listaTareas.set(pos, tarea)
        }
        _tareasStateFlow.value = listaTareas
    }
    //Borra la tarea
    fun delTarea(tarea: Tarea) {
        listaTareas.remove(tarea)
        _tareasStateFlow.value = listaTareas
    }
    //Obtiene la tarea a partir de su id
    fun getTarea(id: Long): Tarea? {
        return listaTareas.find { it.id == id }
    }
    //Inicia el modelo con tareas de prueba
    fun iniciaPruebaTareas() {
        val tecnicos = listOf(
            "Pepe Gotero",
            "Sacarino Pómez",
            "Mortadelo Fernández",
            "Filemón López",
            "Zipi Climent",
            "Zape Gómez",
            "Pepito Grillo"
        )
        val fotos= listOf(R.drawable.foto1, R.drawable.foto2, R.drawable.foto3, R.drawable.foto4)
        lateinit var tarea: Tarea
        (1..10).forEach{
            tarea = Tarea(
                categoria = (0..4).random(),
                prioridad = (0..2).random(),
                img = fotos.random().toString(),
                pagado = Random.nextBoolean(),
                estado = (0..2).random(),
                valoracionCliente = (0..5).random(),
                tecnico = tecnicos.random(),
                descripcion = "tarea $it : Lorem \n ipsum dolor sit amet, consectetur adipiscing elit. Mauris consequat ligula et vehicula mattis. \n Etiam tristique ornare lacinia.\nVestibulum lacus magna, dignissim et tempor id, convallis sed augue"
            )
            listaTareas.add(tarea)
        }
        _tareasStateFlow.value = listaTareas
    }
    //Permite iniciar el objeto Singleton
    operator fun invoke(){
        //this.application= context.applicationContext as Application
        iniciaPruebaTareas()
    }
}