package com.historic.app.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.historic.app.data.Attire
import com.historic.app.data.Camp
import com.historic.app.data.Figure
import com.historic.app.data.Headwear

fun Camp.color(): Color = when (this) {
    Camp.ALLIES -> HC.Allies
    Camp.AXIS -> HC.Enemy
    Camp.SOVIET -> HC.WW1
    Camp.MALAYSIA -> HC.Malay
    Camp.OTHER -> HC.Cold
}

/**
 * Stylised, faceless silhouette portrait: a bust with era-appropriate headwear and attire on a glowing
 * panel. It is an illustration, not a likeness. If a drawable named `fig_<id>` exists in res/drawable
 * (for example fig_churchill.jpg) the photo is shown instead.
 */
@Composable
fun FigureArt(figure: Figure, modifier: Modifier = Modifier, corner: Dp = 22.dp) {
    val context = LocalContext.current
    val photoId = remember(figure.id) {
        context.resources.getIdentifier("fig_${figure.id}", "drawable", context.packageName)
    }
    val accent = figure.camp.color()
    val shape = RoundedCornerShape(corner)

    Box(modifier.clip(shape)) {
        if (photoId != 0) {
            Image(
                painterResource(photoId),
                contentDescription = figure.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
            )
        } else {
            Canvas(Modifier.fillMaxSize()) { drawBust(figure, accent) }
        }
    }
}

