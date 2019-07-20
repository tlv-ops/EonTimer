package io.github.dylmeadows.eontimer.controller

import de.jensd.fx.glyphs.GlyphsDude
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import io.github.dylmeadows.eontimer.controller.settings.SettingsDialog
import io.github.dylmeadows.eontimer.service.TimerRunnerService
import io.github.dylmeadows.reaktorfx.observer.asBinding
import io.github.dylmeadows.reaktorfx.scheduler.JavaFxScheduler
import javafx.fxml.FXML
import javafx.scene.control.Button
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class EonTimerPane @Autowired constructor(
    private val settingsDialog: SettingsDialog,
    private val timerRunnerService: TimerRunnerService) {

    @FXML
    private lateinit var settingsBtn: Button

    fun initialize() {
        settingsBtn.graphic = GlyphsDude.createIcon(FontAwesomeIcon.GEAR)
        settingsBtn.disableProperty().bind(timerRunnerService.onStartStop
            .publishOn(JavaFxScheduler.platform)
            .asBinding())
        settingsBtn.setOnAction {
            settingsDialog.showAndWait()
        }
    }
}