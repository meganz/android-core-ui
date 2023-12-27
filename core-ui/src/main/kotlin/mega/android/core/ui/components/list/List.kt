package mega.android.core.ui.components.list

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import mega.android.core.ui.R
import mega.android.core.ui.components.MegaText
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.theme.tokens.TextColor

private val listItemMinHeight = 60.dp
private val listItemMaxHeight = 88.dp
private val headerListItemMinHeight = 36.dp
private val leadingElementContainerSize = 40.dp
private val vpnCountrySelectedListItemMaxWidth = 382.dp
private const val TITLE_MAX_LINES = 1
private const val SUBTITLE_MAX_LINES = 2
private const val VPN_SUBTITLE_MAX_LINES = 1

@Composable
fun HeaderListItem(
    text: String,
    modifier: Modifier = Modifier,
    onClickListener: () -> Unit = {},
) {
    Box(
        modifier = modifier
            .defaultMinSize(minHeight = headerListItemMinHeight)
            .fillMaxWidth()
            .background(color = AppTheme.colors.background.surface1)
            .clickable { onClickListener() }
            .padding(horizontal = LocalSpacing.current.x16)
    ) {
        MegaText(
            modifier = Modifier
                .align(Alignment.CenterStart),
            text = text,
            textColor = TextColor.Secondary,
            style = AppTheme.typography.titleSmall,
            maxLines = TITLE_MAX_LINES,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun OneLineListItem(
    text: String,
    modifier: Modifier = Modifier,
    leadingElement: (@Composable (BoxScope.() -> Unit))? = null,
    trailingElement: (@Composable (() -> Unit))? = null,
    onClickListener: () -> Unit = {},
) = ListItem(
    modifier = modifier
        .height(listItemMinHeight),
    title = text,
    subtitle = null,
    leadingElement = leadingElement,
    trailingElement = trailingElement,
    onClickListener = onClickListener
)

@Composable
fun MultiLineListItem(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
    leadingElement: (@Composable (BoxScope.() -> Unit))? = null,
    trailingElement: (@Composable (() -> Unit))? = null,
    onClickListener: () -> Unit = {},
) = ListItem(
    modifier = modifier.heightIn(min = listItemMinHeight, max = listItemMaxHeight),
    title = title,
    subtitle = subtitle,
    leadingElement = leadingElement,
    trailingElement = trailingElement,
    onClickListener = onClickListener
)

@Composable
fun VpnSelectedCountryListItem(
    title: String,
    subtitle: String,
    @DrawableRes countryFlagRes: Int,
    @DrawableRes rightIconRes: Int,
    modifier: Modifier = Modifier,
    onClickListener: () -> Unit = {},
) = ListItem(
    modifier = modifier
        .clip(AppTheme.shapes.small)
        .widthIn(max = vpnCountrySelectedListItemMaxWidth),
    title = title,
    subtitle = subtitle,
    subtitleMaxLines = VPN_SUBTITLE_MAX_LINES,
            leadingElement = {
        Icon(
            painter = painterResource(id = countryFlagRes),
            contentDescription = null,
            modifier = Modifier
                .size(32.dp)
                .align(Alignment.Center),
        )
    },
    trailingElement = {
        Icon(
            painter = painterResource(id = rightIconRes),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = AppTheme.colors.icon.primary
        )
    },
    onClickListener = onClickListener,
)

@Composable
private fun ListItem(
    modifier: Modifier,
    title: String,
    subtitle: String? = null,
    leadingElement: (@Composable (BoxScope.() -> Unit))? = null,
    trailingElement: (@Composable (() -> Unit))? = null,
    onClickListener: () -> Unit = {},
    subtitleMaxLines: Int = SUBTITLE_MAX_LINES,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = AppTheme.colors.background.surface1)
            .clickable { onClickListener() }
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
        MultiLineListItem(
            modifier = Modifier,
            title = "List item",
            subtitle = "Supporting line text lorem ipsum lorem ipsum"
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
fun HeaderListItemPreview() {
    AndroidThemeForPreviews {
        HeaderListItem(text = "Header text")
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
            countryFlagRes = R.drawable.ic_alert_triangle,
            rightIconRes = R.drawable.ic_check_circle,
        )
    }
}