package mega.android.core.ui.components.toggle

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import mega.android.core.ui.R
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme

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
    isEnabled: Boolean = true,
) {
    var checked by remember { mutableStateOf(isChecked) }
    val icon: (@Composable () -> Unit) = if (checked) {
        {
            Icon(
                painter = painterResource(id = R.drawable.ic_check_filled),
                contentDescription = null,
                modifier = Modifier.size(SwitchDefaults.IconSize),
            )
        }
    } else {
        {
            Icon(
                painter = painterResource(id = R.drawable.ic_remove_filled),
                contentDescription = null,
                modifier = Modifier.size(SwitchDefaults.IconSize),
            )
        }
    }

    Switch(
        checked = checked,
        onCheckedChange = {
            checked = !checked
            onCheckedChange(checked)
        },
        modifier = Modifier
            .width(defaultToggleWidth)
            .height(defaultToggleHeight),
        thumbContent = icon,
        enabled = isEnabled,
        colors = SwitchDefaults.colors(
            checkedThumbColor = AppTheme.colors.background.pageBackground,
            checkedTrackColor = AppTheme.colors.components.selectionControl,
            checkedIconColor = AppTheme.colors.components.selectionControl,
            uncheckedThumbColor = AppTheme.colors.components.selectionControl,
            uncheckedTrackColor = AppTheme.colors.background.pageBackground,
            uncheckedBorderColor = AppTheme.colors.components.selectionControl,
            uncheckedIconColor = AppTheme.colors.background.pageBackground,
            disabledCheckedThumbColor = AppTheme.colors.background.pageBackground,
            disabledCheckedTrackColor = AppTheme.colors.border.disabled,
            disabledCheckedIconColor = AppTheme.colors.border.disabled,
            disabledUncheckedThumbColor = AppTheme.colors.border.disabled,
            disabledUncheckedTrackColor = AppTheme.colors.background.pageBackground,
            disabledUncheckedBorderColor = AppTheme.colors.border.disabled,
            disabledUncheckedIconColor = AppTheme.colors.background.pageBackground,
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
