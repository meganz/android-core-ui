package mega.android.core.ui.components.list

import androidx.annotation.DrawableRes
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import mega.android.core.ui.R
import mega.android.core.ui.components.MegaText
import mega.android.core.ui.components.image.MegaIcon
import mega.android.core.ui.components.util.shimmerEffect
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.theme.values.IconColor
import mega.android.core.ui.theme.values.TextColor
import mega.android.core.ui.tokens.theme.DSTokens

private val listItemMinHeight = 60.dp
private val headerListItemMinHeight = 36.dp
private val leadingElementContainerSize = 32.dp
private const val TITLE_MAX_LINES = 1
private const val TWO_LINE_LIST_SUBTITLE_MAX_LINES = 1
private const val MULTI_LINE_LIST_SUBTITLE_MAX_LINES = 2

internal object ListItemToken {
    val defaultContentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp)
}

enum class HeaderTextStyle {
    Small, Medium,
}

@Composable
fun PrimaryHeaderListItem(
    text: String,
    modifier: Modifier = Modifier,
    headerTextStyle: HeaderTextStyle = HeaderTextStyle.Small,
    @DrawableRes rightIconRes: Int? = null,
    @DrawableRes secondaryRightIconRes: Int? = null,
    @DrawableRes tertiaryRightIconRes: Int? = null,
    enableClick: Boolean = true,
    onClickListener: () -> Unit = {},
    onLongClickListener: () -> Unit = {},
) {
    HeaderListItem(
        modifier = modifier,
        text = text,
        textColor = TextColor.Primary,
        rightIconRes = rightIconRes,
        secondaryRightIconRes = secondaryRightIconRes,
        tertiaryRightIconRes = tertiaryRightIconRes,
        iconColor = IconColor.Primary,
        enableClick = enableClick,
        onClickListener = onClickListener,
        onLongClickListener = onLongClickListener,
        headerTextStyle = headerTextStyle,
    )
}

@Composable
fun SecondaryHeaderListItem(
    text: String,
    modifier: Modifier = Modifier,
    headerTextStyle: HeaderTextStyle = HeaderTextStyle.Small,
    @DrawableRes rightIconRes: Int? = null,
    @DrawableRes secondaryRightIconRes: Int? = null,
    @DrawableRes tertiaryRightIconRes: Int? = null,
    enableClick: Boolean = true,
    onClickListener: () -> Unit = {},
    onLongClickListener: () -> Unit = {},
) {
    HeaderListItem(
        modifier = modifier,
        text = text,
        textColor = TextColor.Secondary,
        rightIconRes = rightIconRes,
        secondaryRightIconRes = secondaryRightIconRes,
        tertiaryRightIconRes = tertiaryRightIconRes,
        iconColor = IconColor.Secondary,
        enableClick = enableClick,
        onClickListener = onClickListener,
        onLongClickListener = onLongClickListener,
        headerTextStyle = headerTextStyle,
    )
}

