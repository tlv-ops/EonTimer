package io.github.dylmeadows.eontimer.config.jackson

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import io.github.dylmeadows.commonkt.javafx.scene.paint.toHex
import javafx.scene.paint.Color

object ColorModule : TypeModule<Color> {
    override val serializer = object : JsonSerializer<Color>() {
        override fun serialize(value: Color, gen: JsonGenerator, serializers: SerializerProvider) {
            gen.writeString(value.toHex())
        }
    }

    override val deserializer = object : JsonDeserializer<Color>() {
        override fun deserialize(p: JsonParser, ctxt: DeserializationContext): Color {
            return Color.web(p.readValueAs(String::class.java))
        }
    }
}