package com.example.myapplication

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val VerdeOscuro = Color(0xFF10261B)
private val VerdeBosque = Color(0xFF1E4D2B)
private val VerdeLima = Color(0xFFC7FF3D)
private val VerdeLimaClaro = Color(0xFFE8FFB0)
private val FondoClaro = Color(0xFFF4F8F0)
private val NegroSuave = Color(0xFF0E120F)
private val GrisOscuro = Color(0xFF15231A)
private val GrisMedio = Color(0xFF233229)
private val BlancoSuave = Color(0xFFF4F7EE)

private val EsquemaClaro = lightColorScheme(
    primary = VerdeBosque,
    onPrimary = Color.White,
    primaryContainer = VerdeLimaClaro,
    onPrimaryContainer = NegroSuave,
    secondary = VerdeOscuro,
    onSecondary = Color.White,
    tertiary = VerdeLima,
    onTertiary = NegroSuave,
    background = FondoClaro,
    onBackground = NegroSuave,
    surface = Color.White,
    onSurface = NegroSuave,
    surfaceVariant = Color(0xFFDCE6D7),
    onSurfaceVariant = Color(0xFF2A332C),
    outline = VerdeBosque
)

private val EsquemaOscuro = darkColorScheme(
    primary = VerdeLima,
    onPrimary = NegroSuave,
    primaryContainer = Color(0xFF335E28),
    onPrimaryContainer = Color.White,
    secondary = Color(0xFF89C86B),
    onSecondary = NegroSuave,
    tertiary = VerdeLimaClaro,
    onTertiary = NegroSuave,
    background = VerdeOscuro,
    onBackground = BlancoSuave,
    surface = GrisOscuro,
    onSurface = BlancoSuave,
    surfaceVariant = GrisMedio,
    onSurfaceVariant = Color(0xFFD2E2CD),
    outline = Color(0xFF6B9365)
)

@Composable
fun TareasTheme(
    darkTheme: Boolean,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) EsquemaOscuro else EsquemaClaro,
        typography = Typography(),
        content = content
    )
}
