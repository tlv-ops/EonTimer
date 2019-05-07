package io.github.dylmeadows.eontimer

import io.github.dylmeadows.eontimer.config.AppProperties
import io.github.dylmeadows.eontimer.model.resource.CssResource
import io.github.dylmeadows.eontimer.model.resource.FxmlResource
import io.github.dylmeadows.eontimer.model.resource.SoundResource
import io.github.dylmeadows.eontimer.service.GitHubService
import io.github.dylmeadows.eontimer.util.Dimension
import io.github.dylmeadows.eontimer.util.addCss
import io.github.dylmeadows.eontimer.util.asScene
import io.github.dylmeadows.eontimer.util.load
import io.github.dylmeadows.eontimer.util.size
import io.github.dylmeadows.springboot.javafx.SpringJavaFxApplication
import javafx.application.Application.launch
import javafx.scene.Parent
import javafx.stage.Stage
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.web.reactive.function.client.WebClient
import java.io.BufferedInputStream
import javax.sound.sampled.AudioSystem

@SpringBootApplication
@ComponentScan(value = ["io.github.dylmeadows.*"])
open class AppLauncher : SpringJavaFxApplication() {

    private val log = LoggerFactory.getLogger(AppLauncher::class.java)

    override fun onInit() {
        arrayOf("os.name", "os.version", "os.arch", "java.version", "java.vendor", "sun.arch.data.model")
            .forEach { log.info("{} == {}", it, System.getProperty(it)) }
    }

    override fun start(stage: Stage) {
//        stage.title = getBean(AppProperties::class.java).fullApplicationName
//        stage.scene = load<Parent>(FxmlResource.EonTimerPane).asScene()
//        stage.size = Dimension(610.0, 470.0)
//        stage.scene.addCss(CssResource.MAIN)
//        stage.isResizable = false
//        stage.show()
        val service = getBean(GitHubService::class.java)
        val release = service.getLatestRelease().block()
        println(release.tagName)
    }
}

fun main(args: Array<String>) {
    launch(AppLauncher::class.java, *args)
}
