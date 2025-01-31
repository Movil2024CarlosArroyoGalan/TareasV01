package net.iessochoa.carlosarroyogalan.tareasv01.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tareas")
data class Tarea(
    @PrimaryKey(autoGenerate = true)
    var id:Long?=null,
    val categoria:Int,
    val prioridad:Int,
    val img:String,
    val pagado:Boolean,
    val estado:Int,
    val valoracionCliente:Int,
    val tecnico:String,
    val descripcion:String
) {
    companion object {
        var idContador = 1L//iniciamos contador de tareas
        private fun generateId(): Long {
            return idContador++//sumamos uno al contador
        }
    }
    override fun equals(other: Any?): Boolean {
        return (other is Tarea)&&(this.id == other?.id)
    }
    constructor(
        categoria:Int,
        prioridad:Int,
        img:String,
        pagado:Boolean,
        estado:Int,
        valoracionCliente:Int,
        tecnico:String,
        descripcion:String):this(generateId(),categoria,prioridad,img,pagado,estado,valoracionCliente, tecnico, descripcion){}
}
