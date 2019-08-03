package io.github.dylmeadows.eontimer.config.jackson

import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.module.SimpleModule

interface TypeModule<T> {
    val serializer: JsonSerializer<T>
    val deserializer: JsonDeserializer<T>
}

inline fun <reified T> SimpleModule.addTypeModule(module: TypeModule<T>) {
    addSerializer(T::class.java, module.serializer)
    addDeserializer(T::class.java, module.deserializer)
}