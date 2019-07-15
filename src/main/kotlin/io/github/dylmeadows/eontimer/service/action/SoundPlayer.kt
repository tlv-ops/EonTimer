package io.github.dylmeadows.eontimer.service.action

import io.github.dylmeadows.commonkt.core.io.Resource
import io.github.dylmeadows.commonkt.core.io.url
import io.github.dylmeadows.eontimer.model.settings.ActionSettings
import io.github.dylmeadows.reaktorfx.source.valuesOf
import javafx.scene.media.Media
import javafx.scene.media.MediaPlayer
import javafx.util.Duration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.javafx.JavaFx
import kotlinx.coroutines.launch
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class SoundPlayer @Autowired constructor(
    private val actionSettings: ActionSettings) {

    private lateinit var mediaPlayer: MediaPlayer

    @PostConstruct
    private fun initialize() {
        actionSettings.soundProperty.valuesOf()
            .map(::createMediaPlayer)
            .subscribe { mediaPlayer = it }
        // NOTE: this buffers the MediaPlayer.
        // Without this buffering, the first time
        // audio is played, it is delayed.
        GlobalScope.launch(Dispatchers.JavaFx) {
            createMediaPlayer(object : Resource {
                override val path: String = "io/github/dylmeadows/eontimer/sounds/silence.wav"
            }).play()
        }
    }

    private fun createMediaPlayer(resource: Resource): MediaPlayer =
        MediaPlayer(Media(resource.url!!.toExternalForm()))

    fun play() {
        mediaPlayer.startTime = Duration.ZERO
        mediaPlayer.seek(Duration.ZERO)
        mediaPlayer.play()
    }

    fun stop() {
        mediaPlayer.stop()
    }
}