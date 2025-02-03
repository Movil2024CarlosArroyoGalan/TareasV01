package net.iessochoa.carlosarroyogalan.tareasv01.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tareas")
data class Tarea(
    @PrimaryKey(autoGenerate = true)
    var id:Long?=null, //ID autogenerada
    val categoria:Int,
    val prioridad:Int,
    val img:String, //Ruta de la imagen
    val pagado:Boolean, //Indicacion si está pagadao
    val estado:Int, //Estado de la tarea
    val valoracionCliente:Int, //Valoración del cliente
    val tecnico:String, //Tecnico
    val descripcion:String //Descripcion
) {
    companion object {
        var idContador = 1L//iniciamos contador de tareas
        private fun generateId(): Long {
            return idContador++//sumamos uno al contador
        }
    }
    //Sobrescribe el método equals para comparar tareas por id
    override fun equals(other: Any?): Boolean {
        return (other is Tarea)&&(this.id == other?.id)
    }
    //Constructor secundario para almacenar manualmente el ID
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
