package mega.android.core.ui.components.text

import mega.android.core.ui.model.SpanIndicator
import mega.android.core.ui.model.SpanStyleWithAnnotation

data class SpannableText(
    val text: String?,
    val annotations: Map<SpanIndicator, SpanStyleWithAnnotation>? = null,
    val onAnnotationClick: ((annotation: String) -> Unit)? = null
)