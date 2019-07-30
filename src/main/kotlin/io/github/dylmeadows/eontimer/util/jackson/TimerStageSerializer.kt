package io.github.dylmeadows.eontimer.util.jackson

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import io.github.dylmeadows.eontimer.model.TimerStage

class TimerStageSerializer : StdSerializer<TimerStage>(TimerStage::class.java) {
    override fun serialize(value: TimerStage, gen: JsonGenerator, provider: SerializerProvider) {
        gen.writeNumber(value.duration.toMillis())
    }
}