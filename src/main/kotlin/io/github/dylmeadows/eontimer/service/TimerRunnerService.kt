package io.github.dylmeadows.eontimer.service

import io.github.dylmeadows.commonkt.core.time.milliseconds
import io.github.dylmeadows.commonkt.core.time.sum
import io.github.dylmeadows.eontimer.model.TimerStage
import io.github.dylmeadows.eontimer.model.TimerState
import io.github.dylmeadows.eontimer.model.settings.TimerSettings
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.DirectProcessor
import java.time.Duration
import java.time.Instant
import java.util.*

@Service
class TimerRunnerService @Autowired constructor(
    private val timerSettings: TimerSettings) {

    var isRunning = false
        private set
    var total = TimerStage()
    var current = TimerStage()
    val next get() = getStage(stageIndex + 1)
    private val state get() = TimerState(total, current, next)

    val onStartStop = DirectProcessor.create<Boolean>()
    val onUpdate = DirectProcessor.create<TimerState>()

    var stages: List<Duration> = Collections.emptyList()
        set(value) {
            if (!isRunning) {
                field = value
                resetState()
            }
        }
    private var stageIndex = 0

    private lateinit var timerJob: Job

    private val period get() = timerSettings.refreshInterval.milliseconds

    fun start() {
        if (!isRunning) {
            resetState()
            timerJob = GlobalScope.launch {
                var delay = period
                var previous = Instant.now()
                while (isActive && total.elapsed < total.duration) {
                    delay(delay.toMillis())
                    val current = Instant.now()
                    val delta = Duration.between(previous, current)
                    process(delta)
                    // adjust the delay
                    delay -= delta - period
                    previous = current
                }
                stop()
            }
            isRunning = true
            onStartStop.onNext(isRunning)
        }
    }

    private fun process(delta: Duration) {
        total += delta
        current += delta
        onUpdate.onNext(state)
        if (current.elapsed >= current.duration) {
            val overflow = current.elapsed - current.duration
            current = TimerStage(next, overflow)
            onUpdate.onNext(state)
            stageIndex++
        }
    }

    fun stop() {
        if (isRunning) {
            timerJob.cancel()
            isRunning = false
            resetState()
            onStartStop.onNext(isRunning)
        }
    }

    private fun getStage(index: Int): Duration {
        return if (index < stages.size) stages[index] else Duration.ZERO
    }

    private fun resetState() {
        total = TimerStage(stages.sum())
        current = TimerStage(getStage(0))
        stageIndex = 0
        onUpdate.onNext(state)
    }
}