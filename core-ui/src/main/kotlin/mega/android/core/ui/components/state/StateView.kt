package mega.android.core.ui.components.state

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import mega.android.core.ui.components.LinkSpannedText
import mega.android.core.ui.components.MegaText
import mega.android.core.ui.components.button.PrimaryFilledButton
import mega.android.core.ui.model.MegaSpanStyle
import mega.android.core.ui.model.SpanIndicator
import mega.android.core.ui.model.SpanStyleWithAnnotation
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.theme.values.LinkColor
import mega.android.core.ui.theme.values.TextColor

@Composable
fun EmptyStateView(
    modifier: Modifier = Modifier,
    @DrawableRes illustration: Int? = null,
    title: String,
    description: String? = null,
    descriptionSpanStyles: Map<SpanIndicator, SpanStyleWithAnnotation> = emptyMap(),
    buttonText: String? = null,
    onDescriptionAnnotationClick: (String) -> Unit = {},
    onButtonClick: () -> Unit = {}
) {
    Column(
        modifier = modifier.padding(LocalSpacing.current.x16),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (illustration != null) {
            Image(
                modifier = Modifier.size(120.dp),
                painter = painterResource(id = illustration),
                contentDescription = "Empty icon"
            )
        }

        MegaText(
            modifier = Modifier.padding(top = LocalSpacing.current.x24),
            text = title,
            textColor = TextColor.Primary,
            style = AppTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )

        if (description != null) {
            LinkSpannedText(
                modifier = Modifier.padding(top = LocalSpacing.current.x16),
                value = description,
                spanStyles = descriptionSpanStyles,
                baseTextColor = TextColor.Secondary,
                baseStyle = AppTheme.typography.bodyLarge.copy(
                    textAlign = TextAlign.Center
                ),
                onAnnotationClick = onDescriptionAnnotationClick
            )
        }

        if (buttonText != null) {
            PrimaryFilledButton(
                modifier = Modifier
                    .padding(top = LocalSpacing.current.x24)
                    .wrapContentSize()
                    .padding(horizontal = LocalSpacing.current.x24),
                text = buttonText,
                onClick = onButtonClick
            )
        }
    }
}

@CombinedThemePreviews
@Composable
private fun EmptyStatePreview() {
    AndroidThemeForPreviews {
        EmptyStateView(
            illustration = android.R.drawable.ic_delete,
            title = " A short and concise explanation. If possible in positive way",
            description = "Explain clearly the next action to populate the space. You may also explain why the space is empty and include the benefit of taking this step. Please contact [A]support.email.com[/A]",
            descriptionSpanStyles = mapOf(
                SpanIndicator('A') to SpanStyleWithAnnotation(
                    megaSpanStyle = MegaSpanStyle.LinkColorStyle(
                        spanStyle = SpanStyle(),
                        linkColor = LinkColor.Primary
                    ),
                    annotation = "support.email.com"
                        .substringAfter("[A]")
                        .substringBefore("[/A]")
                )
            ),
            buttonText = "Primary Button",
            onButtonClick = { }
        )
    }
}