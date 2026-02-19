package mega.android.core.ui.model

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextDecoration
import mega.android.core.ui.components.text.SpannableText
import mega.android.core.ui.theme.values.LinkColor

/**
 * Link text
 *
 * @param stringResourceIdentifier
 * @param onClick
 * @param url
 * @param spanIndicator
 * @param spanStyle
 * @return Spannable text with a clickable link
 */
@Composable
fun linkText(
    @StringRes stringResourceIdentifier: Int,
    onClick: (String) -> Unit,
    url: String,
    spanIndicator: SpanIndicator = SpanIndicator('A'),
    spanStyle: MegaSpanStyle = defaultLinkSpanStyle,
): SpannableText{
    return SpannableText(
        text = stringResource(id = stringResourceIdentifier),
        annotations = mapOf(
            spanIndicator to SpanStyleWithAnnotation(
                megaSpanStyle = spanStyle,
                annotation = url
            )
        ),
        onAnnotationClick = onClick
    )
}

/**
 * Default link span style
 * Primary link colour
 * Underline text decoration
 */
val defaultLinkSpanStyle = MegaSpanStyle.LinkColorStyle(
    SpanStyle(textDecoration = TextDecoration.Underline),
    LinkColor.Primary
)
