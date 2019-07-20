package io.github.dylmeadows.eontimer.config

import io.github.dylmeadows.eontimer.model.ApplicationModel
import io.github.dylmeadows.eontimer.model.settings.ActionSettings
import io.github.dylmeadows.eontimer.model.settings.TimerSettings
import io.github.dylmeadows.eontimer.model.timer.CustomTimer
import io.github.dylmeadows.eontimer.model.timer.Gen3Timer
import io.github.dylmeadows.eontimer.model.timer.Gen4Timer
import io.github.dylmeadows.eontimer.model.timer.Gen5Timer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.annotation.PreDestroy

@Configuration
open class AppConfig @Autowired constructor(
    private val properties: AppProperties,
    private val ctx: ApplicationContext) {

    @PreDestroy
    private fun destroy() {
        /*val gson = ctx.getBean(Gson::class.java)
        val settings = ctx.getBean(ApplicationModel::class.java)
        File("${properties.name}.json")
            .writeText(gson.toJson(settings))*/
    }

    @Bean
    open fun settings(): ApplicationModel {
        return ApplicationModel()
    }

    @Bean
    open fun gen3Timer(settings: ApplicationModel): Gen3Timer = settings.gen3

    @Bean
    open fun gen4Timer(settings: ApplicationModel): Gen4Timer = settings.gen4

    @Bean
    open fun gen5Timer(settings: ApplicationModel): Gen5Timer = settings.gen5

    @Bean
    open fun customTimer(settings: ApplicationModel): CustomTimer = settings.custom

    @Bean
    open fun actionSettings(settings: ApplicationModel): ActionSettings = settings.actionSettings

    @Bean
    open fun timerSettings(settings: ApplicationModel): TimerSettings = settings.timerSettings
}