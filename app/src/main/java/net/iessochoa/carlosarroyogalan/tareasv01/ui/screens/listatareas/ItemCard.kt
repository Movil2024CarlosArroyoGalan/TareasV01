package net.iessochoa.carlosarroyogalan.tareasv01.ui.screens.listatareas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import net.iessochoa.carlosarroyogalan.tareasv01.R
import net.iessochoa.carlosarroyogalan.tareasv01.data.db.entities.Tarea
import net.iessochoa.carlosarroyogalan.tareasv01.ui.theme.ColorPrioridadAlta

@Composable
fun ItemCard(
    //Recibe una tarea como parámetro
    tarea: Tarea,
    //Lista de categorías
    listaCategorias: List<String> = emptyList(),
    // Función de callback para modificar una tarea
    onItemModificarClick: (id: Long) -> Unit,
    // Función opcional para borrar una tarea
    onClickBorrar: (Long) -> Unit = {},
    modifier: Modifier = Modifier
) {
    //Color de fondo en caso de que la prioridad de la tarea  sea alta
    val cardBackgroundColor = if (tarea.prioridad == 2) {
        ColorPrioridadAlta
    } else {
        Color.Transparent
    }
    //Estado para manejar la expansion del contenido
    var expanded by remember { mutableStateOf(false)}
    //Tarjeta con bordes redondeados
    Card (
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            //Click para modificar la tarea
            .clickable { tarea.id?.let { onItemModificarClick(it) } },
        elevation = androidx.compose.material3.CardDefaults.cardElevation(4.dp)
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(cardBackgroundColor)
        ) {
            //Imagen de la tarea, predeterminada en caso de que no haya una definida
            AsyncImage(
                model = if (tarea.img.isEmpty())
                    //Imagen por defecto
                    R.drawable.ic_nohayimagen
                            else
                                //Imagen de la tarea
                                tarea.img,
                contentDescription = stringResource(R.string.imagen_de_la_tarea),
                modifier = Modifier
                    .width(100.dp)
                    .fillMaxHeight(),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(8.dp))
            //Contenido de la tarjeta
            Column (
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .padding(top = 4.dp, bottom = 8.dp, end = 8.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    //Icono, representa el estado de la tarea
                    Icon(
                        painter = painterResource(
                            id = when (tarea.estado) {
                                0 -> R.drawable.ic_abierta
                                1 -> R.drawable.ic_en_curso
                                2 -> R.drawable.ic_cerrada
                                else -> R.drawable.ic_abierta
                            }
                        ),
                        contentDescription = stringResource(R.string.estado_de_la_tarea)
                    )
                    //Muestra la categoría de la tarea, si no existe lo avisa igualmente
                    Text(
                        text = listaCategorias.getOrNull(tarea.categoria) ?: stringResource(R.string.sin_categor_a),
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.Gray,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
                //Muestra el nombre del técnico
                Text(
                    text = tarea.tecnico,
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.Blue
                )
                //Descripción de la tarea
                Row(modifier = modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically){
                    if(!expanded){
                        Text(
                            text = tarea.descripcion,
                            modifier = Modifier
                                .padding(top = 2.dp)
                                .weight(1f),
                            style = MaterialTheme.typography.bodySmall,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    } else {
                        //Si está expandido muestra todo sin limitaciones
                        Text(
                            text = tarea.descripcion,
                            modifier = Modifier
                                .padding(top = 2.dp)
                                .weight(1f),
                            style = MaterialTheme.typography.bodySmall,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    //Boton para expandir o contraer el item
                    ExpandirItem(
                        expanded = expanded,
                        onIconClick = { expanded = !expanded },
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(4.dp),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Top
            ){
                //Boton de eliminación de tarea
                IconButton(
                    onClick = {tarea.id?.let {onClickBorrar(it)}} //Llama a la funcion para borrar la tarea
                ) {
                    //Icono para eliminar la tarea
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = stringResource(R.string.eliminar)
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(4.dp),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = tarea.id.toString(),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
        }
    }
}
//Metodo que usamos para expandir la descripción del item
@Composable
fun ExpandirItem(
    expanded: Boolean, //Estado del item
    onIconClick: () -> Unit, //Accion para ejecutar la expansion
    modifier: Modifier
) {
    //Boton que responde al clic, con el modificador como parámetro
    IconButton(onClick = onIconClick, modifier = modifier) {
        //Icono de expansión
        Icon(
            Icons.Filled.KeyboardArrowDown,
            stringResource(R.string.expandir),
            Modifier.rotate(
                //Si el icono está sin pulsar se queda como está
                if (expanded)
                    180f
                //Si no, se rota
                else
                    360f
            )
        )
    }
}