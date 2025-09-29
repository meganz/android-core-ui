package mega.android.core.ui.model

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import kotlin.reflect.KProperty

/**
 * Represents a text model that can be resolved into a localized [String] at runtime.
 *
 * This abstraction allows the UI layer to consume either:
 * - [Literal] A literal string
 * - [StringRes] A string resource with optional formatting arguments
 * - [PluralsRes] A plural string resource with quantity and optional formatting
 *
 * It supports multiple access patterns to retrieve the localized text:
 * - .text for explicit resolution in a composable context
 * - Delegation via `val text by LocalizedText.Literal("...")`
 * - get(context: Context) for resolution outside of composables
 */
sealed interface LocalizedText {
    /**
     * Resolves the localized string in a composable context using [LocalContext].
     *
     * Can be used safely inside composables:
     * ```
     * Text(text = text.text)
     * ```
     */
    val text: String
        @Composable
        get() = resolveComposable()

    /**
     * Provides support for property delegation in a composable context using [LocalContext].
     *
     * This allows you to use it like:
     * ```
     * val text by LocalizedText.Literal("...")
     * ```
     */
    @Composable
    operator fun getValue(thisRef: Any?, property: KProperty<*>): String = resolveComposable()

    /**
     * Resolves the localized string in a non-composable context.
     *
     * Can be used outside of composables:
     * ```
     * val text = LocalizedText.Literal("...").get(context)
     * ```
     */
    fun get(context: Context): String = resolveInternal(context)

    /**
     * Resolves the localized string in a composable context.
     *
     * This is the internal implementation that uses [LocalContext] to resolve the text.
     */
    @Composable
    private fun resolveComposable(): String {
        val context = LocalContext.current
        return resolveInternal(context)
    }

    /**
     * Resolves this [LocalizedText] to a [String]
     */
    private fun resolveInternal(context: Context): String {
        return when (this) {
            is StringRes -> context.getString(resId, *formatArgs.toTypedArray())
            is PluralsRes -> context.resources.getQuantityString(
                resId,
                quantity,
                *formatArgs.toTypedArray()
            )

            is Literal -> value

            is Composite -> parts.joinToString(separator = "") { it.get(context) }
        }
    }

    operator fun plus(other: LocalizedText): LocalizedText = when {
        this is Composite && other is Composite -> Composite(this.parts + other.parts)
        this is Composite -> Composite(this.parts + other)
        other is Composite -> Composite(listOf(this) + other.parts)
        else -> Composite(listOf(this, other))
    }

    /**
     * Represents a string resource from `R.string` with optional formatting arguments.
     *
     * @param resId The string resource ID.
     * @param formatArgs Optional arguments for string formatting (e.g. `%1$s`).
     */
    data class StringRes(
        @androidx.annotation.StringRes val resId: Int,
        val formatArgs: List<Any> = emptyList(),
    ) : LocalizedText

    /**
     * Represents a plural string resource from `R.plurals` with a quantity and optional formatting arguments.
     *
     * @param resId The plurals resource ID.
     * @param quantity The quantity used to determine the correct plural form.
     * @param formatArgs Optional arguments to format the resolved plural string.
     */
    data class PluralsRes(
        @androidx.annotation.PluralsRes val resId: Int,
        val quantity: Int,
        val formatArgs: List<Any> = emptyList(),
    ) : LocalizedText

    /**
     * Represents a literal text.
     *
     * @param value the text.
     */
    data class Literal(val value: String) : LocalizedText

    /**
     * Represents a composite of multiple [LocalizedText] parts.
     *
     * This can be used to concatenate multiple localized texts together.
     *
     * @param parts The list of [LocalizedText] parts to concatenate.
     */
    data class Composite(val parts: List<LocalizedText>) : LocalizedText
}