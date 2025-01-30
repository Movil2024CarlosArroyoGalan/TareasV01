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
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = LISTA_TAREAS_RUTA
    ) {
        composable(LISTA_TAREAS_RUTA) {
            ListaTareasScreen(
                onClickNuevaTarea = {
                    navController.navigate("$TAREA_RUTA?desTarea=null")
                },
                onClickModificarTarea = {
                        desTarea -> navController.navigate("$TAREA_RUTA?desTarea=$desTarea")
                },
                onClickVerTarea = {
                        desTarea -> navController.navigate("$VISTA_TAREA_ROUTE?posTarea=$desTarea")
                }
            )
        }
        composable("$TAREA_RUTA?desTarea={desTarea}") { backStackEntry ->
            val desTarea = backStackEntry.arguments?.getString("desTarea")?.toLongOrNull()
            TareaScreen(
                idTarea = desTarea,
                onVolver = {
                    navController.navigateUp()
                },
                onMostrar = {
                    if (desTarea != null)
                        navController.navigate("$VISTA_TAREA_ROUTE?desTarea=$desTarea")
                }
            )
        }
        composable("$VISTA_TAREA_ROUTE?desTarea={desTarea}") { backStackEntry ->
            val desTarea = backStackEntry.arguments?.getString("desTarea")?.toLongOrNull()
            VistaTareaScreen (
                desTarea = desTarea,
                onVolver = {
                    navController.navigateUp()
                },
                onVolverToInicio = {
                    navController.popBackStack(LISTA_TAREAS_RUTA, inclusive = false)
                }
            )
        }

    }
}