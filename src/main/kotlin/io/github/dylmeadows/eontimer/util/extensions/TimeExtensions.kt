package io.github.dylmeadows.eontimer.util.extensions

import io.github.dylmeadows.eontimer.model.settings.TimerSettings
import java.time.Duration

fun Long.toMinimumLength(): Long {
    var normalized = this
    while (normalized < TimerSettings.MINIMUM_LENGTH)
        normalized += Duration.ofMinutes(1).toMillis()
    return normalized
}

fun List<Duration>.getStage(index: Int): Duration {
    return if (index < size) get(index) else Duration.ZERO
}
