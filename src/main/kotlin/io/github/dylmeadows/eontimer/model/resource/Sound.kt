package io.github.dylmeadows.eontimer.model.resource

import io.github.dylmeadows.commonkt.core.io.Resource
import io.github.dylmeadows.commonkt.javafx.util.Choice

enum class Sound(fileName: String) : Resource, Choice {
    BEEP("beep.wav"),
    TICK("tick.wav"),
    DING("ding.wav"),
    POP("pop.wav");

    override val path = "io/github/dylmeadows/eontimer/sounds/$fileName"
    override val displayName = name.toLowerCase().capitalize()
}