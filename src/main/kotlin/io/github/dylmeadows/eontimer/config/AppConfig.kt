package io.github.dylmeadows.eontimer.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import io.github.dylmeadows.eontimer.config.jackson.ColorModule
import io.github.dylmeadows.eontimer.config.jackson.StagesModule
import io.github.dylmeadows.eontimer.config.jackson.addTypeModule
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
import java.io.File
import javax.annotation.PreDestroy

@Configuration
open class AppConfig @Autowired constructor(
    private val properties: AppProperties,
    private val ctx: ApplicationContext
) {
    @PreDestroy
    private fun destroy() {
        val file = File("${properties.name}.json")
        val model = ctx.getBean(ApplicationModel::class.java)
        val objectMapper = ctx.getBean(ObjectMapper::class.java)
        file.writeText(objectMapper
            .writerWithDefaultPrettyPrinter()
            .writeValueAsString(model))
    }

    @Bean
    open fun objectMapper(): ObjectMapper {
        val module = SimpleModule()
        module.addTypeModule(ColorModule)
        module.addTypeModule(StagesModule)
        val objectMapper = ObjectMapper()
        objectMapper.registerModule(module)
        return objectMapper
    }

    @Bean
    open fun settings(objectMapper: ObjectMapper): ApplicationModel {
        val file = File("${properties.name}.json")
        return if (file.exists()) {
            objectMapper.readValue(file, ApplicationModel::class.java)
        } else {
            ApplicationModel()
        }
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
    open fun actionSettings(settings: ApplicationModel): ActionSettings = settings.action

    @Bean
    open fun timerSettings(settings: ApplicationModel): TimerSettings = settings.timer
}