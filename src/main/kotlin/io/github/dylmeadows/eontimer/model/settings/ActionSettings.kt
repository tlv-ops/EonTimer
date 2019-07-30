package io.github.dylmeadows.eontimer.model.settings

import com.fasterxml.jackson.annotation.JsonIgnore
import io.github.dylmeadows.commonkt.javafx.beans.property.getValue
import io.github.dylmeadows.commonkt.javafx.beans.property.setValue
import io.github.dylmeadows.eontimer.model.resource.Sound
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.scene.paint.Color

class ActionSettings {
    @get:JsonIgnore
    val modeProperty = SimpleObjectProperty(DEFAULT_MODE)
    var mode: ActionMode by modeProperty
    @get:JsonIgnore
    val colorProperty = SimpleObjectProperty(DEFAULT_COLOR)
    var color: Color by colorProperty
    @get:JsonIgnore
    val soundProperty = SimpleObjectProperty(DEFAULT_SOUND)
    var sound: Sound by soundProperty
    @get:JsonIgnore
    val intervalProperty = SimpleIntegerProperty(DEFAULT_INTERVAL)
    var interval by intervalProperty
    @get:JsonIgnore
    val countProperty = SimpleIntegerProperty(DEFAULT_COUNT)
    var count by countProperty

    companion object {
        @JvmField
        val DEFAULT_MODE = ActionMode.AUDIO
        @JvmField
        val DEFAULT_SOUND = Sound.BEEP
        @JvmField
        val DEFAULT_COLOR: Color = Color.CYAN
        const val DEFAULT_INTERVAL = 500
        const val DEFAULT_COUNT = 6
    }
}