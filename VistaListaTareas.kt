package com.example.myapplication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VistaListaTareas(
    tareas: List<Tarea>,
    temaOscuro: Boolean,
    onTemaChange: (Boolean) -> Unit,
    onVolverInicio: () -> Unit,
    onAbrirTarea: (Tarea) -> Unit
) {
    var filtro by rememberSaveable { mutableStateOf("TODAS") }

    val tareasFiltradas = when (filtro) {
        "PENDIENTE" -> tareas.filter { !it.completada }
        "COMPLETADA" -> tareas.filter { it.completada }
        else -> tareas
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Gestor de tareas",
                            style = MaterialTheme.typography.headlineSmall
                        )
                        Text(
                            text = "Lista principal",
                            style = MaterialTheme.typography.labelLarge,
                            modifier = Modifier.alpha(0.8f)
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onVolverInicio) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                },
                actions = {
                    Icon(
                        imageVector = if (temaOscuro) Icons.Default.DarkMode else Icons.Default.LightMode,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Switch(
                        checked = temaOscuro,
                        onCheckedChange = onTemaChange
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValores ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValores)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = filtro == "TODAS",
                    onClick = { filtro = "TODAS" },
                    label = { Text("Todas") }
                )
                FilterChip(
                    selected = filtro == "PENDIENTE",
                    onClick = { filtro = "PENDIENTE" },
                    label = { Text("Pendientes") }
                )
                FilterChip(
                    selected = filtro == "COMPLETADA",
                    onClick = { filtro = "COMPLETADA" },
                    label = { Text("Completadas") }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (tareasFiltradas.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No hay tareas para este filtro",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(
                        items = tareasFiltradas,
                        key = { it.id }
                    ) { tarea ->
                        TarjetaTarea(
                            tarea = tarea,
                            onClick = { onAbrirTarea(tarea) }
                        )
                    }
                }
            }
        }
    }
}