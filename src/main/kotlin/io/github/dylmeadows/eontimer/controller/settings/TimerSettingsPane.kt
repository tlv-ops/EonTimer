package io.github.dylmeadows.eontimer.controller.settings

import io.github.dylmeadows.commonkt.javafx.beans.property.bindBidirectional
import io.github.dylmeadows.commonkt.javafx.node.asChoiceField
import io.github.dylmeadows.commonkt.javafx.node.setOnFocusLost
import io.github.dylmeadows.commonkt.javafx.node.spinner.LongValueFactory
import io.github.dylmeadows.commonkt.javafx.node.spinner.valueProperty
import io.github.dylmeadows.eontimer.model.settings.Console
import io.github.dylmeadows.eontimer.model.settings.TimerSettings
import javafx.fxml.FXML
import javafx.scene.control.CheckBox
import javafx.scene.control.ChoiceBox
import javafx.scene.control.Spinner
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class TimerSettingsPane @Autowired constructor(
    private val model: TimerSettings
) {

    @FXML
    private lateinit var consoleField: ChoiceBox<Console>
    @FXML
    private lateinit var refreshIntervalField: Spinner<Long>
    @FXML
    private lateinit var precisionCalibrationField: CheckBox

    fun initialize() {
        consoleField.asChoiceField().valueProperty.bindBidirectional(model.consoleProperty)

        refreshIntervalField.valueFactory = LongValueFactory(0L, 1000L)
        refreshIntervalField.valueProperty!!.bindBidirectional(model.refreshIntervalProperty)
        refreshIntervalField.setOnFocusLost(refreshIntervalField::commitValue)

        precisionCalibrationField.selectedProperty().bindBidirectional(model.precisionCalibrationModeProperty)
    }
}