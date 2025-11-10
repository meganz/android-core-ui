package mega.android.core.ui.components.sheets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import mega.android.core.ui.components.LinkSpannedText
import mega.android.core.ui.model.SpanIndicator
import mega.android.core.ui.model.SpanStyleWithAnnotation
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.values.TextColor
import mega.android.core.ui.tokens.theme.DSTokens

@Composable
fun SheetActionHeader(
    title: String?,
    modifier: Modifier = Modifier,
    leadingElement: (@Composable (RowScope.() -> Unit))? = null,
    subtitle: String? = null,
    titleAnnotations: Map<SpanIndicator, SpanStyleWithAnnotation>? = null,
    onTitleAnnotationClick: ((annotation: String) -> Unit)? = null,
    subtitleAnnotations: Map<SpanIndicator, SpanStyleWithAnnotation>? = null,
    onSubtitleAnnotationClick: ((annotation: String) -> Unit)? = null,
    onClickListener: (() -> Unit)? = null,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClickListener != null) {
                onClickListener?.invoke()
            }
            .padding(DSTokens.spacings.s5),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(DSTokens.spacings.s3),
    ) {
        if (leadingElement != null) {
            leadingElement()
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
        ) {
            if (!title.isNullOrBlank()) {
                LinkSpannedText(
                    value = title,
                    spanStyles = titleAnnotations ?: emptyMap(),
                    onAnnotationClick = onTitleAnnotationClick ?: {},
                    baseStyle = AppTheme.typography.titleSmall,
                    baseTextColor = TextColor.Primary,
                )
            }

            if (subtitle != null) {
                LinkSpannedText(
                    value = subtitle,
                    spanStyles = subtitleAnnotations ?: emptyMap(),
                    onAnnotationClick = onSubtitleAnnotationClick ?: {},
                    baseStyle = AppTheme.typography.bodyMedium,
                    baseTextColor = TextColor.Secondary,
                )
            }
        }
    }
}

@CombinedThemePreviews
@Composable
private fun SheetActionHeaderPreview() {
    AndroidThemeForPreviews {
        SheetActionHeader(
            title = "Title",
            subtitle = "Subtitle",
        )
    }
}