# Historic

Android app (Kotlin + Jetpack Compose) about World War I and II, with an interactive world map
and a dedicated Malay history section (Malaya and Borneo).

- **UI:** true-black OLED background, glassmorphism cards (translucent gradient, rim light, press spring)
- **Haptics:** card press, chip/tab/slider detents, marker thud, war-switch rumble; global on/off switch on Home
- **Map:** pan, pinch-zoom, tap markers; faction shading follows the year slider
- **Content:** 56 events (31 global, 25 Malay), works fully offline

## Build the APK

**Android Studio:** open this folder, let Gradle sync, then Build > Build APK(s)
(or `gradle assembleRelease` with JDK 17 and the Android SDK, Gradle 8.7+).
Output: `app/build/outputs/apk/release/app-release.apk`
(signed with the debug key so it installs directly; use your own keystore for Play Store).

**No Android Studio:** push this folder to a GitHub repo; the included workflow
`.github/workflows/build-apk.yml` builds the APK and attaches it as a downloadable artifact.

Requirements: minSdk 26 (Android 8.0), compileSdk 34.

## Where to edit

- `data/Events.kt` - events and Malay story phases
- `data/MapGeo.kt` - coarse land outlines and faction zones
- `ui/Haptics.kt` - haptic palette
- `ui/Glass.kt` - glass components
# Historic
