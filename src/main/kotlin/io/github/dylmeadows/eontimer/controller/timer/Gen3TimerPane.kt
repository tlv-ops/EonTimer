package io.github.dylmeadows.eontimer.controller.timer

import io.github.dylmeadows.commonkt.core.time.milliseconds
import io.github.dylmeadows.commonkt.core.time.sum
import io.github.dylmeadows.commonkt.javafx.beans.property.bindBidirectional
import io.github.dylmeadows.commonkt.javafx.beans.property.getValue
import io.github.dylmeadows.commonkt.javafx.beans.property.setValue
import io.github.dylmeadows.commonkt.javafx.node.asChoiceField
import io.github.dylmeadows.commonkt.javafx.node.setOnFocusLost
import io.github.dylmeadows.commonkt.javafx.node.showWhen
import io.github.dylmeadows.commonkt.javafx.node.spinner.LongValueFactory
import io.github.dylmeadows.commonkt.javafx.node.spinner.commitValue
import io.github.dylmeadows.commonkt.javafx.node.spinner.text
import io.github.dylmeadows.commonkt.javafx.node.spinner.valueProperty
import io.github.dylmeadows.eontimer.model.TimerState
import io.github.dylmeadows.eontimer.model.timer.Gen3Timer
import io.github.dylmeadows.eontimer.service.CalibrationService
import io.github.dylmeadows.eontimer.service.TimerRunnerService
import io.github.dylmeadows.eontimer.service.factory.Gen3TimerFactory
import io.github.dylmeadows.reaktorfx.source.valuesOf
import javafx.beans.property.BooleanProperty
import javafx.beans.property.SimpleBooleanProperty
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.ChoiceBox
import javafx.scene.control.Spinner
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class Gen3TimerPane @Autowired constructor(
    private val model: Gen3Timer,
    private val timerState: TimerState,
    private val timerFactory: Gen3TimerFactory,
    private val timerRunnerService: TimerRunnerService,
    private val calibrationService: CalibrationService) {

    @FXML
    private lateinit var modeField: ChoiceBox<Gen3Timer.Mode>
    @FXML
    private lateinit var calibrationField: Spinner<Long>
    @FXML
    private lateinit var preTimerField: Spinner<Long>
    @FXML
    private lateinit var targetFrameField: Spinner<Long>
    @FXML
    private lateinit var setTargetFrameBtn: Button
    @FXML
    private lateinit var frameHitField: Spinner<Long>

    private val isPrimedProperty: BooleanProperty = SimpleBooleanProperty(true)
    private var isPrimed by isPrimedProperty

    fun initialize() {
        modeField.asChoiceField().valueProperty
            .bindBidirectional(model.modeProperty)
        modeField.parent.disableProperty().bind(timerState.runningProperty)

        calibrationField.valueFactory = LongValueFactory()
        calibrationField.valueProperty!!.bindBidirectional(model.calibrationProperty)
        calibrationField.parent.disableProperty().bind(timerState.runningProperty)
        calibrationField.setOnFocusLost(calibrationField::commitValue)

        preTimerField.valueFactory = LongValueFactory(0)
        preTimerField.valueProperty!!.bindBidirectional(model.preTimerProperty)
        preTimerField.parent.disableProperty().bind(timerState.runningProperty)
        preTimerField.setOnFocusLost(preTimerField::commitValue)

        targetFrameField.valueFactory = LongValueFactory(0)
        targetFrameField.valueProperty!!.bindBidirectional(model.targetFrameProperty)
        targetFrameField.parent.disableProperty().bind(
            model.modeProperty.isEqualTo(Gen3Timer.Mode.VARIABLE_TARGET)
                .and(timerState.runningProperty.not()
                    .or(isPrimedProperty.not()))
                .or(model.modeProperty.isEqualTo(Gen3Timer.Mode.STANDARD)
                    .and(timerState.runningProperty)))
        targetFrameField.setOnFocusLost(targetFrameField::commitValue)

        setTargetFrameBtn.showWhen(model.modeProperty
            .isEqualTo(Gen3Timer.Mode.VARIABLE_TARGET))
        setTargetFrameBtn.disableProperty().bind(isPrimedProperty.not())
        setTargetFrameBtn.setOnAction {
            if (timerState.running) {
                val duration = calibrationService.toMillis(model.targetFrame)
                timerRunnerService.stages[1] = (duration + model.calibration).milliseconds
                timerState.total.duration = timerRunnerService.stages.sum()
                isPrimed = false
            }
        }

        frameHitField.valueFactory = LongValueFactory(0)
        frameHitField.valueProperty!!.bindBidirectional(model.frameHitProperty)
        frameHitField.parent.disableProperty().bind(timerState.runningProperty)
        frameHitField.setOnFocusLost(frameHitField::commitValue)
        frameHitField.text = ""

        timerState.runningProperty.valuesOf()
            .subscribe { isPrimed = it }
    }

    fun calibrate() {
        timerFactory.calibrate()
        frameHitField.text = ""
    }
}