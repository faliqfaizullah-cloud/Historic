package com.historic.app.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.historic.app.data.HistoricData
import com.historic.app.data.HistoricEvent

@Composable
fun MalayScreen(onOpen: (String) -> Unit, onShowOnMap: () -> Unit) {
    val malayEvents = HistoricData.events.filter { it.malay }

    LazyColumn(
        Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        item {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Caption("Sejarah Malaysia", color = HC.Malay)
                Text(
                    "Malaya at war",
                    fontFamily = Serif,
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Bold,
                    color = HC.Ink,
                )
            }
        }
        item {
            GlassCard(Modifier.fillMaxWidth(), tint = HC.Malay) {
                Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        "British Malaya was a prize of tin, rubber and a gateway to Singapore. " +
                            "It was touched by the First World War, then overrun in the Second — " +
                            "and the experience of occupation helped set the stage for independence in 1957.",
                        fontSize = 15.sp,
                        lineHeight = 22.sp,
                        color = HC.Ink.copy(alpha = 0.9f),
                    )
                    Text(
                        "See all on the map  →",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = HC.Malay,
                        modifier = Modifier.padding(top = 4.dp),
                    )
                }
            }
        }
        item {
            GlassCard(Modifier.fillMaxWidth(), tint = HC.Malay, onClick = onShowOnMap) {
                Column(Modifier.padding(horizontal = 20.dp, vertical = 14.dp)) {
                    Text("Open Malaya on the map", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = HC.Ink)
                    Text("Zoom to the Malay Peninsula and Borneo", fontSize = 12.sp, color = HC.Muted)
                }
            }
        }

        HistoricData.malayPhases.forEach { (phase, blurb) ->
            val inPhase = malayEvents.filter { it.phase == phase }
                .sortedWith(compareBy<HistoricEvent>({ it.year }, { it.month }))
            if (inPhase.isNotEmpty()) {
                item(key = "phase-$phase") {
                    Column(Modifier.padding(top = 10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(phase, fontFamily = Serif, fontSize = 22.sp, fontWeight = FontWeight.SemiBold, color = HC.Malay)
                        Text(blurb, fontSize = 13.sp, lineHeight = 19.sp, color = HC.Muted)
                    }
                }
                items(inPhase, key = { it.id }) { e -> EventCard(e, onClick = { onOpen(e.id) }) }
            }
        }
    }
}
