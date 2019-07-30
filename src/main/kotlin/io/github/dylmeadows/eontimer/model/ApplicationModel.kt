package io.github.dylmeadows.eontimer.model

import com.fasterxml.jackson.annotation.JsonIgnore
import io.github.dylmeadows.commonkt.javafx.beans.property.getValue
import io.github.dylmeadows.commonkt.javafx.beans.property.setValue
import io.github.dylmeadows.eontimer.model.settings.ActionSettings
import io.github.dylmeadows.eontimer.model.settings.TimerSettings
import io.github.dylmeadows.eontimer.model.timer.CustomTimer
import io.github.dylmeadows.eontimer.model.timer.Gen3Timer
import io.github.dylmeadows.eontimer.model.timer.Gen4Timer
import io.github.dylmeadows.eontimer.model.timer.Gen5Timer
import io.github.dylmeadows.eontimer.model.timer.TimerConstants
import io.github.dylmeadows.eontimer.model.timer.TimerType
import javafx.beans.property.SimpleObjectProperty

class ApplicationModel {
    var gen3 = Gen3Timer()
    var gen4 = Gen4Timer()
    var gen5 = Gen5Timer()
    var custom = CustomTimer()
    var action = ActionSettings()
    var timer = TimerSettings()
    @get:JsonIgnore
    val selectedTimerTypeProperty = SimpleObjectProperty(TimerConstants.DEFAULT_TIMER_TYPE)
    var selectedTimerType: TimerType by selectedTimerTypeProperty
}