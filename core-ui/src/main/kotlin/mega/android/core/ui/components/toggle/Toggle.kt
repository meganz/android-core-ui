package mega.android.core.ui.components.toggle

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import mega.android.core.ui.R
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.tokens.theme.DSTokens

private val defaultToggleWidth = 52.dp
private val defaultToggleHeight = 32.dp

/**
 * Toggles: Switches the state of a single item on or off
 * @param isChecked         Is Toggle initially checked
 * @param onCheckedChange   What happens if toggle state changes
 * @param isEnabled         Is Toggle enabled (switchable)
 */
@Composable
fun Toggle(
    isChecked: Boolean,
    onCheckedChange: ((Boolean) -> Unit),
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
) {
    Switch(
        checked = isChecked,
        onCheckedChange = {
            onCheckedChange(it)
        },
        modifier = modifier
            .width(defaultToggleWidth)
            .height(defaultToggleHeight),
        thumbContent = {
            Icon(
                painter = painterResource(
                    id = if (isChecked) {
                        R.drawable.ic_check_filled
                    } else {
                        R.drawable.ic_remove_filled
                    }
                ),
                contentDescription = null,
                modifier = Modifier.size(SwitchDefaults.IconSize),
            )
        },
        enabled = isEnabled,
        colors = SwitchDefaults.colors(
            checkedThumbColor = DSTokens.colors.background.pageBackground,
            checkedTrackColor = DSTokens.colors.components.selectionControl,
            checkedIconColor = DSTokens.colors.components.selectionControl,
            uncheckedThumbColor = DSTokens.colors.components.selectionControl,
            uncheckedTrackColor = DSTokens.colors.background.pageBackground,
            uncheckedBorderColor = DSTokens.colors.components.selectionControl,
            uncheckedIconColor = DSTokens.colors.background.pageBackground,
            disabledCheckedThumbColor = DSTokens.colors.background.pageBackground,
            disabledCheckedTrackColor = DSTokens.colors.border.disabled,
            disabledCheckedIconColor = DSTokens.colors.border.disabled,
            disabledUncheckedThumbColor = DSTokens.colors.border.disabled,
            disabledUncheckedTrackColor = DSTokens.colors.background.pageBackground,
            disabledUncheckedBorderColor = DSTokens.colors.border.disabled,
            disabledUncheckedIconColor = DSTokens.colors.background.pageBackground,
        )
    )
}

@CombinedThemePreviews
@Composable
private fun ToggleOffEnabledPreview() {
    AndroidThemeForPreviews {
        Toggle(isChecked = false, onCheckedChange = {})
    }
}

@CombinedThemePreviews
@Composable
private fun ToggleOnEnabledPreview() {
    AndroidThemeForPreviews {
        Toggle(isChecked = true, onCheckedChange = {})
    }
}

@CombinedThemePreviews
@Composable
private fun ToggleOffDisabledPreview() {
    AndroidThemeForPreviews {
        Toggle(isChecked = false, onCheckedChange = {}, isEnabled = false)
    }
}

@CombinedThemePreviews
@Composable
private fun ToggleOnDisabledPreview() {
    AndroidThemeForPreviews {
        Toggle(isChecked = true, onCheckedChange = {}, isEnabled = false)
    }
}
