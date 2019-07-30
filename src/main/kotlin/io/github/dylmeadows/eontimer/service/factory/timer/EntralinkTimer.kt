package io.github.dylmeadows.eontimer.service.factory.timer

import io.github.dylmeadows.commonkt.core.time.milliseconds
import io.github.dylmeadows.eontimer.model.timer.Gen5Timer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.Duration
import kotlin.math.roundToLong

@Service
class EntralinkTimer @Autowired constructor(
    private val delayTimer: DelayTimer
) {

    fun createStages(
        targetSecond: Long,
        targetDelay: Long,
        calibration: Long,
        entralinkCalibration: Long
    ): List<Duration> {
        val stages = delayTimer.createStages(targetSecond, targetDelay, calibration)
        return listOf(stage1(stages), stage2(stages, entralinkCalibration))
    }

    fun createStages(
        targetSecond: Long,
        targetDelay: Long,
        targetAdvances: Long,
        calibration: Long,
        entralinkCalibration: Long,
        frameCalibration: Long
    ): List<Duration> {
        val stages = createStages(targetSecond, targetDelay, calibration, entralinkCalibration)
        return listOf(stages[0], stages[1], stage3(targetAdvances, frameCalibration))
    }

    fun calibrateDelays(targetDelay: Long, delayHit: Long): Long {
        return delayTimer.calibrate(targetDelay, delayHit)
    }

    fun calibrateAdvances(targetAdvances: Long, actualAdvances: Long): Long {
        return ((targetAdvances - actualAdvances) / Gen5Timer.ENTRALINK_FRAME_RATE).roundToLong() * 1000
    }

    private fun stage1(stages: List<Duration>): Duration {
        return stages[0] + 250L.milliseconds
    }

    private fun stage2(stages: List<Duration>, entralinkCalibration: Long): Duration {
        return stages[1] - entralinkCalibration.milliseconds
    }

    private fun stage3(targetAdvances: Long, frameCalibration: Long): Duration {
        return Duration.ofMillis((targetAdvances / Gen5Timer.ENTRALINK_FRAME_RATE).roundToLong() * 1000 + frameCalibration)
    }
}
