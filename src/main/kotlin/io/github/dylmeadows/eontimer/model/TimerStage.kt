package io.github.dylmeadows.eontimer.model

import java.time.Duration

data class TimerStage(val duration: Duration = Duration.ZERO,
                      val elapsed: Duration = Duration.ZERO) {
    val remaining: Duration get() = duration - elapsed

    operator fun plus(duration: Duration): TimerStage {
        return TimerStage(this.duration, elapsed + duration)
    }

    fun withDuration(duration: Duration): TimerStage {
        return TimerStage(duration, elapsed)
    }

    fun withElapsed(elapsed: Duration): TimerStage {
        return TimerStage(duration, elapsed)
    }

    companion object {
        @JvmField
        val ZERO = TimerStage()
    }
}