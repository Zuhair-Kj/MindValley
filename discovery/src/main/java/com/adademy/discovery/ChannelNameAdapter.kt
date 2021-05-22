package com.adademy.discovery

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.lang.reflect.Type

class ChannelNameAdapter: JsonDeserializer<String?> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): String? {
        if (json == null) return null
        try {
            if (json is JsonObject && json.has("title"))
                return json.get("title").asString
        } catch (e: Exception) {
            return null
        }
        return null
    }
}