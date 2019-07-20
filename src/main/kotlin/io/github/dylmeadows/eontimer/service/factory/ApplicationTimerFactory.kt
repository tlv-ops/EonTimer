package io.github.dylmeadows.eontimer.service.factory

import io.github.dylmeadows.eontimer.model.ApplicationModel
import io.github.dylmeadows.eontimer.model.settings.TimerSettings
import io.github.dylmeadows.eontimer.model.timer.TimerType
import io.github.dylmeadows.eontimer.service.TimerRunnerService
import io.github.dylmeadows.reaktorfx.source.valuesOf
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.Duration
import javax.annotation.PostConstruct

@Component
class ApplicationTimerFactory @Autowired constructor(
    private val timerSettings: TimerSettings,
    private val gen3TimerFactory: TimerFactory,
    private val gen4TimerFactory: TimerFactory,
    private val gen5TimerFactory: TimerFactory,
    private val customTimerFactory: TimerFactory,
    private val applicationModel: ApplicationModel,
    private val timerRunnerService: TimerRunnerService) : TimerFactory {

    override val stages get() = timerFactory.stages
    private val timerFactory get() = applicationModel.selectedTimerType.timerFactory

    @PostConstruct
    private fun initialize() {
        applicationModel.selectedTimerTypeProperty.valuesOf()
            .map { it.timerFactory }
            .map(TimerFactory::stages)
            .subscribe {
                timerRunnerService.stages = it
            }
        listOf(
            timerSettings.consoleProperty,
            timerSettings.precisionCalibrationModeProperty)
            .map { it.valuesOf() }
            .forEach {
                it.subscribe {
                    timerRunnerService.stages = stages
                }
            }
    }

    override fun calibrate() = timerFactory.calibrate()

    private val TimerType.timerFactory: TimerFactory
        get() = when (this) {
            TimerType.GEN3 -> gen3TimerFactory
            TimerType.GEN4 -> gen4TimerFactory
            TimerType.GEN5 -> gen5TimerFactory
            TimerType.CUSTOM -> customTimerFactory
        }
}