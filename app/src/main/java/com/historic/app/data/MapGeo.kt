package com.historic.app.data

/** Equirectangular crop used by the map: roughly 84N down to 58S. */
const val LAT_TOP = 84.0
const val LAT_BOTTOM = -58.0

private fun poly(vararg v: Number): List<LonLat> =
    List(v.size / 2) { LonLat(v[it * 2].toDouble(), v[it * 2 + 1].toDouble()) }

/**
 * Deliberately coarse, hand-drawn outlines. They give the map its shape without shipping
 * a heavy GeoJSON file, and they are easy to refine later (just add more points).
 */
object MapGeo {

    // ---------- Landmasses ----------
    private val northAmerica = poly(
        -168, 66, -156, 71, -125, 70, -95, 72, -80, 69, -62, 58, -56, 52, -66, 45, -70, 42, -76, 36,
        -81, 31, -80, 25.5, -84, 30, -90, 29, -97, 26, -97, 21, -90, 21, -87, 21, -88, 16, -83, 15,
        -83, 10, -79, 9, -80, 7.5, -85, 10, -92, 14, -105, 20, -112, 29, -110, 23, -115, 30, -118, 34,
        -124, 40, -124, 48, -135, 58, -148, 60, -165, 55, -158, 58, -165, 62
    )
    private val southAmerica = poly(
        -78, 8, -72, 12, -62, 10, -52, 5, -50, 0, -35, -6, -39, -14, -41, -22, -48, -26, -58, -35,
        -57, -39, -65, -42, -68, -52, -72, -54, -74, -45, -72, -30, -70, -18, -76, -14, -81, -5,
        -80, 0, -77, 4
    )
    private val eurasia = poly(
        -9.5, 37, -9, 43.5, -1.5, 46, -4.5, 48.5, 1.5, 50.5, 4, 52, 8.5, 54, 8.5, 57, 12, 55, 14, 54,
        21, 55, 24, 59, 30, 60, 22, 60.5, 21, 65, 25, 65.5, 18, 69, 28, 71, 40, 67, 44, 68, 60, 69,
        70, 73, 100, 77, 113, 74, 140, 72, 160, 70, 180, 68, 178, 64, 163, 60, 156, 51, 143, 59,
        135, 55, 141, 52, 140, 48, 132, 43, 130, 42, 129, 35, 126, 35, 126, 38, 122, 40, 121, 37,
        119, 35, 122, 30, 120, 25, 110, 21, 108, 17, 109, 11, 105, 9, 100, 13, 100, 8, 103, 1.3,
        101, 3, 98, 8, 97, 17, 94, 17, 91, 22, 87, 21, 80, 15, 78, 8, 73, 17, 70, 22, 67, 25,
        57, 25, 56, 27, 50, 30, 48, 29, 51, 24, 56, 26, 59, 22, 52, 16, 43, 13, 39, 21, 35, 28,
        34.5, 31, 36, 36, 30, 36.5, 27, 37, 26, 40, 29, 41, 41, 41, 41, 43, 37, 45, 30, 46, 28, 44,
        26, 41, 24, 40, 23, 37, 21, 38, 19, 41, 14, 45, 12, 45, 16, 41, 16, 38, 12, 38, 10, 44,
        6, 43, 3, 43, 0, 38, -5, 36
    )
    private val africa = poly(
        -17, 21, -13, 28, -6, 36, 10, 37, 11, 33, 20, 31, 32, 31, 34, 28, 38, 20, 43, 12, 51, 12,
        41, -3, 40, -15, 35, -24, 32, -29, 27, -34, 19, -35, 15, -27, 12, -17, 13, -5, 9, 4,
        -8, 4.5, -13, 8, -17, 14
    )
    private val australia = poly(
        114, -22, 122, -18, 130, -12, 137, -12, 142, -11, 146, -19, 153, -27, 150, -37, 141, -38,
        135, -34, 115, -34
    )
    private val sumatra = poly(95, 5, 98, 4, 104, -2, 106, -6, 101, -3, 96, 2)
    private val borneo = poly(109, 2, 117, 7, 119, 1, 116, -4, 110, -3)
    private val java = poly(105, -6, 114, -8, 106, -7.5)
    private val newGuinea = poly(131, -1, 141, -3, 150, -10, 141, -9, 135, -4)
    private val philippines = poly(120, 18, 123, 18, 126, 8, 122, 7, 120, 14)
    private val japan = poly(130, 31, 135, 33.5, 140, 35, 142, 39.5, 141.5, 45, 140, 41, 137, 37, 132, 35)
    private val britain = poly(-5.5, 50, 1.5, 51, 1.5, 53, -0.5, 54.5, -2, 57.5, -3.5, 58.5, -6, 57, -5, 55, -4.5, 54, -3, 52, -5, 51.5)
    private val ireland = poly(-10, 52, -6, 52, -6, 55, -9, 54.5)
    private val iceland = poly(-24, 64, -14, 64, -15, 66, -23, 66)
    private val greenland = poly(-55, 60, -20, 70, -20, 82, -50, 83, -70, 78, -55, 70)
    private val madagascar = poly(44, -25, 50, -15, 49, -12, 44, -17)
    private val newZealand = poly(172, -34, 178, -38, 175, -41, 167, -46, 172, -41)

