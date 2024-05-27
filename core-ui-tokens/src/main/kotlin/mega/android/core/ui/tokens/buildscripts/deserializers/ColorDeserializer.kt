package mega.android.core.ui.tokens.buildscripts.deserializers

import androidx.compose.ui.graphics.Color
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.util.regex.Pattern

/**
 * Deserializer to convert a string of different formats to Color
 * Accepted formats:
 *   -  rgba(255, 255, 255, 1.0)
 *   -  #ffffff
 */
internal class ColorDeserializer : JsonDeserializer<Color> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext,
    ): Color? {
        val colorStr = json.asString
        val r: Int?
        val g: Int?
        val b: Int?
        val a: Int?

        val rgbMatcher = RGB_PATTERN.matcher(colorStr)
        val hexMatcher = HEX_PATTERN.matcher(colorStr)
        val hexAlphaMatcher = HEX_ALPHA_PATTERN.matcher(colorStr)
        when {
            rgbMatcher.matches() -> {
                r = rgbMatcher.group(1)?.toIntOrNull()
                g = rgbMatcher.group(2)?.toIntOrNull()
                b = rgbMatcher.group(3)?.toIntOrNull()
                a = rgbMatcher.group(4)?.toDoubleOrNull()?.times(255.0)?.toInt()
            }

            hexMatcher.matches() -> {
                val hexValue = hexMatcher.group(1)
                r = hexValue?.substring(0, 2)?.toInt(16)
                g = hexValue?.substring(2, 4)?.toInt(16)
                b = hexValue?.substring(4, 6)?.toInt(16)
                a = 255
            }

            hexAlphaMatcher.matches() -> {
                val hexValue = hexAlphaMatcher.group(1)
                r = hexValue?.substring(0, 2)?.toInt(16)
                g = hexValue?.substring(2, 4)?.toInt(16)
                b = hexValue?.substring(4, 6)?.toInt(16)
                a = hexValue?.substring(6, 8)?.toInt(16)
            }

            else -> {
                return null
            }
        }
        if (r != null && g != null && b != null && a != null) {
            return Color(r, g, b, a)
        }

        return null // unknown
    }

    companion object {
        private val RGB_PATTERN =
            Pattern.compile("rgba\\((\\d+),\\s*(\\d+),\\s*(\\d+),\\s*([\\d.]+)\\)")
        private val HEX_PATTERN = Pattern.compile("#([a-fA-F0-9]{6})")
        private val HEX_ALPHA_PATTERN = Pattern.compile("#([a-fA-F0-9]{8})")

    }
}