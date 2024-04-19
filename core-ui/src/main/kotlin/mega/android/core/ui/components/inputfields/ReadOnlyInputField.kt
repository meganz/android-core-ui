package mega.android.core.ui.components.inputfields

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import mega.android.core.ui.R
import mega.android.core.ui.components.MegaText
import mega.android.core.ui.components.divider.StrongDivider
import mega.android.core.ui.components.image.MegaIcon
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.theme.tokens.IconColor
import mega.android.core.ui.theme.tokens.TextColor

@Composable
fun ReadOnlyInputField(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = AppTheme.colors.background.surface1, shape = AppTheme.shapes.small)
    ) {
        content()
    }
}

@Composable
fun ReadOnlyInputFieldItem(
    text: String,
    modifier: Modifier = Modifier,
    label: String? = null,
    optionalLabelText: String? = null,
    showDivider: Boolean = false,
    firstTrailingIcon: @Composable (() -> Unit)? = null,
    secondTrailingIcon: @Composable (() -> Unit)? = null,
    helpText: @Composable (() -> Unit)? = null,
) {
    val spacing = LocalSpacing.current
    Column(
        modifier = modifier
            .padding(spacing.x12),
        verticalArrangement = Arrangement.spacedBy(spacing.x8),
    ) {
        label?.let {
            Row(
                modifier = Modifier.wrapContentWidth(),
                horizontalArrangement = Arrangement.spacedBy(spacing.x8),
            ) {
                Text(
                    text = label,
                    style = AppTheme.typography.titleSmall,
                    color = AppTheme.colors.text.primary
                )
                optionalLabelText?.let {
                    Text(
                        text = optionalLabelText,
                        style = AppTheme.typography.bodyMedium,
                        color = AppTheme.colors.text.secondary
                    )
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(spacing.x16),
        ) {
            MegaText(
                modifier = Modifier.weight(1f),
                text = text,
                textColor = TextColor.Primary,
                style = AppTheme.typography.bodyLarge,
            )

            firstTrailingIcon?.let {
                Box(modifier = Modifier.size(spacing.x20)) {
                    it()
                }
            }

            secondTrailingIcon?.let {
                Box(modifier = Modifier.size(spacing.x20)) {
                    it()
                }
            }
        }

        helpText?.let { it() }
    }
    if (showDivider)
        StrongDivider(modifier = modifier.padding(horizontal = spacing.x12))
}

@Composable
@CombinedThemePreviews
private fun ReadOnlyInputFieldPreview() {
    AndroidThemeForPreviews {
        ReadOnlyInputField {
            ReadOnlyInputFieldItem(
                text = "Text",
                label = "Label",
                optionalLabelText = "Optional",
                showDivider = true,
                firstTrailingIcon = null,
                secondTrailingIcon = null
            )

            ReadOnlyInputFieldItem(
                text = "This is very very long text that takes multiple lines to display and is not truncated",
                label = "Label",
                optionalLabelText = "Optional",
                showDivider = false,
                firstTrailingIcon = {
                    MegaIcon(
                        painter = painterResource(id = R.drawable.ic_alert_circle),
                        contentDescription = "Alert",
                        tint = IconColor.Primary
                    )
                },
                secondTrailingIcon = {
                    MegaIcon(
                        painter = painterResource(id = R.drawable.ic_alert_circle),
                        contentDescription = "Alert",
                        tint = IconColor.Primary
                    )
                }
            )
        }
    }
}