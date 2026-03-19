package mega.android.core.ui.components.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewParameter
import kotlinx.coroutines.launch
import mega.android.core.ui.R
import mega.android.core.ui.components.MegaScaffold
import mega.android.core.ui.components.MegaText
import mega.android.core.ui.components.buttonDefaultHeightM3
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

/**
 * A Composable that displays a group of inline buttons, designed to be anchored to the bottom
 * of a screen, typically within a `bottomBar`.
 *
 * This convenience overload creates a layout with a [Button.TextOnlyButtonM3] and a
 * [Button.PrimaryButtonM3], aligned to the end of the container. This should be used by default to follow the design system.
 *
 * It automatically handles padding for system navigation bars and display cutouts to ensure
 * correct placement if [applyInsets] is true.
 *
 * @param primaryButtonText The text to be displayed on the primary action button.
 * @param textOnlyButtonText The text to be displayed on the secondary text-only button.
 * @param onPrimaryButtonClick The callback invoked when the primary button is clicked.
 * @param onTextOnlyButtonClick The callback invoked when the text-only button is clicked.
 * @param modifier The [Modifier] to be applied to the button group container.
 * @param primaryButtonEnabled Controls the enabled state of the primary button. Defaults to `true`.
 * @param applyInsets If `true` (the default), padding for the navigation bar and display
 * cutouts is applied. Set to `false` if padding is already handled at a higher level and not consumed.
 */
@Composable
fun InlineAnchoredButtonGroup(
    primaryButtonText: String,
    textOnlyButtonText: String,
    onPrimaryButtonClick: () -> Unit,
    onTextOnlyButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    primaryButtonEnabled: Boolean = true,
    primaryButtonLeadingIcon: Painter? = null,
    primaryButtonTrailingIcon: Painter? = null,
    applyInsets: Boolean = true,
) {
    CompositionLocalProvider(
        LocalMinimumInteractiveComponentSize provides buttonDefaultHeightM3
    ) {
        InlineAnchoredButtonGroup(
            modifier = modifier,
            applyInsets = applyInsets,
            buttonGroup = listOf(
                {
                    Button.TextOnlyButtonM3(
                        text = textOnlyButtonText,
                        onClick = onTextOnlyButtonClick,
                    )
                },
                {
                    Button.PrimaryButtonM3(
                        text = primaryButtonText,
                        onClick = onPrimaryButtonClick,
                        enabled = primaryButtonEnabled,
                        leadingIcon = primaryButtonLeadingIcon,
                        trailingIcon = primaryButtonTrailingIcon,
                    )
                },
            )
        )
    }
}

@Composable
fun InlineAnchoredButtonGroup(
    buttonGroup: List<@Composable RowScope.() -> Button>,
    modifier: Modifier = Modifier,
    applyInsets: Boolean = true,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(DSTokens.colors.background.surface1)
    ) {
        Row(
            modifier = Modifier
                .then(
                    if (applyInsets) {
                        Modifier
                            .navigationBarsPadding()
                            .windowInsetsPadding(WindowInsets.displayCutout.only(WindowInsetsSides.Horizontal))
                    } else Modifier
                )
                .padding(LocalSpacing.current.x16)
                .align(Alignment.CenterEnd),
            horizontalArrangement = Arrangement.spacedBy(
                LocalSpacing.current.x16,
            ),
            verticalAlignment = Alignment.CenterVertically
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
            primaryButtonEnabled = enabled,
            primaryButtonLeadingIcon = painterResource(id = R.drawable.ic_search_large_medium_thin_outline),
        )
    }
}

@CombinedThemePreviews
@Composable
private fun InlineAnchoredButtonGroupScaffoldPreview(
) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    AndroidThemeForPreviews {
        MegaScaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            bottomBar = {
                InlineAnchoredButtonGroup(
                    primaryButtonText = "Accept",
                    textOnlyButtonText = "Cancel",
                    onPrimaryButtonClick = {
                        scope.launch {
                            snackbarHostState.showSnackbar("Accept button clicked")
                        }
                    },
                    onTextOnlyButtonClick = {
                        scope.launch {
                            snackbarHostState.showSnackbar("Cancel button clicked")
                        }
                    },
                )
            }
        ) {}
    }
}