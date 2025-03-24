package mega.android.core.ui.app.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextAlign
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
import mega.android.core.ui.components.text.ContentText
import mega.android.core.ui.components.text.ContentTextDefaults
import mega.android.core.ui.model.IllustrationIconSizeMode
import mega.android.core.ui.model.MegaSpanStyle
import mega.android.core.ui.model.SpanIndicator
import mega.android.core.ui.model.SpanStyleWithAnnotation
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.theme.values.LinkColor
import mega.android.core.ui.theme.values.TextColor

@Composable
fun PromotionalSheetsCatalog(
    footerClickable: Boolean,
    showCloseButton: Boolean,
    illustrationMode: IllustrationIconSizeMode,
    onShowCloseButtonChange: (Boolean) -> Unit,
    onShowClickableFooterChange: (Boolean) -> Unit,
    onIllustrationModeChange: (IllustrationIconSizeMode) -> Unit
) {
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
                modifier = Modifier.padding(horizontal = LocalSpacing.current.x8),
                text = "Show Close Button",
                textColor = TextColor.Primary
            )

            Checkbox(
                checked = illustrationMode == IllustrationIconSizeMode.Large,
                onCheckStateChanged = {
                    onIllustrationModeChange(if (it) IllustrationIconSizeMode.Large else IllustrationIconSizeMode.Small)
                })

            MegaText(
                modifier = Modifier.padding(start = LocalSpacing.current.x8),
                text = "Illustration Mode: ${if (illustrationMode == IllustrationIconSizeMode.Large) "Large" else "Small"}",
                textColor = TextColor.Primary
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(checked = footerClickable, onCheckStateChanged = {
                onShowClickableFooterChange(it)
            })

            MegaText(
                modifier = Modifier.padding(horizontal = LocalSpacing.current.x8),
                text = "Make Description Clickable",
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
                        showCloseButton = showCloseButton,
                        illustrationMode = illustrationMode,
                        showClickableDescription = footerClickable
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
                        showCloseButton = showCloseButton,
                        showClickableDescription = footerClickable
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
                        showCloseButton = showCloseButton,
                        showClickableDescription = footerClickable
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
                        showCloseButton = showCloseButton,
                        illustrationMode = illustrationMode,
                        showClickableDescription = footerClickable
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
    showClickableDescription: Boolean,
    illustrationMode: IllustrationIconSizeMode,
    onDismissRequest: () -> Unit
) {
    PromotionalPlainSheet(
        modifier = Modifier.statusBarsPadding(),
        title = "Title",
        headline = "Headline",
        description = getPromotionalContentDescription(showClickableDescription),
        showCloseButton = showCloseButton,
        illustrationMode = illustrationMode,
        primaryButton = "Button" to {},
        secondaryButton = "Button 2" to {},
        onDismissRequest = onDismissRequest,
        listItems = listItemSamples,
        footer = ContentTextDefaults.description("*terms and conditions. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip")
    )
}

@Composable
private fun PromotionalFullImageSheetComponent(
    showCloseButton: Boolean,
    showClickableDescription: Boolean,
    onDismissRequest: () -> Unit
) {
    PromotionalFullImageSheet(
        modifier = Modifier.statusBarsPadding(),
        image = "https:images.unsplash.com/photo-1579353977828-2a4eab540b9a?q=80&w=1000&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8c2FtcGxlfGVufDB8fDB8fHww",
        title = "Title",
        headline = "Headline",
        description = getPromotionalContentDescription(showClickableDescription),
        showCloseButton = showCloseButton,
        primaryButton = "Button" to {},
        secondaryButton = "Button 2" to {},
        onDismissRequest = onDismissRequest,
        listItems = listItemSamples,
        footer = ContentTextDefaults.description("*terms and conditions. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip")
    )
}

@Composable
private fun PromotionalIllustrationSheetComponent(
    showCloseButton: Boolean,
    showClickableDescription: Boolean,
    illustrationMode: IllustrationIconSizeMode,
    onDismissRequest: () -> Unit
) {
    PromotionalIllustrationSheet(
        modifier = Modifier.statusBarsPadding(),
        illustration = R.drawable.illustration_mega_anniversary,
        title = "Title",
        headline = "Headline",
        description = getPromotionalContentDescription(showClickableDescription),
        showCloseButton = showCloseButton,
        illustrationMode = illustrationMode,
        primaryButton = "Button" to {},
        secondaryButton = "Button 2" to {},
        onDismissRequest = onDismissRequest,
        listItems = listItemSamples,
        footer = ContentTextDefaults.description("*terms and conditions. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip")
    )
}

@Composable
private fun PromotionalImageSheetComponent(
    showCloseButton: Boolean,
    showClickableDescription: Boolean,
    onDismissRequest: () -> Unit
) {
    PromotionalImageSheet(
        modifier = Modifier.statusBarsPadding(),
        image = "https:images.unsplash.com/photo-1579353977828-2a4eab540b9a?q=80&w=1000&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8c2FtcGxlfGVufDB8fDB8fHww",
        title = "Title",
        headline = "Headline",
        description = getPromotionalContentDescription(showClickableDescription),
        showCloseButton = showCloseButton,
        primaryButton = "Button" to {},
        secondaryButton = "Button 2" to {},
        onDismissRequest = onDismissRequest,
        listItems = listItemSamples,
        footer = ContentTextDefaults.description("*terms and conditions. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip")
    )
}

private val listItemSamples = IntRange(1, 3).map {
    PromotionalListAttributes(
        title = "Title $it",
        subtitle = "Subtitle $it",
        icon = R.drawable.ic_check_circle,
        imageUrl = "https://placehold.co/400x400/000000/FFFFFF/png"
    )
}


@Composable
private fun getPromotionalContentDescription(
    showClickableDescription: Boolean
): ContentText {
    val descriptionText = if (showClickableDescription) {
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip \n\n[B]Click here to learn more[/B]"
    } else {
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip"
    }

    val footerTextAlign = if (showClickableDescription) TextAlign.Center else TextAlign.Start
    return ContentTextDefaults.description(
        text = descriptionText,
        textAlign = footerTextAlign,
        spanStyles = if (showClickableDescription) {
            mapOf(
                SpanIndicator('B') to SpanStyleWithAnnotation(
                    MegaSpanStyle.LinkColorStyle(
                        SpanStyle(),
                        LinkColor.Primary
                    ), "d"
                )
            )
        } else {
            emptyMap()
        },
        onClick = {}
    )
}
