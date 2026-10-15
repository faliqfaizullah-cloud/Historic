package com.historic.app.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.historic.app.data.HistoricData
import com.historic.app.data.War

enum class NavTab(val label: String, val icon: ImageVector) {
    HOME("Home", Icons.Filled.Home),
    MAP("Map", Icons.Filled.Place),
    TIMELINE("Timeline", Icons.Filled.DateRange),
    MALAYA("Malaya", Icons.Filled.Star),
}

@Composable
fun HistoricApp() {
    val context = LocalContext.current
    val view = LocalView.current
    val haptics = remember(view) { Haptics(view, systemVibrator(context)) }

    CompositionLocalProvider(LocalHaptics provides haptics) {
        HistoricTheme {
            var tab by rememberSaveable { mutableStateOf(NavTab.HOME) }
            var detailId by rememberSaveable { mutableStateOf<String?>(null) }
            var filter by rememberSaveable { mutableStateOf(TimelineFilter.ALL) }
            val mapState = remember { MapState() }

            val detailEvent = detailId?.let { HistoricData.byId[it] }

            BackHandler(enabled = detailEvent != null) { detailId = null }
            BackHandler(enabled = detailEvent == null && tab != NavTab.HOME) { tab = NavTab.HOME }

            val (glowA, glowB) = when (tab) {
                NavTab.HOME -> HC.WW1 to HC.WW2
                NavTab.MAP -> HC.WW2 to HC.WW1
                NavTab.TIMELINE -> HC.WW1 to HC.Malay
                NavTab.MALAYA -> HC.Malay to HC.WW1
            }

            Box(Modifier.fillMaxSize().background(Color.Black)) {
                OledBackdrop(glowA, glowB)

                Column(Modifier.fillMaxSize().statusBarsPadding()) {
                    Box(Modifier.weight(1f)) {
                        AnimatedContent(
                            targetState = tab,
                            transitionSpec = { fadeIn(tween(220)) togetherWith fadeOut(tween(140)) },
                            label = "tabs",
                        ) { current ->
                            when (current) {
                                NavTab.HOME -> HomeScreen(
                                    onWar = { war ->
                                        filter = when (war) {
                                            War.WW1 -> TimelineFilter.WW1
                                            War.WW2 -> TimelineFilter.WW2
                                            War.COLD -> TimelineFilter.COLD
                                        }
                                        tab = NavTab.TIMELINE
                                    },
                                    onMap = { tab = NavTab.MAP },
                                    onMalay = { tab = NavTab.MALAYA },
                                    onOpen = { detailId = it },
                                )
                                NavTab.MAP -> MapScreen(mapState, onOpen = { detailId = it })
                                NavTab.TIMELINE -> TimelineScreen(filter, onFilter = { filter = it }, onOpen = { detailId = it })
                                NavTab.MALAYA -> MalayScreen(
                                    onOpen = { detailId = it },
                                    onShowOnMap = {
                                        mapState.openWar(War.WW2)
                                        mapState.focusOn(Regions.seAsia)
                                        tab = NavTab.MAP
                                    },
                                )
                            }
                        }
                    }
                    GlassNavBar(current = tab, onSelect = { tab = it })
                }

                AnimatedVisibility(
                    visible = detailEvent != null,
                    enter = slideInVertically(tween(320)) { it / 3 } + fadeIn(tween(320)),
                    exit = slideOutVertically(tween(240)) { it / 3 } + fadeOut(tween(240)),
                ) {
                    if (detailEvent != null) {
                        Box(Modifier.fillMaxSize().background(Color.Black).statusBarsPadding()) {
                            DetailScreen(
                                event = detailEvent,
                                onBack = { detailId = null },
                                onShowOnMap = {
                                    mapState.showEvent(detailEvent)
                                    detailId = null
                                    tab = NavTab.MAP
                                },
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun GlassNavBar(current: NavTab, onSelect: (NavTab) -> Unit) {
    val haptics = LocalHaptics.current
    GlassCard(
        Modifier.fillMaxWidth().navigationBarsPadding().padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(30.dp),
        strength = 1.3f,
    ) {
        Row(Modifier.fillMaxWidth().padding(6.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
            NavTab.values().forEach { t ->
                val selected = t == current
                val accent = when (t) {
                    NavTab.MALAYA -> HC.Malay
                    NavTab.TIMELINE -> HC.WW1
                    else -> HC.WW2
                }
                val shape = RoundedCornerShape(24.dp)
                Column(
                    Modifier
                        .clip(shape)
                        .background(if (selected) accent.copy(alpha = 0.22f) else Color.Transparent)
                        .clickable {
                            if (!selected) {
                                haptics.selection()
                                onSelect(t)
                            }
                        }
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Icon(t.icon, contentDescription = t.label, tint = if (selected) accent else HC.Muted)
                    Text(
                        t.label,
                        fontSize = 11.sp,
                        fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
                        color = if (selected) HC.Ink else HC.Muted,
                    )
                }
            }
        }
    }
}
