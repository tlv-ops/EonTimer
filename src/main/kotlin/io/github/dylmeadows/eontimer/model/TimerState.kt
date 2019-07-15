package io.github.dylmeadows.eontimer.model

import io.github.dylmeadows.commonkt.core.time.isIndefinite
import io.github.dylmeadows.commonkt.core.time.sum
import io.github.dylmeadows.commonkt.javafx.beans.property.getValue
import io.github.dylmeadows.commonkt.javafx.beans.property.setValue
import io.github.dylmeadows.eontimer.util.extensions.getStage
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleObjectProperty
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class TimerState {
    val runningProperty = SimpleBooleanProperty(false)
    var running by runningProperty

    val total = TimerStage()
    val current = TimerStage()

    val nextProperty = SimpleObjectProperty(Duration.ZERO)
    var next: Duration by nextProperty

    fun update(stages: List<Duration>) {
        current.duration = stages.getStage(0)
        current.elapsed = if (!current.duration.isIndefinite)
            current.duration
        else
            Duration.ZERO
        next = stages.getStage(1)
        total.duration = stages.sum()
    }
}
