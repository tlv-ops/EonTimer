package io.github.dylmeadows.eontimer.controller

import io.github.dylmeadows.commonkt.core.time.INDEFINITE
import io.github.dylmeadows.commonkt.core.time.toSeconds
import io.github.dylmeadows.commonkt.javafx.scene.paint.toHex
import io.github.dylmeadows.eontimer.model.TimerState
import io.github.dylmeadows.eontimer.model.settings.ActionSettings
import io.github.dylmeadows.eontimer.service.action.TimerActionService
import io.github.dylmeadows.reaktorfx.scheduler.JavaFxScheduler
import io.github.dylmeadows.reaktorfx.source.combineWith
import io.github.dylmeadows.reaktorfx.source.valuesOf
import javafx.fxml.FXML
import javafx.scene.control.Label
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import reactor.util.function.Tuples
import java.time.Duration

@Component
class TimerDisplayPane @Autowired constructor(
    private val timerState: TimerState,
    private val timerActionService: TimerActionService,
    private val actionSettings: ActionSettings) {

    @FXML
    lateinit var currentStageLbl: Label
    @FXML
    lateinit var minutesBeforeTargetLbl: Label
    @FXML
    lateinit var nextStageLbl: Label

    fun initialize() {
        timerState.total.durationProperty.combineWith(timerState.total.elapsedProperty, Tuples::of)
            .subscribeOn(JavaFxScheduler.platform)
            .subscribe {
                minutesBeforeTargetLbl.text =
                    formatMinutesBeforeTarget(it.t1!!, it.t2!!)
            }
        timerState.current.durationProperty.valuesOf()
            .subscribeOn(JavaFxScheduler.platform)
            .map(this::formatTime)
            .subscribe(currentStageLbl::setText)
        timerState.current.elapsedProperty.valuesOf()
            .subscribeOn(JavaFxScheduler.platform)
            .map(this::formatTime)
            .subscribe(currentStageLbl::setText)
        timerState.nextProperty.valuesOf()
            .subscribeOn(JavaFxScheduler.platform)
            .map(this::formatTime)
            .subscribe(nextStageLbl::setText)

        timerActionService.activeProperty.valuesOf()
            .subscribe { currentStageLbl.isActive = it }
        actionSettings.colorProperty.valuesOf()
            .map { "-theme-active: ${it.toHex()}" }
            .subscribe(currentStageLbl::setStyle)
    }

    private fun formatMinutesBeforeTarget(totalTime: Duration, totalElapsed: Duration): String {
        return when (totalTime) {
            INDEFINITE -> "?"
            else -> {
                val remaining = totalTime - totalElapsed
                remaining.toMinutes().toString()
            }
        }
    }

    private fun formatTime(duration: Duration): String {
        return when (duration) {
            INDEFINITE -> "?:??"
            else -> String.format("%d:%02d",
                duration.toSeconds(),
                duration.toMillis()
                    / 10 % 100)
        }
    }

    var Label.isActive: Boolean
        get() = this.styleClass.contains("active")
        set(value) {
            when (value) {
                true -> styleClass.add("active")
                false -> styleClass.remove("active")
            }
        }
}