package io.github.dylmeadows.eontimer.model

import io.github.dylmeadows.commonkt.javafx.beans.property.getValue
import io.github.dylmeadows.commonkt.javafx.beans.property.setValue
import javafx.beans.property.SimpleObjectProperty
import java.time.Duration

class TimerStage(duration: Duration = Duration.ZERO,
                 elapsed: Duration = Duration.ZERO) {

    val durationProperty = SimpleObjectProperty(duration)
    var duration: Duration by durationProperty

    val elapsedProperty = SimpleObjectProperty(elapsed)
    var elapsed: Duration by elapsedProperty
}