@Composable
private fun HeaderListItem(
    text: String,
    textColor: TextColor,
    modifier: Modifier = Modifier,
    headerTextStyle: HeaderTextStyle = HeaderTextStyle.Small,
    @DrawableRes rightIconRes: Int?,
    @DrawableRes secondaryRightIconRes: Int?,
    @DrawableRes tertiaryRightIconRes: Int?,
    iconColor: IconColor,
    enableClick: Boolean = true,
    onClickListener: () -> Unit,
    onLongClickListener: () -> Unit,
) {
    Row(
        modifier = modifier
            .defaultMinSize(minHeight = headerListItemMinHeight)
            .padding(horizontal = LocalSpacing.current.x16, vertical = LocalSpacing.current.x8)
            .combinedClickable(
                enabled = enableClick,
                onClick = onClickListener,
                onLongClick = onLongClickListener
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row (modifier = Modifier.weight(1f)) {
            MegaText(
                text = text,
                textColor = textColor,
                style = when (headerTextStyle) {
                    HeaderTextStyle.Small -> AppTheme.typography.titleSmall
                    HeaderTextStyle.Medium -> AppTheme.typography.titleMedium
                },
                maxLines = TITLE_MAX_LINES,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.width(8.dp))
            rightIconRes?.let {
                MegaIconRight(modifier, it, iconColor)
            }
        }
        Row {
            tertiaryRightIconRes?.let {
                MegaIconRight(modifier, it, iconColor)
            }
            Spacer(modifier = Modifier.width(8.dp))
            secondaryRightIconRes?.let {
                MegaIconRight(modifier, it, iconColor)
            }
        }
    }
}

@Composable
private fun RowScope.MegaIconRight(
    modifier: Modifier,
    rightIconRes: Int,
    iconColor: IconColor
) {
    MegaIcon(
        modifier = modifier
            .size(16.dp)
            .align(Alignment.CenterVertically),
        painter = painterResource(id = rightIconRes),
        tint = iconColor,
        contentDescription = null
    )
}

@Composable
fun OneLineListItem(
    text: String,
    modifier: Modifier = Modifier,
    leadingElement: (@Composable (BoxScope.() -> Unit))? = null,
    trailingElement: (@Composable (() -> Unit))? = null,
    enableClick: Boolean = true,
    contentPadding: PaddingValues = ListItemToken.defaultContentPadding,
    onClickListener: () -> Unit = {},
    onLongClickListener: (() -> Unit)? = null,
) = ListItem(
    modifier = modifier
        .defaultMinSize(minHeight = listItemMinHeight),
    title = text,
    subtitle = null,
    leadingElement = leadingElement,
    trailingElement = trailingElement,
    contentPadding = contentPadding,
    enableClick = enableClick,
    onClickListener = onClickListener,
    onLongClickListener = onLongClickListener,
)

@Composable
fun TwoLineListItem(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
    leadingElement: (@Composable (BoxScope.() -> Unit))? = null,
    trailingElement: (@Composable (() -> Unit))? = null,
    subtitleOverflow: TextOverflow = TextOverflow.Ellipsis,
    contentPadding: PaddingValues = ListItemToken.defaultContentPadding,
    enableClick: Boolean = true,
    onClickListener: () -> Unit = {},
    onLongClickListener: () -> Unit = {},
    replaceNullSubtitleWithShimmer: Boolean = false,
) = ListItem(
    modifier = modifier
        .defaultMinSize(minHeight = listItemMinHeight),
    title = title,
    subtitle = subtitle,
    leadingElement = leadingElement,
    trailingElement = trailingElement,
    subtitleOverflow = subtitleOverflow,
    contentPadding = contentPadding,
    enableClick = enableClick,
    onClickListener = onClickListener,
    onLongClickListener = onLongClickListener,
    subtitleMaxLines = TWO_LINE_LIST_SUBTITLE_MAX_LINES,
    replaceNullSubtitleWithShimmer = replaceNullSubtitleWithShimmer,
)

@Deprecated(
    message = "Use FlexibleLineListItem instead",
    replaceWith = ReplaceWith(
        "FlexibleLineListItem",
        "mega.android.core.ui.components.list.FlexibleLineListItem"
    ),
)
@Composable
fun MultiLineListItem(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
    leadingElement: (@Composable (BoxScope.() -> Unit))? = null,
    trailingElement: (@Composable (() -> Unit))? = null,
    subtitleOverflow: TextOverflow = TextOverflow.Ellipsis,
    contentPadding: PaddingValues = ListItemToken.defaultContentPadding,
    enableClick: Boolean = true,
    onClickListener: () -> Unit = {},
    onLongClickListener: () -> Unit = {},
) = ListItem(
    modifier = modifier
        .defaultMinSize(minHeight = listItemMinHeight),
    title = title,
    subtitle = subtitle,
    leadingElement = leadingElement,
    trailingElement = trailingElement,
    subtitleOverflow = subtitleOverflow,
    contentPadding = contentPadding,
    enableClick = enableClick,
    onClickListener = onClickListener,
    onLongClickListener = onLongClickListener,
    subtitleMaxLines = Int.MAX_VALUE
)

@Composable
fun FlexibleLineListItem(
    modifier: Modifier = Modifier,
    title: String? = null,
    subtitle: String? = null,
    titleMaxLines: Int = Int.MAX_VALUE,
    subtitleMaxLines: Int = Int.MAX_VALUE,
    leadingElement: (@Composable (BoxScope.() -> Unit))? = null,
    trailingElement: (@Composable (() -> Unit))? = null,
    subtitleOverflow: TextOverflow = TextOverflow.Ellipsis,
    contentPadding: PaddingValues = ListItemToken.defaultContentPadding,
    enableClick: Boolean = true,
    onClickListener: () -> Unit = {},
    onLongClickListener: () -> Unit = {},
    minHeight: Dp = listItemMinHeight,
    replaceNullSubtitleWithShimmer: Boolean = false,
    titleTextColor: TextColor = TextColor.Primary,
) {
    ListItem(
        modifier = modifier
            .defaultMinSize(minHeight = minHeight),
        title = title,
        subtitle = subtitle,
        leadingElement = leadingElement,
        trailingElement = trailingElement,
        subtitleOverflow = subtitleOverflow,
        contentPadding = contentPadding,
        enableClick = enableClick,
        onClickListener = onClickListener,
        onLongClickListener = onLongClickListener,
        titleMaxLines = titleMaxLines,
        subtitleMaxLines = subtitleMaxLines,
        replaceNullSubtitleWithShimmer = replaceNullSubtitleWithShimmer,
        titleTextColor = titleTextColor,
    )
}

@Composable
private fun ListItem(
    modifier: Modifier,
    contentPadding: PaddingValues,
    title: String? = null,
    subtitle: String? = null,
    leadingElement: (@Composable (BoxScope.() -> Unit))? = null,
    trailingElement: (@Composable (() -> Unit))? = null,
    subtitleOverflow: TextOverflow = TextOverflow.Ellipsis,
    enableClick: Boolean = true,
    onClickListener: () -> Unit = {},
    onLongClickListener: (() -> Unit)? = null,
    titleMaxLines: Int = TITLE_MAX_LINES,
    subtitleMaxLines: Int = MULTI_LINE_LIST_SUBTITLE_MAX_LINES,
    replaceNullSubtitleWithShimmer: Boolean = false,
    titleTextColor: TextColor = TextColor.Primary,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .combinedClickable(
                enabled = enableClick,
                onClick = onClickListener,
                onLongClick = onLongClickListener
            )
            .padding(contentPadding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(LocalSpacing.current.x16)
    ) {
        if (leadingElement != null) {
            CompositionLocalProvider(
                LocalContentColor provides DSTokens.colors.icon.primary.copy(alpha = 1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(leadingElementContainerSize)
                        .align(Alignment.CenterVertically),
                ) {
                    leadingElement()
                }
            }
        }

        Column(
            modifier = Modifier
                .wrapContentHeight()
                .weight(1f),
        ) {
            if (title != null) {
                MegaText(
                    text = title,
                    textColor = titleTextColor,
                    style = AppTheme.typography.bodyLarge,
                    maxLines = titleMaxLines,
                    overflow = TextOverflow.Ellipsis
                )
            }

            if (subtitle != null) {
                MegaText(
                    text = subtitle,
                    textColor = TextColor.Secondary,
                    style = AppTheme.typography.bodyMedium,
                    maxLines = subtitleMaxLines,
                    overflow = subtitleOverflow,
                )
            } else if (replaceNullSubtitleWithShimmer) {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp)
                        .shimmerEffect()
                )
            }
        }

        if (trailingElement != null) {
            trailingElement()
        }
    }
}

