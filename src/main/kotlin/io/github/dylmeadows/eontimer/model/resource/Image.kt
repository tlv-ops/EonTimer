package io.github.dylmeadows.eontimer.model.resource

import io.github.dylmeadows.commonkt.core.io.Resource

enum class Image(fileName: String) : Resource {
    DefaultBackgroundImage("default_background_image.png");

    override val path = "io/github/dylmeadows/eontimer/img/$fileName"
}