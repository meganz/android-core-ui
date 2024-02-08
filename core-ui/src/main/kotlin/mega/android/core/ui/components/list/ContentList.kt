package mega.android.core.ui.components.list

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import mega.android.core.ui.components.LinkSpannedText
import mega.android.core.ui.components.MegaText
import mega.android.core.ui.components.image.MegaIcon
import mega.android.core.ui.model.MegaSpanStyle
import mega.android.core.ui.model.SpanIndicator
import mega.android.core.ui.model.SpanStyleWithAnnotation
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.theme.tokens.IconColor
import mega.android.core.ui.theme.tokens.LinkColor
import mega.android.core.ui.theme.tokens.TextColor

@Composable
fun ImageContentListItem(
    modifier: Modifier,
    imageUrl: String,
    title: String,
    subtitle: String? = null,
    titleAnnotations: Map<SpanIndicator, SpanStyleWithAnnotation>? = null,
    onTitleAnnotationClick: ((annotation: String) -> Unit)? = null,
    subtitleAnnotations: Map<SpanIndicator, SpanStyleWithAnnotation>? = null,
    onSubtitleAnnotationClick: ((annotation: String) -> Unit)? = null,
    onClickListener: () -> Unit = {},
) {
    ContentListItem(
        modifier = modifier,
        title = title,
        subtitle = subtitle,
        titleAnnotations = titleAnnotations,
        onTitleAnnotationClick = onTitleAnnotationClick,
        subtitleAnnotations = subtitleAnnotations,
        onSubtitleAnnotationClick = onSubtitleAnnotationClick,
        leadingElement = {
            AsyncImage(
                modifier = Modifier
                    .size(40.dp)
                    .background(AppTheme.colors.background.surface1),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = title,
                contentScale = ContentScale.Fit,
            )
        },
        onClickListener = onClickListener
    )
}

@Composable
fun IconContentListItem(
    modifier: Modifier,
    @DrawableRes iconResId: Int,
    title: String,
    iconTint: IconColor = IconColor.Primary,
    subtitle: String? = null,
    titleAnnotations: Map<SpanIndicator, SpanStyleWithAnnotation>? = null,
    onTitleAnnotationClick: ((annotation: String) -> Unit)? = null,
    subtitleAnnotations: Map<SpanIndicator, SpanStyleWithAnnotation>? = null,
    onSubtitleAnnotationClick: ((annotation: String) -> Unit)? = null,
    onClickListener: () -> Unit = {},
) {
    ContentListItem(
        modifier = modifier,
        title = title,
        subtitle = subtitle,
        titleAnnotations = titleAnnotations,
        onTitleAnnotationClick = onTitleAnnotationClick,
        subtitleAnnotations = subtitleAnnotations,
        onSubtitleAnnotationClick = onSubtitleAnnotationClick,
        leadingElement = {
            MegaIcon(
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.CenterVertically),
                painter = painterResource(id = iconResId),
                tint = iconTint
            )
        },
        onClickListener = onClickListener
    )
}

@Composable
fun PlainContentListItem(
    modifier: Modifier,
    title: String,
    subtitle: String? = null,
    titleAnnotations: Map<SpanIndicator, SpanStyleWithAnnotation>? = null,
    onTitleAnnotationClick: ((annotation: String) -> Unit)? = null,
    subtitleAnnotations: Map<SpanIndicator, SpanStyleWithAnnotation>? = null,
    onSubtitleAnnotationClick: ((annotation: String) -> Unit)? = null,
    onClickListener: () -> Unit = {},
) {
    ContentListItem(
        modifier = modifier,
        title = title,
        subtitle = subtitle,
        titleAnnotations = titleAnnotations,
        onTitleAnnotationClick = onTitleAnnotationClick,
        subtitleAnnotations = subtitleAnnotations,
        onSubtitleAnnotationClick = onSubtitleAnnotationClick,
        onClickListener = onClickListener
    )
}

@Composable
fun NumberContentListItem(
    modifier: Modifier,
    number: Int,
    title: String,
    subtitle: String? = null,
    titleAnnotations: Map<SpanIndicator, SpanStyleWithAnnotation>? = null,
    onTitleAnnotationClick: ((annotation: String) -> Unit)? = null,
    subtitleAnnotations: Map<SpanIndicator, SpanStyleWithAnnotation>? = null,
    onSubtitleAnnotationClick: ((annotation: String) -> Unit)? = null,
    onClickListener: () -> Unit = {},
) {
    ContentListItem(
        modifier = modifier,
        title = title,
        subtitle = subtitle,
        titleAnnotations = titleAnnotations,
        onTitleAnnotationClick = onTitleAnnotationClick,
        subtitleAnnotations = subtitleAnnotations,
        onSubtitleAnnotationClick = onSubtitleAnnotationClick,
        leadingElement = {
            MegaText(
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.CenterVertically)
                    .border(
                        width = 1.dp,
                        color = AppTheme.colors.border.strong,
                        shape = CircleShape
                    ),
                text = "$number",
                textAlign = TextAlign.Center,
                textColor = TextColor.Primary,
                style = AppTheme.typography.titleMedium
            )
        },
        onClickListener = onClickListener
    )
}

