package mega.android.core.ui.components.button

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import mega.android.core.ui.R
import mega.android.core.ui.components.MegaText
import mega.android.core.ui.components.divider.StrongVerticalDivider
import mega.android.core.ui.model.AccessoryBarButtonContent
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.theme.values.TextColor

/**
 * A row of button that is used as accessory buttons in the app.
 *
 * @param button The [AccessoryBarButtonContent] to be displayed.
 * @param modifier Modifier to be applied to the layout.
 */
@Composable
fun AccessoryBarButton(
    button: AccessoryBarButtonContent,
    modifier: Modifier = Modifier
) {
    AccessoryBarButton(
        modifier = modifier,
        buttons = persistentListOf(button)
    )
}

/**
 * A row of buttons that are used as accessory buttons in the app.
 *
 * @param firstButton The first [AccessoryBarButtonContent] to be displayed.
 * @param secondButton The second [AccessoryBarButtonContent] to be displayed.
 * @param modifier Modifier to be applied to the layout.
 */
@Composable
fun AccessoryBarButtonGroup(
    firstButton: AccessoryBarButtonContent,
    secondButton: AccessoryBarButtonContent,
    modifier: Modifier = Modifier
) {
    AccessoryBarButton(modifier = modifier, buttons = persistentListOf(firstButton, secondButton))
}

/**
 * A row of buttons that are used as accessory buttons in the app.
 *
 * @param modifier Modifier to be applied to the layout.
 * @param buttons List of [AccessoryBarButtonContent] to be displayed.
 */
@Composable
private fun AccessoryBarButton(
    buttons: ImmutableList<AccessoryBarButtonContent>,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .background(AppTheme.colors.background.surface3)
    ) {
        repeat(buttons.size) { i ->
            val content = buttons[i]
            AccessoryBarButtonItem(content)
            if (i < buttons.lastIndex) {
                StrongVerticalDivider(
                    thickness = 2.dp,
                    Modifier
                        .height(26.dp)
                        .testTag(ACCESSORY_BAR_BUTTON_VERTICAL_DIVIDER_TEST_TAG)
                )
            }
        }
    }
}

@Composable
private fun RowScope.AccessoryBarButtonItem(content: AccessoryBarButtonContent) {
    val spacing = LocalSpacing.current
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(vertical = spacing.x12)
            .weight(1f)
            .fillMaxWidth()
            .clickable { content.onClick() }
            .testTag(ACCESSORY_BAR_BUTTON_TEST_TAG)
    ) {
        content.icon?.let {
            Icon(
                painter = it,
                contentDescription = null,
                tint = AppTheme.colors.icon.primary,
                modifier = Modifier.testTag(ACCESSORY_BAR_BUTTON_ICON_TEST_TAG)
            )
        }
        MegaText(
            text = content.text,
            style = AppTheme.typography.bodyLarge,
            textColor = TextColor.Primary,
            modifier = Modifier
                .padding(start = spacing.x8)
                .testTag(ACCESSORY_BAR_BUTTON_TEXT_TEST_TAG)
        )
    }
}


@CombinedThemePreviews
@Composable
private fun AccessoryBarButtonPreview() {
    AndroidThemeForPreviews {
        AccessoryBarButtonGroup(
            firstButton = AccessoryBarButtonContent(
                icon = painterResource(id = R.drawable.ic_search_large),
                text = "Button",
                onClick = {}
            ),
            secondButton = AccessoryBarButtonContent(
                icon = painterResource(id = R.drawable.ic_search_large),
                text = "Button",
                onClick = {}
            )
        )
    }
}

@CombinedThemePreviews
@Composable
private fun AccessoryBarButtonSinglePreview() {
    AndroidThemeForPreviews {
        AccessoryBarButton(
            button = AccessoryBarButtonContent(
                icon = painterResource(id = R.drawable.ic_search_large),
                text = "Button",
                onClick = {}
            ),
        )
    }
}

internal const val ACCESSORY_BAR_BUTTON_TEST_TAG = "AccessoryBarButton"
internal const val ACCESSORY_BAR_BUTTON_ICON_TEST_TAG = "${ACCESSORY_BAR_BUTTON_TEST_TAG}:Icon"
internal const val ACCESSORY_BAR_BUTTON_TEXT_TEST_TAG = "${ACCESSORY_BAR_BUTTON_TEST_TAG}:Text"
internal const val ACCESSORY_BAR_BUTTON_VERTICAL_DIVIDER_TEST_TAG = "${ACCESSORY_BAR_BUTTON_TEST_TAG}:Vertical_divider"
