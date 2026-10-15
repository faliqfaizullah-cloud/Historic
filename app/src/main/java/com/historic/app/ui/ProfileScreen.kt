package com.historic.app.ui

import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.historic.app.data.MahathirData
import com.historic.app.data.ProfileItem

private enum class ProfileTab(val label: String) {
    LIFE("Biography"), WORK("Contributions"), CRITIQUE("Criticism"), LEGACY("Legacy")
}

@Composable
fun ProfileScreen(onBack: () -> Unit) {
    var tab by rememberSaveable { mutableStateOf(ProfileTab.LIFE) }
    val accent = HC.Malay

    Box(Modifier.fillMaxSize()) {
        OledBackdrop(accent, HC.WW1)
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
                    Caption("Sejarah Malaysia", color = accent)
                    Pill("BIOGRAPHY", accent)
                }
                Text(
                    MahathirData.NAME,
                    fontFamily = Serif,
                    fontSize = 34.sp,
                    lineHeight = 40.sp,
                    fontWeight = FontWeight.Bold,
                    color = HC.Ink,
                )
                Text(MahathirData.ROLE, fontSize = 15.sp, color = accent, fontWeight = FontWeight.Medium)

                GlassCard(Modifier.fillMaxWidth(), tint = accent) {
                    Text(
                        MahathirData.INTRO,
                        modifier = Modifier.padding(20.dp),
                        fontFamily = Serif,
                        fontSize = 17.sp,
                        lineHeight = 25.sp,
                        color = HC.Ink,
                    )
                }

                GlassCard(Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        MahathirData.facts.forEach { (k, v) ->
                            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                Text(k, fontSize = 12.sp, color = HC.Muted, modifier = Modifier.weight(0.38f))
                                Text(v, fontSize = 14.sp, color = HC.Ink, modifier = Modifier.weight(0.62f))
                            }
                        }
                    }
                }

                Row(
                    Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    ProfileTab.values().forEach { t ->
                        GlassChip(t.label, tab == t, accent) { tab = t }
                    }
                }

                when (tab) {
                    ProfileTab.LIFE -> MahathirData.life.forEach { ProfileCard(it, accent) }
                    ProfileTab.WORK -> MahathirData.contributions.forEach { ProfileCard(it, accent) }
                    ProfileTab.CRITIQUE -> {
                        Text(
                            "Historians and the public disagree about these episodes. They are listed here alongside his achievements so you can judge for yourself.",
                            fontSize = 13.sp,
                            lineHeight = 19.sp,
                            color = HC.Muted,
                        )
                        MahathirData.criticisms.forEach { ProfileCard(it, HC.Enemy) }
                    }
                    ProfileTab.LEGACY -> MahathirData.legacy.forEach { line ->
                        GlassCard(Modifier.fillMaxWidth(), tint = accent) {
                            Text(
                                line,
                                modifier = Modifier.padding(20.dp),
                                fontFamily = Serif,
                                fontSize = 17.sp,
                                lineHeight = 25.sp,
                                color = HC.Ink,
                            )
                        }
                    }
                }

                Text(MahathirData.SOURCES_NOTE, fontSize = 11.sp, lineHeight = 16.sp, color = HC.Muted.copy(alpha = 0.8f))
                Spacer(Modifier.height(28.dp))
            }
        }
    }
}

@Composable
private fun ProfileCard(item: ProfileItem, accent: androidx.compose.ui.graphics.Color) {
    GlassCard(Modifier.fillMaxWidth(), tint = accent) {
        Column(Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Caption(item.tag, color = accent)
            Text(
                item.title,
                fontFamily = Serif,
                fontSize = 20.sp,
                lineHeight = 25.sp,
                fontWeight = FontWeight.SemiBold,
                color = HC.Ink,
            )
            Text(item.body, fontSize = 14.sp, lineHeight = 21.sp, color = HC.Ink.copy(alpha = 0.85f))
        }
    }
}
