package io.github.dylmeadows.eontimer.service.factory

import io.github.dylmeadows.eontimer.model.TimerModel
import java.util.*

interface TimerFactory {
    fun createTimer(): List<Long>
}

fun <T> List<T>.toUnmodifiableList(): List<T> =
    Collections.unmodifiableList(this)