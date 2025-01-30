package net.iessochoa.carlosarroyogalan.tareasv01.ui.screens.listatareas

import net.iessochoa.carlosarroyogalan.tareasv01.data.db.entities.Tarea

data class ListaUiState(
    val listaPalabra: List<Tarea> = listOf()
)