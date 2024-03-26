package mega.android.core.ui.app.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import mega.android.core.ui.R
import mega.android.core.ui.app.util.Section
import mega.android.core.ui.components.MegaText
import mega.android.core.ui.components.button.PrimaryFilledButton
import mega.android.core.ui.components.checkbox.Checkbox
import mega.android.core.ui.components.common.PromotionalListAttributes
import mega.android.core.ui.components.sheets.PromotionalFullImageSheet
import mega.android.core.ui.components.sheets.PromotionalIllustrationSheet
import mega.android.core.ui.components.sheets.PromotionalImageSheet
import mega.android.core.ui.components.sheets.PromotionalPlainSheet
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.theme.tokens.TextColor

@Composable
fun PromotionalSheetsCatalog(showCloseButton: Boolean, onShowCloseButtonChange: (Boolean) -> Unit) {
    Spacer(modifier = Modifier.height(LocalSpacing.current.x16))

    Section(header = "Promotional Sheets") {
        Row(
            modifier = Modifier.padding(top = LocalSpacing.current.x16),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(checked = showCloseButton, onCheckStateChanged = {
                onShowCloseButtonChange(it)
            })

            MegaText(
                modifier = Modifier.padding(start = LocalSpacing.current.x8),
                text = "Show Close Button",
                textColor = TextColor.Primary
            )
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .heightIn(max = 300.dp)
                .padding(vertical = LocalSpacing.current.x16),
            verticalArrangement = Arrangement.spacedBy(LocalSpacing.current.x16),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {


            item {
                var showPlainSheet by remember { mutableStateOf(false) }

                PrimaryFilledButton(
                    modifier = Modifier.wrapContentSize(),
                    text = "Plain",
                    onClick = {
                        showPlainSheet = true
                    }
                )

                if (showPlainSheet) {
                    PromotionalPlainSheetComponent(
                        showCloseButton = showCloseButton
                    ) {
                        showPlainSheet = false
                    }
                }
            }

            item {
                var showImageSheet by remember { mutableStateOf(false) }

                PrimaryFilledButton(
                    modifier = Modifier.wrapContentSize(),
                    text = "Image",
                    onClick = {
                        showImageSheet = true
                    }
                )

                if (showImageSheet) {
                    PromotionalImageSheetComponent(
                        showCloseButton = showCloseButton
                    ) {
                        showImageSheet = false
                    }
                }
            }

            item {
                var showFullImageSheet by remember { mutableStateOf(false) }

                PrimaryFilledButton(
                    modifier = Modifier.wrapContentSize(),
                    text = "Full Image",
                    onClick = {
                        showFullImageSheet = true
                    }
                )

                if (showFullImageSheet) {
                    PromotionalFullImageSheetComponent(
                        showCloseButton = showCloseButton
                    ) {
                        showFullImageSheet = false
                    }
                }
            }

            item {
                var showIllustrationSheet by remember { mutableStateOf(false) }

                PrimaryFilledButton(
                    modifier = Modifier.wrapContentSize(),
                    text = "Illustration",
                    onClick = {
                        showIllustrationSheet = true
                    }
                )

                if (showIllustrationSheet) {
                    PromotionalIllustrationSheetComponent(
                        showCloseButton = showCloseButton
                    ) {
                        showIllustrationSheet = false
                    }
                }
            }
        }
    }
}

@Composable
private fun PromotionalPlainSheetComponent(
    showCloseButton: Boolean,
    onDismissRequest: () -> Unit
) {
    PromotionalPlainSheet(
        title = "Title",
        headline = "Headline",
        showCloseButton = showCloseButton,
        primaryButton = "Button" to {},
        secondaryButton = "Button 2" to {},
        onDismissRequest = onDismissRequest,
        listItems = listItemSamples,
        footer = "*terms and conditions. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip"
    )
}

@Composable
private fun PromotionalFullImageSheetComponent(
    showCloseButton: Boolean,
    onDismissRequest: () -> Unit
) {
    PromotionalFullImageSheet(
        imageUrl = "https:images.unsplash.com/photo-1579353977828-2a4eab540b9a?q=80&w=1000&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8c2FtcGxlfGVufDB8fDB8fHww",
        title = "Title",
        headline = "Headline",
        showCloseButton = showCloseButton,
        primaryButton = "Button" to {},
        secondaryButton = "Button 2" to {},
        onDismissRequest = onDismissRequest,
        listItems = listItemSamples,
        footer = "*terms and conditions. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip"
    )
}

@Composable
private fun PromotionalIllustrationSheetComponent(
    showCloseButton: Boolean,
    onDismissRequest: () -> Unit
) {
    PromotionalIllustrationSheet(
        illustration = R.drawable.illustration_mega_anniversary,
        title = "Title",
        headline = "Headline",
        showCloseButton = showCloseButton,
        primaryButton = "Button" to {},
        secondaryButton = "Button 2" to {},
        onDismissRequest = onDismissRequest,
        listItems = listItemSamples,
        footer = "*terms and conditions. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip"
    )
}

@Composable
private fun PromotionalImageSheetComponent(
    showCloseButton: Boolean,
    onDismissRequest: () -> Unit
) {
    PromotionalImageSheet(
        imageUrl = "https:images.unsplash.com/photo-1579353977828-2a4eab540b9a?q=80&w=1000&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8c2FtcGxlfGVufDB8fDB8fHww",
        title = "Title",
        headline = "Headline",
        showCloseButton = showCloseButton,
        primaryButton = "Button" to {},
        secondaryButton = "Button 2" to {},
        onDismissRequest = onDismissRequest,
        listItems = listItemSamples,
        footer = "*terms and conditions. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip"
    )
}

private val listItemSamples = IntRange(1, 3).map {
    PromotionalListAttributes(
        title = "Title $it",
        subtitle = "Subtitle $it",
        icon = R.drawable.ic_check_circle
    )
}
