package mega.android.core.ui.tokens.buildscripts.deserializers

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import mega.android.core.ui.tokens.buildscripts.kotlingenerator.generic.jsonNameToKotlinName
import mega.android.core.ui.tokens.buildscripts.json.JsonTokenName
import java.lang.reflect.Type
import java.util.Locale

/**
 * Deserializer to convert a String to JsonTokenName
 */
internal class JsonTokenNameDeserializer : JsonDeserializer<JsonTokenName> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext,
    ): JsonTokenName {
        val parts = json.asString
            .removePrefix("{").removeSuffix("}")
            .split(".")
        val sanitized = parts
            .mapIndexed { index, string ->
                if (index == parts.lastIndex) {
                    string.jsonNameToKotlinName().replaceFirstChar { it.lowercase(Locale.ROOT) }
                } else {
                    string.jsonNameToKotlinName()
                }

            }
            .joinToString(".")
        return JsonTokenName(sanitized)
    }
}