package io.github.dylmeadows.eontimer.service.factory

import java.time.Duration

interface TimerFactory {

    val stages: List<Duration>

    fun calibrate()
}

