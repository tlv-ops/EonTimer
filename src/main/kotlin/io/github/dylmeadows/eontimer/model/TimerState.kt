package io.github.dylmeadows.eontimer.model

import java.time.Duration

data class TimerState(val total: TimerStage = TimerStage.ZERO,
                      val current: TimerStage = TimerStage.ZERO,
                      val next: Duration = Duration.ZERO)
