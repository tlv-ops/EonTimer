package io.github.dylmeadows.eontimer.service

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
import reactor.util.function.Tuples
import java.time.Duration
import java.util.*

@Service
class TimerActionService @Autowired constructor(
    timerRunnerService: TimerRunnerService,
    private val timerActionSettings: ActionSettings,
    private val soundPlayer: SoundPlayer) {

    private var actionInterval: List<Duration> = Collections.emptyList()
    private var nextAction = 0

    val activeProperty: BooleanProperty = SimpleBooleanProperty(false)
    private var active by activeProperty

    init {
        timerActionSettings.countProperty
            .combineWith(timerActionSettings.intervalProperty, Tuples::of)
            .map { it.mapT1 { t1 -> t1!!.toInt() } }
            .map { it.mapT2 { t2 -> t2!!.toInt() } }
            .map { createActionInterval(it.t1, it.t2) }
            .subscribe { actionInterval = it }

        timerRunnerService.onUpdate
            .publishOn(JavaFxScheduler.platform)
            .filter { it.current.remaining <= actionInterval[nextAction] }
            .subscribe {
                invokeAction()
                nextAction = when {
                    nextAction + 1 < actionInterval.size ->
                        nextAction + 1
                    else ->
                        0
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
            .toList().asReversed()
            .map { it * interval }
            .map(Number::toLong)
            .map(Duration::ofMillis)
    }

    private fun invokeAction() {
        if (timerActionSettings.mode == ActionMode.AUDIO || timerActionSettings.mode == ActionMode.AV)
            GlobalScope.launch {
                soundPlayer.play()
            }
        if (timerActionSettings.mode == ActionMode.VISUAL || timerActionSettings.mode == ActionMode.AV) {
            active = true
            GlobalScope.launch {
                delay(75)
                active = false
            }
        }
    }
}