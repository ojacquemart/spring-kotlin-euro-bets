package org.ojacquemart.eurobets.util

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.util.Assert
import java.io.IOException

class JsonFileLoader<T> {

    fun load(fileName: String, clazz: Class<T>): T {
        try {
            val resource = javaClass.classLoader.getResourceAsStream(fileName)
            Assert.notNull(resource)

            val objectMapper = ObjectMapper().registerModule(KotlinModule())

            return objectMapper.readValue(resource, clazz)
        } catch (e: IOException) {
            throw FileReadException(e)
        }
    }
}