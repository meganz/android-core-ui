package mega.android.core.ui.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import mega.android.core.ui.theme.values.TextColor

@Immutable
data class InputFieldLabelSpanStyle(
    val value: String,
    val spanStyles: Map<SpanIndicator, SpanStyleWithAnnotation>,
    val baseStyle: TextStyle,
    val baseTextColor: TextColor
)
