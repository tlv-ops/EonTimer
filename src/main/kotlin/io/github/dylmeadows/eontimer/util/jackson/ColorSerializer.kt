package io.github.dylmeadows.eontimer.util.jackson

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import io.github.dylmeadows.commonkt.javafx.scene.paint.toHex
import javafx.scene.paint.Color

class ColorSerializer : StdSerializer<Color>(Color::class.java) {
    override fun serialize(value: Color, gen: JsonGenerator, provider: SerializerProvider) {
        gen.writeString(value.toHex())
    }
}