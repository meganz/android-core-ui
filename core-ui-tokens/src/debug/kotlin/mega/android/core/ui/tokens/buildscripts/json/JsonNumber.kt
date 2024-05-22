package mega.android.core.ui.tokens.buildscripts.json

/**
 * Number following json structure
 */
internal data class JsonNumber(
    override var name: String?,
    val value: Int,
) : JsonCoreUiObject