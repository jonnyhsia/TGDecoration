package com.tugou.decoration.model.base.typeadapter

import com.google.gson.*
import java.lang.reflect.Type

class BooleanSafeAdapter : JsonSerializer<Boolean>, JsonDeserializer<Boolean> {

    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Boolean {
        return try {
            json.asInt == 1
        } catch (e: Exception) {
            json.asBoolean
        }
    }

    override fun serialize(src: Boolean, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
        return JsonPrimitive(src)
    }
}