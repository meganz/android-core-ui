package mega.android.core.ui.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import mega.android.core.ui.theme.linkColor
import mega.android.core.ui.theme.textColor
import mega.android.core.ui.tokens.theme.DSTokens
import mega.android.core.ui.theme.values.LinkColor
import mega.android.core.ui.theme.values.TextColor

sealed class MegaSpanStyle {
    abstract val spanStyle: SpanStyle

    @Composable
    abstract fun getSpanStyleColor(): Color?

    /**
     * Converts MegaSpanStyle to SpanStyle
     */
    @Composable
    fun toSpanStyle(): SpanStyle {
        return getSpanStyleColor()?.let { color ->
            spanStyle.copy(color = color)
        } ?: spanStyle
    }

    data class TextColorStyle(override val spanStyle: SpanStyle, val textColor: TextColor) : MegaSpanStyle() {
        @Composable
        override fun getSpanStyleColor(): Color {
            return DSTokens.textColor(textColor = textColor)
        }
    }

    data class LinkColorStyle(override val spanStyle: SpanStyle, val linkColor: LinkColor): MegaSpanStyle() {
        @Composable
        override fun getSpanStyleColor(): Color {
            return DSTokens.linkColor(linkColor = linkColor)
        }
    }

    data class DefaultColorStyle(override val spanStyle: SpanStyle) : MegaSpanStyle() {
        @Composable
        override fun getSpanStyleColor(): Color? {
            return null
        }
    }
}