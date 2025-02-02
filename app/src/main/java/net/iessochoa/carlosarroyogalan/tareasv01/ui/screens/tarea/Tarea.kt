package net.iessochoa.carlosarroyogalan.tareasv01.ui.screens.tarea

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import kotlinx.coroutines.launch
import net.iessochoa.carlosarroyogalan.tareasv01.R
import net.iessochoa.carlosarroyogalan.tareasv01.ui.components.AppBar
import net.iessochoa.carlosarroyogalan.tareasv01.ui.components.BasicRadioButton
import net.iessochoa.carlosarroyogalan.tareasv01.ui.components.DialogoDeConfirmacion
import net.iessochoa.carlosarroyogalan.tareasv01.ui.components.DynamicSelectTextField
import net.iessochoa.carlosarroyogalan.tareasv01.ui.components.RatingBar
import net.iessochoa.carlosarroyogalan.tareasv01.ui.theme.TareasV01Theme

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun TareaScreen(
   viewModel: TareaViewModel = viewModel(),
   idTarea: Long? = null,
   onVolver: () -> Unit = {},
   onMostrar: () -> Unit = {}
) {
    val uiStateTarea by viewModel.uiStateTarea.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val isTareaNueva = idTarea == null
    /*
Permisos:
 Petición de permisos múltiples condicionales según la versión de Android
*/
    val permissionState = rememberMultiplePermissionsState(
        permissions = mutableListOf(//permiso para hacer fotos
            android.Manifest.permission.CAMERA
        ).apply {//Permisos para la galería
            //Si es Android menor de 10
            if (android.os.Build.VERSION.SDK_INT <= android.os.Build.VERSION_CODES.P) {
                add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                add(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            }//si es Android igual o superior a 13
            if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.TIRAMISU) {
                add(android.Manifest.permission.READ_MEDIA_IMAGES)
            }//si es Android igual o superior a 14. Este permiso no lo tengo claro si es necesario
            //podéis probar en el dispositivo real si tenéis Android 14
            /*if (android.os.Build.VERSION.SDK_INT >=
           android.os.Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            add(android.Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED)
            }*/
        }
    )
    idTarea?.let { viewModel.getTarea(it) }

    /*
    var categoriaSeleccionada by remember {
        mutableStateOf("")
    }
    var isPagado by remember {
        mutableStateOf(false)
    }
    val estadoTarea = remember {
        mutableStateOf("")
    }
    var valoracionCliente by remember {
        mutableIntStateOf(0)
    }
    var tecnico by remember {
        mutableStateOf("")
    }
    var descripcion by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current
    val estadosTarea = context.resources.getStringArray(R.array.estado_tarea)


        val categorias = context.resources.getStringArray(R.array.categoria)
        val prioridades = context.resources.getStringArray(R.array.prioridad)

    val colorFondo = if (prioridadSeleccionada == prioridades[2]) ColorPrioridadAlta else Color.Transparent
    *
     */
    Scaffold (
        modifier = Modifier.fillMaxSize(),
        snackbarHost = {SnackbarHost(snackbarHostState)},
        floatingActionButton = {
            FloatingActionButton(onClick = {
                if(uiStateTarea.esFormularioValido)
                    viewModel.onGuardar()
                else{
                    uiStateTarea.scope.launch {
                        uiStateTarea.snackbarHostState.showSnackbar(
                            message = context.getString(R.string.hay_que_rellenar_todos_los_campos),
                            duration = SnackbarDuration.Short
                        )
                    }
                }
            }) {
                Icon(
                    painter = painterResource(android.R.drawable.ic_menu_save),
                    contentDescription = stringResource(R.string.guardar)
                )
            }
        },
        topBar ={
            AppBar(
                tituloPantallaActual =
                if (uiStateTarea.esTareaNueva)
                    stringResource(R.string.tarea_nueva)
                else
                    stringResource(R.string.modificar_tarea),
                puedeNavegarAtras = true,
                navegaAtras = onVolver
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier.padding(innerPadding),
            color = uiStateTarea.colorFondo
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        DynamicSelectTextField (
                            selectedValue = uiStateTarea.categoria,
                            options = viewModel.listaCategory,
                            label = stringResource(R.string.categorias),
                            onSelectionChanged = { viewModel.onValueChangeCategoria(it) },
                        )
                        DynamicSelectTextField (
                            selectedValue = uiStateTarea.prioridad,
                            options = viewModel.listaPrioridad,
                            label = stringResource(R.string.prioridad),
                            onSelectionChanged = { viewModel.onValueChangePrioridad(it) }
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    AsyncImage(
                        model = R.drawable.ic_launcher_background   ,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .aspectRatio(2f)
                            .clip(RoundedCornerShape(16.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
                Row(modifier = Modifier.padding(8.dp, 0.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(painter = if (uiStateTarea.pagado) painterResource(R.drawable.ic_pagado)
                    else painterResource(R.drawable.ic_no_pagado), contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(stringResource(R.string.pagado))
                    Spacer(modifier = Modifier.width(8.dp))
                    Switch(checked = uiStateTarea.pagado,
                        onCheckedChange = { viewModel.onValueChangePagado(it) })
                    IconButton(
                        onClick = {
                            if (!permissionState.allPermissionsGranted)
                                permissionState.launchMultiplePermissionRequest()
                        }) {
                        Icon(painterResource(R.drawable.ic_imagesearch),
                            contentDescription = stringResource(R.string.abrir_galeria)
                        )
                    }
                    IconButton(
                        onClick = {
                            if (!permissionState.allPermissionsGranted)
                                permissionState.launchMultiplePermissionRequest()
                        }) {
                        Icon(painterResource(R.drawable.ic_camera),
                            contentDescription = stringResource(R.string.abrir_c_mara)
                        )
                    }
                }
                Row(modifier = Modifier.padding(8.dp, 0.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text(text = stringResource(R.string.estado_de_la_tarea))
                    Spacer(modifier = Modifier.width(8.dp))
                    uiStateTarea.estado.let { estado ->
                        val iconId = when (estado) {
                            "Abierta" -> R.drawable.ic_abierta
                            "En curso" -> R.drawable.ic_en_curso
                            "Cerrada" -> R.drawable.ic_cerrada
                            else -> null
                        }
                        iconId?.let { id ->
                            Icon(
                                painter = painterResource(id = id),
                                contentDescription = estado
                            )
                        }
                    }
                }
                BasicRadioButton(selectedOption = uiStateTarea.estado, onOptionSelected = { viewModel.onValueChangeEstado(it) }, options = viewModel.listaEstados)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = stringResource(R.string.valoracion_cliente))
                    RatingBar (uiStateTarea.valoracion, onRatingChanged = { viewModel.onValueChangeValoracion(it)})
                }
                OutlinedTextField(
                    value = uiStateTarea.tecnico,
                    onValueChange = { viewModel.onValueChangeTecnico(it)},
                    label = { Text(stringResource(R.string.tecnico)) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
                )
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)) {
                    OutlinedTextField(
                        value = uiStateTarea.descripcion,
                        onValueChange = { viewModel.onValueChangeDescripcion(it)},
                        label = { Text(stringResource(R.string.descripcion)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .verticalScroll(rememberScrollState()),
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                        singleLine = false
                    )
                }
                Box(modifier = Modifier){
                    if (uiStateTarea.mostrarDialogo) {
                        DialogoDeConfirmacion(
                            onDismissRequest = {
                                //cancela el dialogo
                                viewModel.onCancelarDialogoGuardar()
                            },
                            onConfirmation = {
                                //guardaría los cambios
                                viewModel.onConfirmarDialogoGuardar()
                                uiStateTarea.scope.launch {
                                    uiStateTarea.snackbarHostState.showSnackbar(
                                        message = context.getString(R.string.tarea_guardada),
                                        duration = SnackbarDuration.Short
                                    )
                                }
                            },
                            dialogTitle = stringResource(R.string.atenci_n),
                            dialogText = stringResource(R.string.desea_guardar_los_cambios),
                            icon = Icons.Default.Info
                        )
                    }
                    if (!isTareaNueva){
                        Spacer(modifier = Modifier.width(16.dp))
                        Button(
                            onClick = onMostrar,
                            enabled = uiStateTarea.listaTareas.isNotEmpty()
                        ) {
                            Text(stringResource(R.string.ver_tarea))
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun TareaScreenPreview() {
    TareasV01Theme {
        TareaScreen()
    }
}