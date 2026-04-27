package com.example.myapplication

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp

@Composable
fun TarjetaTarea(
    tarea: Tarea,
    onClick: () -> Unit
) {
    val borde = if (tarea.completada) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.secondary
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(1.dp, borde.copy(alpha = 0.55f))
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = tarea.titulo,
                style = MaterialTheme.typography.titleMedium,
                textDecoration = if (tarea.completada) TextDecoration.LineThrough else null,
                color = if (tarea.completada) {
                    MaterialTheme.colorScheme.onSurfaceVariant
                } else {
                    MaterialTheme.colorScheme.onSurface
                }
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = if (tarea.completada) "Estado: completa" else "Estado: pendiente",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}