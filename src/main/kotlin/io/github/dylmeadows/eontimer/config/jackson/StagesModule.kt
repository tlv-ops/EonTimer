package io.github.dylmeadows.eontimer.config.jackson

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonToken
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import io.github.dylmeadows.eontimer.model.TimerStage
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import java.time.Duration

object StagesModule : TypeModule<ObservableList<TimerStage>> {
    override val serializer = object : JsonSerializer<ObservableList<TimerStage>>() {
        override fun serialize(value: ObservableList<TimerStage>, gen: JsonGenerator, serializers: SerializerProvider) {
            gen.writeStartArray()
            value.map(TimerStage::duration)
                .map(Duration::toMillis)
                .forEach(gen::writeNumber)
            gen.writeEndArray()
        }
    }

    override val deserializer = object : JsonDeserializer<ObservableList<TimerStage>>() {
        override fun deserialize(p: JsonParser, ctxt: DeserializationContext): ObservableList<TimerStage> {
            val list = ArrayList<Long>()
            if (p.isExpectedStartArrayToken) {
                var token = p.nextToken()
                while (token != JsonToken.END_ARRAY) {
                    list.add(p.longValue)
                    token = p.nextToken()
                }
            }
            return list.map { Duration.ofMillis(it) }
                .map { TimerStage(it) }
                .asObservableList()
        }
    }

    private fun <T> List<T>.asObservableList(): ObservableList<T> {
        return FXCollections.observableList(this)
    }
}