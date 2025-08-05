package mega.android.core.ui.tokens.buildscripts.json

import androidx.compose.ui.graphics.Color
import com.google.gson.annotations.SerializedName
import mega.android.core.ui.tokens.buildscripts.kotlingenerator.generic.getPropertyName

/**
 * Color that is referencing another color by its token name and can have an alpha value
 */
internal data class JsonColorRef(
    override var name: String?,
    @SerializedName("value")
    val tokenName: JsonTokenName?,
) : JsonCoreUiObject, SemanticValueRef {

    override fun getValueForDataClassInitializer(groupParentName: String?): String {
        val propertyName = getPropertyName(groupParentName)
        return if (tokenName?.hasAlpha == true) {
            "$propertyName = ${tokenName.value}.copy(alpha = ${tokenName.alpha}f)"
        }
        else {
            "$propertyName = ${tokenName?.value ?: "Unknown"}"
        }
    }

    override fun getPropertyName(groupParentName: String?) =
        name.getPropertyName("Color", groupParentName)

    override fun getPropertyClass() = Color::class

    override fun getPropertyInitializer() = "Color.Magenta"
}