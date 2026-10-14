package com.historic.app.ui

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import com.historic.app.data.HistoricData
import com.historic.app.data.HistoricEvent
import com.historic.app.data.War

enum class TimelineFilter(val label: String) {
    ALL("All"), WW1("World War I"), WW2("World War II"), MALAYA("Malaya")
}

@Composable
fun TimelineScreen(
    filter: TimelineFilter,
    onFilter: (TimelineFilter) -> Unit,
    onOpen: (String) -> Unit,
) {
    val sorted = remember(filter) {
        HistoricData.events
            .filter {
                when (filter) {
                    TimelineFilter.ALL -> true
                    TimelineFilter.WW1 -> it.war == War.WW1
                    TimelineFilter.WW2 -> it.war == War.WW2
                    TimelineFilter.MALAYA -> it.malay
                }
            }
            .sortedWith(compareBy<HistoricEvent>({ it.war }, { it.year }, { it.month }))
    }
    val grouped = remember(sorted) { sorted.groupBy { it.war to it.year }.entries.toList() }

    Column(Modifier.fillMaxSize()) {
        Column(Modifier.padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 8.dp)) {
            Caption("Timeline", color = HC.WW1)
            Text("Year by year", fontFamily = Serif, fontSize = 32.sp, fontWeight = FontWeight.Bold, color = HC.Ink)
        }
        Row(
            Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()).padding(horizontal = 20.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            TimelineFilter.values().forEach { f ->
                val accent = when (f) {
                    TimelineFilter.WW1 -> HC.WW1
                    TimelineFilter.WW2 -> HC.WW2
                    TimelineFilter.MALAYA -> HC.Malay
                    TimelineFilter.ALL -> Color.White
                }
                GlassChip(f.label, filter == f, accent) { onFilter(f) }
            }
        }
        LazyColumn(
            Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 8.dp, bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            grouped.forEach { (key, list) ->
                val (war, year) = key
                item(key = "header-${war.name}-$year") {
                    Row(
                        Modifier.fillMaxWidth().padding(top = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        Text("$year", fontFamily = Serif, fontSize = 30.sp, fontWeight = FontWeight.Bold, color = war.color())
                        Box(Modifier.weight(1f).height(1.dp).background(Color.White.copy(alpha = 0.14f)))
                        Caption(war.short)
                    }
                }
                items(list, key = { it.id }) { e -> EventCard(e, onClick = { onOpen(e.id) }) }
            }
        }
    }
}
