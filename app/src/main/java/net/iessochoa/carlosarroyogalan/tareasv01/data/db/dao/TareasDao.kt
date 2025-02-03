package net.iessochoa.carlosarroyogalan.tareasv01.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import net.iessochoa.carlosarroyogalan.tareasv01.data.db.entities.Tarea

@Dao
interface TareasDao {
    // Inserta una tarea en la base de datos. Si ya existe, la reemplaza
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTarea(tarea: Tarea)
    //Elimina la tarea de la base de datoos
    @Delete
    suspend fun delTarea(tarea: Tarea)
    //Obtiene todas las tareas de la base de datos
    @Query("SELECT * FROM tareas")
    fun getTareas(): Flow<List<Tarea>>
    //Recupera una tarea específica a partir de su id
    @Query("SELECT * FROM tareas WHERE id = :id")
    suspend fun getTarea(id: Long): Tarea
    //Obtiene todas las tareas a partir de su estado, este tambien nos sirve para nuestro filtro
    @Query("SELECT * FROM tareas WHERE estado = :estado")
    fun getTareasByEstado(estado: Int): Flow<List<Tarea>>
    //Obtiene todas las tareas no pagadas, esto nos sirve para nuestro filtro
    @Query("SELECT * FROM tareas WHERE pagado = 0")
    fun getTareasByPayment(): Flow<List<Tarea>>

}