@Composable
@CombinedThemePreviews
private fun OneLineListItemPreview() {
    AndroidThemeForPreviews {
        OneLineListItem(
            modifier = Modifier,
            text = "List item",
        )
    }
}

@Composable
@CombinedThemePreviews
private fun TwoLineListItemPreview() {
    AndroidThemeForPreviews {
        TwoLineListItem(
            title = "List item",
            subtitle = "Supporting line text lorem ipsum lorem ipsum lorem ipsum lorem ipsum",
            modifier = Modifier
        )
    }
}

@Composable
@CombinedThemePreviews
private fun TwoLineListItemMiddleEllipsisSubtitlePreview() {
    AndroidThemeForPreviews {
        TwoLineListItem(
            title = "List item",
            subtitle = "Supporting line text lorem ipsum lorem ipsum lorem ipsum lorem ipsum",
            subtitleOverflow = TextOverflow.MiddleEllipsis,
        )
    }
}

@Composable
@CombinedThemePreviews
private fun ThreeLineListItemPreview() {
    AndroidThemeForPreviews {
        FlexibleLineListItem(
            modifier = Modifier,
            title = "List item",
            subtitle = "Supporting line text lorem ipsum lorem ipsum lorem ipsum lorem ipsum"
        )
    }
}

@Composable
@CombinedThemePreviews
private fun OneLineListItemPreviewWithElements() {
    AndroidThemeForPreviews {
        OneLineListItem(
            modifier = Modifier,
            text = "List item",
            leadingElement = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_alert_triangle_medium_thin_outline),
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.Center),
                    tint = DSTokens.colors.icon.primary
                )
            },
            trailingElement = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_check_circle_medium_thin_outline),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = DSTokens.colors.icon.primary
                )
            },
        )
    }
}