    val land: List<List<LonLat>> = listOf(
        northAmerica, southAmerica, eurasia, africa, australia, sumatra, borneo, java, newGuinea,
        philippines, japan, britain, ireland, iceland, greenland, madagascar, newZealand
    )

    // ---------- Shared faction shapes ----------
    private val usa = poly(-124, 48.5, -95, 49, -83, 46, -75, 45, -67, 45, -70, 42, -76, 36, -81, 31, -80, 25.5, -84, 30, -90, 29, -97, 26, -104, 29, -111, 31.3, -117, 32.5, -124, 40)
    private val canada = poly(-141, 70, -125, 70, -95, 72, -80, 69, -62, 58, -56, 52, -66, 45, -75, 45, -83, 46, -95, 49, -124, 49, -135, 58, -141, 60)
    private val india = poly(61, 25, 67, 25, 70, 22, 73, 17, 78, 8, 80, 15, 87, 21, 91, 22, 94, 17, 98, 28, 92, 28, 88, 28, 80, 30, 75, 35, 72, 34, 66, 30)
    private val russia = poly(24, 54, 24, 56, 28, 60, 30, 69, 40, 67, 60, 69, 80, 73, 130, 72, 180, 68, 180, 50, 135, 48, 131, 43, 120, 50, 87, 49, 60, 50, 50, 45, 38, 46, 33, 50, 28, 52)
    private val italy = poly(7, 44, 7, 46, 13.5, 46.5, 13.5, 45.5, 12.5, 44, 16, 41.5, 18.5, 40, 16, 38, 12.5, 38.2, 10.5, 43, 8, 44)
    private val franceBenelux = poly(-4.5, 48.5, -1.5, 46, -1.8, 43.3, 3, 42.5, 7.5, 43.8, 7, 46, 8, 48.5, 6.5, 50.3, 6, 51.5, 4, 53, 3, 51.2, 1.5, 50.8)
    private val germany = poly(6, 53.5, 9, 55, 14, 54.2, 18, 54.5, 18, 51, 15, 50.5, 13, 48.5, 7.5, 47.6, 6, 50)
    private val austria = poly(9.5, 47.5, 17, 48.2, 16, 46.8, 13, 46.5)
    private val poland = poly(14, 54, 18.5, 54.8, 23.5, 54.3, 24, 50, 23, 49, 19, 49.4, 15, 50.8)
    private val norway = poly(5, 58.5, 5, 62, 12, 66, 20, 70, 28, 71, 26, 69, 18, 66, 12, 63, 11, 59)
    private val balkans = poly(14, 45, 19, 46, 22.5, 44.5, 28, 44, 28, 41.5, 26, 41, 22, 37.5, 19.5, 41, 16, 43)
    private val westUssr = poly(23, 50, 24, 55, 28, 60, 32, 60, 40, 56, 44, 48, 40, 46, 37, 45, 30, 46, 26, 48)
    private val libya = poly(10, 33, 20, 31, 25, 31.5, 25, 22, 15, 23, 10, 30)
    private val egypt = poly(25, 31.5, 34, 31, 34.5, 28, 33, 22, 25, 22)
    private val chinaEast = poly(114, 45, 130, 48, 130, 42, 122, 40, 121, 37, 119, 35, 122, 30, 120, 25, 113, 23, 112, 32, 116, 40)
    private val indochinaBurma = poly(92, 24, 100, 22, 108, 21, 109, 11, 105, 9, 100.5, 13, 100, 8, 98, 8, 97, 17, 94, 17)
    private val malaya = poly(100, 6.5, 102, 6.2, 103.5, 4, 104.2, 1.4, 102.5, 1.9, 100.8, 3.8, 100.3, 5.5)
    private val pacificArc = poly(140, 12, 165, 12, 172, 5, 175, -3, 160, -9, 148, -8, 142, -3, 135, 2)
    private val ottoman = poly(26, 41, 41, 41, 44, 38, 48, 30, 42, 24, 39, 21, 35, 28, 34.5, 31, 36, 36, 30, 36.5, 27, 37)
    private val hungary = poly(13, 48.5, 18, 49.5, 24, 48.5, 26, 47, 23, 45, 19, 45, 16, 46, 13, 46.5)
    private val bulgaria = poly(22.5, 44, 28.5, 44, 28, 41.5, 23, 41.5)

