package io.github.dylmeadows.eontimer.service

import io.github.dylmeadows.commonkt.core.time.isIndefinite
import io.github.dylmeadows.commonkt.core.time.milliseconds
import io.github.dylmeadows.commonkt.core.time.sum
import io.github.dylmeadows.commonkt.core.util.asStack
import io.github.dylmeadows.eontimer.model.TimerState
import io.github.dylmeadows.eontimer.model.settings.TimerSettings
import io.github.dylmeadows.eontimer.service.action.TimerActionService
import io.github.dylmeadows.eontimer.util.extensions.getStage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.javafx.JavaFx
import kotlinx.coroutines.launch
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.Instant
import java.util.*

@Service
class TimerRunnerService @Autowired constructor(
    private val timerState: TimerState,
    private val timerSettings: TimerSettings,
    private val timerActionService: TimerActionService) {

    private lateinit var timerJob: Job
    var stages: MutableList<Duration> = Collections.emptyList()
        private set
    private var mStages: List<Duration> = Collections.emptyList()

    private val total get() = timerState.total
    private val current get() = timerState.current
    private var next
        get() = timerState.next
        set(value) {
            timerState.next = value
        }
    private var isRunning
        get() = timerState.running
        private set(value) {
            timerState.running = value
        }
    private val actionInterval
        get() = timerActionService.actionInterval
            .filter { it < current.duration }
            .asStack()

    private val period: Duration get() = timerSettings.refreshInterval.milliseconds

    fun start(stages: List<Duration> = mStages) {
        if (!isRunning) {
            resetState(stages)
            timerJob = GlobalScope.launch(Dispatchers.JavaFx) {
                var stageIndex = 0
                var preElapsed = Duration.ZERO
                while (isActive && stageIndex < stages.size) {
                    preElapsed = runStage(this, stageIndex, preElapsed) - stages.getStage(stageIndex)
                    stageIndex++
                }
                isRunning = false
                resetState()
            }
            isRunning = true
        }
    }

    private suspend fun runStage(scope: CoroutineScope, stageIndex: Int, preElapsed: Duration): Duration {
        var elapsed = preElapsed
        var adjustedDelay = period
        var lastTimestamp = Instant.now()
        current.duration = stages.getStage(stageIndex)

        val actionInterval = this.actionInterval
        while (scope.isActive && elapsed < current.duration) {
            delay(adjustedDelay.toMillis())

            val now = Instant.now()
            val delta = Duration.between(lastTimestamp, now)
            adjustedDelay -= delta - period
            lastTimestamp = now
            elapsed += delta

            updateState(stageIndex, delta, elapsed)
            if (!current.duration.isIndefinite && current.elapsed <= actionInterval.peek()) {
                timerActionService.invokeAction()
                actionInterval.pop()
            }
        }
        return elapsed
    }

    fun stop() {
        if (timerState.running) {
            timerJob.cancel()
            isRunning = false
            resetState()
        }
    }

    private fun updateState(stageIndex: Int = 0,
                            delta: Duration = Duration.ZERO,
                            elapsed: Duration = Duration.ZERO) {
        current.duration = stages.getStage(stageIndex)
        current.elapsed = if (!current.duration.isIndefinite)
            current.duration - elapsed
        else
            elapsed
        total.elapsed += delta
    }

    private fun resetState(stages: List<Duration> = this.mStages) {
        this.mStages = stages
        this.stages = stages.toMutableList()

        total.duration = mStages.sum()
        total.elapsed = Duration.ZERO
        current.duration = mStages.getStage(0)
        current.elapsed = if (!current.duration.isIndefinite) current.duration else Duration.ZERO
        next = mStages.getStage(1)
    }
}