package com.historic.app.ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/** Pure-black OLED canvas with two very soft drifting colour glows for the glass to refract. */
@Composable
fun OledBackdrop(a: Color, b: Color, modifier: Modifier = Modifier) {
    val transition = rememberInfiniteTransition(label = "backdrop")
    val p by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(16000, easing = LinearEasing), RepeatMode.Reverse),
        label = "drift",
    )
    Canvas(modifier.fillMaxSize()) {
        val w = size.width
        val h = size.height
        val c1 = Offset(w * (0.12f + 0.25f * p), h * (0.16f + 0.05f * p))
        val c2 = Offset(w * (0.95f - 0.25f * p), h * (0.82f - 0.06f * p))
        drawCircle(Brush.radialGradient(listOf(a.copy(alpha = 0.30f), Color.Transparent), center = c1, radius = w * 0.85f), radius = w * 0.85f, center = c1)
        drawCircle(Brush.radialGradient(listOf(b.copy(alpha = 0.24f), Color.Transparent), center = c2, radius = w * 0.9f), radius = w * 0.9f, center = c2)
    }
}

/**
 * Frosted-glass surface: translucent white gradient fill, a bright-to-dim rim light, a press-scale
 * spring and haptic feedback (tick on press, confirm on release).
 */
@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(26.dp),
    tint: Color = Color.White,
    strength: Float = 1f,
    onClick: (() -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit,
) {
    val haptics = LocalHaptics.current
    val interaction = remember { MutableInteractionSource() }
    val pressed by interaction.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.97f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessMedium),
        label = "press",
    )
    if (onClick != null) {
        LaunchedEffect(pressed) { if (pressed) haptics.tick() }
    }
    val glass = Modifier
        .graphicsLayer { scaleX = scale; scaleY = scale }
        .clip(shape)
        .background(
            Brush.linearGradient(
                listOf(tint.copy(alpha = 0.17f * strength), tint.copy(alpha = 0.05f * strength))
            ),
            shape,
        )
        .border(
            1.dp,
            Brush.linearGradient(
                listOf(Color.White.copy(alpha = 0.42f), Color.White.copy(alpha = 0.04f), Color.White.copy(alpha = 0.18f))
            ),
            shape,
        )
    val clickable = if (onClick != null) {
        Modifier.clickable(interactionSource = interaction, indication = null) {
            haptics.confirm()
            onClick()
        }
    } else Modifier
    Box(modifier.then(glass).then(clickable), content = content)
}

@Composable
fun GlassChip(
    label: String,
    selected: Boolean,
    accent: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val haptics = LocalHaptics.current
    val shape = RoundedCornerShape(50)
    Box(
        modifier
            .clip(shape)
            .background(if (selected) accent.copy(alpha = 0.28f) else Color.White.copy(alpha = 0.07f))
            .border(1.dp, if (selected) accent.copy(alpha = 0.85f) else Color.White.copy(alpha = 0.16f), shape)
            .clickable { haptics.selection(); onClick() }
            .padding(horizontal = 14.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            label,
            color = if (selected) Color.White else HC.Muted,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
        )
    }
}

@Composable
fun Pill(text: String, color: Color, modifier: Modifier = Modifier) {
    val shape = RoundedCornerShape(50)
    Box(
        modifier
            .clip(shape)
            .background(color.copy(alpha = 0.2f))
            .border(1.dp, color.copy(alpha = 0.6f), shape)
            .padding(horizontal = 8.dp, vertical = 2.dp)
    ) {
        Text(text, color = color, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.2.sp)
    }
}

@Composable
fun Caption(text: String, modifier: Modifier = Modifier, color: Color = HC.Muted) {
    Text(
        text.uppercase(),
        modifier = modifier,
        color = color,
        fontSize = 11.sp,
        letterSpacing = 1.8.sp,
        fontWeight = FontWeight.SemiBold,
    )
}
