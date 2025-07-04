package mega.android.core.ui.app.component

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import mega.android.core.ui.R
import mega.android.core.ui.app.util.Section
import mega.android.core.ui.components.button.PrimaryFilledButton
import mega.android.core.ui.components.common.PromotionalListAttributes
import mega.android.core.ui.components.dialogs.PromotionalFullImageDialog
import mega.android.core.ui.components.dialogs.PromotionalIllustrationDialog
import mega.android.core.ui.components.dialogs.PromotionalImageDialog
import mega.android.core.ui.components.dialogs.PromotionalPlainDialog
import mega.android.core.ui.components.text.ContentText
import mega.android.core.ui.components.text.ContentTextDefaults
import mega.android.core.ui.model.IllustrationIconSizeMode
import mega.android.core.ui.model.MegaSpanStyle
import mega.android.core.ui.model.SpanIndicator
import mega.android.core.ui.model.SpanStyleWithAnnotation
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.theme.values.LinkColor

@Composable
fun PromotionalDialogsCatalog(
    showCloseButton: Boolean,
    showClickableDescription: Boolean,
    illustrationsMode: IllustrationIconSizeMode,
) {
    Spacer(modifier = Modifier.height(LocalSpacing.current.x16))

    Section(header = "Promotional Dialog") {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .heightIn(max = 300.dp)
                .padding(vertical = LocalSpacing.current.x16),
            verticalArrangement = Arrangement.spacedBy(LocalSpacing.current.x16),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            item {
                var showPlainDialog by remember { mutableStateOf(false) }

                PrimaryFilledButton(
                    modifier = Modifier.wrapContentSize(),
                    text = "Plain",
                    onClick = {
                        showPlainDialog = true
                    }
                )

                if (showPlainDialog) {
                    PromotionalPlainDialogComponent(
                        showCloseButton = showCloseButton,
                        illustrationMode = illustrationsMode,
                        showClickableDescription = showClickableDescription,
                        onDismissRequest = {
                            showPlainDialog = false
                        }
                    )
                }
            }

            item {
                var showImageDialog by remember { mutableStateOf(false) }

                PrimaryFilledButton(
                    modifier = Modifier.wrapContentSize(),
                    text = "Image",
                    onClick = {
                        showImageDialog = true
                    }
                )

                if (showImageDialog) {
                    PromotionalImageDialogComponent(
                        showCloseButton = showCloseButton,
                        showClickableDescription = showClickableDescription
                    ) {
                        showImageDialog = false
                    }
                }
            }

            item {
                var showFullImageDialog by remember { mutableStateOf(false) }

                PrimaryFilledButton(
                    modifier = Modifier.wrapContentSize(),
                    text = "Full Image",
                    onClick = {
                        showFullImageDialog = true
                    }
                )

                if (showFullImageDialog) {
                    PromotionalFullImageDialogComponent(
                        showCloseButton = showCloseButton,
                        showClickableDescription = showClickableDescription
                    ) {
                        showFullImageDialog = false
                    }
                }
            }

            item {
                var showIllustrationDialog by remember { mutableStateOf(false) }

                PrimaryFilledButton(
                    modifier = Modifier.wrapContentSize(),
                    text = "Illustration",
                    onClick = {
                        showIllustrationDialog = true
                    }
                )

                if (showIllustrationDialog) {
                    PromotionalIllustrationDialogComponent(
                        showCloseButton = showCloseButton,
                        illustrationMode = illustrationsMode,
                        showClickableDescription = showClickableDescription
                    ) {
                        showIllustrationDialog = false
                    }
                }
            }
        }
    }
}

@Composable
private fun PromotionalImageDialogComponent(
    showCloseButton: Boolean,
    showClickableDescription: Boolean,
    onDismissRequest: () -> Unit
) {

    PromotionalImageDialog(
        image = "https:images.unsplash.com/photo-1579353977828-2a4eab540b9a?q=80&w=1000&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8c2FtcGxlfGVufDB8fDB8fHww",
        title = "Title",
        headline = "Headline",
        description = getPromotionalContentDescription(showClickableDescription),
        showCloseButton = showCloseButton,
        primaryButton = "Button" to {},
        secondaryButton = "Button 2" to {},
        onDismissRequest = onDismissRequest,
        listItems = listItemSamples,
        footer = ContentTextDefaults.description("*terms and conditions. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip"),
    )
}

@Composable
private fun PromotionalFullImageDialogComponent(
    showCloseButton: Boolean,
    showClickableDescription: Boolean,
    onDismissRequest: () -> Unit
) {
    PromotionalFullImageDialog(
        image = "https:images.unsplash.com/photo-1579353977828-2a4eab540b9a?q=80&w=1000&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8c2FtcGxlfGVufDB8fDB8fHww",
        title = "Title",
        headline = "Headline",
        description = getPromotionalContentDescription(showClickableDescription),
        showCloseButton = showCloseButton,
        primaryButton = "Button" to {},
        secondaryButton = "Button 2" to {},
        onDismissRequest = onDismissRequest,
        listItems = listItemSamples,
        footer = ContentTextDefaults.description("*terms and conditions. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip"),
    )
}

@Composable
private fun PromotionalIllustrationDialogComponent(
    showCloseButton: Boolean,
    showClickableDescription: Boolean,
    illustrationMode: IllustrationIconSizeMode = IllustrationIconSizeMode.Small,
    onDismissRequest: () -> Unit
) {
    PromotionalIllustrationDialog(
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
        footer = ContentTextDefaults.description("*terms and conditions. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip"),
    )
}

@Composable
private fun PromotionalPlainDialogComponent(
    showCloseButton: Boolean,
    showClickableDescription: Boolean,
    illustrationMode: IllustrationIconSizeMode = IllustrationIconSizeMode.Small,
    onDismissRequest: () -> Unit
) {
    PromotionalPlainDialog(
        title = "Title",
        headline = "Headline",
        description = getPromotionalContentDescription(showClickableDescription),
        showCloseButton = showCloseButton,
        illustrationMode = illustrationMode,
        primaryButton = "Button" to {},
        secondaryButton = "Button 2" to {},
        onDismissRequest = onDismissRequest,
        listItems = listItemSamples,
        footer = ContentTextDefaults.description("*terms and conditions. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip"),
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

private val listItemSamples = IntRange(1, 10).map {
    PromotionalListAttributes(
        title = "Title $it",
        subtitle = "Subtitle $it",
        icon = R.drawable.ic_check_circle_medium_thin_outline,
        imageUrl = "https://placehold.co/400x400/000000/FFFFFF/png"
    )
}
