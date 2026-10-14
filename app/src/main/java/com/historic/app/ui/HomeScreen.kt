package com.historic.app.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.historic.app.data.HistoricData
import com.historic.app.data.LAT_BOTTOM
import com.historic.app.data.LAT_TOP
import com.historic.app.data.MapGeo
import com.historic.app.data.War
import java.time.LocalDate

@Composable
fun HomeScreen(
    onWar: (War) -> Unit,
    onMap: () -> Unit,
    onMalay: () -> Unit,
    onOpen: (String) -> Unit,
) {
    val haptics = LocalHaptics.current
    val events = HistoricData.events
    val featured = remember { events[LocalDate.now().dayOfYear % events.size] }

    LazyColumn(
        Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        item {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Caption("Historic", color = HC.WW1)
                Text(
                    "The world at war",
                    fontFamily = Serif,
                    fontSize = 36.sp,
                    lineHeight = 42.sp,
                    fontWeight = FontWeight.Bold,
                    color = HC.Ink,
                )
                Text(
                    "Two world wars, mapped and told — with Malaya's own story woven in.",
                    fontSize = 15.sp,
                    lineHeight = 21.sp,
                    color = HC.Muted,
                )
            }
        }

        item {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                WarCard(War.WW1, "The Great War", Modifier.weight(1f)) { onWar(War.WW1) }
                WarCard(War.WW2, "A world in flames", Modifier.weight(1f)) { onWar(War.WW2) }
            }
        }

        item {
            GlassCard(Modifier.fillMaxWidth().height(190.dp), onClick = onMap) {
                Canvas(Modifier.fillMaxSize()) {
                    val w = size.width
                    val h = w * ((LAT_TOP - LAT_BOTTOM) / 360.0).toFloat()
                    val top = (size.height - h) / 2f
                    fun p(lon: Double, lat: Double) = Offset(
                        ((lon + 180.0) / 360.0 * w).toFloat(),
                        top + ((LAT_TOP - lat) / (LAT_TOP - LAT_BOTTOM) * h).toFloat(),
                    )
                    for (poly in MapGeo.land) {
                        val path = Path()
                        poly.forEachIndexed { i, q ->
                            val s = p(q.lon, q.lat)
                            if (i == 0) path.moveTo(s.x, s.y) else path.lineTo(s.x, s.y)
                        }
                        path.close()
                        drawPath(path, Color.White.copy(alpha = 0.09f))
                        drawPath(path, Color.White.copy(alpha = 0.22f), style = Stroke(1.dp.toPx()))
                    }
                    for (e in events) {
                        val c = if (e.malay) HC.Malay else e.war.color()
                        val s = p(e.lon, e.lat)
                        drawCircle(c.copy(alpha = 0.35f), radius = 7.dp.toPx(), center = s)
                        drawCircle(c, radius = 2.2.dp.toPx(), center = s)
                    }
                }
                Column(Modifier.align(Alignment.BottomStart).padding(18.dp)) {
                    Caption("Interactive")
                    Text("World map", fontFamily = Serif, fontSize = 24.sp, fontWeight = FontWeight.SemiBold, color = HC.Ink)
                    Text("Scrub the years, pinch to zoom, tap a marker", fontSize = 13.sp, color = HC.Muted)
                }
            }
        }

        item {
            GlassCard(Modifier.fillMaxWidth(), tint = HC.Malay, onClick = onMalay) {
                Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Caption("Sejarah Malaysia", color = HC.Malay)
                        Pill("MALAY HISTORY", HC.Malay)
                    }
                    Text(
                        "Malaya in two world wars",
                        fontFamily = Serif,
                        fontSize = 24.sp,
                        lineHeight = 29.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = HC.Ink,
                    )
                    Text(
                        "From the Emden raid on Penang to the Fall of Singapore, the occupation, and the road to Merdeka.",
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        color = HC.Ink.copy(alpha = 0.82f),
                    )
                    Text(
                        "${events.count { it.malay }} stories  →",
                        fontSize = 13.sp,
                        color = HC.Malay,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
            }
        }

        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Caption("Featured today")
                EventCard(featured, onClick = { onOpen(featured.id) })
            }
        }

        item {
            GlassCard(Modifier.fillMaxWidth()) {
                Row(
                    Modifier.padding(horizontal = 20.dp, vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Column(Modifier.weight(1f)) {
                        Text("Haptic feedback", fontSize = 16.sp, fontWeight = FontWeight.Medium, color = HC.Ink)
                        Text("Taps, slider detents and map markers", fontSize = 12.sp, color = HC.Muted)
                    }
                    Switch(
                        checked = haptics.enabled,
                        onCheckedChange = {
                            haptics.enabled = it
                            if (it) haptics.confirm()
                        },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = HC.Malay.copy(alpha = 0.75f),
                            uncheckedThumbColor = HC.Muted,
                            uncheckedTrackColor = Color.White.copy(alpha = 0.08f),
                            uncheckedBorderColor = Color.White.copy(alpha = 0.2f),
                        ),
                    )
                }
            }
        }

        item {
            Text(
                "Historic · works offline · overview content, not a scholarly archive",
                fontSize = 11.sp,
                color = HC.Muted.copy(alpha = 0.7f),
                modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
            )
        }
    }
}

@Composable
private fun WarCard(war: War, tagline: String, modifier: Modifier, onClick: () -> Unit) {
    val c = war.color()
    GlassCard(modifier.height(200.dp), tint = c, onClick = onClick) {
        Column(
            Modifier.fillMaxSize().padding(18.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Caption(war.short, color = c)
                Text(
                    "${war.start}–${war.end}",
                    fontFamily = Serif,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = HC.Ink,
                )
                Text(tagline, fontSize = 14.sp, color = HC.Ink.copy(alpha = 0.8f))
            }
            Spacer(Modifier.height(4.dp))
            Text(
                "${HistoricData.count(war)} events  →",
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = c,
            )
        }
    }
}
