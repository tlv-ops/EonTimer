package io.github.dylmeadows.eontimer.service.factory

import io.github.dylmeadows.eontimer.model.timer.Gen3Timer
import io.github.dylmeadows.eontimer.service.factory.timer.FrameTimer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class Gen3TimerFactory @Autowired constructor(
    private val gen3Timer: Gen3Timer,
    private val frameTimer: FrameTimer) : TimerFactory {

    override val stages: List<Duration>
        get() {
            return when (gen3Timer.mode) {
                Gen3Timer.Mode.STANDARD ->
                    frameTimer.createStages(
                        gen3Timer.preTimer,
                        gen3Timer.targetFrame,
                        gen3Timer.calibration)
                Gen3Timer.Mode.VARIABLE_TARGET ->
                    frameTimer.createStages(
                        gen3Timer.preTimer)
            }
        }

    override fun calibrate() {
        gen3Timer.calibration +=
            frameTimer.calibrate(
                gen3Timer.targetFrame,
                gen3Timer.frameHit)
    }
}