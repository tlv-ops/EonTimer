package io.github.dylmeadows.eontimer.model.timer

import com.fasterxml.jackson.annotation.JsonIgnore
import io.github.dylmeadows.commonkt.javafx.beans.property.getValue
import io.github.dylmeadows.commonkt.javafx.beans.property.setValue
import io.github.dylmeadows.commonkt.javafx.util.Choice
import javafx.beans.property.SimpleLongProperty
import javafx.beans.property.SimpleObjectProperty

class Gen3Timer {
    @get:JsonIgnore
    val modeProperty = SimpleObjectProperty(DEFAULT_MODE)
    var mode: Mode by modeProperty
    @get:JsonIgnore
    val calibrationProperty = SimpleLongProperty(DEFAULT_CALIBRATION)
    var calibration by calibrationProperty
    @get:JsonIgnore
    val preTimerProperty = SimpleLongProperty(DEFAULT_PRE_TIMER)
    var preTimer by preTimerProperty
    @get:JsonIgnore
    val targetFrameProperty = SimpleLongProperty(DEFAULT_TARGET_FRAME)
    var targetFrame by targetFrameProperty
    @get:JsonIgnore
    val frameHitProperty = SimpleLongProperty()
    @get:JsonIgnore
    var frameHit by frameHitProperty

    enum class Mode(override val displayName: String) : Choice {
        STANDARD("Standard"),
        VARIABLE_TARGET("Variable Target");
    }

    companion object {
        @JvmField
        val DEFAULT_MODE = Mode.STANDARD
        const val DEFAULT_CALIBRATION = 0L
        const val DEFAULT_PRE_TIMER = 5000L
        const val DEFAULT_TARGET_FRAME = 1000L
    }
}