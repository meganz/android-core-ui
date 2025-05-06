package mega.android.core.ui.components.badge

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import mega.android.core.ui.R
import mega.android.core.ui.components.MegaText
import mega.android.core.ui.components.image.MegaIcon
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.tokens.theme.DSTokens
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.theme.values.TextColor

enum class BadgeType {
    Success,
    Warning,
    Info,
    Error,
    Mega,
    MegaSecondary
}

private data class Badge(
    val textColor: TextColor,
    val backgroundColor: Color,
    val borderColor: Color? = null
)

/**
 * A Badge is a compact visual element that conveys small pieces of information, such as status or notifications.
 * It helps highlight key information without taking up too much screen space.
 * it is never interactive (use Chips for that)
 *
 * @param badgeType the type of badge to display
 * @param text the text to display in the badge
 * @param modifier the modifier to apply to the badge
 * @param icon the icon to display in the badge
 */
@Composable
fun Badge(
    badgeType: BadgeType,
    text: String,
    modifier: Modifier = Modifier,
    icon: Painter? = null,
) {
    val attributes = getBadgeAttributes(badgeType)
    val spacing = LocalSpacing.current

    Row(
        modifier = modifier
            .wrapContentHeight()
            .background(
                color = attributes.backgroundColor,
                shape = DSTokens.shapes.extraSmall
            )
            .then(
                if (attributes.borderColor != null) {
                    Modifier.border(
                        width = 1.dp,
                        color = attributes.borderColor,
                        shape = DSTokens.shapes.extraSmall
                    )
                } else {
                    Modifier
                }
            )
            .padding(vertical = spacing.x2, horizontal = spacing.x4),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(spacing.x2)
    ) {
        if (icon != null) {
            MegaIcon(
                modifier = Modifier.size(16.dp),
                painter = icon,
                textColorTint = attributes.textColor,
                contentDescription = null
            )
        }

        MegaText(
            text = text,
            style = AppTheme.typography.bodyMedium,
            textColor = attributes.textColor,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun getBadgeAttributes(badgeType: BadgeType): Badge {
    return when (badgeType) {
        BadgeType.Success -> Badge(
            textColor = TextColor.Success,
            backgroundColor = DSTokens.colors.notifications.notificationSuccess
        )
        BadgeType.Warning -> Badge(
            textColor = TextColor.Warning,
            backgroundColor = DSTokens.colors.notifications.notificationWarning
        )
        BadgeType.Info -> Badge(
            textColor = TextColor.Info,
            backgroundColor = DSTokens.colors.notifications.notificationInfo
        )
        BadgeType.Error -> Badge(
            textColor = TextColor.Error,
            backgroundColor = DSTokens.colors.notifications.notificationError
        )
        BadgeType.Mega -> Badge(
            textColor = TextColor.OnColor,
            backgroundColor = DSTokens.colors.button.brand
        )
        BadgeType.MegaSecondary -> Badge(
            textColor = TextColor.Brand,
            backgroundColor = Color.Transparent,
            borderColor = DSTokens.colors.button.brand
        )
    }
}

@Composable
@CombinedThemePreviews
private fun BadgePreview() {
    AndroidThemeForPreviews {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .padding(LocalSpacing.current.x24),
            verticalArrangement = Arrangement.spacedBy(LocalSpacing.current.x8)
        ) {
            BadgeType.entries.forEach {
                Badge(
                    badgeType = it,
                    text = it.name,
                    icon = painterResource(id = R.drawable.ic_close)
                )
            }
        }
    }
}