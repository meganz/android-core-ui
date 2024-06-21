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
import androidx.compose.ui.unit.dp
import mega.android.core.ui.R
import mega.android.core.ui.app.util.Section
import mega.android.core.ui.components.button.PrimaryFilledButton
import mega.android.core.ui.components.common.PromotionalListAttributes
import mega.android.core.ui.components.dialogs.PromotionalFullImageDialog
import mega.android.core.ui.components.dialogs.PromotionalIllustrationDialog
import mega.android.core.ui.components.dialogs.PromotionalImageDialog
import mega.android.core.ui.components.dialogs.PromotionalPlainDialog
import mega.android.core.ui.model.IllustrationIconSizeMode
import mega.android.core.ui.theme.spacing.LocalSpacing

@Composable
fun PromotionalDialogsCatalog(
    showCloseButton: Boolean,
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
                        showCloseButton = showCloseButton
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
                        showCloseButton = showCloseButton
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
                        illustrationMode = illustrationsMode
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
    onDismissRequest: () -> Unit
) {
    PromotionalImageDialog(
        imageUrl = "https:images.unsplash.com/photo-1579353977828-2a4eab540b9a?q=80&w=1000&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8c2FtcGxlfGVufDB8fDB8fHww",
        title = "Title",
        headline = "Headline",
        description = "Description",
        showCloseButton = showCloseButton,
        primaryButton = "Button" to {},
        secondaryButton = "Button 2" to {},
        onDismissRequest = onDismissRequest,
        listItems = listItemSamples,
        footer = "*terms and conditions. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip"
    )
}

@Composable
private fun PromotionalFullImageDialogComponent(
    showCloseButton: Boolean,
    onDismissRequest: () -> Unit
) {
    PromotionalFullImageDialog(
        imageUrl = "https:images.unsplash.com/photo-1579353977828-2a4eab540b9a?q=80&w=1000&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8c2FtcGxlfGVufDB8fDB8fHww",
        title = "Title",
        headline = "Headline",
        description = "I’m just an intro title, I don’t have any meaningful content at the moment. I’ll get there",
        showCloseButton = showCloseButton,
        primaryButton = "Button" to {},
        secondaryButton = "Button 2" to {},
        onDismissRequest = onDismissRequest,
        listItems = listItemSamples,
        footer = "*terms and conditions. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip"
    )
}

@Composable
private fun PromotionalIllustrationDialogComponent(
    showCloseButton: Boolean,
    illustrationMode: IllustrationIconSizeMode = IllustrationIconSizeMode.Small,
    onDismissRequest: () -> Unit
) {
    PromotionalIllustrationDialog(
        illustration = R.drawable.illustration_mega_anniversary,
        title = "Title",
        headline = "Headline",
        description = "I’m just an intro title, I don’t have any meaningful content at the moment. I’ll get there",
        showCloseButton = showCloseButton,
        illustrationMode = illustrationMode,
        primaryButton = "Button" to {},
        secondaryButton = "Button 2" to {},
        onDismissRequest = onDismissRequest,
        listItems = listItemSamples,
        footer = "*terms and conditions. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip"
    )
}

@Composable
private fun PromotionalPlainDialogComponent(
    showCloseButton: Boolean,
    illustrationMode: IllustrationIconSizeMode = IllustrationIconSizeMode.Small,
    onDismissRequest: () -> Unit
) {
    PromotionalPlainDialog(
        title = "Title",
        headline = "Headline",
        description = "I’m just an intro title, I don’t have any meaningful content at the moment. I’ll get there",
        showCloseButton = showCloseButton,
        illustrationMode = illustrationMode,
        primaryButton = "Button" to {},
        secondaryButton = "Button 2" to {},
        onDismissRequest = onDismissRequest,
        listItems = listItemSamples,
        footer = "*terms and conditions. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip"
    )
}

private val listItemSamples = IntRange(1, 10).map {
    PromotionalListAttributes(
        title = "Title $it",
        subtitle = "Subtitle $it",
        icon = R.drawable.ic_check_circle
    )
}
