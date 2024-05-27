package mega.android.core.ui.tokens.buildscripts.json

/**
 * represents an unknown core ui json element
 */
internal data class JsonCoreUiObjectUnknown(
    override var name: String? = null,
) : JsonCoreUiObject {
    override fun toString() = "unknown"
}