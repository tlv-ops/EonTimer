package io.github.dylmeadows.eontimer.model.timer

import io.github.dylmeadows.commonkt.javafx.beans.property.getValue
import io.github.dylmeadows.commonkt.javafx.beans.property.setValue
import io.github.dylmeadows.commonkt.javafx.util.Choice
import javafx.beans.property.SimpleLongProperty
import javafx.beans.property.SimpleObjectProperty

class Gen4Timer {
    @Transient
    val modeProperty = SimpleObjectProperty(DEFAULT_MODE)
    var mode: Mode by modeProperty
    @Transient
    val calibratedDelayProperty = SimpleLongProperty(DEFAULT_CALIBRATED_DELAY)
    var calibratedDelay by calibratedDelayProperty
    @Transient
    val calibratedSecondProperty = SimpleLongProperty(DEFAULT_CALIBRATED_SECOND)
    var calibratedSecond by calibratedSecondProperty
    @Transient
    val targetDelayProperty = SimpleLongProperty(DEFAULT_TARGET_DELAY)
    var targetDelay by targetDelayProperty
    @Transient
    val targetSecondProperty = SimpleLongProperty(DEFAULT_TARGET_SECOND)
    var targetSecond by targetSecondProperty
    @Transient
    val delayHitProperty = SimpleLongProperty()
    var delayHit by delayHitProperty

    enum class Mode(override val displayName: String) : Choice {
        STANDARD("Standard");
    }

    companion object {
        @JvmField
        val DEFAULT_MODE = Mode.STANDARD
        const val DEFAULT_CALIBRATED_DELAY = 500L
        const val DEFAULT_CALIBRATED_SECOND = 14L
        const val DEFAULT_TARGET_DELAY = 600L
        const val DEFAULT_TARGET_SECOND = 50L
    }
}