private fun DrawScope.drawBust(f: Figure, accent: Color) {
    val w = size.width
    val h = size.height
    val line = Color.White.copy(alpha = 0.55f)
    val fill = Color.White.copy(alpha = 0.16f)
    val stroke = Stroke(width = (w * 0.012f).coerceAtLeast(1.5f))

    // Backdrop glow + horizon
    drawRect(Brush.verticalGradient(listOf(accent.copy(alpha = 0.34f), accent.copy(alpha = 0.06f))))
    drawCircle(
        Brush.radialGradient(listOf(accent.copy(alpha = 0.55f), Color.Transparent), center = Offset(w / 2, h * 0.42f), radius = w * 0.55f),
        radius = w * 0.55f,
        center = Offset(w / 2, h * 0.42f),
    )

    val cx = w / 2f
    val headR = w * 0.17f
    val headCy = h * 0.37f

    // Shoulders
    val body = Path().apply {
        moveTo(w * 0.08f, h)
        quadraticBezierTo(w * 0.08f, h * 0.70f, w * 0.36f, h * 0.65f)
        lineTo(w * 0.64f, h * 0.65f)
        quadraticBezierTo(w * 0.92f, h * 0.70f, w * 0.92f, h)
        close()
    }
    drawPath(body, fill)
    drawPath(body, line, style = stroke)

    // Neck
    val neck = Path().apply {
        moveTo(cx - w * 0.065f, headCy + headR * 0.7f)
        lineTo(cx - w * 0.075f, h * 0.67f)
        lineTo(cx + w * 0.075f, h * 0.67f)
        lineTo(cx + w * 0.065f, headCy + headR * 0.7f)
        close()
    }
    drawPath(neck, fill)
    drawPath(neck, line, style = stroke)

    // Attire details
    when (f.attire) {
        Attire.SUIT -> {
            // Lapels and tie
            val l = Path().apply { moveTo(cx - w * 0.075f, h * 0.67f); lineTo(cx - w * 0.02f, h * 0.84f); lineTo(cx - w * 0.2f, h * 0.9f) }
            val r = Path().apply { moveTo(cx + w * 0.075f, h * 0.67f); lineTo(cx + w * 0.02f, h * 0.84f); lineTo(cx + w * 0.2f, h * 0.9f) }
            drawPath(l, line, style = stroke)
            drawPath(r, line, style = stroke)
            val tie = Path().apply {
                moveTo(cx, h * 0.69f)
                lineTo(cx + w * 0.03f, h * 0.76f)
                lineTo(cx, h * 0.9f)
                lineTo(cx - w * 0.03f, h * 0.76f)
                close()
            }
            drawPath(tie, accent.copy(alpha = 0.7f))
        }
        Attire.UNIFORM -> {
            // Collar, buttons and epaulettes
            drawLine(line, Offset(cx - w * 0.075f, h * 0.67f), Offset(cx - w * 0.04f, h * 0.76f), stroke.width)
            drawLine(line, Offset(cx + w * 0.075f, h * 0.67f), Offset(cx + w * 0.04f, h * 0.76f), stroke.width)
            drawLine(line, Offset(cx, h * 0.72f), Offset(cx, h), stroke.width)
            for (i in 0..2) drawCircle(accent.copy(alpha = 0.9f), radius = w * 0.012f, center = Offset(cx + w * 0.03f, h * (0.78f + i * 0.07f)))
            drawRoundRect(accent.copy(alpha = 0.6f), Offset(w * 0.2f, h * 0.72f), Size(w * 0.14f, h * 0.03f), CornerRadius(4f))
            drawRoundRect(accent.copy(alpha = 0.6f), Offset(w * 0.66f, h * 0.72f), Size(w * 0.14f, h * 0.03f), CornerRadius(4f))
        }
        Attire.TUNIC -> {
            // High closed collar
            drawLine(line, Offset(cx - w * 0.07f, h * 0.69f), Offset(cx + w * 0.07f, h * 0.69f), stroke.width)
            drawLine(line, Offset(cx, h * 0.69f), Offset(cx, h), stroke.width)
            for (i in 0..3) drawCircle(accent.copy(alpha = 0.9f), radius = w * 0.011f, center = Offset(cx - w * 0.025f, h * (0.74f + i * 0.065f)))
        }
    }

    // Head
    val headTopLeft = Offset(cx - headR, headCy - headR * 1.15f)
    val headSize = Size(headR * 2, headR * 2.3f)
    drawOval(fill, headTopLeft, headSize)
    drawOval(line, headTopLeft, headSize, style = stroke)

    // Headwear
    val top = headCy - headR * 1.15f
    when (f.headwear) {
        Headwear.NONE -> Unit
        Headwear.PEAKED_CAP, Headwear.OFFICER_CAP -> {
            val crown = Path().apply {
                moveTo(cx - headR * 1.05f, top + headR * 0.55f)
                quadraticBezierTo(cx - headR * 1.15f, top - headR * 0.2f, cx, top - headR * 0.25f)
                quadraticBezierTo(cx + headR * 1.15f, top - headR * 0.2f, cx + headR * 1.05f, top + headR * 0.55f)
                close()
            }
            drawPath(crown, accent.copy(alpha = 0.55f))
            drawPath(crown, line, style = stroke)
            drawRoundRect(line, Offset(cx - headR * 1.1f, top + headR * 0.5f), Size(headR * 2.2f, headR * 0.28f), CornerRadius(6f))
            // visor
            drawRoundRect(line.copy(alpha = 0.8f), Offset(cx - headR * 0.8f, top + headR * 0.75f), Size(headR * 1.6f, headR * 0.16f), CornerRadius(6f))
        }
        Headwear.SPIKED_HELMET -> {
            val dome = Path().apply {
                moveTo(cx - headR * 1.05f, top + headR * 0.6f)
                quadraticBezierTo(cx - headR * 1.1f, top - headR * 0.35f, cx, top - headR * 0.4f)
                quadraticBezierTo(cx + headR * 1.1f, top - headR * 0.35f, cx + headR * 1.05f, top + headR * 0.6f)
                close()
            }
            drawPath(dome, accent.copy(alpha = 0.5f))
            drawPath(dome, line, style = stroke)
            drawLine(line, Offset(cx, top - headR * 0.4f), Offset(cx, top - headR * 0.95f), stroke.width * 1.6f)
            drawCircle(line, radius = w * 0.012f, center = Offset(cx, top - headR * 1.0f))
            drawRoundRect(line, Offset(cx - headR * 1.1f, top + headR * 0.55f), Size(headR * 2.2f, headR * 0.15f), CornerRadius(4f))
        }
        Headwear.HOMBURG -> {
            val brim = Offset(cx - headR * 1.35f, top + headR * 0.45f)
            drawOval(accent.copy(alpha = 0.55f), brim, Size(headR * 2.7f, headR * 0.45f))
            drawOval(line, brim, Size(headR * 2.7f, headR * 0.45f), style = stroke)
            drawRoundRect(accent.copy(alpha = 0.55f), Offset(cx - headR * 0.85f, top - headR * 0.35f), Size(headR * 1.7f, headR * 0.95f), CornerRadius(headR * 0.4f))
            drawRoundRect(line, Offset(cx - headR * 0.85f, top - headR * 0.35f), Size(headR * 1.7f, headR * 0.95f), CornerRadius(headR * 0.4f), style = stroke)
        }
        Headwear.SONGKOK -> {
            val sk = Path().apply {
                moveTo(cx - headR * 0.9f, top + headR * 0.55f)
                lineTo(cx - headR * 0.8f, top - headR * 0.25f)
                lineTo(cx + headR * 0.8f, top - headR * 0.25f)
                lineTo(cx + headR * 0.9f, top + headR * 0.55f)
                close()
            }
            drawPath(sk, accent.copy(alpha = 0.7f))
            drawPath(sk, line, style = stroke)
        }
        Headwear.FLAT_CAP -> {
            val cap = Path().apply {
                moveTo(cx - headR * 1.0f, top + headR * 0.55f)
                quadraticBezierTo(cx - headR * 1.0f, top - headR * 0.1f, cx, top - headR * 0.1f)
                quadraticBezierTo(cx + headR * 1.0f, top - headR * 0.1f, cx + headR * 1.0f, top + headR * 0.55f)
                close()
            }
            drawPath(cap, accent.copy(alpha = 0.55f))
            drawPath(cap, line, style = stroke)
            drawRoundRect(line.copy(alpha = 0.8f), Offset(cx - headR * 0.2f, top + headR * 0.45f), Size(headR * 1.35f, headR * 0.16f), CornerRadius(6f))
        }
    }
}
