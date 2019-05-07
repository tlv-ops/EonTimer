package io.github.dylmeadows.eontimer.controller

import io.github.dylmeadows.eontimer.config.AppProperties
import io.github.dylmeadows.eontimer.model.resource.FxmlResource
import io.github.dylmeadows.eontimer.service.GitHubService
import io.github.dylmeadows.springboot.javafx.fxml.SpringFxmlLoader
import javafx.scene.Parent
import javafx.scene.control.ButtonType
import javafx.scene.control.Dialog
import javafx.stage.Modality
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ApplicationUpdateDialog @Autowired constructor(
    private val controller: ApplicationUpdatePane,
    private val gitHubService: GitHubService,
    private val properties: AppProperties,
    loader: SpringFxmlLoader) {

    private val view = loader.load<Parent>(FxmlResource.AvailableUpdatePane.asStream)
    private val logger = LoggerFactory.getLogger(ApplicationUpdateDialog::class.java)

    fun checkForUpdate() {
        logger.info("Checking for update...")
        gitHubService.getLatestRelease()
            .subscribe {
                logger.info("Latest release is {}", it.tagName)
                controller.release = it
                val dialog = Dialog<Void>()
                dialog.title = properties.name
                dialog.dialogPane.content = view
                dialog.dialogPane.buttonTypes.setAll(ButtonType.OK)
                dialog.initModality(Modality.APPLICATION_MODAL)
                dialog.show()
            }
    }
}