    // Cold War shapes
    private val westGermany = poly(6, 53.5, 9, 55, 10.5, 54, 11, 51, 12, 50, 13, 48.5, 7.5, 47.6, 6, 50)
    private val eastGermany = poly(10.5, 54, 14, 54.2, 14.5, 51, 12, 50, 11, 51)
    private val easternEurope = poly(12, 50.3, 18.5, 50, 23, 49, 27, 48, 29, 45, 28, 43.5, 22.5, 44, 19, 46, 17, 47.7, 13.5, 48.5)
    private val turkey = poly(26, 41, 41, 41, 44, 40, 42.5, 37, 36, 36, 30, 36.5, 27, 37.5)
    private val greece = poly(20.5, 40.5, 26, 41.2, 26, 38, 23, 36.5, 21, 38)
    private val china = poly(74, 39, 80, 45, 87, 49, 96, 43, 111, 43, 119, 47, 122, 53, 130, 48, 130, 42, 122, 40, 121, 37, 119, 35, 122, 30, 120, 25, 110, 21, 106, 22, 98, 24, 92, 28, 80, 30, 75, 35)
    private val northKorea = poly(124.5, 40, 129.5, 42.5, 128.5, 38.5, 125, 38)
    private val southKorea = poly(126, 38, 129, 38, 129.5, 35.5, 126.5, 34.5)
    private val northVietnam = poly(102.2, 22.3, 106.5, 22.9, 108, 21.6, 106.4, 17.5, 104.5, 19, 102.5, 20.5)
    private val southVietnam = poly(106.4, 17.5, 108.3, 16, 109.3, 12, 105.2, 8.7, 105, 11, 107, 14)
    private val cuba = poly(-84.9, 21.9, -82.5, 23.1, -80, 23, -74.2, 20.2, -77.7, 19.9, -80.5, 21.5, -82.5, 22.0)
    private val afghanistan = poly(61, 35.5, 65, 37.3, 70, 37.3, 74.5, 37, 71, 34, 70, 30, 66, 29.5, 62, 29.5)

    private fun z(war: War, from: Int, to: Int, side: Side, name: String, pts: List<LonLat>) =
        Zone(war, from, to, side, name, pts)

