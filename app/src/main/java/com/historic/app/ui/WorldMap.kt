package com.historic.app.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.historic.app.data.HistoricEvent
import com.historic.app.data.LAT_BOTTOM
import com.historic.app.data.LAT_TOP
import com.historic.app.data.MapGeo
import com.historic.app.data.War
import com.historic.app.data.visibleOn
import androidx.compose.ui.geometry.lerp as lerpOffset

data class MapFocus(val lon: Double, val lat: Double, val zoom: Float, val nonce: Int = 0)

object Regions {
    val world = MapFocus(10.0, 22.0, 1.15f)
    val europe = MapFocus(16.0, 49.0, 3.4f)
    val mediterranean = MapFocus(20.0, 33.0, 3.2f)
    val pacific = MapFocus(150.0, 12.0, 2.3f)
    val seAsia = MapFocus(104.0, 3.5, 5.5f)
    val atlantic = MapFocus(-30.0, 35.0, 1.8f)
}

/**
 * Pan / pinch-zoom / tap world map drawn straight on a Canvas. Faction shading follows the year
 * slider and event markers appear as the timeline reaches them.
 */
@Composable
fun WorldMap(
    war: War,
    year: Int,
    events: List<HistoricEvent>,
    selectedId: String?,
    focus: MapFocus,
    onSelect: (HistoricEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    var canvasSize by remember { mutableStateOf(IntSize.Zero) }
    var scale by remember { mutableFloatStateOf(focus.zoom) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    var placed by remember { mutableStateOf(false) }

    val visible = events.filter { it.visibleOn(war, year) }
    val visibleState by rememberUpdatedState(visible)
    val onSelectState by rememberUpdatedState(onSelect)

    fun baseW(): Float = canvasSize.width.toFloat()
    fun baseH(): Float = baseW() * ((LAT_TOP - LAT_BOTTOM) / 360.0).toFloat()
    fun toBase(lon: Double, lat: Double): Offset {
        val x = ((lon + 180.0) / 360.0 * baseW()).toFloat()
        val y = (canvasSize.height - baseH()) / 2f +
            ((LAT_TOP - lat) / (LAT_TOP - LAT_BOTTOM) * baseH()).toFloat()
        return Offset(x, y)
    }
    fun toScreen(lon: Double, lat: Double): Offset = toBase(lon, lat) * scale + offset

    // Jump on first layout, glide on later focus changes (region chips, "Show on map").
    LaunchedEffect(canvasSize, focus) {
        if (canvasSize == IntSize.Zero) return@LaunchedEffect
        val centre = Offset(canvasSize.width / 2f, canvasSize.height / 2f)
        val targetScale = focus.zoom.coerceIn(1f, 16f)
        val targetOffset = centre - toBase(focus.lon, focus.lat) * targetScale
        if (!placed) {
            scale = targetScale
            offset = targetOffset
            placed = true
        } else {
            val s0 = scale
            val o0 = offset
            Animatable(0f).animateTo(1f, tween(750, easing = FastOutSlowInEasing)) {
                scale = lerp(s0, targetScale, value)
                offset = lerpOffset(o0, targetOffset, value)
            }
        }
    }

    val pulse by rememberInfiniteTransition(label = "pulse").animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(1600, easing = LinearEasing)),
        label = "pulseValue",
    )

    val zones = MapGeo.zones.filter { it.war == war && year in it.from..it.to }

    Canvas(
        modifier
            .fillMaxSize()
            .onSizeChanged { canvasSize = it }
            .pointerInput(Unit) {
                detectTransformGestures { centroid, pan, zoom, _ ->
                    val newScale = (scale * zoom).coerceIn(1f, 16f)
                    offset = centroid - (centroid - offset) * (newScale / scale) + pan
                    scale = newScale
                }
            }
            .pointerInput(Unit) {
                detectTapGestures { tap ->
                    val threshold = 36.dp.toPx()
                    val hit = visibleState.minByOrNull { (toScreen(it.lon, it.lat) - tap).getDistance() }
                    if (hit != null && (toScreen(hit.lon, hit.lat) - tap).getDistance() < threshold) {
                        onSelectState(hit)
                    }
                }
            }
    ) {
        // Graticule
        val grid = Color.White.copy(alpha = 0.05f)
        for (lon in -180..180 step 30) {
            drawLine(grid, toScreen(lon.toDouble(), LAT_TOP), toScreen(lon.toDouble(), LAT_BOTTOM), 1f)
        }
        for (lat in -40..80 step 20) {
            drawLine(grid, toScreen(-180.0, lat.toDouble()), toScreen(180.0, lat.toDouble()), 1f)
        }

        // Land
        for (poly in MapGeo.land) {
            val path = Path()
            poly.forEachIndexed { i, p ->
                val s = toScreen(p.lon, p.lat)
                if (i == 0) path.moveTo(s.x, s.y) else path.lineTo(s.x, s.y)
            }
            path.close()
            drawPath(path, Color.White.copy(alpha = 0.07f))
            drawPath(path, Color.White.copy(alpha = 0.24f), style = Stroke(width = 1.2.dp.toPx()))
        }

        // Faction zones
        for (zone in zones) {
            val path = Path()
            zone.points.forEachIndexed { i, p ->
                val s = toScreen(p.lon, p.lat)
                if (i == 0) path.moveTo(s.x, s.y) else path.lineTo(s.x, s.y)
            }
            path.close()
            val c = zone.side.color()
            drawPath(path, c.copy(alpha = 0.26f))
            drawPath(path, c.copy(alpha = 0.7f), style = Stroke(width = 1.4.dp.toPx()))
        }

        // Event markers (selected drawn last)
        val ordered = visible.sortedBy { if (it.id == selectedId) 1 else 0 }
        for (e in ordered) {
            val c = toScreen(e.lon, e.lat)
            val col = if (e.malay) HC.Malay else war.color()
            val selected = e.id == selectedId
            val glow = 26.dp.toPx()
            drawCircle(
                Brush.radialGradient(listOf(col.copy(alpha = 0.55f), Color.Transparent), center = c, radius = glow),
                radius = glow,
                center = c,
            )
            drawCircle(col, radius = (if (selected) 7.dp else 4.5.dp).toPx(), center = c)
            drawCircle(Color.White, radius = 1.8.dp.toPx(), center = c)
            if (selected) {
                val r = (10 + 18 * pulse).dp.toPx()
                drawCircle(col.copy(alpha = 1f - pulse), radius = r, center = c, style = Stroke(1.5.dp.toPx()))
            }
        }
    }
}
