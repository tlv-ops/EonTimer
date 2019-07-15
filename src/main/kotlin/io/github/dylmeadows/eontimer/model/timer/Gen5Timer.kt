package io.github.dylmeadows.eontimer.model.timer

import io.github.dylmeadows.commonkt.javafx.beans.property.getValue
import io.github.dylmeadows.commonkt.javafx.beans.property.setValue
import io.github.dylmeadows.commonkt.javafx.util.Choice
import javafx.beans.property.LongProperty
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
    val targetDelayProperty = SimpleLongProperty(DEFAULT_TARGET_DELAY)
    var targetDelay by targetDelayProperty
    @Transient
    val targetSecondProperty = SimpleLongProperty(DEFAULT_TARGET_SECOND)
    var targetSecond by targetSecondProperty
    @Transient
    val secondHitProperty: LongProperty = SimpleLongProperty()
    var secondHit by secondHitProperty
    @Transient
    val delayHitProperty: LongProperty = SimpleLongProperty()
    var delayHit by delayHitProperty

    enum class Mode(override val displayName: String) : Choice {
        STANDARD("Standard"),
        C_GEAR("C-Gear")
    }

    companion object {
        @JvmField
        val DEFAULT_MODE = Mode.STANDARD
        const val DEFAULT_CALIBRATION = -95L
        const val DEFAULT_TARGET_DELAY = 1200L
        const val DEFAULT_TARGET_SECOND = 50L
    }
}