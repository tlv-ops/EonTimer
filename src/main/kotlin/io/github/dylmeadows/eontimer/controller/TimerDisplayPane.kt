package io.github.dylmeadows.eontimer.controller

import io.github.dylmeadows.commonkt.core.time.isIndefinite
import io.github.dylmeadows.commonkt.javafx.scene.paint.toHex
import io.github.dylmeadows.eontimer.model.settings.ActionSettings
import io.github.dylmeadows.eontimer.service.TimerActionService
import io.github.dylmeadows.eontimer.service.TimerRunnerService
import io.github.dylmeadows.reaktorfx.scheduler.JavaFxScheduler
import io.github.dylmeadows.reaktorfx.source.valuesOf
import javafx.fxml.FXML
import javafx.scene.control.Label
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class TimerDisplayPane @Autowired constructor(
    private val timerRunnerService: TimerRunnerService,
    private val timerActionService: TimerActionService,
    private val actionSettings: ActionSettings
) {

    @FXML
    lateinit var currentStageLbl: Label
    @FXML
    lateinit var minutesBeforeTargetLbl: Label
    @FXML
    lateinit var nextStageLbl: Label

    fun initialize() {
        timerRunnerService.onUpdate
            .publishOn(JavaFxScheduler.platform)
            .subscribe {
                currentStageLbl.text = formatSeconds(it.current.remaining)
                minutesBeforeTargetLbl.text = formatMinutes(it.total.remaining)
                nextStageLbl.text = formatSeconds(it.next)
            }
        timerActionService.activeProperty.valuesOf()
            .subscribe { currentStageLbl.isActive = it }
        actionSettings.colorProperty.valuesOf()
            .map { "-theme-active: ${it.toHex()}" }
            .subscribe(currentStageLbl::setStyle)
    }

    private fun formatSeconds(duration: Duration): String {
        return when {
            !duration.isIndefinite ->
                String.format("%d:%03d",
                    duration.toSeconds(),
                    duration.toMillis() % 1000)
            else -> "?:???"
        }
    }

    private fun formatMinutes(duration: Duration): String {
        return when {
            !duration.isIndefinite ->
                duration.toMinutes().toString()
            else -> "?"
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