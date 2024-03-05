package mega.android.core.ui.components.list

import androidx.annotation.DrawableRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import mega.android.core.ui.R
import mega.android.core.ui.components.MegaText
import mega.android.core.ui.components.image.MegaIcon
import mega.android.core.ui.components.util.shimmerEffect
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.theme.tokens.IconColor
import mega.android.core.ui.theme.tokens.TextColor

private val listItemMinHeight = 60.dp
private val headerListItemMinHeight = 36.dp
private val leadingElementContainerSize = 32.dp
private val vpnCountrySelectedListItemMaxWidth = 382.dp
private const val TITLE_MAX_LINES = 1
private const val VPN_SUBTITLE_MAX_LINES = 1
private const val TWO_LINE_LIST_SUBTITLE_MAX_LINES = 1
private const val MULTI_LINE_LIST_SUBTITLE_MAX_LINES = 2

@Composable
fun PrimaryHeaderListItem(
    text: String,
    modifier: Modifier = Modifier,
    @DrawableRes rightIconRes: Int? = null,
    enableClick: Boolean = true,
    onClickListener: () -> Unit = {},
    onLongClickListener: () -> Unit = {},
) {
    HeaderListItem(
        modifier = modifier,
        text = text,
        textColor = TextColor.Primary,
        rightIconRes = rightIconRes,
        iconColor = IconColor.Primary,
        enableClick = enableClick,
        onClickListener = onClickListener,
        onLongClickListener = onLongClickListener,
    )
}

@Composable
fun SecondaryHeaderListItem(
    text: String,
    modifier: Modifier = Modifier,
    @DrawableRes rightIconRes: Int? = null,
    enableClick: Boolean = true,
    onClickListener: () -> Unit = {},
    onLongClickListener: () -> Unit = {},
) {
    HeaderListItem(
        modifier = modifier,
        text = text,
        textColor = TextColor.Secondary,
        rightIconRes = rightIconRes,
        iconColor = IconColor.Secondary,
        enableClick = enableClick,
        onClickListener = onClickListener,
        onLongClickListener = onLongClickListener,
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HeaderListItem(
    text: String,
    textColor: TextColor,
    modifier: Modifier = Modifier,
    @DrawableRes rightIconRes: Int?,
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
            )
    ) {
        MegaText(
            text = text,
            textColor = textColor,
            style = AppTheme.typography.titleSmall,
            maxLines = TITLE_MAX_LINES,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.width(8.dp))
        rightIconRes?.let {
            MegaIcon(
                modifier = modifier
                    .size(16.dp)
                    .align(Alignment.CenterVertically),
                painter = painterResource(id = rightIconRes),
                tint = iconColor,
                contentDescription = null
            )
        }
    }
}

@Composable
fun OneLineListItem(
    text: String,
    modifier: Modifier = Modifier,
    leadingElement: (@Composable (BoxScope.() -> Unit))? = null,
    trailingElement: (@Composable (() -> Unit))? = null,
    enableClick: Boolean = true,
    onClickListener: () -> Unit = {},
    onLongClickListener: () -> Unit = {},
) = ListItem(
    modifier = modifier
        .defaultMinSize(minHeight = listItemMinHeight),
    title = text,
    subtitle = null,
    leadingElement = leadingElement,
    trailingElement = trailingElement,
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
    enableClick = enableClick,
    onClickListener = onClickListener,
    onLongClickListener = onLongClickListener,
    subtitleMaxLines = TWO_LINE_LIST_SUBTITLE_MAX_LINES,
)

@Composable
fun MultiLineListItem(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
    leadingElement: (@Composable (BoxScope.() -> Unit))? = null,
    trailingElement: (@Composable (() -> Unit))? = null,
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
    enableClick = enableClick,
    onClickListener = onClickListener,
    onLongClickListener = onLongClickListener,
    subtitleMaxLines = Int.MAX_VALUE
)

