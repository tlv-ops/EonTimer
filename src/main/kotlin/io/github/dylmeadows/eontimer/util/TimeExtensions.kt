package io.github.dylmeadows.eontimer.util

import io.github.dylmeadows.eontimer.model.settings.TimerSettings
import java.time.Duration

fun Long.toMinimumLength(): Long {
    var normalized = this
    while (normalized < TimerSettings.MINIMUM_LENGTH)
        normalized += Duration.ofMinutes(1).toMillis()
    return normalized
}
