package mega.android.core.ui.components.common

import androidx.compose.ui.text.style.TextAlign
import mega.android.core.ui.model.SpanIndicator
import mega.android.core.ui.model.SpanStyleWithAnnotation

class PromotionalContentFooter(
    val text: String,
    val textAlign: TextAlign,
    val spanStyles: Map<SpanIndicator, SpanStyleWithAnnotation>,
    val onClick: (String) -> Unit
)

object PromotionalContentFooterDefaults {
    fun footer(
        text: String,
        textAlign: TextAlign = TextAlign.Start,
        spanStyles: Map<SpanIndicator, SpanStyleWithAnnotation> = emptyMap(),
        onClick: (String) -> Unit = {}
    ) = PromotionalContentFooter(
        text = text,
        textAlign = textAlign,
        spanStyles = spanStyles,
        onClick = onClick
    )
}
