package com.historic.app.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import com.historic.app.data.Side
import com.historic.app.data.War

object HC {
    val Black = Color(0xFF000000)          // true OLED black
    val Ink = Color(0xFFF4F4F7)
    val Muted = Color(0xFFA0A0AC)
    val WW1 = Color(0xFFFFB454)            // amber
    val WW2 = Color(0xFF58A6FF)            // steel blue
    val Malay = Color(0xFF3DDC97)          // emerald
    val Cold = Color(0xFFB68CFF)           // violet
    val Allies = Color(0xFF4DA3FF)
    val Enemy = Color(0xFFFF6B6B)
}

val Serif: FontFamily = FontFamily.Serif

fun War.color(): Color = when (this) {
    War.WW1 -> HC.WW1
    War.WW2 -> HC.WW2
    War.COLD -> HC.Cold
}

fun Side.color(): Color = when (this) {
    Side.ALLIES, Side.WEST -> HC.Allies
    else -> HC.Enemy
}

@Composable
fun HistoricTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = darkColorScheme(
            primary = HC.WW2,
            secondary = HC.WW1,
            tertiary = HC.Malay,
            background = HC.Black,
            surface = HC.Black,
            onBackground = HC.Ink,
            onSurface = HC.Ink,
        ),
        content = content,
    )
}
