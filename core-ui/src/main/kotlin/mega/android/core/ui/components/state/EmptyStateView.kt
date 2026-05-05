package mega.android.core.ui.components.state

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import mega.android.core.ui.components.LinkSpannedText
import mega.android.core.ui.components.MegaText
import mega.android.core.ui.components.SpannedText
import mega.android.core.ui.components.button.PrimaryFilledButton
import mega.android.core.ui.components.button.SecondaryFilledButton
import mega.android.core.ui.components.text.SpannableText
import mega.android.core.ui.model.MegaSpanStyle
import mega.android.core.ui.model.SpanIndicator
import mega.android.core.ui.model.SpanStyleWithAnnotation
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.theme.values.LinkColor
import mega.android.core.ui.theme.values.TextColor
import mega.android.core.ui.tokens.theme.DSTokens

@Deprecated("Use EmptyStateView instead")
@Composable
fun EmptyStateView(
    modifier: Modifier = Modifier,
    @DrawableRes illustration: Int? = null,
    title: String? = null,
    description: String? = null,
    descriptionSpanStyles: Map<SpanIndicator, SpanStyleWithAnnotation> = emptyMap(),
    buttonText: String? = null,
    onDescriptionAnnotationClick: (String) -> Unit = {},
    onButtonClick: () -> Unit = {}
) {
    EmptyStateView(
        modifier = modifier,
        illustration = illustration,
        title = title,
        description = SpannableText(
            text = description,
            annotations = descriptionSpanStyles,
            onAnnotationClick = onDescriptionAnnotationClick
        ),
        actions = {
            buttonText?.let {
                PrimaryFilledButton(
                    modifier = Modifier
                        .wrapContentSize(),
                    text = it,
                    onClick = onButtonClick
                )
            }
        }
    )
}

@Deprecated("Use EmptyStateView instead")
@Composable
fun EmptyStateView(
    modifier: Modifier = Modifier,
    @DrawableRes illustration: Int? = null,
    title: String? = null,
    description: SpannableText? = null,
    actions: (@Composable ColumnScope.() -> Unit)? = null
) {
    Column(
        modifier = modifier.padding(LocalSpacing.current.x16),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        illustration?.let {
            Image(
                modifier = Modifier
                    .size(120.dp)
                    .clip(shape = DSTokens.shapes.medium),
                painter = painterResource(id = illustration),
                contentDescription = "Empty icon"
            )
        }

        title?.let {
            val titleModifier = if (illustration != null) {
                Modifier.padding(top = LocalSpacing.current.x24)
            } else {
                Modifier
            }
            MegaText(
                modifier = titleModifier,
                text = title,
                textColor = TextColor.Primary,
                style = AppTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )
        }

        description?.let {
            val descriptionModifier = if (title != null || illustration != null) {
                Modifier.padding(top = LocalSpacing.current.x16)
            } else {
                Modifier
            }
            LinkSpannedText(
                modifier = descriptionModifier,
                value = it.text.orEmpty(),
                spanStyles = it.annotations ?: emptyMap(),
                baseTextColor = TextColor.Secondary,
                baseStyle = AppTheme.typography.bodyLarge.copy(
                    textAlign = TextAlign.Center
                ),
                onAnnotationClick = it.onAnnotationClick ?: {}
            )
        }

        actions?.let {
            val buttonModifier = if (description != null || illustration != null || title != null) {
                Modifier.padding(top = LocalSpacing.current.x24)
            } else {
                Modifier
            }

            Column(
                modifier = buttonModifier
                    .padding(horizontal = LocalSpacing.current.x24),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                it()
            }
        }
    }
}

/**
 * Empty state view with an illustration, title, optional description and up to two actions.
 *
 * @param primaryAction Slot for the primary action. Must be a FilledButton variant
 *   (e.g. [PrimaryFilledButton], [SecondaryFilledButton] or any other FilledButton).
 *   This is a convention — not enforced by the type system.
 * @param secondaryAction Slot for the secondary action. Same constraint as [primaryAction].
 */
