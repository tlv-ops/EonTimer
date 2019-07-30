package io.github.dylmeadows.eontimer.controller.timer

import io.github.dylmeadows.commonkt.javafx.beans.property.bindBidirectional
import io.github.dylmeadows.commonkt.javafx.node.asChoiceField
import io.github.dylmeadows.commonkt.javafx.node.setOnFocusLost
import io.github.dylmeadows.commonkt.javafx.node.showWhen
import io.github.dylmeadows.commonkt.javafx.node.spinner.LongValueFactory
import io.github.dylmeadows.commonkt.javafx.node.spinner.text
import io.github.dylmeadows.commonkt.javafx.node.spinner.valueProperty
import io.github.dylmeadows.eontimer.model.timer.Gen5Timer
import io.github.dylmeadows.eontimer.service.TimerRunnerService
import io.github.dylmeadows.eontimer.service.factory.Gen5TimerFactory
import io.github.dylmeadows.reaktorfx.observer.asBinding
import io.github.dylmeadows.reaktorfx.scheduler.JavaFxScheduler
import javafx.beans.binding.BooleanBinding
import javafx.fxml.FXML
import javafx.scene.control.ChoiceBox
import javafx.scene.control.Spinner
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class Gen5TimerPane @Autowired constructor(
    private val model: Gen5Timer,
    private val timerFactory: Gen5TimerFactory,
    private val timerRunnerService: TimerRunnerService
) {

    @FXML
    private lateinit var modeField: ChoiceBox<Gen5Timer.Mode>
    @FXML
    private lateinit var calibrationField: Spinner<Long>
    @FXML
    private lateinit var frameCalibrationField: Spinner<Long>
    @FXML
    private lateinit var entralinkCalibrationField: Spinner<Long>
    @FXML
    private lateinit var targetDelayField: Spinner<Long>
    @FXML
    private lateinit var targetSecondField: Spinner<Long>
    @FXML
    private lateinit var targetAdvancesField: Spinner<Long>
    @FXML
    private lateinit var secondHitField: Spinner<Long>
    @FXML
    private lateinit var delayHitField: Spinner<Long>
    @FXML
    private lateinit var actualAdvancesField: Spinner<Long>

    fun initialize() {
        val runningProperty = BooleanBinding.booleanExpression(
            timerRunnerService.onStartStop
                .publishOn(JavaFxScheduler.platform)
                .asBinding())

        modeField.asChoiceField().valueProperty
            .bindBidirectional(model.modeProperty)
        modeField.parent.disableProperty().bind(runningProperty)

        calibrationField.valueFactory = LongValueFactory()
        calibrationField.valueProperty!!.bindBidirectional(model.calibrationProperty)
        calibrationField.parent.disableProperty().bind(runningProperty)
        calibrationField.setOnFocusLost(calibrationField::commitValue)

        frameCalibrationField.valueFactory = LongValueFactory()
        frameCalibrationField.valueProperty!!.bindBidirectional(model.frameCalibrationProperty)
        frameCalibrationField.parent.disableProperty().bind(runningProperty)
        frameCalibrationField.setOnFocusLost(frameCalibrationField::commitValue)

        entralinkCalibrationField.valueFactory = LongValueFactory()
        entralinkCalibrationField.valueProperty!!.bindBidirectional(model.entralinkCalibrationProperty)
        entralinkCalibrationField.parent.disableProperty().bind(runningProperty)
        entralinkCalibrationField.setOnFocusLost(entralinkCalibrationField::commitValue)

        targetDelayField.valueFactory = LongValueFactory(0)
        targetDelayField.valueProperty!!.bindBidirectional(model.targetDelayProperty)
        targetDelayField.parent.disableProperty().bind(runningProperty)
        targetDelayField.parent.showWhen(model.modeProperty.isEqualTo(Gen5Timer.Mode.C_GEAR))
        targetDelayField.setOnFocusLost(targetDelayField::commitValue)

        targetSecondField.valueFactory = LongValueFactory(0)
        targetSecondField.valueProperty!!.bindBidirectional(model.targetSecondProperty)
        targetSecondField.parent.disableProperty().bind(runningProperty)
        targetSecondField.setOnFocusLost(targetSecondField::commitValue)

        targetAdvancesField.valueFactory = LongValueFactory(0)
        targetAdvancesField.valueProperty!!.bindBidirectional(model.targetAdvancesProperty)
        targetAdvancesField.parent.disableProperty().bind(runningProperty)
        targetAdvancesField.setOnFocusLost(targetAdvancesField::commitValue)

        secondHitField.valueFactory = LongValueFactory(0)
        secondHitField.valueProperty!!.bindBidirectional(model.secondHitProperty)
        secondHitField.parent.disableProperty().bind(runningProperty)
        secondHitField.parent.showWhen(model.modeProperty.isEqualTo(Gen5Timer.Mode.STANDARD))
        secondHitField.setOnFocusLost(secondHitField::commitValue)
        secondHitField.text = ""

        delayHitField.valueFactory = LongValueFactory(0)
        delayHitField.valueProperty!!.bindBidirectional(model.delayHitProperty)
        delayHitField.parent.disableProperty().bind(runningProperty)
        delayHitField.parent.showWhen(
            model.modeProperty.isEqualTo(Gen5Timer.Mode.C_GEAR))
        delayHitField.setOnFocusLost(delayHitField::commitValue)
        delayHitField.text = ""

        actualAdvancesField.valueFactory = LongValueFactory(0)
        actualAdvancesField.valueProperty!!.bindBidirectional(model.actualAdvancesProperty)
        actualAdvancesField.parent.disableProperty().bind(runningProperty)
        actualAdvancesField.setOnFocusLost(actualAdvancesField::commitValue)
        actualAdvancesField.text = ""
    }

    fun calibrate() {
        timerFactory.calibrate()
        secondHitField.text = ""
        delayHitField.text = ""
        actualAdvancesField.text = ""
    }
}