@Composable
private fun ContentListItem(
    modifier: Modifier,
    title: String,
    subtitle: String? = null,
    titleAnnotations: Map<SpanIndicator, SpanStyleWithAnnotation>? = null,
    onTitleAnnotationClick: ((annotation: String) -> Unit)? = null,
    subtitleAnnotations: Map<SpanIndicator, SpanStyleWithAnnotation>? = null,
    onSubtitleAnnotationClick: ((annotation: String) -> Unit)? = null,
    leadingElement: (@Composable (RowScope.() -> Unit))? = null,
    onClickListener: () -> Unit = {},
) {
    val spacing = LocalSpacing.current

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClickListener() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(spacing.x24),
    ) {
        if (leadingElement != null) {
            leadingElement()
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
        ) {
            LinkSpannedText(
                value = title,
                spanStyles = titleAnnotations ?: emptyMap(),
                onAnnotationClick = onTitleAnnotationClick ?: {},
                baseStyle = AppTheme.typography.titleSmall,
                baseTextColor = TextColor.Primary,
            )

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
fun ImageContentListItemPreview() {
    AndroidThemeForPreviews {
        ImageContentListItem(
            modifier = Modifier
                .padding(16.dp),
            imageUrl = "https://placehold.co/400x400/000000/FFFFFF/png",
            title = "This is a [A]title[/A]",
            titleAnnotations = mapOf(
                SpanIndicator('A') to SpanStyleWithAnnotation(
                    MegaSpanStyle.LinkColorStyle(
                        SpanStyle(),
                        LinkColor.Primary
                    ), "title",
                )
            ),
            subtitle = "This is a [A]subtitle[/A]",
            subtitleAnnotations = mapOf(
                SpanIndicator('A') to SpanStyleWithAnnotation(
                    MegaSpanStyle.LinkColorStyle(
                        SpanStyle(),
                        LinkColor.Primary
                    ), "subtitle",
                )
            ),
        )
    }
}

@CombinedThemePreviews
@Composable
fun IconContentListItemPreview() {
    AndroidThemeForPreviews {
        IconContentListItem(
            modifier = Modifier
                .padding(16.dp),
            iconResId = android.R.drawable.ic_dialog_info,
            title = "This is a [A]title[/A]",
            titleAnnotations = mapOf(
                SpanIndicator('A') to SpanStyleWithAnnotation(
                    MegaSpanStyle.LinkColorStyle(
                        SpanStyle(),
                        LinkColor.Primary
                    ), "title",
                )
            ),
            subtitle = "This is a [A]subtitle[/A]",
            subtitleAnnotations = mapOf(
                SpanIndicator('A') to SpanStyleWithAnnotation(
                    MegaSpanStyle.LinkColorStyle(
                        SpanStyle(),
                        LinkColor.Primary
                    ), "subtitle",
                )
            ),
        )
    }
}

@CombinedThemePreviews
@Composable
fun PlainContentListItemPreview() {
    AndroidThemeForPreviews {
        PlainContentListItem(
            modifier = Modifier
                .padding(16.dp),
            title = "This is a [A]title[/A]",
            titleAnnotations = mapOf(
                SpanIndicator('A') to SpanStyleWithAnnotation(
                    MegaSpanStyle.LinkColorStyle(
                        SpanStyle(),
                        LinkColor.Primary
                    ), "title",
                )
            ),
            subtitle = "This is a [A]subtitle[/A]",
            subtitleAnnotations = mapOf(
                SpanIndicator('A') to SpanStyleWithAnnotation(
                    MegaSpanStyle.LinkColorStyle(
                        SpanStyle(),
                        LinkColor.Primary
                    ), "subtitle",
                )
            ),
        )
    }
}

@CombinedThemePreviews
@Composable
fun NumberContentListItemPreview() {
    AndroidThemeForPreviews {
        NumberContentListItem(
            modifier = Modifier
                .padding(16.dp),
            number = 10,
            title = "This is a [A]title[/A]",
            titleAnnotations = mapOf(
                SpanIndicator('A') to SpanStyleWithAnnotation(
                    MegaSpanStyle.LinkColorStyle(
                        SpanStyle(),
                        LinkColor.Primary
                    ), "title",
                )
            ),
            subtitle = "This is a [A]subtitle[/A]",
            subtitleAnnotations = mapOf(
                SpanIndicator('A') to SpanStyleWithAnnotation(
                    MegaSpanStyle.LinkColorStyle(
                        SpanStyle(),
                        LinkColor.Primary
                    ), "subtitle",
                )
            ),
        )
    }
}