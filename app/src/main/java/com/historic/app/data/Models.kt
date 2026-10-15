package com.historic.app.data

enum class War(
    val label: String,
    val short: String,
    val start: Int,
    val end: Int,
    val friendly: String,
    val rival: String,
) {
    WW1("World War I", "WWI", 1914, 1918, "Allies", "Central Powers"),
    WW2("World War II", "WWII", 1939, 1945, "Allies", "Axis"),
    COLD("Cold War", "Cold War", 1947, 1991, "Western bloc", "Soviet bloc")
}

enum class Side(val label: String) {
    ALLIES("Allies"),
    CENTRAL("Central Powers"),
    AXIS("Axis"),
    WEST("Western bloc"),
    EAST("Soviet bloc")
}

data class HistoricEvent(
    val id: String,
    val war: War,
    val year: Int,
    val month: Int,
    val date: String,
    val title: String,
    val place: String,
    val lat: Double,
    val lon: Double,
    val summary: String,
    val detail: String,
    val malay: Boolean = false,
    val phase: String? = null,
)

data class LonLat(val lon: Double, val lat: Double)

data class Zone(
    val war: War,
    val from: Int,
    val to: Int,
    val side: Side,
    val name: String,
    val points: List<LonLat>,
)

/** An event shows on the map once the slider reaches its year (post-war events appear at the final year). */
fun HistoricEvent.visibleOn(war: War, year: Int): Boolean =
    this.war == war && (this.year <= year || (year >= war.end && this.year > war.end))
