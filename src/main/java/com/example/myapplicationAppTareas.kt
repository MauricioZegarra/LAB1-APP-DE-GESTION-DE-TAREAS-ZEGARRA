package com.example.myapplication

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope

@Composable
fun AppTareas() {
    val context = LocalContext.current.applicationContext
    val repository = remember(context) { TaskRepository(context) }
    val scope = rememberCoroutineScope()

    val tareas by repository.tareas.collectAsState(initial = emptyList())
    val temaOscuro by repository.temaOscuro.collectAsState(initial = false)

    var pantalla by rememberSaveable { mutableStateOf(Pantalla.INICIO.name) }
    var tareaSeleccionadaId by rememberSaveable { mutableIntStateOf(-1) }

    val tareaSeleccionada = tareas.firstOrNull { it.id == tareaSeleccionadaId }

    TareasTheme(darkTheme = temaOscuro) {
        Surface(modifier = Modifier.fillMaxSize()) {
            when (Pantalla.valueOf(pantalla)) {
                Pantalla.INICIO -> {
                    PantallaInicio(
                        onAgregarTarea = {
                            tareaSeleccionadaId = -1
                            pantalla = Pantalla.NUEVA.name
                        },
                        onVerTareas = {
                            pantalla = Pantalla.LISTA.name
                        }
                    )
                }

                Pantalla.LISTA -> {
                    VistaListaTareas(
                        tareas = tareas,
                        temaOscuro = temaOscuro,
                        onTemaChange = { valor ->
                            scope.launch {
                                repository.guardarTemaOscuro(valor)
                            }
                        },
                        onVolverInicio = {
                            pantalla = Pantalla.INICIO.name
                        },
                        onAbrirTarea = { tarea ->
                            tareaSeleccionadaId = tarea.id
                            pantalla = Pantalla.DETALLE.name
                        }
                    )
                }

                Pantalla.NUEVA -> {
                    EditorTarea(
                        tarea = null,
                        temaOscuro = temaOscuro,
                        onTemaChange = { valor ->
                            scope.launch {
                                repository.guardarTemaOscuro(valor)
                            }
                        },
                        onVolver = {
                            pantalla = Pantalla.INICIO.name
                        },
                        onGuardar = { titulo, descripcion ->
                            val nuevaTarea = Tarea(
                                id = siguienteId(tareas),
                                titulo = titulo,
                                descripcion = descripcion,
                                completada = false
                            )
                            scope.launch {
                                repository.guardarTareas(tareas + nuevaTarea)
                            }
                            pantalla = Pantalla.LISTA.name
                        },
                        onMarcarCompletada = null,
                        onEliminar = null
                    )
                }

                Pantalla.DETALLE -> {
                    EditorTarea(
                        tarea = tareaSeleccionada,
                        temaOscuro = temaOscuro,
                        onTemaChange = { valor ->
                            scope.launch {
                                repository.guardarTemaOscuro(valor)
                            }
                        },
                        onVolver = {
                            pantalla = Pantalla.LISTA.name
                        },
                        onGuardar = { titulo, descripcion ->
                            val actual = tareaSeleccionada
                            if (actual != null) {
                                scope.launch {
                                    repository.guardarTareas(
                                        tareas.map {
                                            if (it.id == actual.id) {
                                                it.copy(
                                                    titulo = titulo,
                                                    descripcion = descripcion
                                                )
                                            } else {
                                                it
                                            }
                                        }
                                    )
                                }
                            }
                            pantalla = Pantalla.LISTA.name
                        },
                        onMarcarCompletada = { titulo, descripcion ->
                            val actual = tareaSeleccionada
                            if (actual != null && !actual.completada) {
                                scope.launch {
                                    repository.guardarTareas(
                                        tareas.map {
                                            if (it.id == actual.id) {
                                                it.copy(
                                                    titulo = titulo,
                                                    descripcion = descripcion,
                                                    completada = true
                                                )
                                            } else {
                                                it
                                            }
                                        }
                                    )
                                }
                            }
                        },
                        onEliminar = {
                            val actual = tareaSeleccionada
                            if (actual != null) {
                                scope.launch {
                                    repository.guardarTareas(tareas.filter { it.id != actual.id })
                                }
                            }
                            tareaSeleccionadaId = -1
                            pantalla = Pantalla.LISTA.name
                        }
                    )
                }
            }
        }
    }
}