    val zones: List<Zone> = listOf(
        // ----- World War I (Allies first, then Central Powers drawn on top) -----
        z(War.WW1, 1914, 1918, Side.ALLIES, "United Kingdom", britain),
        z(War.WW1, 1914, 1918, Side.ALLIES, "France & Belgium", franceBenelux),
        z(War.WW1, 1914, 1917, Side.ALLIES, "Russian Empire", russia),
        z(War.WW1, 1915, 1918, Side.ALLIES, "Italy", italy),
        z(War.WW1, 1914, 1918, Side.ALLIES, "Japan", japan),
        z(War.WW1, 1917, 1918, Side.ALLIES, "United States", usa),
        z(War.WW1, 1914, 1918, Side.ALLIES, "Canada", canada),
        z(War.WW1, 1914, 1918, Side.ALLIES, "Australia", australia),
        z(War.WW1, 1914, 1918, Side.ALLIES, "British India", india),
        z(War.WW1, 1914, 1918, Side.CENTRAL, "German Empire", germany),
        z(War.WW1, 1914, 1918, Side.CENTRAL, "Austria-Hungary", hungary),
        z(War.WW1, 1914, 1918, Side.CENTRAL, "Ottoman Empire", ottoman),
        z(War.WW1, 1915, 1918, Side.CENTRAL, "Bulgaria", bulgaria),

        // ----- World War II -----
        z(War.WW2, 1939, 1945, Side.ALLIES, "United Kingdom", britain),
        z(War.WW2, 1939, 1945, Side.ALLIES, "Canada", canada),
        z(War.WW2, 1939, 1945, Side.ALLIES, "Australia", australia),
        z(War.WW2, 1939, 1945, Side.ALLIES, "British India", india),
        z(War.WW2, 1939, 1945, Side.ALLIES, "Egypt", egypt),
        z(War.WW2, 1941, 1945, Side.ALLIES, "Soviet Union", russia),
        z(War.WW2, 1941, 1945, Side.ALLIES, "United States", usa),
        z(War.WW2, 1944, 1945, Side.ALLIES, "Liberated France", franceBenelux),
        z(War.WW2, 1944, 1945, Side.ALLIES, "Liberated Poland", poland),
        z(War.WW2, 1944, 1945, Side.ALLIES, "Liberated Italy", italy),
        z(War.WW2, 1939, 1945, Side.AXIS, "Germany", germany),
        z(War.WW2, 1939, 1945, Side.AXIS, "Austria", austria),
        z(War.WW2, 1939, 1943, Side.AXIS, "Occupied Poland", poland),
        z(War.WW2, 1940, 1943, Side.AXIS, "Occupied France & Benelux", franceBenelux),
        z(War.WW2, 1940, 1944, Side.AXIS, "Occupied Norway", norway),
        z(War.WW2, 1941, 1944, Side.AXIS, "Balkans", balkans),
        z(War.WW2, 1939, 1943, Side.AXIS, "Italy", italy),
        z(War.WW2, 1939, 1942, Side.AXIS, "Italian Libya", libya),
        z(War.WW2, 1941, 1943, Side.AXIS, "Occupied Soviet territory", westUssr),
        z(War.WW2, 1939, 1945, Side.AXIS, "Japan", japan),
        z(War.WW2, 1939, 1945, Side.AXIS, "Occupied China", chinaEast),
        z(War.WW2, 1942, 1945, Side.AXIS, "Indochina, Thailand & Burma", indochinaBurma),
        z(War.WW2, 1942, 1945, Side.AXIS, "Japanese-occupied Malaya", malaya),
        z(War.WW2, 1942, 1944, Side.AXIS, "Sumatra", sumatra),
        z(War.WW2, 1942, 1944, Side.AXIS, "Java", java),
        z(War.WW2, 1942, 1944, Side.AXIS, "Borneo", borneo),
        z(War.WW2, 1942, 1944, Side.AXIS, "Philippines", philippines),
        z(War.WW2, 1942, 1944, Side.AXIS, "New Guinea (north)", newGuinea),
        z(War.WW2, 1942, 1944, Side.AXIS, "Pacific islands", pacificArc),

        // ----- Cold War (Western bloc first, then the Soviet bloc on top) -----
        z(War.COLD, 1947, 1991, Side.WEST, "United States", usa),
        z(War.COLD, 1949, 1991, Side.WEST, "Canada", canada),
        z(War.COLD, 1947, 1991, Side.WEST, "United Kingdom", britain),
        z(War.COLD, 1949, 1991, Side.WEST, "France & Benelux", franceBenelux),
        z(War.COLD, 1949, 1991, Side.WEST, "Italy", italy),
        z(War.COLD, 1949, 1989, Side.WEST, "West Germany", westGermany),
        z(War.COLD, 1990, 1991, Side.WEST, "Reunified Germany", germany),
        z(War.COLD, 1952, 1991, Side.WEST, "Turkey", turkey),
        z(War.COLD, 1952, 1991, Side.WEST, "Greece", greece),
        z(War.COLD, 1947, 1991, Side.WEST, "Japan", japan),
        z(War.COLD, 1948, 1991, Side.WEST, "South Korea", southKorea),
        z(War.COLD, 1951, 1991, Side.WEST, "Australia", australia),
        z(War.COLD, 1955, 1975, Side.WEST, "South Vietnam", southVietnam),
        z(War.COLD, 1947, 1991, Side.EAST, "Soviet Union", russia),
        z(War.COLD, 1947, 1991, Side.EAST, "Soviet Union (west)", westUssr),
        z(War.COLD, 1947, 1989, Side.EAST, "Poland", poland),
        z(War.COLD, 1947, 1989, Side.EAST, "Eastern Europe", easternEurope),
        z(War.COLD, 1949, 1989, Side.EAST, "East Germany", eastGermany),
        z(War.COLD, 1949, 1991, Side.EAST, "People's Republic of China", china),
        z(War.COLD, 1948, 1991, Side.EAST, "North Korea", northKorea),
        z(War.COLD, 1954, 1991, Side.EAST, "Vietnam (north)", northVietnam),
        z(War.COLD, 1976, 1991, Side.EAST, "Vietnam (south)", southVietnam),
        z(War.COLD, 1959, 1991, Side.EAST, "Cuba", cuba),
        z(War.COLD, 1979, 1989, Side.EAST, "Soviet-occupied Afghanistan", afghanistan),
    )
}
