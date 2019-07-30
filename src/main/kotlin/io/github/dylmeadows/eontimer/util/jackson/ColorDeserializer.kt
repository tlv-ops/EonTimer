package io.github.dylmeadows.eontimer.util.jackson

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import javafx.scene.paint.Color

class ColorDeserializer : StdDeserializer<Color>(Color::class.java) {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): Color {
        val value = p.readValueAs(String::class.java)
        println(value)
        return Color.ALICEBLUE
    }
}