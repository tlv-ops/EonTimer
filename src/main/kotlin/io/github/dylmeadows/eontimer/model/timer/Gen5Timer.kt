package io.github.dylmeadows.eontimer.model.timer

import com.fasterxml.jackson.annotation.JsonIgnore
import io.github.dylmeadows.commonkt.javafx.beans.property.getValue
import io.github.dylmeadows.commonkt.javafx.beans.property.setValue
import io.github.dylmeadows.commonkt.javafx.util.Choice
import javafx.beans.property.SimpleLongProperty
import javafx.beans.property.SimpleObjectProperty

class Gen5Timer {
    @get:JsonIgnore
    val modeProperty = SimpleObjectProperty(DEFAULT_MODE)
    var mode: Mode by modeProperty
    @get:JsonIgnore
    val calibrationProperty = SimpleLongProperty(DEFAULT_CALIBRATION)
    var calibration by calibrationProperty
    @get:JsonIgnore
    val entralinkCalibrationProperty = SimpleLongProperty(DEFAULT_ENTRALINK_CALIBRATION)
    var entralinkCalibration by entralinkCalibrationProperty
    @get:JsonIgnore
    val frameCalibrationProperty = SimpleLongProperty(DEFAULT_FRAME_CALIBRATION)
    var frameCalibration by frameCalibrationProperty
    @get:JsonIgnore
    val targetDelayProperty = SimpleLongProperty(DEFAULT_TARGET_DELAY)
    var targetDelay by targetDelayProperty
    @get:JsonIgnore
    val targetSecondProperty = SimpleLongProperty(DEFAULT_TARGET_SECOND)
    var targetSecond by targetSecondProperty
    @get:JsonIgnore
    val targetAdvancesProperty = SimpleLongProperty(DEFAULT_TARGET_ADVANCES)
    var targetAdvances by targetAdvancesProperty
    @get:JsonIgnore
    val secondHitProperty = SimpleLongProperty()
    @get:JsonIgnore
    var secondHit by secondHitProperty
    @get:JsonIgnore
    val delayHitProperty = SimpleLongProperty()
    @get:JsonIgnore
    var delayHit by delayHitProperty
    @get:JsonIgnore
    val actualAdvancesProperty = SimpleLongProperty()
    @get:JsonIgnore
    var actualAdvances by actualAdvancesProperty

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