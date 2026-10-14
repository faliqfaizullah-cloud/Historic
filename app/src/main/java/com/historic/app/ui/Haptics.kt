package com.historic.app.ui

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.view.HapticFeedbackConstants
import android.view.View
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf

/**
 * Central haptic palette. Light effects go through the View (respecting the system touch-feedback
 * setting); stronger "impact" effects use the Vibrator. A single in-app switch mutes everything.
 */
class Haptics(private val view: View, private val vibrator: Vibrator?) {

    var enabled by mutableStateOf(true)

    private fun feedback(constant: Int) {
        if (enabled) view.performHapticFeedback(constant)
    }

    private fun vibrate(effect: VibrationEffect) {
        if (!enabled) return
        val v = vibrator ?: return
        if (v.hasVibrator()) v.vibrate(effect)
    }

    /** Soft tick when a glass card is pressed. */
    fun tick() = feedback(HapticFeedbackConstants.CLOCK_TICK)

    /** Crisp detent for chips, the year slider and tabs. */
    fun selection() =
        if (Build.VERSION.SDK_INT >= 34) feedback(HapticFeedbackConstants.SEGMENT_TICK)
        else feedback(HapticFeedbackConstants.CLOCK_TICK)

    fun click() = feedback(HapticFeedbackConstants.VIRTUAL_KEY)

    /** Confirmation when something opens. */
    fun confirm() =
        if (Build.VERSION.SDK_INT >= 30) feedback(HapticFeedbackConstants.CONFIRM)
        else feedback(HapticFeedbackConstants.LONG_PRESS)

    /** Firm thud when a map marker is selected. */
    fun heavy() = vibrate(
        if (Build.VERSION.SDK_INT >= 29) VibrationEffect.createPredefined(VibrationEffect.EFFECT_HEAVY_CLICK)
        else VibrationEffect.createOneShot(40, VibrationEffect.DEFAULT_AMPLITUDE)
    )

    /** Rolling "distant artillery" rumble used when switching between the two wars. */
    fun boom() {
        val timings = longArrayOf(0, 35, 45, 70, 40, 110)
        val amplitudes = intArrayOf(0, 140, 0, 220, 0, 255)
        val effect = if (vibrator?.hasAmplitudeControl() == true) {
            VibrationEffect.createWaveform(timings, amplitudes, -1)
        } else {
            VibrationEffect.createWaveform(timings, -1)
        }
        vibrate(effect)
    }
}

@Suppress("DEPRECATION")
fun systemVibrator(context: Context): Vibrator? =
    if (Build.VERSION.SDK_INT >= 31) {
        (context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager)?.defaultVibrator
    } else {
        context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
    }

val LocalHaptics = staticCompositionLocalOf<Haptics> { error("Haptics not provided") }
