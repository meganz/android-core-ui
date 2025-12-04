package mega.android.core.ui.components.list

import androidx.annotation.DrawableRes
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
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
import mega.android.core.ui.model.HighlightedText
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
        text = HighlightedText(text),
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
fun PrimaryHeaderListItem(
    text: HighlightedText,
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
        text = HighlightedText(text),
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
fun SecondaryHeaderListItem(
    text: HighlightedText,
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
    text: HighlightedText,
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
        Row(modifier = Modifier.weight(1f)) {
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
    titleTrailingElement: (@Composable (() -> Unit))? = null,
) = ListItem(
    modifier = modifier
        .defaultMinSize(minHeight = listItemMinHeight),
    title = HighlightedText(text),
    leadingElement = leadingElement,
    trailingElement = trailingElement,
    contentPadding = contentPadding,
    enableClick = enableClick,
    onClickListener = onClickListener,
    onLongClickListener = onLongClickListener,
    titleTrailingElement = titleTrailingElement,
)

@Composable
fun OneLineListItem(
    text: HighlightedText,
    modifier: Modifier = Modifier,
    leadingElement: (@Composable (BoxScope.() -> Unit))? = null,
    trailingElement: (@Composable (() -> Unit))? = null,
    enableClick: Boolean = true,
    contentPadding: PaddingValues = ListItemToken.defaultContentPadding,
    onClickListener: () -> Unit = {},
    onLongClickListener: (() -> Unit)? = null,
    titleTrailingElement: (@Composable (() -> Unit))? = null,
) = ListItem(
    modifier = modifier
        .defaultMinSize(minHeight = listItemMinHeight),
    title = text,
    leadingElement = leadingElement,
    trailingElement = trailingElement,
    contentPadding = contentPadding,
    enableClick = enableClick,
    onClickListener = onClickListener,
    onLongClickListener = onLongClickListener,
    titleTrailingElement = titleTrailingElement,
)

@Deprecated(
    message = "Use FlexibleLineListItem instead",
    replaceWith = ReplaceWith(
        "FlexibleLineListItem",
        "mega.android.core.ui.components.list.FlexibleLineListItem"
    ),
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
    titleTrailingElement: (@Composable (() -> Unit))? = null,
) = ListItem(
    modifier = modifier
        .defaultMinSize(minHeight = listItemMinHeight),
    title = HighlightedText(title),
    subtitle = HighlightedText(subtitle),
    leadingElement = leadingElement,
    trailingElement = trailingElement,
    subtitleOverflow = subtitleOverflow,
    contentPadding = contentPadding,
    enableClick = enableClick,
    onClickListener = onClickListener,
    onLongClickListener = onLongClickListener,
    subtitleMaxLines = TWO_LINE_LIST_SUBTITLE_MAX_LINES,
    replaceNullSubtitleWithShimmer = replaceNullSubtitleWithShimmer,
    titleTrailingElement = titleTrailingElement,
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
    titleTrailingElement: (@Composable (() -> Unit))? = null,
) {
    ListItem(
        modifier = modifier
            .defaultMinSize(minHeight = minHeight),
        title = title?.let { HighlightedText(it) },
        subtitle = subtitle?.let { HighlightedText(it) },
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
        titleTrailingElement = titleTrailingElement,
    )
}

@Composable
fun FlexibleLineListItem(
    modifier: Modifier = Modifier,
    title: HighlightedText? = null,
    subtitle: HighlightedText? = null,
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
    titleTrailingElement: (@Composable (() -> Unit))? = null,
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
        titleTrailingElement = titleTrailingElement,
    )
}

@Composable
private fun ListItem(
    modifier: Modifier,
    contentPadding: PaddingValues,
    title: HighlightedText? = null,
    subtitle: HighlightedText? = null,
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
    titleTrailingElement: (@Composable (() -> Unit))? = null,
) {
    GenericListItem(
        modifier = modifier,
        contentPadding = contentPadding,
        title = {
            if (title != null) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    MegaText(
                        text = title,
                        textColor = titleTextColor,
                        style = AppTheme.typography.bodyLarge,
                        maxLines = titleMaxLines,
                        overflow = TextOverflow.Ellipsis
                    )
                    titleTrailingElement?.invoke()
                }
            }
        },
        subtitle = {
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
        },
        leadingElement = if (leadingElement != null) {
            {
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
        } else null,
        trailingElement = trailingElement,
        enableClick = enableClick,
        onClickListener = onClickListener,
        onLongClickListener = onLongClickListener,
        horizontalArrangement = Arrangement.spacedBy(LocalSpacing.current.x16)
    )
}

/**
 * A generic list item that can be used to create a variety of list items with highly customizable content.
 *
 * @param modifier Modifier for the list item.
 * @param contentPadding Padding values for the content inside the list item.
 * @param title Composable function for the title of the list item.
 * @param subtitle Composable function for the subtitle of the list item.
 * @param leadingElement Optional composable function for a leading element (e.g., an icon).
 * @param trailingElement Optional composable function for a trailing element (e.g., an icon).
 * @param enableClick Whether the list item is clickable.
 * @param onClickListener Click listener for the list item.
 * @param onLongClickListener Long click listener for the list item, if any.
 */
