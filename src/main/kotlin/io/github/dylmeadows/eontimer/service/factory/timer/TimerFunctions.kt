package io.github.dylmeadows.eontimer.service.factory.timer

import io.github.dylmeadows.eontimer.model.settings.TimerSettings
import java.time.Duration

private val oneMinute = Duration.ofMinutes(1).toMillis()

fun Long.toMinimumLength(): Long {
    var normalized = this
    while (normalized < TimerSettings.MINIMUM_LENGTH)
        normalized += oneMinute
    return normalized
}