package io.github.dylmeadows.eontimer

import io.github.dylmeadows.springboot.javafx.SpringFxApplication
import javafx.application.Application
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan("io.github.dylmeadows.*")
open class AppLauncher : SpringFxApplication()

fun main(args: Array<String>) {
    Application.launch(AppLauncher::class.java, *args)
}

