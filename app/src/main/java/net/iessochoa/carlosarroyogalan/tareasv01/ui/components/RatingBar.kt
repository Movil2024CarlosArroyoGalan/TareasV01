package net.iessochoa.carlosarroyogalan.tareasv01.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import net.iessochoa.carlosarroyogalan.tareasv01.R
//Barra de calificación RatingBar, con nuestros iconos
@Composable
fun RatingBar(rating: Int, onRatingChanged: (Int) -> Unit) {
    //Iconos selección vacia
    val emojiOutlines = listOf(
        R.drawable.ic_no_pagado,
        R.drawable.ic_no_pagado,
        R.drawable.ic_no_pagado,
        R.drawable.ic_no_pagado,
        R.drawable.ic_no_pagado
    )
    //Iconos cuando se ha seleccionado el rating
    val emojiFilled = listOf(
        R.drawable.ic_pagado,
        R.drawable.ic_pagado,
        R.drawable.ic_pagado,
        R.drawable.ic_pagado,
        R.drawable.ic_pagado
    )
    Row {
        //Iteración del 1 al 5 para tener 5 iconos
        for (i in 1..5) {
            //Botón que cambia la clasificación
            IconButton(onClick = { onRatingChanged(i) }) {
                val emojiSeleccionado: Painter = painterResource(
                    id = if (i <= rating) emojiFilled[i - 1] else emojiOutlines[i - 1]
                ) //Selecciona el icono al que pertenece la seleccion
                //Imagen del icono seleccionado
                Image(painter = emojiSeleccionado, contentDescription = null)
            }
        }
    }
}