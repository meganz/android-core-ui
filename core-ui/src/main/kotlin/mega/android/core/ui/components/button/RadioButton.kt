package mega.android.core.ui.components.button

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme

@Composable
fun MegaRadioButton(
    identifier: Any,
    onOptionSelected: (Any) -> Unit,
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    enabled: Boolean = true
) {
    val buttonSize = 20.dp
    val containerSize = 48.dp
    val buttonColor = if (enabled) AppTheme.colors.button.primary else AppTheme.colors.border.disabled

    Box(
        modifier = modifier
            // container size is 48.dp based on Material Design guidelines
            .requiredSize(containerSize)
            // ripple size is 40.dp based on Material Design guidelines
            .padding(4.dp)
            .clip(CircleShape)
            .clickable(enabled) {
                onOptionSelected(identifier)
            }
    ) {
        Box(
            modifier = Modifier
                // button size is 20.dp based on Material Design guidelines
                .requiredSize(buttonSize)
                .clip(CircleShape)
                .border(1.dp, buttonColor, CircleShape)
                .align(Alignment.Center)
        ) {
            if (selected) {
                Box(
                    modifier = Modifier
                        .requiredSize(buttonSize / 2)
                        .clip(CircleShape)
                        .background(buttonColor)
                        .align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
@CombinedThemePreviews
private fun MegaRadioButtonPreview() {
    AndroidThemeForPreviews {
        MegaRadioButton(
            identifier = 1,
            onOptionSelected = {}
        )
    }
}

@Composable
@CombinedThemePreviews
private fun MegaRadioButtonPreviewSelected() {
    AndroidThemeForPreviews {
        MegaRadioButton(
            identifier = 1,
            onOptionSelected = {},
            selected = true
        )
    }
}