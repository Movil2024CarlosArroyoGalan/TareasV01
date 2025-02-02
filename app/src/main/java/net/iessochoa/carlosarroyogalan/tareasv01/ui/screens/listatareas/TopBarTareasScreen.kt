package net.iessochoa.carlosarroyogalan.tareasv01.ui.screens.listatareas

import android.content.Intent
import android.widget.Toast
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import net.iessochoa.carlosarroyogalan.tareasv01.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarTareas() {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    TopAppBar(
        title = {
            Text(text = stringResource(R.string.lista_tareas))
        },
        actions = {
            IconButton(onClick = {
                val intent = Intent(Intent.ACTION_VIEW, android.net.Uri.parse("https://portal.edu.gva.es/03013224/va/inici/"))
                context.startActivity(intent)
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_browsepage),
                    contentDescription = stringResource(R.string.web_del_instituto_ies_severo_ochoa)
                )
            }
            IconButton(onClick = { expanded = true }) {
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = stringResource(R.string.opciones)
                )
            }
            DropdownMenu (
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text(stringResource(R.string.llamar)) },
                    onClick = {
                        expanded = false
                        val phoneNumber = "tel:966912260"
                        val intent = Intent(Intent.ACTION_CALL).apply {
                            data = android.net.Uri.parse(phoneNumber)
                        }
                        try {
                            context.startActivity(intent)
                        } catch (e: SecurityException) {
                            Toast.makeText(context,
                                context.getString(R.string.no_se_puedo_hacer_la_llamada),
                                Toast.LENGTH_SHORT).show()
                        }
                    }
                )
                DropdownMenuItem(
                    text = { Text(stringResource(R.string.configuraci_n)) },
                    onClick = {
                        expanded = false
                        Toast.makeText(context,
                            context.getString(R.string.configuraci_n), Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }
    )
}