@Composable
fun GenericListItem(
    title: (@Composable (ColumnScope.() -> Unit)),
    subtitle: (@Composable (ColumnScope.() -> Unit)),
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = ListItemToken.defaultContentPadding,
    leadingElement: (@Composable (RowScope.() -> Unit))? = null,
    trailingElement: (@Composable (() -> Unit))? = null,
    enableClick: Boolean = true,
    onClickListener: () -> Unit = {},
    onLongClickListener: (() -> Unit)? = null,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    horizontalArrangement: Arrangement.HorizontalOrVertical = Arrangement.spacedBy(DSTokens.spacings.s4)
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
        verticalAlignment = verticalAlignment,
        horizontalArrangement = horizontalArrangement
    ) {
        if (leadingElement != null) {
            leadingElement()
        }

        Column(
            modifier = Modifier
                .wrapContentHeight()
                .weight(1f),
        ) {
            title()
            subtitle()
        }

        if (trailingElement != null) {
            trailingElement()
        }
    }
}

@Composable
@CombinedThemePreviews
private fun GenericListItemPreview() {
    AndroidThemeForPreviews {
        GenericListItem(
            title = {
                Row {
                    MegaIcon(
                        painter = painterResource(id = R.drawable.ic_check_circle_medium_thin_outline),
                        contentDescription = null,
                        modifier = Modifier
                            .size(14.dp)
                            .align(Alignment.CenterVertically),
                        tint = IconColor.Primary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    MegaText(
                        text = "List item",
                        textColor = TextColor.Primary,
                        style = AppTheme.typography.bodyLarge,
                        maxLines = TITLE_MAX_LINES,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            },
            subtitle = {
                MegaText(
                    text = "Supporting line text lorem ipsum lorem ipsum lorem ipsum lorem ipsum",
                    textColor = TextColor.Secondary,
                    style = AppTheme.typography.bodyMedium,
                    maxLines = MULTI_LINE_LIST_SUBTITLE_MAX_LINES,
                    overflow = TextOverflow.Ellipsis
                )
                MegaText(
                    text = "Line two subtitle",
                    textColor = TextColor.Secondary,
                    style = AppTheme.typography.bodyMedium,
                    maxLines = MULTI_LINE_LIST_SUBTITLE_MAX_LINES,
                    overflow = TextOverflow.Ellipsis
                )
            },
            leadingElement = {
                MegaIcon(
                    painter = painterResource(id = R.drawable.illustration_mega_anniversary),
                    contentDescription = null,
                    modifier = Modifier
                        .size(leadingElementContainerSize)
                        .align(Alignment.CenterVertically),
                )
            },
            trailingElement = {
                MegaIcon(
                    painter = painterResource(id = R.drawable.ic_check_circle_medium_thin_outline),
                    contentDescription = null,
                    modifier = Modifier
                        .size(leadingElementContainerSize),
                    tint = IconColor.Primary
                )
            },
        )
    }
}

@Composable
@CombinedThemePreviews
private fun GenericListItemPreview2() {
    AndroidThemeForPreviews {
        GenericListItem(
            title = {
                MegaText(
                    text = "List item",
                    textColor = TextColor.Primary,
                    style = AppTheme.typography.bodyLarge,
                    maxLines = TITLE_MAX_LINES,
                    overflow = TextOverflow.Ellipsis
                )
            },
            subtitle = {
                MegaText(
                    text = "Supporting line text lorem ipsum lorem ipsum lorem ipsum lorem ipsum",
                    textColor = TextColor.Secondary,
                    style = AppTheme.typography.bodyMedium,
                    maxLines = MULTI_LINE_LIST_SUBTITLE_MAX_LINES,
                    overflow = TextOverflow.Ellipsis
                )
            },
        )
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
private fun OneLineListItemPreviewWithElementsAndHighlights() {
    AndroidThemeForPreviews {
        OneLineListItem(
            modifier = Modifier,
            text = HighlightedText("List item", "item"),
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
            titleTrailingElement = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_check_circle_medium_thin_outline),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = DSTokens.colors.icon.brand
                )
            }
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
            },
            titleTrailingElement = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_check_circle_medium_thin_outline),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = DSTokens.colors.icon.brand
                )
            }
        )
    }
}

@Composable
@CombinedThemePreviews
private fun MultiLineListItemPreviewWithElementsAndHighlights() {
    AndroidThemeForPreviews {
        FlexibleLineListItem(
            modifier = Modifier,
            title = HighlightedText("List item", "item"),
            subtitle = HighlightedText("Supporting line text lorem ipsum lorem ipsum", "lorem ipsum"),
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
            titleTrailingElement = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_check_circle_medium_thin_outline),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = DSTokens.colors.icon.brand
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
        SecondaryHeaderListItem(
            text = "Header text",
            rightIconRes = R.drawable.ic_arrow_left_medium_thin_outline
        )
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
        PrimaryHeaderListItem(
            text = "Header text",
            rightIconRes = R.drawable.ic_check_medium_thin_outline
        )
    }
}

@Composable
@CombinedThemePreviews
private fun PrimaryHeaderListItemHighlightedPreview() {
    AndroidThemeForPreviews {
        PrimaryHeaderListItem(
            text = HighlightedText("Header text", "text"),
            rightIconRes = R.drawable.ic_check_medium_thin_outline
        )
    }
}