@Composable
@CombinedThemePreviews
private fun OneLineListItemPreviewWithLargeElements() {
    AndroidThemeForPreviews {
        OneLineListItem(
            modifier = Modifier,
            text = "List item",
            leadingElement = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_alert_triangle_medium_thin_outline),
                    contentDescription = null,
                    modifier = Modifier
                        .size(35.dp)
                        .align(Alignment.Center),
                    tint = DSTokens.colors.icon.primary
                )
            },
            trailingElement = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_check_circle_medium_thin_outline),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = DSTokens.colors.icon.primary
                )
            },
        )
    }
}

@Composable
@CombinedThemePreviews
private fun MultiLineListItemPreviewWithElements() {
    AndroidThemeForPreviews {
        FlexibleLineListItem(
            modifier = Modifier,
            title = "List item",
            subtitle = "Supporting line text lorem ipsum lorem ipsum",
            leadingElement = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_alert_triangle_medium_thin_outline),
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.Center),
                    tint = DSTokens.colors.icon.primary
                )
            },
            trailingElement = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_check_circle_medium_thin_outline),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = DSTokens.colors.icon.primary
                )
            }
        )
    }
}

@Composable
@CombinedThemePreviews
private fun SecondaryHeaderListItemPreview() {
    AndroidThemeForPreviews {
        SecondaryHeaderListItem(text = "Header text")
    }
}

@Composable
@CombinedThemePreviews
private fun SecondaryHeaderListItemWithIconPreview() {
    AndroidThemeForPreviews {
        SecondaryHeaderListItem(text = "Header text", rightIconRes = R.drawable.ic_arrow_left_medium_thin_outline)
    }
}

@Composable
@CombinedThemePreviews
private fun PrimaryHeaderListItemPreview() {
    AndroidThemeForPreviews {
        PrimaryHeaderListItem(text = "Header text")
    }
}

@Composable
@CombinedThemePreviews
private fun PrimaryHeaderListItemWithIconPreview() {
    AndroidThemeForPreviews {
        PrimaryHeaderListItem(text = "Header text", rightIconRes = R.drawable.ic_check_medium_thin_outline)
    }
}