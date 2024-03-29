package mega.android.core.ui.components.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import mega.android.core.ui.components.MegaText
import mega.android.core.ui.components.divider.StrongDivider
import mega.android.core.ui.model.Button
import mega.android.core.ui.model.LocalButton
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.theme.tokens.TextColor

@Composable
fun AnchoredButtonGroup(
    buttonGroup: List<@Composable ColumnScope.() -> Button>,
    modifier: Modifier = Modifier,
    title: String? = null,
    withDivider: Boolean = false
) {
    val spacing = LocalSpacing.current

    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        if (withDivider) {
            StrongDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
            )
        }

        Column(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(all = spacing.x16),
            verticalArrangement = Arrangement.spacedBy(spacing.x16),
        ) {
            title?.let {
                MegaText(
                    modifier = Modifier.fillMaxWidth(),
                    text = it,
                    textColor = TextColor.Primary,
                    style = AppTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
            }

            buttonGroup.forEach { button ->
                button(this@Column).LocalButton()
            }
        }
    }
}

@CombinedThemePreviews
@Composable
private fun AnchoredButtonGroupPreview() {
    AndroidThemeForPreviews {
        AnchoredButtonGroup(
            title = "Anchored button group",
            buttonGroup = listOf(
                {
                    Button.PrimaryButton(
                        text = "Primary",
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                {
                    Button.SecondaryButton(
                        text = "Secondary",
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                {
                    Button.TextOnlyButton(
                        text = "Text Only",
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            ),
            withDivider = true
        )
    }
}