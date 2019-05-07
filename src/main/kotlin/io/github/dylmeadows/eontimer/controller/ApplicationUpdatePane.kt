package io.github.dylmeadows.eontimer.controller

import io.github.dylmeadows.eontimer.model.GitHubRelease
import javafx.fxml.FXML
import javafx.scene.control.Hyperlink
import javafx.scene.control.Label
import org.springframework.stereotype.Component

@Component
class ApplicationUpdatePane {

    @FXML
    private lateinit var messageLbl: Label
    @FXML
    private lateinit var downloadLink: Hyperlink

    private companion object {
        const val VERSION_KEY = "%VERSION%"
        const val TEXT_TEMPLATE = "EonTimer $VERSION_KEY is now available and can be downloaded from:"
    }

    var release: GitHubRelease? = null
        set(value) {
            field = value
            messageLbl.text = TEXT_TEMPLATE.replace(VERSION_KEY, value!!.tagName!!)
            downloadLink.text = value.htmlUrl!!
        }
}