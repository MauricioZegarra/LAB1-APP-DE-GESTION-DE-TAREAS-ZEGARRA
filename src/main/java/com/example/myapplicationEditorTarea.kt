package com.example.myapplication

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.width
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditorTarea(
    tarea: Tarea?,
    temaOscuro: Boolean,
    onTemaChange: (Boolean) -> Unit,
    onVolver: () -> Unit,
    onGuardar: (String, String) -> Unit,
    onMarcarCompletada: ((String, String) -> Unit)?,
    onEliminar: (() -> Unit)?
) {
    var titulo by rememberSaveable { mutableStateOf("") }
    var descripcion by rememberSaveable { mutableStateOf("") }
    var mostrarDialogoSalida by remember { mutableStateOf(false) }

    LaunchedEffect(tarea?.id) {
        titulo = tarea?.titulo.orEmpty()
        descripcion = tarea?.descripcion.orEmpty()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (tarea == null) "Nueva tarea" else "Detalle de tarea"
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { mostrarDialogoSalida = true }) {
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
                .fillMaxWidth()
                .padding(paddingValores)
                .padding(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.35f))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    OutlinedTextField(
                        value = titulo,
                        onValueChange = { titulo = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Nombre de la tarea") },
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = descripcion,
                        onValueChange = { descripcion = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Descripción") },
                        minLines = 3
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    if (tarea != null) {
                        Text(
                            text = "Estado actual: ${if (tarea.completada) "Completada" else "Pendiente"}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            val valorTitulo = titulo.trim()
                            if (valorTitulo.isNotEmpty()) {
                                onGuardar(valorTitulo, descripcion.trim())
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(if (tarea == null) "Guardar tarea" else "Guardar cambios")
                    }

                    if (tarea != null && !tarea.completada && onMarcarCompletada != null) {
                        Spacer(modifier = Modifier.height(10.dp))
                        OutlinedButton(
                            onClick = {
                                val valorTitulo = titulo.trim()
                                if (valorTitulo.isNotEmpty()) {
                                    onMarcarCompletada(valorTitulo, descripcion.trim())
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Marcar como completada")
                        }
                    }

                    if (tarea != null && tarea.completada) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Esta tarea ya está completada y no puede volver a pendiente.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    if (onEliminar != null) {
                        Spacer(modifier = Modifier.height(10.dp))
                        OutlinedButton(
                            onClick = onEliminar,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = null
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Eliminar tarea")
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "Nombre visible en la lista: solo título.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        if (mostrarDialogoSalida) {
            AlertDialog(
                onDismissRequest = { mostrarDialogoSalida = false },
                title = {
                    Text("Salir")
                },
                text = {
                    Text("¿Estás seguro de salir sin guardar?")
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            mostrarDialogoSalida = false
                            onVolver()
                        }
                    ) {
                        Text("Sí")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            mostrarDialogoSalida = false
                        }
                    ) {
                        Text("No")
                    }
                }
            )
        }
    }
}
