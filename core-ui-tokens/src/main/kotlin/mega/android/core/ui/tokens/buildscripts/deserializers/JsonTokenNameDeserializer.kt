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
 * Handles both simple token references like "{Colors.Grey.500}" 
 * and rgba values with alpha like "rgba( {Colors.Grey.500}, 0.3)"
 */
internal class JsonTokenNameDeserializer : JsonDeserializer<JsonTokenName> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext,
    ): JsonTokenName {
        val value = json.asString
        
        // Check if it's an rgba value with alpha
        val rgbaPattern = """rgba\(\s*\{([^}]+)\}\s*,\s*([0-9.]+)\s*\)""".toRegex()
        val rgbaMatch = rgbaPattern.find(value)
        
        return if (rgbaMatch != null) {
            // Extract the token name and alpha value
            val tokenNameString = "{${rgbaMatch.groupValues[1]}}"
            val alphaString = rgbaMatch.groupValues[2]
            
            val baseTokenName = deserializeTokenName(tokenNameString)
            val alpha = alphaString.toFloatOrNull()
            
            JsonTokenName(baseTokenName, alpha)
        } else {
            // Simple token reference without alpha
            val baseTokenName = deserializeTokenName(value)
            JsonTokenName(baseTokenName, null)
        }
    }
    
    private fun deserializeTokenName(tokenNameString: String): String {
        val parts = tokenNameString
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
        return sanitized
    }
}