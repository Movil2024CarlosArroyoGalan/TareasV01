package net.iessochoa.carlosarroyogalan.tareasv01.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import net.iessochoa.carlosarroyogalan.tareasv01.R

@Composable
fun RatingBar(rating: Int, onRatingChanged: (Int) -> Unit) {
    val emojiOutlines = listOf(
        R.drawable.ic_pagado,
        R.drawable.ic_pagado,
        R.drawable.ic_pagado,
        R.drawable.ic_pagado,
        R.drawable.ic_pagado
    )
    val emojiFilled = listOf(
        R.drawable.ic_no_pagado,
        R.drawable.ic_no_pagado,
        R.drawable.ic_no_pagado,
        R.drawable.ic_no_pagado,
        R.drawable.ic_no_pagado
    )
    Row {
        for (i in 1..5) {
            IconButton(onClick = { onRatingChanged(i) }) {
                val emojiPainter: Painter = painterResource(
                    id = if (i <= rating) emojiFilled[i - 1] else emojiOutlines[i - 1]
                )
                Image(painter = emojiPainter, contentDescription = null)
            }
        }
    }
}