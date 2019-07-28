package io.github.dylmeadows.eontimer.model.timer

import io.github.dylmeadows.commonkt.javafx.beans.property.getValue
import io.github.dylmeadows.commonkt.javafx.beans.property.setValue
import io.github.dylmeadows.commonkt.javafx.util.Choice
import javafx.beans.property.SimpleLongProperty
import javafx.beans.property.SimpleObjectProperty

class Gen5Timer {
    @Transient
    val modeProperty = SimpleObjectProperty(DEFAULT_MODE)
    var mode: Mode by modeProperty
    @Transient
    val calibrationProperty = SimpleLongProperty(DEFAULT_CALIBRATION)
    var calibration by calibrationProperty
    @Transient
    val entralinkCalibrationProperty = SimpleLongProperty(DEFAULT_ENTRALINK_CALIBRATION)
    var entralinkCalibration by entralinkCalibrationProperty
    @Transient
    val frameCalibrationProperty = SimpleLongProperty(DEFAULT_FRAME_CALIBRATION)
    var frameCalibration by frameCalibrationProperty
    @Transient
    val targetDelayProperty = SimpleLongProperty(DEFAULT_TARGET_DELAY)
    var targetDelay by targetDelayProperty
    @Transient
    val targetSecondProperty = SimpleLongProperty(DEFAULT_TARGET_SECOND)
    var targetSecond by targetSecondProperty
    @Transient
    val targetAdvancesProperty = SimpleLongProperty(DEFAULT_TARGET_ADVANCES)
    var targetAdvances by targetAdvancesProperty
    @Transient
    val secondHitProperty = SimpleLongProperty()
    var secondHit by secondHitProperty
    @Transient
    val delayHitProperty = SimpleLongProperty()
    var delayHit by delayHitProperty

    enum class Mode(override val displayName: String) : Choice {
        STANDARD("Standard"),
        C_GEAR("C-Gear"),
        ENTRALINK("Entralink"),
        ENTRALINK_PLUS("Entralink+")
    }

    companion object {
        @JvmField
        val DEFAULT_MODE = Mode.STANDARD
        const val DEFAULT_CALIBRATION = -95L
        const val DEFAULT_TARGET_DELAY = 1200L
        const val DEFAULT_TARGET_SECOND = 50L
        const val DEFAULT_ENTRALINK_CALIBRATION = 256L
        const val DEFAULT_FRAME_CALIBRATION = 0L
        const val DEFAULT_TARGET_ADVANCES = 100L
        const val ENTRALINK_FRAME_RATE = 0.837148929
    }
}