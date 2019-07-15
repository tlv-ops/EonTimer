package io.github.dylmeadows.eontimer.service.factory

import io.github.dylmeadows.eontimer.model.ApplicationModel
import io.github.dylmeadows.eontimer.model.timer.TimerType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class ApplicationTimerFactory @Autowired constructor(
    private val gen3TimerFactory: TimerFactory,
    private val gen4TimerFactory: TimerFactory,
    private val gen5TimerFactory: TimerFactory,
    private val customTimerFactory: TimerFactory,
    private val applicationModel: ApplicationModel) : TimerFactory {

    override val stages: List<Duration> get() = timerFactory.stages
    private val timerFactory: TimerFactory get() = applicationModel.selectedTimerType.timerFactory

    override fun calibrate() = timerFactory.calibrate()

    private val TimerType.timerFactory: TimerFactory
        get() = when (this) {
            TimerType.GEN3 -> gen3TimerFactory
            TimerType.GEN4 -> gen4TimerFactory
            TimerType.GEN5 -> gen5TimerFactory
            TimerType.CUSTOM -> customTimerFactory
        }
}