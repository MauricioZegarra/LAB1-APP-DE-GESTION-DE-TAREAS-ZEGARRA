package com.example.myapplication

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

val Context.tareasDataStore by preferencesDataStore(name = "tareas_datastore")
