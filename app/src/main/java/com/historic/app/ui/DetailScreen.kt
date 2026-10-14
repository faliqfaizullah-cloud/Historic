package com.historic.app.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.historic.app.data.HistoricEvent

@Composable
fun DetailScreen(event: HistoricEvent, onBack: () -> Unit, onShowOnMap: () -> Unit) {
    val haptics = LocalHaptics.current
    val accent = if (event.malay) HC.Malay else event.war.color()

    Box(Modifier.fillMaxSize()) {
        OledBackdrop(accent, if (event.malay) HC.WW1 else HC.Malay)
        Column(Modifier.fillMaxSize()) {
            Row(Modifier.padding(horizontal = 12.dp, vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                GlassCard(shape = CircleShape, onClick = onBack) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = HC.Ink,
                        modifier = Modifier.padding(12.dp),
                    )
                }
            }
            Column(
                Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(horizontal = 20.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Caption(event.date, color = accent)
                    if (event.malay) Pill("MALAYA", HC.Malay)
                }
                Text(
                    event.title,
                    fontFamily = Serif,
                    fontSize = 34.sp,
                    lineHeight = 40.sp,
                    fontWeight = FontWeight.Bold,
                    color = HC.Ink,
                )
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Icon(Icons.Filled.Place, contentDescription = null, tint = accent, modifier = Modifier.padding(0.dp))
                    Text(event.place, fontSize = 14.sp, color = HC.Muted)
                }
                GlassCard(Modifier.fillMaxWidth(), tint = accent) {
                    Text(
                        event.summary,
                        modifier = Modifier.padding(20.dp),
                        fontFamily = Serif,
                        fontSize = 18.sp,
                        lineHeight = 26.sp,
                        color = HC.Ink,
                    )
                }
                Text(event.detail, fontSize = 16.sp, lineHeight = 25.sp, color = HC.Ink.copy(alpha = 0.88f))
                if (event.malay && event.phase != null) {
                    Caption(event.phase, color = HC.Malay)
                }
                GlassCard(Modifier.fillMaxWidth(), tint = accent, onClick = {
                    haptics.heavy()
                    onShowOnMap()
                }) {
                    Row(
                        Modifier.padding(horizontal = 20.dp, vertical = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        Icon(Icons.Filled.Place, contentDescription = null, tint = accent)
                        Text("Show on map", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = HC.Ink)
                    }
                }
                Spacer(Modifier.height(28.dp))
            }
        }
    }
}
