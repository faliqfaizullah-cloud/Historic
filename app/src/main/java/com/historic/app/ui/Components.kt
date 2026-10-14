package com.historic.app.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.historic.app.data.HistoricEvent

@Composable
fun EventCard(event: HistoricEvent, onClick: () -> Unit, modifier: Modifier = Modifier) {
    val accent = if (event.malay) HC.Malay else event.war.color()
    GlassCard(modifier.fillMaxWidth(), tint = accent, onClick = onClick) {
        Column(Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Caption(event.date, color = accent)
                if (event.malay) Pill("MALAYA", HC.Malay)
            }
            Text(
                event.title,
                fontFamily = Serif,
                fontSize = 20.sp,
                lineHeight = 25.sp,
                fontWeight = FontWeight.SemiBold,
                color = HC.Ink,
            )
            Text(event.place, fontSize = 12.sp, color = HC.Muted)
            Text(event.summary, fontSize = 14.sp, lineHeight = 20.sp, color = HC.Ink.copy(alpha = 0.82f))
        }
    }
}
