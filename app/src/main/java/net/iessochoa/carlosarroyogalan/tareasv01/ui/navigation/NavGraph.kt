package net.iessochoa.carlosarroyogalan.tareasv01.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import net.iessochoa.carlosarroyogalan.tareasv01.ui.screens.listatareas.ListaTareasScreen
import net.iessochoa.carlosarroyogalan.tareasv01.ui.screens.tarea.TareaScreen
import net.iessochoa.carlosarroyogalan.tareasv01.ui.screens.vistaTarea.VistaTareaScreen

@Composable
fun AppNavigation(){
    //Controlador de navegación para el flujo de pantallas
    val navController = rememberNavController()
    //NavHost definido, gestionará la navegacion de la aplicación
    NavHost(
        navController = navController,
        //Pantalla de inicio
        startDestination = LISTA_TAREAS_RUTA
    ) {
        //Ruta para la pantalla de lista de tareas
        composable(LISTA_TAREAS_RUTA) {
            ListaTareasScreen(
                // Ruta a la pantalla de creación de una nueva tarea
                onClickNuevaTarea = {
                    navController.navigate("$TAREA_RUTA?desTarea=null")
                },
                // Ruta a la pantalla de modificación de una tarea existente, pasando su ID
                onClickModificarTarea = {
                    desTarea -> navController.navigate("$TAREA_RUTA?desTarea=$desTarea")
                },
                // Ruta a la pantalla de visualización de una tarea específica
                onClickVerTarea = {
                        desTarea -> navController.navigate("$VISTA_TAREA_ROUTE?posTarea=$desTarea")
                }
            )
        }
        //Ruta para la pantalla de Modificación o creacion de Tareas
        composable("$TAREA_RUTA?desTarea={desTarea}") { backStackEntry ->
            val desTarea = backStackEntry.arguments?.getString("desTarea")?.toLongOrNull()
            TareaScreen(
                idTarea = desTarea,
                onVolver = {
                    navController.navigateUp()
                },
                // Función para mostrar la tarea en la pantalla de detalles
                onMostrar = {
                    if (desTarea != null)
                        navController.navigate("$VISTA_TAREA_ROUTE?desTarea=$desTarea")
                }
            )
        }
        //Ruta para la pantalla de visualizacion de la tarea
        composable("$VISTA_TAREA_ROUTE?desTarea={desTarea}") { backStackEntry ->
            val desTarea = backStackEntry.arguments?.getString("desTarea")?.toLongOrNull()
            VistaTareaScreen (
                desTarea = desTarea,
                onVolver = {
                    navController.navigateUp()
                },
                //Funcion para volver de vuelta a la pantalla lista tareas
                onVolverToInicio = {
                    navController.popBackStack(LISTA_TAREAS_RUTA, inclusive = false)
                }
            )
        }

    }
}