package io.github.dylmeadows.eontimer.service.factory

import io.github.dylmeadows.eontimer.model.timer.CustomTimer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class CustomTimerFactory @Autowired constructor(
    private val customTimer: CustomTimer) : TimerFactory {

    override val stages: List<Duration>
        get() = customTimer.stages
            .map { it.duration }

    override fun calibrate() = Unit
}