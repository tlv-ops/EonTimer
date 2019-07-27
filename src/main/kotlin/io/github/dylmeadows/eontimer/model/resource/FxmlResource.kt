package io.github.dylmeadows.eontimer.model.resource

import io.github.dylmeadows.commonkt.core.io.Resource

enum class FxmlResource(fileName: String) : Resource {
    EonTimerPane("EonTimerPane.fxml"),
    SettingsControlPane("settings/SettingsControlPane.fxml");

    override val path = "io/github/dylmeadows/eontimer/fxml/$fileName"
}