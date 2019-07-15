package io.github.dylmeadows.eontimer.model.resource

import io.github.dylmeadows.commonkt.core.io.Resource

enum class CssResource(fileName: String) : Resource {
    MAIN("main.css");

    override val path = "io/github/dylmeadows/eontimer/css/$fileName"
}