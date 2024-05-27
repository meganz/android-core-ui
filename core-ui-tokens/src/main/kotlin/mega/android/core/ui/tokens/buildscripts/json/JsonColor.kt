package mega.android.core.ui.tokens.buildscripts.json

import androidx.compose.ui.graphics.Color
import mega.android.core.ui.tokens.buildscripts.kotlingenerator.generic.getPropertyName

/**
 * Color following json structure
 */
internal data class JsonColor(
    override var name: String?,
    val value: Color?,
) : JsonCoreUiObject, JsonLeaf {
    override fun getPropertyName(groupParentName: String?) =
        name.getPropertyName("Color", groupParentName)


    override fun getPropertyClass() = Color::class

    override fun getPropertyInitializer() =
        if (value == null) "Undefined" else {
            "Color(${value.red.toBase255()}, ${value.green.toBase255()}, ${value.blue.toBase255()}, ${value.alpha.toBase255()})"
        }
}

private fun Float.toBase255() = (this * 255f).toInt()
