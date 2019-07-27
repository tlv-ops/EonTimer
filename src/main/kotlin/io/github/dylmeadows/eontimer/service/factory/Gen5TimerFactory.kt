package io.github.dylmeadows.eontimer.service.factory

import io.github.dylmeadows.eontimer.model.timer.Gen5Timer
import io.github.dylmeadows.eontimer.service.CalibrationService
import io.github.dylmeadows.eontimer.service.factory.timer.DelayTimer
import io.github.dylmeadows.eontimer.service.factory.timer.SecondTimer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class Gen5TimerFactory @Autowired constructor(
    private val gen5Timer: Gen5Timer,
    private val delayTimer: DelayTimer,
    private val secondTimer: SecondTimer,
    private val calibrationService: CalibrationService
) : TimerFactory {

    private val delayCalibration: Long
        get() = delayTimer.calibrate(gen5Timer.targetDelay, gen5Timer.delayHit)

    private val secondCalibration: Long
        get() = secondTimer.calibrate(gen5Timer.targetSecond, gen5Timer.secondHit)

    override val stages: List<Duration>
        get() {
            return when (gen5Timer.mode) {
                Gen5Timer.Mode.STANDARD ->
                    secondTimer.createStages(
                        gen5Timer.targetSecond,
                        calibrationService.calibrateToMillis(gen5Timer.calibration))
                Gen5Timer.Mode.C_GEAR ->
                    delayTimer.createStages(
                        gen5Timer.targetSecond,
                        gen5Timer.targetDelay,
                        calibrationService.calibrateToMillis(gen5Timer.calibration))
            }
        }

    override fun calibrate() {
        when (gen5Timer.mode) {
            Gen5Timer.Mode.STANDARD -> {
                gen5Timer.calibration += calibrationService.calibrateToDelays(secondCalibration)
            }
            Gen5Timer.Mode.C_GEAR -> {
                gen5Timer.calibration += calibrationService.calibrateToDelays(delayCalibration)
            }
        }
    }
}