package mega.android.core.ui.components.tooltip.popup.interactive

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import mega.android.core.ui.R
import mega.android.core.ui.components.MegaText
import mega.android.core.ui.components.minimumTouchTargetSize
import mega.android.core.ui.components.image.MegaIcon
import mega.android.core.ui.components.tooltip.component.TooltipBody
import mega.android.core.ui.components.tooltip.component.TooltipTitle
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.theme.values.IconColor
import mega.android.core.ui.theme.values.TextColor
import mega.android.core.ui.tokens.theme.DSTokens

/**
 * Fallback label for the tooltip's close icon. The library ships no string resources, so callers
 * that need a localised label pass their own through `closeIconDescription`.
 */
internal const val DEFAULT_CLOSE_ICON_DESCRIPTION = "Close"

@Stable
class InteractiveTooltipButtonProperties(
    val text: String,
    val onClick: () -> Unit
)

@Stable
class InteractiveTooltipStepCounterProperties(
    val currentPage: Int,
    val totalPage: Int
)

@Composable
internal fun InteractiveTooltipContent(
    title: String,
    modifier: Modifier = Modifier,
    body: String? = null,
    primaryButton: InteractiveTooltipButtonProperties? = null,
    secondaryButton: InteractiveTooltipButtonProperties? = null,
    stepCounter: InteractiveTooltipStepCounterProperties? = null,
    needCloseIcon: Boolean = false,
    onCloseClick: (() -> Unit)? = null,
    needDivider: Boolean = false,
    closeIconDescription: String = DEFAULT_CLOSE_ICON_DESCRIPTION
) {
    val spacing = LocalSpacing.current

    Row(
        modifier = modifier
            .padding(spacing.x16)
            .height(IntrinsicSize.Min)
    ) {
        LeftContent(
            modifier = Modifier.weight(1F),
            title = title,
            body = body,
            primaryButton = primaryButton,
            secondaryButton = secondaryButton,
            needDivider = needDivider
        )

        Spacer(modifier = Modifier.width(spacing.x8))

        RightContent(
            modifier = Modifier.fillMaxHeight(),
            stepCounter = stepCounter,
            needCloseIcon = needCloseIcon,
            closeIconDescription = closeIconDescription,
            onCloseClick = { onCloseClick?.invoke() }
        )
    }
}

@Composable
private fun LeftContent(
    title: String,
    modifier: Modifier = Modifier,
    body: String? = null,
    primaryButton: InteractiveTooltipButtonProperties? = null,
    secondaryButton: InteractiveTooltipButtonProperties? = null,
    needDivider: Boolean = false
) {
    val spacing = LocalSpacing.current

    Column(modifier = modifier) {
        TooltipTitle(value = title)

        body?.let {
            TooltipBody(
                modifier = Modifier.padding(top = spacing.x8),
                value = it
            )
        }

        if (needDivider) {
            HorizontalDivider(
                modifier = Modifier.padding(top = spacing.x12, bottom = spacing.x4),
                thickness = 1.dp,
                color = DSTokens.colors.icon.secondary
            )
        }

        primaryButton?.let {
            MegaText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = spacing.x8)
                    .clickable(onClick = { it.onClick() }, role = Role.Button)
                    // The minimum height has to sit inside the clickable so that it is part of the
                    // hit area; the top padding above it is spacing between the rows only.
                    .sizeIn(minHeight = minimumTouchTargetSize)
                    .wrapContentHeight(Alignment.CenterVertically),
                text = it.text,
                style = AppTheme.typography.labelLarge.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                textColor = TextColor.InverseAccent
            )
        }

        secondaryButton?.let {
            MegaText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = spacing.x8)
                    .clickable(onClick = { it.onClick() }, role = Role.Button)
                    .sizeIn(minHeight = minimumTouchTargetSize)
                    .wrapContentHeight(Alignment.CenterVertically),
                text = it.text,
                style = AppTheme.typography.labelLarge,
                textColor = TextColor.InverseSecondary
            )
        }
    }
}

@Composable
private fun RightContent(
    onCloseClick: () -> Unit,
    modifier: Modifier = Modifier,
    stepCounter: InteractiveTooltipStepCounterProperties? = null,
    needCloseIcon: Boolean = false,
    closeIconDescription: String = DEFAULT_CLOSE_ICON_DESCRIPTION
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        if (needCloseIcon) {
            MegaIcon(
                modifier = Modifier
                    .clickable(onClick = onCloseClick, role = Role.Button)
                    // Same as the buttons above: the 24dp icon keeps its size, the clickable around
                    // it grows to the minimum touch target
                    .sizeIn(
                        minWidth = minimumTouchTargetSize,
                        minHeight = minimumTouchTargetSize
                    )
                    .wrapContentSize(Alignment.Center),
                painter = painterResource(R.drawable.ic_close_medium_thin_outline),
                contentDescription = closeIconDescription,
                tint = IconColor.InverseSecondary
            )
        }

        stepCounter?.let {
            MegaText(
                modifier = Modifier.padding(top = LocalSpacing.current.x8),
                text = "${it.currentPage}/${it.totalPage}",
                style = AppTheme.typography.bodySmall,
                textColor = TextColor.InverseSecondary
            )
        }
    }
}

@CombinedThemePreviews
@Composable
private fun InteractiveTooltipContentPreview() {
    AndroidThemeForPreviews {
        Box(modifier = Modifier.background(DSTokens.colors.background.inverse)) {
            InteractiveTooltipContent(
                title = "Title",
                body = "Body",
                primaryButton = InteractiveTooltipButtonProperties(
                    text = "Primary button",
                    onClick = {}
                ),
                secondaryButton = InteractiveTooltipButtonProperties(
                    text = "Secondary button",
                    onClick = {}
                ),
                stepCounter = InteractiveTooltipStepCounterProperties(
                    currentPage = 1,
                    totalPage = 4
                ),
                needDivider = true,
                needCloseIcon = true
            )
        }
    }
}
