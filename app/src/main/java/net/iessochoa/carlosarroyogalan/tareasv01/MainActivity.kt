package net.iessochoa.carlosarroyogalan.tareasv01

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import net.iessochoa.carlosarroyogalan.tareasv01.ui.navigation.AppNavigation
import net.iessochoa.carlosarroyogalan.tareasv01.ui.screens.tarea.TareaScreen
import net.iessochoa.carlosarroyogalan.tareasv01.ui.theme.TareasV01Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TareasV01Theme  {
                AppNavigation()
            }
        }
    }
}

