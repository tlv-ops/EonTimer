package io.github.dylmeadows.eontimer.service

import io.github.dylmeadows.commonkt.core.time.isIndefinite
import io.github.dylmeadows.commonkt.javafx.beans.property.getValue
import io.github.dylmeadows.commonkt.javafx.beans.property.setValue
import io.github.dylmeadows.eontimer.model.settings.ActionMode
import io.github.dylmeadows.eontimer.model.settings.ActionSettings
import io.github.dylmeadows.eontimer.service.action.SoundPlayer
import io.github.dylmeadows.reaktorfx.scheduler.JavaFxScheduler
import io.github.dylmeadows.reaktorfx.source.combineWith
import javafx.beans.property.BooleanProperty
import javafx.beans.property.SimpleBooleanProperty
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.Duration
import java.util.*

@Service
class TimerActionService @Autowired constructor(
    timerRunnerService: TimerRunnerService,
    private val timerActionSettings: ActionSettings,
    private val soundPlayer: SoundPlayer
) {
    private var actionInterval: List<Duration> = Collections.emptyList()
    private var nextAction = 0

    val activeProperty: BooleanProperty = SimpleBooleanProperty(false)
    private var active by activeProperty

    init {
        timerActionSettings.countProperty
            .combineWith(timerActionSettings.intervalProperty) { count, interval ->
                createActionInterval(count!!.toInt(), interval!!.toInt())
            }
            .subscribe { actionInterval = it }

        timerRunnerService.onUpdate
            .publishOn(JavaFxScheduler.platform)
            .filter { it.current.remaining <= actionInterval[nextAction] }
            .subscribe {
                if (it.current.duration.isIndefinite || it.current.duration > Duration.ZERO) {
                    invokeAction()
                    nextAction = when {
                        nextAction + 1 < actionInterval.size ->
                            nextAction + 1
                        else ->
                            0
                    }
                }
            }
        timerRunnerService.onStartStop
            .publishOn(JavaFxScheduler.platform)
            .subscribe {
                nextAction = 0
            }
    }

    private fun createActionInterval(count: Int, interval: Int): List<Duration> {
        return IntRange(0, count - 1)
            .reversed()
            .map { it * interval }
            .map(Number::toLong)
            .map(Duration::ofMillis)
    }

    private fun invokeAction() {
        if (timerActionSettings.mode == ActionMode.AUDIO || timerActionSettings.mode == ActionMode.AV)
            soundPlayer.play()
        if (timerActionSettings.mode == ActionMode.VISUAL || timerActionSettings.mode == ActionMode.AV) {
            active = true
            GlobalScope.launch {
                delay(75)
                active = false
            }
        }
    }
}