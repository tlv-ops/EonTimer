package io.github.dylmeadows.eontimer.service.factory

import io.github.dylmeadows.eontimer.model.timer.Gen4Timer
import io.github.dylmeadows.eontimer.service.CalibrationService
import io.github.dylmeadows.eontimer.service.factory.timer.DelayTimer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class Gen4TimerFactory @Autowired constructor(
    private val gen4Timer: Gen4Timer,
    private val delayTimer: DelayTimer,
    private val calibrationService: CalibrationService) : TimerFactory {

    private val calibration: Long
        get() = calibrationService.createCalibration(gen4Timer.calibratedDelay, gen4Timer.calibratedSecond)

    override val stages: List<Duration>
        get() = delayTimer.createStages(
            gen4Timer.targetSecond,
            gen4Timer.targetDelay,
            calibration)

    override fun calibrate() {
        gen4Timer.calibratedDelay +=
            calibrationService.calibrateToDelays(
                delayTimer.calibrate(
                    gen4Timer.targetDelay,
                    gen4Timer.delayHit))
    }
}