@Composable
fun VpnSelectedCountryListItem(
    title: String,
    subtitle: String?,
    countryFlag: Painter?,
    rightIcon: Painter,
    modifier: Modifier = Modifier,
    colorFilter: ColorFilter? = null,
    onClickListener: () -> Unit = {},
    onLongClickListener: () -> Unit = {},
) = ListItem(
    modifier = modifier
        .clip(AppTheme.shapes.small)
        .widthIn(max = vpnCountrySelectedListItemMaxWidth)
        .background(AppTheme.colors.background.surface1),
    title = title,
    subtitle = subtitle,
    subtitleMaxLines = VPN_SUBTITLE_MAX_LINES,
    leadingElement = {
        Box(
            modifier = Modifier
                .size(32.dp)
                .align(Alignment.Center)
        ) {
            if (countryFlag != null) {
                Image(
                    painter = countryFlag,
                    contentDescription = subtitle,
                    modifier = Modifier
                        .size(32.dp)
                        .align(Alignment.Center),
                    contentScale = ContentScale.Inside,
                    colorFilter = colorFilter
                )
            } else {
                Spacer(
                    modifier = Modifier
                        .size(32.dp)
                        .align(Alignment.Center)
                        .shimmerEffect()
                )
            }
        }
    },
    trailingElement = {
        Icon(
            painter = rightIcon,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = AppTheme.colors.icon.primary
        )
    },
    enableClick = true,
    onClickListener = onClickListener,
    onLongClickListener = onLongClickListener,
    replaceNullSubtitleWithShimmer = true
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ListItem(
    modifier: Modifier,
    title: String,
    subtitle: String? = null,
    leadingElement: (@Composable (BoxScope.() -> Unit))? = null,
    trailingElement: (@Composable (() -> Unit))? = null,
    enableClick: Boolean = true,
    onClickListener: () -> Unit = {},
    onLongClickListener: () -> Unit = {},
    subtitleMaxLines: Int = MULTI_LINE_LIST_SUBTITLE_MAX_LINES,
    replaceNullSubtitleWithShimmer: Boolean = false
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .combinedClickable(
                enabled = enableClick,
                onClick = onClickListener,
                onLongClick = onLongClickListener
            )
            .padding(
                vertical = LocalSpacing.current.x12,
                horizontal = LocalSpacing.current.x16
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(LocalSpacing.current.x16)
    ) {
        if (leadingElement != null) {
            CompositionLocalProvider(
                LocalContentColor provides AppTheme.colors.icon.primary.copy(alpha = 1f)
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
            MegaText(
                text = title,
                textColor = TextColor.Primary,
                style = AppTheme.typography.bodyLarge,
                maxLines = TITLE_MAX_LINES,
                overflow = TextOverflow.Ellipsis
            )

            if (subtitle != null) {
                MegaText(
                    text = subtitle,
                    textColor = TextColor.Secondary,
                    style = AppTheme.typography.bodyMedium,
                    maxLines = subtitleMaxLines,
                    overflow = TextOverflow.Ellipsis
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
private fun ThreeLineListItemPreview() {
    AndroidThemeForPreviews {
        MultiLineListItem(
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
                    painter = painterResource(id = R.drawable.ic_alert_triangle),
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.Center),
                    tint = AppTheme.colors.icon.primary
                )
            },
            trailingElement = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_check_circle),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = AppTheme.colors.icon.primary
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
                    painter = painterResource(id = R.drawable.ic_alert_triangle),
                    contentDescription = null,
                    modifier = Modifier
                        .size(35.dp)
                        .align(Alignment.Center),
                    tint = AppTheme.colors.icon.primary
                )
            },
            trailingElement = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_check_circle),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = AppTheme.colors.icon.primary
                )
            },
        )
    }
}

@Composable
@CombinedThemePreviews
private fun MultiLineListItemPreviewWithElements() {
    AndroidThemeForPreviews {
        MultiLineListItem(
            modifier = Modifier,
            title = "List item",
            subtitle = "Supporting line text lorem ipsum lorem ipsum",
            leadingElement = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_alert_triangle),
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.Center),
                    tint = AppTheme.colors.icon.primary
                )
            },
            trailingElement = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_check_circle),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = AppTheme.colors.icon.primary
                )
            }
        )
    }
}

@Composable
@CombinedThemePreviews
fun SecondaryHeaderListItemPreview() {
    AndroidThemeForPreviews {
        SecondaryHeaderListItem(text = "Header text")
    }
}

@Composable
@CombinedThemePreviews
fun SecondaryHeaderListItemWithIconPreview() {
    AndroidThemeForPreviews {
        SecondaryHeaderListItem(text = "Header text", rightIconRes = R.drawable.ic_arrow_left)
    }
}

@Composable
@CombinedThemePreviews
fun PrimaryHeaderListItemPreview() {
    AndroidThemeForPreviews {
        PrimaryHeaderListItem(text = "Header text")
    }
}

@Composable
@CombinedThemePreviews
fun PrimaryHeaderListItemWithIconPreview() {
    AndroidThemeForPreviews {
        PrimaryHeaderListItem(text = "Header text", rightIconRes = R.drawable.ic_check_filled)
    }
}

@Composable
@CombinedThemePreviews
private fun VpnSelectedCountryListItemPreview() {
    AndroidThemeForPreviews {
        VpnSelectedCountryListItem(
            modifier = Modifier,
            title = "Selected server",
            subtitle = "Country name",
            countryFlag = painterResource(id = R.drawable.ic_alert_triangle),
            rightIcon = painterResource(id = R.drawable.ic_check_circle),
        )
    }
}

@Composable
@CombinedThemePreviews
private fun VpnSelectedCountryListItemShimmerPreview() {
    AndroidThemeForPreviews {
        VpnSelectedCountryListItem(
            modifier = Modifier,
            title = "Selected server",
            subtitle = null,
            countryFlag = null,
            rightIcon = painterResource(id = R.drawable.ic_check_circle),
        )
    }
}