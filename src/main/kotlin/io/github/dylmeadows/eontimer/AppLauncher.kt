package io.github.dylmeadows.eontimer

import io.github.dylmeadows.commonkt.javafx.scene.addCss
import io.github.dylmeadows.commonkt.javafx.scene.asScene
import io.github.dylmeadows.commonkt.javafx.stage.size
import io.github.dylmeadows.commonkt.javafx.util.Dimension
import io.github.dylmeadows.eontimer.config.AppProperties
import io.github.dylmeadows.eontimer.model.resource.CssResource
import io.github.dylmeadows.eontimer.model.resource.FxmlResource
import io.github.dylmeadows.eontimer.util.extensions.load
import io.github.dylmeadows.springboot.javafx.ApplicationEntryPoint
import io.github.dylmeadows.springboot.javafx.SpringFxApplication
import io.github.dylmeadows.springboot.javafx.fxml.SpringFxmlLoader
import javafx.scene.Parent
import javafx.stage.Stage
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component

@Component
@Configuration
@ComponentScan("io.github.dylmeadows.*")
open class AppLauncher @Autowired constructor(
    private val fxmlLoader: SpringFxmlLoader,
    private val properties: AppProperties
) : ApplicationEntryPoint {

    private val log = LoggerFactory.getLogger(AppLauncher::class.java)

    override fun init() {
        arrayOf("os.name", "os.version", "os.arch", "java.version", "java.vendor", "sun.arch.data.model")
            .forEach { log.info("{} == {}", it, System.getProperty(it)) }
    }

    override fun start(stage: Stage) {
        stage.title = properties.fullApplicationName
        stage.scene = fxmlLoader.load<Parent>(FxmlResource.EonTimerPane).asScene()
        stage.size = Dimension(610.0, 470.0)
        stage.scene.addCss(CssResource.MAIN)
        stage.isResizable = false
        stage.show()
    }

    override fun stop() {
    }
}

fun main(args: Array<String>) {
    SpringFxApplication.launch(AppLauncher::class, *args)
}
