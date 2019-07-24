package io.github.dylmeadows.eontimer.controller.settings

import io.github.dylmeadows.commonkt.javafx.beans.property.bindBidirectional
import io.github.dylmeadows.commonkt.javafx.node.asChoiceField
import io.github.dylmeadows.commonkt.javafx.node.setOnFocusLost
import io.github.dylmeadows.commonkt.javafx.node.spinner.IntValueFactory
import io.github.dylmeadows.commonkt.javafx.node.spinner.valueProperty
import io.github.dylmeadows.eontimer.model.resource.Sound
import io.github.dylmeadows.eontimer.model.settings.ActionMode
import io.github.dylmeadows.eontimer.model.settings.ActionSettings
import javafx.fxml.FXML
import javafx.scene.control.ChoiceBox
import javafx.scene.control.ColorPicker
import javafx.scene.control.Spinner
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ActionSettingsPane @Autowired constructor(
    private val model: ActionSettings) {

    @FXML
    private lateinit var modeField: ChoiceBox<ActionMode>
    @FXML
    private lateinit var soundField: ChoiceBox<Sound>
    @FXML
    private lateinit var colorField: ColorPicker
    @FXML
    private lateinit var intervalField: Spinner<Int>
    @FXML
    private lateinit var countField: Spinner<Int>

    fun initialize() {
        modeField.asChoiceField().valueProperty.bindBidirectional(model.modeProperty)
        soundField.asChoiceField().valueProperty.bindBidirectional(model.soundProperty)
        colorField.valueProperty().bindBidirectional(model.colorProperty)

        intervalField.valueFactory = IntValueFactory(0, 1000)
        intervalField.valueProperty!!.bindBidirectional(model.intervalProperty)
        intervalField.setOnFocusLost(intervalField::commitValue)

        countField.valueFactory = IntValueFactory(0, 50)
        countField.valueProperty?.bindBidirectional(model.countProperty)
        countField.setOnFocusLost(countField::commitValue)
    }
}