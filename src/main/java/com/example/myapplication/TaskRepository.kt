package com.example.myapplication

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.map

private val TASKS_KEY = stringPreferencesKey("tasks_json")
private val DARK_THEME_KEY = booleanPreferencesKey("dark_theme")

class TaskRepository(private val context: Context) {

    val tareas = context.tareasDataStore.data.map { preferences ->
        tareasDesdeJson(preferences[TASKS_KEY].orEmpty())
    }

    val temaOscuro = context.tareasDataStore.data.map { preferences ->
        preferences[DARK_THEME_KEY] ?: false
    }

    suspend fun guardarTareas(tareas: List<Tarea>) {
        context.tareasDataStore.edit { preferences ->
            preferences[TASKS_KEY] = tareasAJson(tareas)
        }
    }

    suspend fun guardarTemaOscuro(valor: Boolean) {
        context.tareasDataStore.edit { preferences ->
            preferences[DARK_THEME_KEY] = valor
        }
    }
}
