package io.github.dylmeadows.eontimer.util.extensions

import io.github.dylmeadows.commonkt.core.io.url
import io.github.dylmeadows.eontimer.model.resource.FxmlResource
import io.github.dylmeadows.springboot.javafx.fxml.SpringFxmlLoader
import javafx.scene.Parent

fun <T : Parent> SpringFxmlLoader.load(resource: FxmlResource): T {
    return load(resource.url!!)
}


