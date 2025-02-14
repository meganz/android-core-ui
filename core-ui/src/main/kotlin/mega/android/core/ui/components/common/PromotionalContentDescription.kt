package mega.android.core.ui.components.common

import androidx.compose.ui.text.style.TextAlign
import mega.android.core.ui.model.SpanIndicator
import mega.android.core.ui.model.SpanStyleWithAnnotation

class PromotionalContentDescription(
    val text: String,
    val textAlign: TextAlign,
    val spanStyles: Map<SpanIndicator, SpanStyleWithAnnotation>,
    val onClick: (String) -> Unit
)

object PromotionalContentDescriptionDefaults {
    fun description(
        text: String,
        textAlign: TextAlign = TextAlign.Start,
        spanStyles: Map<SpanIndicator, SpanStyleWithAnnotation> = emptyMap(),
        onClick: (String) -> Unit = {}
    ) = PromotionalContentDescription(
        text = text,
        textAlign = textAlign,
        spanStyles = spanStyles,
        onClick = onClick
    )
}
