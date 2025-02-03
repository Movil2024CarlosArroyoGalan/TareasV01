package net.iessochoa.carlosarroyogalan.tareasv01.ui.screens.tarea

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import coil3.Uri
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
import net.iessochoa.carlosarroyogalan.tareasv01.utils.loadFromUri
import net.iessochoa.carlosarroyogalan.tareasv01.utils.saveBitmapImage

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun TareaScreen(
    //Valores que usaremos en el futuro
   viewModel: TareaViewModel = viewModel(),
   idTarea: Long? = null,
   onVolver: () -> Unit = {},
   onMostrar: () -> Unit = {}
) {
    //Llamada al uiStateTarea por un valor
    val uiStateTarea by viewModel.uiStateTarea.collectAsState()
    //SnackBarHostState para poder definir el valor de nuestros valores
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    //Valor para nueva tarea
    val isTareaNueva = idTarea == null
    //Valor que se usará para que la imagen que tiene guardada esta tarea no se pierda
    val takePictureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val image = result.data?.extras?.get("data") as? Bitmap
            if (image != null) {
                uiStateTarea.scope.launch {
                    val uriCopia = saveBitmapImage(context, image)
                    viewModel.setUri(uriCopia.toString())
                }
            }
        }
    }

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
    /*
 Llamada a Galeria versión por encima de Versión 13 en Android. Para usarlo en
versiones inferiores
 tenéis incluir el Service de google que aparece en el manifest.xml
 */
    //Metodo para que cargue una imagen de la galería lo he modificado respecto a la original debido a que me fallaba
    val launcherGaleria = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri: android.net.Uri? ->
            if (uri != null) {
                try {
                    uiStateTarea.scope.launch {
                        val bitmap = loadFromUri(context, uri)
                        if (bitmap != null) {
                            val uriImagen = saveBitmapImage(context, bitmap)
                            viewModel.setUri(uriImagen.toString())
                        } else {
                            uiStateTarea.snackbarHostState.showSnackbar(
                                message = context.getString(R.string.error_al_cargar_la_imagen),
                                duration = SnackbarDuration.Short
                            )
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    uiStateTarea.scope.launch {
                        uiStateTarea.snackbarHostState.showSnackbar(
                            message = "Error inesperado: ${e.message}",
                            duration = SnackbarDuration.Short
                        )
                    }
                }
            } else {
                uiStateTarea.scope.launch {
                    uiStateTarea.snackbarHostState.showSnackbar(
                        message = context.getString(R.string.imagen_sin_seleccionar),
                        duration = SnackbarDuration.Short
                    )
                }
            }
        }
    )
    // Si se proporciona un idTarea, obtenemos su información del ViewModel
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
                //Distincion en caso de que en la lista hayas elegido crear una tarea nueva o modificar tarea
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
                // Sección de selección de categoría y prioridad junto con imagen
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        //Lista de categoría
                        DynamicSelectTextField (
                            selectedValue = uiStateTarea.categoria,
                            options = viewModel.listaCategory,
                            label = stringResource(R.string.categorias),
                            onSelectionChanged = { viewModel.onValueChangeCategoria(it) },
                        )
                        //Lista de prioridades
                        DynamicSelectTextField (
                            selectedValue = uiStateTarea.prioridad,
                            options = viewModel.listaPrioridad,
                            label = stringResource(R.string.prioridad),
                            onSelectionChanged = { viewModel.onValueChangePrioridad(it) }
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    //Imagen que saldrá, si está vacia ya hay una imagen definida vacía, si no agarras la que hay
                    AsyncImage(
                        model = if (uiStateTarea.uriImagen.isEmpty())
                            R.drawable.ic_nohayimagen
                        else
                            uiStateTarea.uriImagen,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .aspectRatio(2f)
                            .clip(RoundedCornerShape(16.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
                Row(modifier = Modifier.padding(8.dp, 0.dp), verticalAlignment = Alignment.CenterVertically) {
                    //Valoracion
                    Icon(painter = if (uiStateTarea.pagado) painterResource(R.drawable.ic_pagado)
                    else painterResource(R.drawable.ic_no_pagado), contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(stringResource(R.string.pagado))
                    //Switch pagado
                    Spacer(modifier = Modifier.width(8.dp))
                    Switch(checked = uiStateTarea.pagado,
                        onCheckedChange = { viewModel.onValueChangePagado(it) })
                    //Boton para importar imagen de galería
                    IconButton(
                        onClick = {
                            if (!permissionState.allPermissionsGranted)
                                permissionState.launchMultiplePermissionRequest()
                            else
                                launcherGaleria.launch(
                                    PickVisualMediaRequest(
                                        ActivityResultContracts.PickVisualMedia.ImageOnly
                                    )
                                )
                        }) {
                        Icon(painterResource(R.drawable.ic_imagesearch),
                            contentDescription = stringResource(R.string.abrir_galeria)
                        )
                    }
                    //Boton para hacer foto con la cámara y guardar la imagen
                    IconButton(
                        onClick = {
                            if (!permissionState.allPermissionsGranted)
                                permissionState.launchMultiplePermissionRequest()
                            else{
                                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                                takePictureLauncher.launch(intent)
                            }
                        }) {
                        Icon(painterResource(R.drawable.ic_camera),
                            contentDescription = stringResource(R.string.abrir_c_mara)
                        )
                    }
                }
                //Clasificación de la tarea, en caso de que sea X taréa se le definirá X icono
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
                //Estados
                BasicRadioButton(selectedOption = uiStateTarea.estado, onOptionSelected = { viewModel.onValueChangeEstado(it) }, options = viewModel.listaEstados)
                //Valoración del cliente, RatingBar para cambiar la valoracion
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = stringResource(R.string.valoracion_cliente))
                    RatingBar (uiStateTarea.valoracion, onRatingChanged = { viewModel.onValueChangeValoracion(it)})
                }
                //Campo para insertar el nombre del técnico
                OutlinedTextField(
                    value = uiStateTarea.tecnico,
                    onValueChange = { viewModel.onValueChangeTecnico(it)},
                    label = { Text(stringResource(R.string.tecnico)) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
                )
                //Descripción, desplazamiento vertical en caso de que el texto sea largo
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)) {
                    OutlinedTextField(
                        value = uiStateTarea.descripcion, //Campo descripción definido en uiStateTarea
                        onValueChange = { viewModel.onValueChangeDescripcion(it)},
                        label = { Text(stringResource(R.string.descripcion)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .verticalScroll(rememberScrollState()), //Scroll lateral ya mencionado
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                        singleLine = false
                    )
                }
                //Dialogo de confirmación para guardar los cambios
                Box(modifier = Modifier){
                    if (uiStateTarea.mostrarDialogo) {
                        DialogoDeConfirmacion(
                            onDismissRequest = {
                                //cancela el dialogo
                                viewModel.onCancelarDialogoGuardar()
                            },
                            onConfirmation = {
                                //guardaría los cambios, muestra un snackBar de confirmacion
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
                    //En caso de que no sea una nueva tarea, muestra el boton para verla
                    if (!isTareaNueva){
                        Spacer(modifier = Modifier.width(16.dp))
                        Button(
                            onClick = onMostrar,
                            enabled = uiStateTarea.listaTareas.isNotEmpty() //Habilitado si no hay tareas disponibles
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