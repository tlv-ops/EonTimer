package io.github.dylmeadows.eontimer.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties("application")
open class AppProperties {
    lateinit var name: String
    lateinit var version: String
}