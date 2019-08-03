package io.github.dylmeadows.eontimer.service

import io.github.dylmeadows.eontimer.model.settings.TimerSettings
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import kotlin.math.roundToLong

@Service
class CalibrationService @Autowired constructor(
    private val timerSettings: TimerSettings
) {
    private val console get() = timerSettings.console
    private val isPrecisionCalibration get() = timerSettings.precisionCalibrationMode

    fun toMillis(delays: Long): Long =
        (delays * console.frameRate).roundToLong()

    fun toDelays(millis: Long): Long =
        (millis / console.frameRate).roundToLong()

    fun createCalibration(delay: Long, second: Long): Long =
        toMillis(delay - toDelays(second * 1000))

    fun calibrateToMillis(value: Long): Long {
        return if (isPrecisionCalibration) value else toMillis(value)
    }

    fun calibrateToDelays(value: Long): Long {
        return if (isPrecisionCalibration) value else toDelays(value)
    }
}