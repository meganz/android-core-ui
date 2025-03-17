package mega.android.core.ui.components.text

import androidx.compose.ui.text.style.TextAlign
import mega.android.core.ui.model.SpanIndicator
import mega.android.core.ui.model.SpanStyleWithAnnotation

class ContentText internal constructor(
    val text: String,
    val textAlign: TextAlign,
    val spanStyles: Map<SpanIndicator, SpanStyleWithAnnotation>,
    val onClick: (String) -> Unit
)

object ContentTextDefaults {
    fun description(
        text: String,
        textAlign: TextAlign = TextAlign.Start,
        spanStyles: Map<SpanIndicator, SpanStyleWithAnnotation> = emptyMap(),
        onClick: (String) -> Unit = {}
    ) = ContentText(
        text = text,
        textAlign = textAlign,
        spanStyles = spanStyles,
        onClick = onClick
    )
}
