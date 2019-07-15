package io.github.dylmeadows.eontimer.model.settings

import io.github.dylmeadows.commonkt.javafx.util.Choice

enum class Console(val fps: Double) : Choice {
    GBA(59.7271),
    NDS(59.8261);

    override val displayName: String = name

    val frameRate: Double get() = 1000 / fps
}