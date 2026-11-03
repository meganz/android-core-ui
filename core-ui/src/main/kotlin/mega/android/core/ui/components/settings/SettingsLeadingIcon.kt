package mega.android.core.ui.components.settings

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.testTag
import mega.android.core.ui.components.image.MegaIcon
import mega.android.core.ui.theme.values.IconColor
import mega.android.core.ui.theme.values.SupportColor

/**
 * Creates the leading element of a settings item from an optional icon.
 *
 * The icon colour follows the title colour of the item, so that an icon is disabled or
 * destructive only when its title is.
 *
 * @param leadingIcon the icon to show, or null to show no leading element
 * @param key the key of the settings item, used for the test tag
 * @param disabled true to use the disabled icon colour
 * @param destructive true to use the error colour
 * @return the leading element, or null when [leadingIcon] is null
 */
internal fun settingsLeadingElement(
    leadingIcon: Painter?,
    key: String,
    disabled: Boolean = false,
    destructive: Boolean = false,
): (@Composable BoxScope.() -> Unit)? = leadingIcon?.let { painter ->
    {
        val modifier = Modifier
            .align(Alignment.Center)
            .size(SettingsItemConst.leadingIconSize)
            .testTag(SettingsItemConst.leadingIconTag(key))
        when {
            disabled -> MegaIcon(
                painter = painter,
                tint = IconColor.Disabled,
                modifier = modifier,
            )

            destructive -> MegaIcon(
                painter = painter,
                supportTint = SupportColor.Error,
                modifier = modifier,
            )

            else -> MegaIcon(
                painter = painter,
                tint = IconColor.Primary,
                modifier = modifier,
            )
        }
    }
}
