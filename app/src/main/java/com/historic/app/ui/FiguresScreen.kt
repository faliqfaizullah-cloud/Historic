package com.historic.app.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.historic.app.data.Figure
import com.historic.app.data.FigureData
import com.historic.app.data.FigureEra
import com.historic.app.data.HistoricData

@Composable
fun FiguresScreen(onOpen: (String) -> Unit) {
    var era by rememberSaveable { mutableStateOf<FigureEra?>(null) }
    val list = remember(era) { FigureData.all.filter { era == null || it.era == era } }
    val rows = remember(list) { list.chunked(2) }

    Column(Modifier.fillMaxSize()) {
        Column(Modifier.padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 8.dp)) {
            Caption("Faces of history", color = HC.WW1)
            Text("People who shaped the wars", fontFamily = Serif, fontSize = 30.sp, lineHeight = 36.sp, fontWeight = FontWeight.Bold, color = HC.Ink)
            Text(
                "Stylised illustrations, not likenesses.",
                fontSize = 12.sp,
                color = HC.Muted,
                modifier = Modifier.padding(top = 2.dp),
            )
        }
        Row(
            Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()).padding(horizontal = 20.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            GlassChip("All", era == null, Color.White) { era = null }
            FigureEra.values().forEach { e ->
                GlassChip(e.label, era == e, HC.WW1) { era = e }
            }
        }
        LazyColumn(
            Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 8.dp, bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            items(rows, key = { r -> r.first().id }) { pair ->
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                    pair.forEach { f -> FigureCard(f, Modifier.weight(1f)) { onOpen(f.id) } }
                    if (pair.size == 1) Spacer(Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
private fun FigureCard(f: Figure, modifier: Modifier, onClick: () -> Unit) {
    val accent = f.camp.color()
    GlassCard(modifier, tint = accent, onClick = onClick) {
        Column(Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            FigureArt(f, Modifier.fillMaxWidth().aspectRatio(0.9f), corner = 18.dp)
            Column(Modifier.padding(horizontal = 4.dp), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(
                    f.name,
                    fontFamily = Serif,
                    fontSize = 16.sp,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = HC.Ink,
                    minLines = 2,
                    maxLines = 2,
                )
                Text(f.years, fontSize = 12.sp, color = accent, fontWeight = FontWeight.Medium)
                Text(f.affiliation, fontSize = 11.sp, color = HC.Muted)
            }
        }
    }
}

@Composable
fun FigureDetailScreen(
    figure: Figure,
    onBack: () -> Unit,
    onOpenEvent: (String) -> Unit,
    onFullProfile: (() -> Unit)? = null,
) {
    val accent = figure.camp.color()
    Box(Modifier.fillMaxSize()) {
        OledBackdrop(accent, HC.WW2)
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
                GlassCard(Modifier.fillMaxWidth(), tint = accent) {
                    FigureArt(figure, Modifier.fillMaxWidth().height(260.dp), corner = 26.dp)
                }
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Caption(figure.years, color = accent)
                    Pill(figure.affiliation.uppercase(), accent)
                }
                Text(
                    figure.name,
                    fontFamily = Serif,
                    fontSize = 32.sp,
                    lineHeight = 38.sp,
                    fontWeight = FontWeight.Bold,
                    color = HC.Ink,
                )
                Text(figure.role, fontSize = 15.sp, color = accent, fontWeight = FontWeight.Medium)

                GlassCard(Modifier.fillMaxWidth(), tint = accent) {
                    Text(
                        figure.summary,
                        modifier = Modifier.padding(20.dp),
                        fontFamily = Serif,
                        fontSize = 17.sp,
                        lineHeight = 25.sp,
                        color = HC.Ink,
                    )
                }

                Caption("Key moments")
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    figure.highlights.forEach { line ->
                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            Box(
                                Modifier
                                    .padding(top = 9.dp)
                                    .size(6.dp)
                                    .clip(CircleShape)
                                    .background(accent),
                            )
                            Text(line, fontSize = 15.sp, lineHeight = 22.sp, color = HC.Ink.copy(alpha = 0.88f))
                        }
                    }
                }

                if (figure.criticism != null) {
                    GlassCard(Modifier.fillMaxWidth(), tint = HC.Enemy) {
                        Column(Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Caption("Controversy and criticism", color = HC.Enemy)
                            Text(figure.criticism, fontSize = 14.sp, lineHeight = 21.sp, color = HC.Ink.copy(alpha = 0.88f))
                        }
                    }
                }

                if (onFullProfile != null) {
                    GlassCard(Modifier.fillMaxWidth(), tint = HC.Malay, onClick = onFullProfile) {
                        Row(
                            Modifier.padding(horizontal = 20.dp, vertical = 16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text("Read the full biography  →", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = HC.Ink)
                        }
                    }
                }

                val related = figure.events.mapNotNull { HistoricData.byId[it] }
                if (related.isNotEmpty()) {
                    Caption("Related events")
                    related.forEach { e -> EventCard(e, onClick = { onOpenEvent(e.id) }) }
                }

                Text(
                    "Illustration is a stylised silhouette. To use a real photo, add a public-domain image named fig_${figure.id}.jpg to app/src/main/res/drawable.",
                    fontSize = 11.sp,
                    lineHeight = 16.sp,
                    color = HC.Muted.copy(alpha = 0.8f),
                )
                Spacer(Modifier.height(28.dp))
            }
        }
    }
}
