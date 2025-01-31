package net.iessochoa.carlosarroyogalan.tareasv01.data.db.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import net.iessochoa.carlosarroyogalan.tareasv01.R
import net.iessochoa.carlosarroyogalan.tareasv01.data.db.dao.TareasDao
import net.iessochoa.carlosarroyogalan.tareasv01.data.db.entities.Tarea

@Database(entities = arrayOf(Tarea::class), version = 1, exportSchema
= false)
public abstract class TareasDataBase : RoomDatabase() {
    abstract fun tareasDao(): TareasDao
    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: TareasDataBase? = null
        fun getDatabase(context: Context): TareasDataBase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TareasDataBase::class.java,
                    context.getString(R.string.tareas_database)
                )
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}