@Composable
fun EmptyStateView(
    imagePainter: Painter,
    title: String,
    modifier: Modifier = Modifier,
    description: SpannableText? = null,
    primaryAction: (@Composable () -> Unit)? = null,
    secondaryAction: (@Composable () -> Unit)? = null,
) {
    val emptySpanStyle = MegaSpanStyle.DefaultColorStyle(SpanStyle())

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(LocalSpacing.current.x16),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = imagePainter,
            contentDescription = "Empty Image",
            modifier = Modifier.size(120.dp)
        )
        Spacer(modifier = Modifier.height(LocalSpacing.current.x24))
        SpannedText(
            value = title,
            spanStyles = mapOf(
                SpanIndicator('A') to emptySpanStyle,
                SpanIndicator('B') to emptySpanStyle
            ),
            baseStyle = AppTheme.typography.titleLarge.copy(
                textAlign = TextAlign.Center
            ),
            baseTextColor = TextColor.Primary,
        )
        description?.let {
            Spacer(modifier = Modifier.height(LocalSpacing.current.x16))
            LinkSpannedText(
                value = it.text.orEmpty(),
                spanStyles = it.annotations ?: emptyMap(),
                baseTextColor = TextColor.Secondary,
                baseStyle = AppTheme.typography.bodyLarge.copy(
                    textAlign = TextAlign.Center
                ),
                onAnnotationClick = it.onAnnotationClick ?: {}
            )
        }
        primaryAction?.let {
            Spacer(modifier = Modifier.height(LocalSpacing.current.x24))
            primaryAction()
        }
        secondaryAction?.let {
            Spacer(
                modifier = Modifier.height(
                    if (primaryAction == null) LocalSpacing.current.x24
                    else LocalSpacing.current.x16
                )
            )
            secondaryAction()
        }
    }
}

@CombinedThemePreviews
@Composable
private fun EmptyStatePreview() {
    AndroidThemeForPreviews {
        EmptyStateView(
            modifier = Modifier.fillMaxSize(),
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

@CombinedThemePreviews
@Composable
private fun EmptyStatePreview2() {
    AndroidThemeForPreviews {
        EmptyStateView(
            modifier = Modifier.fillMaxSize(),
            illustration = android.R.drawable.ic_delete,
            title = " A short and concise explanation. If possible in positive way",
            description = SpannableText(
                text = "Explain clearly the next action to populate the space. You may also explain why the space is empty and include the benefit of taking this step. Please contact [A]support.email.com[/A]",
                annotations = mapOf(
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
                onAnnotationClick = {}
            ),
            actions = {
                PrimaryFilledButton(
                    modifier = Modifier,
                    text = "Primary Button",
                    onClick = { }
                )
                SecondaryFilledButton(
                    modifier = Modifier.padding(top = 8.dp),
                    text = "Secondary Button",
                    onClick = { }
                )
            }
        )
    }
}

@CombinedThemePreviews
@Composable
private fun EmptyStateViewPreview() {
    AndroidThemeForPreviews {
        EmptyStateView(
            imagePainter = painterResource(android.R.drawable.ic_delete),
            title = "Title",
        )
    }
}

@CombinedThemePreviews
@Composable
private fun EmptyStateViewWithSimpleDescriptionPreview() {
    AndroidThemeForPreviews {
        EmptyStateView(
            imagePainter = painterResource(android.R.drawable.ic_delete),
            title = "Title",
            description = SpannableText(text = "Brief explanation goes here"),
        )
    }
}

@CombinedThemePreviews
@Composable
private fun EmptyStateViewWithAnnotationDescriptionPreview() {
    AndroidThemeForPreviews {
        EmptyStateView(
            imagePainter = painterResource(android.R.drawable.ic_delete),
            title = "Title",
            description = SpannableText(
                text = "Brief explanation goes here. Please contact [A]support.email.com[/A]",
                annotations = mapOf(
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
            ),
        )
    }
}

@CombinedThemePreviews
@Composable
private fun EmptyStateViewWithPrimaryButtonPreview() {
    AndroidThemeForPreviews {
        EmptyStateView(
            imagePainter = painterResource(android.R.drawable.ic_delete),
            title = "Title",
            description = SpannableText(text = "Brief explanation goes here"),
            primaryAction = {
                PrimaryFilledButton(
                    modifier = Modifier,
                    text = "Button",
                    onClick = { }
                )
            }
        )
    }
}

@CombinedThemePreviews
@Composable
private fun EmptyStateViewWithSecondaryButtonPreview() {
    AndroidThemeForPreviews {
        EmptyStateView(
            imagePainter = painterResource(android.R.drawable.ic_delete),
            title = "Title",
            description = SpannableText(text = "Brief explanation goes here"),
            secondaryAction = {
                PrimaryFilledButton(
                    modifier = Modifier,
                    text = "Button",
                    onClick = { }
                )
            }
        )
    }
}

@CombinedThemePreviews
@Composable
private fun EmptyStateViewWithTwoButtonsPreview() {
    AndroidThemeForPreviews {
        EmptyStateView(
            imagePainter = painterResource(android.R.drawable.ic_delete),
            title = "Title",
            description = SpannableText(text = "Brief explanation goes here"),
            primaryAction = {
                PrimaryFilledButton(
                    modifier = Modifier,
                    text = "Action 1",
                    onClick = { }
                )
            },
            secondaryAction = {
                SecondaryFilledButton(
                    modifier = Modifier,
                    text = "Action 2",
                    onClick = { }
                )
            }
        )
    }
}