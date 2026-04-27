package com.example.myapplication

import org.json.JSONArray
import org.json.JSONObject

fun siguienteId(tareas: List<Tarea>): Int {
    return if (tareas.isEmpty()) {
        1
    } else {
        tareas.maxOf { it.id } + 1
    }
}

fun tareasAJson(tareas: List<Tarea>): String {
    val array = JSONArray()
    tareas.forEach { tarea ->
        val objeto = JSONObject()
        objeto.put("id", tarea.id)
        objeto.put("titulo", tarea.titulo)
        objeto.put("descripcion", tarea.descripcion)
        objeto.put("completada", tarea.completada)
        array.put(objeto)
    }
    return array.toString()
}

fun tareasDesdeJson(valor: String): List<Tarea> {
    if (valor.isBlank()) {
        return emptyList()
    }

    return try {
        val array = JSONArray(valor)
        val lista = mutableListOf<Tarea>()
        for (i in 0 until array.length()) {
            val objeto = array.getJSONObject(i)
            lista.add(
                Tarea(
                    id = objeto.getInt("id"),
                    titulo = objeto.optString("titulo"),
                    descripcion = objeto.optString("descripcion"),
                    completada = objeto.optBoolean("completada", false)
                )
            )
        }
        lista
    } catch (_: Exception) {
        emptyList()
    }
}
