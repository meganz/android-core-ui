package mega.android.core.ui.components.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewParameter
import mega.android.core.ui.components.MegaText
import mega.android.core.ui.components.divider.StrongDivider
import mega.android.core.ui.model.Button
import mega.android.core.ui.model.LocalButton
import mega.android.core.ui.preview.BooleanProvider
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.theme.values.TextColor
import mega.android.core.ui.tokens.theme.DSTokens

@Composable
fun HorizontalAnchoredButtonGroup(
    buttonGroup: List<@Composable RowScope.() -> Button>,
    modifier: Modifier = Modifier,
    withDivider: Boolean = false,
    innerPadding: PaddingValues = PaddingValues(all = LocalSpacing.current.x16),
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

        Row(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(innerPadding),
            horizontalArrangement = Arrangement.spacedBy(spacing.x16),
        ) {
            buttonGroup.forEach { button ->
                button(this@Row).LocalButton()
            }
        }
    }
}

@Composable
fun AnchoredButtonGroup(
    buttonGroup: List<@Composable ColumnScope.() -> Button>,
    modifier: Modifier = Modifier,
    title: String? = null,
    withDivider: Boolean = false,
    innerPadding: PaddingValues = PaddingValues(all = LocalSpacing.current.x16),
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
                .padding(innerPadding),
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

@Composable
fun InlineAnchoredButtonGroup(
    primaryButtonText: String,
    textOnlyButtonText: String,
    onPrimaryButtonClick: () -> Unit,
    onTextOnlyButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    primaryButtonEnabled: Boolean = true,
) {
    InlineAnchoredButtonGroup(
        modifier = modifier,
        buttonGroup = listOf(
            {
                Button.TextOnlyButtonM3(
                    text = textOnlyButtonText,
                    onClick = onPrimaryButtonClick,
                )
            },
            {
                Button.PrimaryButton(
                    text = primaryButtonText,
                    onClick = onTextOnlyButtonClick,
                    enabled = primaryButtonEnabled,
                )
            },
        )
    )
}

@Composable
fun InlineAnchoredButtonGroup(
    buttonGroup: List<@Composable RowScope.() -> Button>,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(DSTokens.colors.background.surface1)
    ) {
        Row(
            modifier = Modifier
                .padding(LocalSpacing.current.x16)
                .align(Alignment.CenterEnd),
            horizontalArrangement = Arrangement.spacedBy(
                LocalSpacing.current.x16,
            )
        ) {
            buttonGroup.forEach { button ->
                button(this@Row).LocalButton()
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

@CombinedThemePreviews
@Composable
private fun HorizontalAnchoredButtonGroupPreview() {
    AndroidThemeForPreviews {
        HorizontalAnchoredButtonGroup(
            buttonGroup = listOf(
                {
                    Button.PrimaryButton(
                        text = "Primary",
                        modifier = Modifier.weight(1f)
                    )
                },
                {
                    Button.SecondaryButton(
                        text = "Secondary",
                        modifier = Modifier.weight(1f)
                    )
                },
            ),
            withDivider = true
        )
    }
}

@CombinedThemePreviews
@Composable
private fun InlineAnchoredButtonGroupPreview(
    @PreviewParameter(BooleanProvider::class) enabled: Boolean,
) {
    AndroidThemeForPreviews {
        InlineAnchoredButtonGroup(
            primaryButtonText = "Accept",
            textOnlyButtonText = "Cancel",
            onPrimaryButtonClick = {},
            onTextOnlyButtonClick = {},
            primaryButtonEnabled = enabled
        )
    }
}