package mega.android.core.ui.tokens.buildscripts.json

/**
 * Token name that can include an alpha value
 */
internal data class JsonTokenName(
    val value: String,
    val alpha: Float? = null,
) {
    val hasAlpha: Boolean = alpha != null
}