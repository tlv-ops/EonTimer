package io.github.dylmeadows.eontimer.util.jackson

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import io.github.dylmeadows.eontimer.model.TimerStage

class TimerStageDeserializer : StdDeserializer<TimerStage>(TimerStage::class.java) {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): TimerStage {
        return TimerStage.ZERO
    }
}