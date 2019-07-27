package io.github.dylmeadows.eontimer.service

import io.github.dylmeadows.eontimer.model.settings.TimerSettings
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CalibrationService @Autowired constructor(
    private val timerSettings: TimerSettings
) {

    private val console get() = timerSettings.console

    fun toMillis(delays: Long): Long =
        Math.round(delays * console.frameRate)

    fun toDelays(millis: Long): Long =
        Math.round(millis / console.frameRate)

    fun createCalibration(delay: Long, second: Long): Long =
        toMillis(delay - toDelays(second * 1000))

    fun calibrateToMillis(value: Long): Long {
        return if (timerSettings.precisionCalibrationMode) value else toMillis(value)
    }

    fun calibrateToDelays(value: Long): Long {
        return if (timerSettings.precisionCalibrationMode) value else toDelays(value)
    }
}