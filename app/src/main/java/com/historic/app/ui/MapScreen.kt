package com.historic.app.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.historic.app.data.HistoricData
import com.historic.app.data.HistoricEvent
import com.historic.app.data.War
import kotlin.math.roundToInt

/** Map state lives above the screen so it survives tab switches. */
@Stable
class MapState {
    var war by mutableStateOf(War.WW2)
    var year by mutableIntStateOf(1945)
    var selectedId by mutableStateOf<String?>(null)
    var focus by mutableStateOf(Regions.world)
    private var nonce = 0

    fun focusOn(region: MapFocus) {
        nonce++
        focus = region.copy(nonce = nonce)
    }

    fun showEvent(e: HistoricEvent) {
        war = e.war
        year = e.year.coerceIn(e.war.start, e.war.end)
        selectedId = e.id
        focusOn(MapFocus(e.lon, e.lat, if (e.malay) 7f else 5f))
    }

    fun openWar(w: War) {
        war = w
        year = w.end
        selectedId = null
        focusOn(if (w == War.WW1) Regions.europe else Regions.world)
    }
}

@Composable
fun MapScreen(state: MapState, onOpen: (String) -> Unit) {
    val haptics = LocalHaptics.current
    val events = HistoricData.events
    val selected = events.firstOrNull { it.id == state.selectedId }
    val war = state.war

    Box(Modifier.fillMaxSize()) {
        WorldMap(
            war = war,
            year = state.year,
            events = events,
            selectedId = state.selectedId,
            focus = state.focus,
            onSelect = {
                haptics.heavy()
                state.selectedId = it.id
            },
            modifier = Modifier.fillMaxSize(),
        )

        Column(Modifier.fillMaxSize().padding(horizontal = 16.dp, vertical = 12.dp)) {
            // Top: war toggle + legend
            GlassCard(Modifier.fillMaxWidth(), shape = RoundedCornerShape(24.dp)) {
                Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        GlassChip("World War I", war == War.WW1, HC.WW1) {
                            if (war != War.WW1) { haptics.boom(); state.openWar(War.WW1) }
                        }
                        GlassChip("World War II", war == War.WW2, HC.WW2) {
                            if (war != War.WW2) { haptics.boom(); state.openWar(War.WW2) }
                        }
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(14.dp), verticalAlignment = Alignment.CenterVertically) {
                        LegendDot(HC.Allies, "Allies")
                        LegendDot(HC.Enemy, if (war == War.WW1) "Central Powers" else "Axis")
                        LegendDot(HC.Malay, "Malay story")
                    }
                }
            }

            Spacer(Modifier.weight(1f))

            AnimatedVisibility(
                visible = selected != null,
                enter = fadeIn() + slideInVertically { it / 2 },
            ) {
                if (selected != null) {
                    SelectedCard(selected, onOpen = { onOpen(selected.id) }, onClose = { state.selectedId = null })
                }
            }
            Spacer(Modifier.padding(4.dp))

            // Bottom: year scrubber + region chips
            GlassCard(Modifier.fillMaxWidth(), shape = RoundedCornerShape(24.dp)) {
                Column(Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("${war.start}", fontSize = 12.sp, color = HC.Muted)
                        Spacer(Modifier.weight(1f))
                        Text(
                            "${state.year}",
                            fontFamily = Serif,
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold,
                            color = war.color(),
                        )
                        Spacer(Modifier.weight(1f))
                        Text("${war.end}", fontSize = 12.sp, color = HC.Muted)
                    }
                    Slider(
                        value = state.year.toFloat(),
                        onValueChange = { v ->
                            val y = v.roundToInt()
                            if (y != state.year) {
                                state.year = y
                                haptics.selection()
                            }
                        },
                        valueRange = war.start.toFloat()..war.end.toFloat(),
                        steps = war.end - war.start - 1,
                        colors = SliderDefaults.colors(
                            thumbColor = war.color(),
                            activeTrackColor = war.color(),
                            inactiveTrackColor = Color.White.copy(alpha = 0.15f),
                            activeTickColor = Color.Black.copy(alpha = 0.5f),
                            inactiveTickColor = Color.White.copy(alpha = 0.3f),
                        ),
                    )
                    Row(
                        Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        RegionChip("World", Regions.world, state)
                        RegionChip("Europe", Regions.europe, state)
                        RegionChip("Mediterranean", Regions.mediterranean, state)
                        RegionChip("Pacific", Regions.pacific, state)
                        RegionChip("Malaya & SE Asia", Regions.seAsia, state, HC.Malay)
                        RegionChip("Atlantic", Regions.atlantic, state)
                    }
                }
            }
        }
    }
}

@Composable
private fun RegionChip(label: String, region: MapFocus, state: MapState, accent: Color = HC.WW2) {
    GlassChip(label, selected = false, accent = accent) { state.focusOn(region) }
}

@Composable
private fun LegendDot(color: Color, label: String) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        Box(Modifier.size(9.dp).clip(CircleShape).background(color))
        Text(label, fontSize = 12.sp, color = HC.Muted)
    }
}

@Composable
private fun SelectedCard(event: HistoricEvent, onOpen: () -> Unit, onClose: () -> Unit) {
    val accent = if (event.malay) HC.Malay else event.war.color()
    GlassCard(Modifier.fillMaxWidth(), tint = accent, shape = RoundedCornerShape(24.dp), onClick = onOpen) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Caption(event.date, color = accent)
                Spacer(Modifier.weight(1f))
                Text(
                    "✕",
                    color = HC.Muted,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable { onClose() }
                        .padding(horizontal = 8.dp, vertical = 2.dp),
                )
            }
            Text(event.title, fontFamily = Serif, fontSize = 20.sp, fontWeight = FontWeight.SemiBold, color = HC.Ink)
            Text(event.summary, fontSize = 13.sp, lineHeight = 18.sp, color = HC.Ink.copy(alpha = 0.82f))
            Text("Read more  →", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = accent)
        }
    }
}
