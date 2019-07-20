package io.github.dylmeadows.eontimer.controller.timer

import io.github.dylmeadows.eontimer.model.ApplicationModel
import io.github.dylmeadows.eontimer.model.timer.TimerType
import io.github.dylmeadows.eontimer.service.TimerRunnerService
import io.github.dylmeadows.eontimer.service.factory.ApplicationTimerFactory
import io.github.dylmeadows.reaktorfx.observer.asBinding
import io.github.dylmeadows.reaktorfx.scheduler.JavaFxScheduler
import io.github.dylmeadows.reaktorfx.source.valuesOf
import javafx.beans.binding.BooleanBinding
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.Tab
import javafx.scene.control.TabPane
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class TimerControlPane @Autowired constructor(
    private val model: ApplicationModel,
    private val timerRunnerService: TimerRunnerService,
    private val gen3TimerPane: Gen3TimerPane,
    private val gen4TimerPane: Gen4TimerPane,
    private val gen5TimerPane: Gen5TimerPane) {

    @FXML
    private lateinit var gen3Tab: Tab
    @FXML
    private lateinit var gen4Tab: Tab
    @FXML
    private lateinit var gen5Tab: Tab
    @FXML
    private lateinit var customTab: Tab
    @FXML
    private lateinit var timerTabPane: TabPane
    @FXML
    private lateinit var updateBtn: Button
    @FXML
    private lateinit var timerBtn: Button

    private var timerType: TimerType
        get() = model.selectedTimerType
        set(value) {
            model.selectedTimerType = value
        }

    fun initialize() {
        val runningProperty = BooleanBinding.booleanExpression(
            timerRunnerService.onStartStop
                .publishOn(JavaFxScheduler.platform)
                .asBinding())

        timerTabPane.selectionModel.select(timerType.tab)
        timerTabPane.selectionModel.selectedItemProperty().valuesOf()
            .map { it.timerType }
            .subscribe {
                timerType = it
            }

        gen3Tab.disableProperty().bind(
            timerTabPane.selectionModel.selectedItemProperty().isNotEqualTo(gen3Tab)
                .and(runningProperty))
        gen4Tab.disableProperty().bind(
            timerTabPane.selectionModel.selectedItemProperty().isNotEqualTo(gen4Tab)
                .and(runningProperty))
        gen5Tab.disableProperty().bind(
            timerTabPane.selectionModel.selectedItemProperty().isNotEqualTo(gen5Tab)
                .and(runningProperty))
        customTab.disableProperty().bind(
            timerTabPane.selectionModel.selectedItemProperty().isNotEqualTo(customTab)
                .and(runningProperty))

        runningProperty.valuesOf()
            .map { if (!it) "Start" else "Stop" }
            .subscribe { timerBtn.text = it }
        timerBtn.setOnAction {
            if (!timerRunnerService.isRunning) {
                timerRunnerService.start()
            } else {
                timerRunnerService.stop()
            }
        }

        updateBtn.disableProperty().bind(runningProperty)
        updateBtn.setOnAction {
            calibrate()
        }
    }

    private fun calibrate() {
        when (timerType) {
            TimerType.GEN3 -> gen3TimerPane.calibrate()
            TimerType.GEN4 -> gen4TimerPane.calibrate()
            TimerType.GEN5 -> gen5TimerPane.calibrate()
            else -> Unit
        }
    }

    private val Tab.timerType: TimerType
        get() {
            return when (this) {
                gen3Tab -> TimerType.GEN3
                gen4Tab -> TimerType.GEN4
                gen5Tab -> TimerType.GEN5
                customTab -> TimerType.CUSTOM
                else -> throw IllegalStateException("unable to find TimerType for selected tab")
            }
        }

    private val TimerType.tab: Tab
        get() {
            return when (this) {
                TimerType.GEN3 -> gen3Tab
                TimerType.GEN4 -> gen4Tab
                TimerType.GEN5 -> gen5Tab
                TimerType.CUSTOM -> customTab
            }
        }
}