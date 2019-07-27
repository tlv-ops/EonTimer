package io.github.dylmeadows.eontimer.controller.timer

import io.github.dylmeadows.commonkt.javafx.beans.property.bindBidirectional
import io.github.dylmeadows.commonkt.javafx.node.asChoiceField
import io.github.dylmeadows.commonkt.javafx.node.setOnFocusLost
import io.github.dylmeadows.commonkt.javafx.node.spinner.LongValueFactory
import io.github.dylmeadows.commonkt.javafx.node.spinner.text
import io.github.dylmeadows.commonkt.javafx.node.spinner.valueProperty
import io.github.dylmeadows.eontimer.model.timer.Gen4Timer
import io.github.dylmeadows.eontimer.service.TimerRunnerService
import io.github.dylmeadows.eontimer.service.factory.Gen4TimerFactory
import io.github.dylmeadows.reaktorfx.observer.asBinding
import io.github.dylmeadows.reaktorfx.scheduler.JavaFxScheduler
import javafx.beans.binding.BooleanBinding
import javafx.fxml.FXML
import javafx.scene.control.ChoiceBox
import javafx.scene.control.Spinner
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class Gen4TimerPane @Autowired constructor(
    private val model: Gen4Timer,
    private val timerFactory: Gen4TimerFactory,
    private val timerRunnerService: TimerRunnerService
) {

    @FXML
    private lateinit var modeField: ChoiceBox<Gen4Timer.Mode>
    @FXML
    private lateinit var calibratedDelayField: Spinner<Long>
    @FXML
    private lateinit var calibratedSecondField: Spinner<Long>
    @FXML
    private lateinit var targetDelayField: Spinner<Long>
    @FXML
    private lateinit var targetSecondField: Spinner<Long>
    @FXML
    private lateinit var delayHitField: Spinner<Long>

    fun initialize() {
        val runningProperty = BooleanBinding.booleanExpression(
            timerRunnerService.onStartStop
                .publishOn(JavaFxScheduler.platform)
                .asBinding())

        modeField.asChoiceField().valueProperty
            .bindBidirectional(model.modeProperty)
        modeField.parent.disableProperty().bind(runningProperty)

        calibratedDelayField.valueFactory = LongValueFactory()
        calibratedDelayField.valueProperty!!.bindBidirectional(model.calibratedDelayProperty)
        calibratedDelayField.parent.disableProperty().bind(runningProperty)
        calibratedDelayField.setOnFocusLost(calibratedDelayField::commitValue)

        calibratedSecondField.valueFactory = LongValueFactory()
        calibratedSecondField.valueProperty!!.bindBidirectional(model.calibratedSecondProperty)
        calibratedSecondField.parent.disableProperty().bind(runningProperty)
        calibratedSecondField.setOnFocusLost(calibratedSecondField::commitValue)

        targetDelayField.valueFactory = LongValueFactory(0)
        targetDelayField.valueProperty!!.bindBidirectional(model.targetDelayProperty)
        targetDelayField.parent.disableProperty().bind(runningProperty)
        targetDelayField.setOnFocusLost(targetDelayField::commitValue)

        targetSecondField.valueFactory = LongValueFactory(0)
        targetSecondField.valueProperty!!.bindBidirectional(model.targetSecondProperty)
        targetSecondField.parent.disableProperty().bind(runningProperty)
        targetSecondField.setOnFocusLost(targetSecondField::commitValue)

        delayHitField.valueFactory = LongValueFactory(0)
        delayHitField.valueProperty!!.bindBidirectional(model.delayHitProperty)
        delayHitField.parent.disableProperty().bind(runningProperty)
        delayHitField.setOnFocusLost(delayHitField::commitValue)
        delayHitField.text = ""
    }

    fun calibrate() {
        timerFactory.calibrate()
        delayHitField.text = ""
    }
}