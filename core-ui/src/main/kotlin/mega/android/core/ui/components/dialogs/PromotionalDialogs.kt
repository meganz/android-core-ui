package mega.android.core.ui.components.dialogs

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import mega.android.core.ui.R
import mega.android.core.ui.components.button.PrimaryFilledButton
import mega.android.core.ui.components.button.SecondaryFilledButton
import mega.android.core.ui.components.common.PromotionalContent
import mega.android.core.ui.components.common.PromotionalFullImage
import mega.android.core.ui.components.common.PromotionalImage
import mega.android.core.ui.components.common.PromotionalListAttributes
import mega.android.core.ui.components.image.MegaIcon
import mega.android.core.ui.components.toolbar.MegaTopAppBar
import mega.android.core.ui.model.IllustrationIconSizeMode
import mega.android.core.ui.preview.CombinedThemePreviewsTablet
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.theme.values.IconColor

typealias DialogButtonAttribute = Pair<String, () -> Unit>

private val DIALOG_MAXIMUM_WIDTH = 543.dp
private val DIALOG_MAXIMUM_HEIGHT = 600.dp

/**
 * Promo dialog with image in the center below the toolbar.
 * This is specifically designed for tablets
 * Figma: https://www.figma.com/file/5ShSh0nuHWlYYjamj1dz4m/%5BDSN-1630%5D-What%E2%80%99s-new-dialog?node-id=742%3A7109
 */
@Composable
fun PromotionalImageDialog(
    modifier: Modifier = Modifier,
    imageUrl: String,
    title: String,
    headline: String,
    showCloseButton: Boolean = true,
    primaryButton: DialogButtonAttribute? = null,
    secondaryButton: DialogButtonAttribute? = null,
    listItems: List<PromotionalListAttributes> = emptyList(),
    contentText: String? = null,
    footer: String? = null,
    onDismissRequest: () -> Unit = {}
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(decorFitsSystemWindows = false)
    ) {
        val spacing = LocalSpacing.current
        val scrollState = rememberScrollState()
        var isScrollable by rememberSaveable { mutableStateOf(false) }

        LaunchedEffect(scrollState) {
            isScrollable = scrollState.canScrollForward
        }

        DialogContent(modifier = modifier) {
            MegaTopAppBar(
                modifier = Modifier.wrapContentHeight(),
                title = "",
                navigationIcon = if (showCloseButton) painterResource(id = R.drawable.ic_close) else null,
                onNavigationIconClicked = onDismissRequest
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(scrollState, enabled = true)
            ) {
                PromotionalImage(
                    modifier = Modifier
                        .padding(horizontal = spacing.x16)
                        .widthIn(max = 328.dp)
                        .align(alignment = Alignment.CenterHorizontally),
                    imageUrl = imageUrl,
                    description = title
                )

                PromotionalContent(
                    modifier = Modifier.padding(top = spacing.x24),
                    title = title,
                    headline = headline,
                    contentText = contentText,
                    footer = footer,
                    listItems = listItems
                )
            }

            DialogActions(
                modifier = Modifier
                    .background(AppTheme.colors.background.pageBackground),
                primaryButton = primaryButton,
                secondaryButton = secondaryButton,
                isDividerVisible = isScrollable
            )
        }
    }
}

/**
 * Promotional dialog with full image that occupies the toolbar
 * This is specifically designed for tablets
 * Figma:
 */
@Composable
fun PromotionalFullImageDialog(
    modifier: Modifier = Modifier,
    imageUrl: String,
    title: String,
    headline: String,
    showCloseButton: Boolean = true,
    primaryButton: DialogButtonAttribute? = null,
    secondaryButton: DialogButtonAttribute? = null,
    listItems: List<PromotionalListAttributes> = emptyList(),
    contentText: String? = null,
    footer: String? = null,
    onDismissRequest: () -> Unit = {},
) {
    val spacing = LocalSpacing.current
    val scrollState = rememberScrollState()
    var isScrollable by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(scrollState) {
        isScrollable = scrollState.canScrollForward
    }

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(decorFitsSystemWindows = false)
    ) {
        DialogContent(modifier = modifier) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(scrollState, enabled = true)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    PromotionalFullImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)),
                        imageUrl = imageUrl,
                        description = title
                    )

                    PromotionalContent(
                        modifier = Modifier
                            .padding(top = LocalSpacing.current.x32),
                        title = title,
                        headline = headline,
                        listItems = listItems,
                        contentText = contentText,
                        footer = footer
                    )
                }

                if (showCloseButton) {
                    Button(
                        modifier = Modifier
                            .padding(top = spacing.x16, start = spacing.x16)
                            .size(32.dp),
                        onClick = onDismissRequest,
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Black.copy(alpha = 0.5f),
                            contentColor = AppTheme.colors.icon.onColor
                        ),
                        contentPadding = PaddingValues(4.dp)
                    ) {
                        MegaIcon(
                            painter = painterResource(id = R.drawable.ic_close),
                            tint = IconColor.OnColor
                        )
                    }
                }
            }

            DialogActions(
                modifier = Modifier
                    .background(AppTheme.colors.background.pageBackground)
                    .wrapContentHeight(),
                primaryButton = primaryButton,
                secondaryButton = secondaryButton,
                isDividerVisible = isScrollable
            )
        }
    }
}

/**
 * Promotional sheet with illustration in the center below the toolbar
 * This is specifically designed for tablets
 * Figma: https://www.figma.com/file/5ShSh0nuHWlYYjamj1dz4m/%5BDSN-1630%5D-What%E2%80%99s-new-dialog?node-id=742%3A7290
 */
@Composable
fun PromotionalIllustrationDialog(
    modifier: Modifier = Modifier,
    title: String,
    headline: String,
    showCloseButton: Boolean = true,
    @DrawableRes illustration: Int? = null,
    illustrationMode: IllustrationIconSizeMode = IllustrationIconSizeMode.Small,
    primaryButton: DialogButtonAttribute? = null,
    secondaryButton: DialogButtonAttribute? = null,
    listItems: List<PromotionalListAttributes> = emptyList(),
    contentText: String? = null,
    footer: String? = null,
    onDismissRequest: () -> Unit = {}
) {
    val scrollState = rememberScrollState()
    var isScrollable by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(scrollState) {
        isScrollable = scrollState.canScrollForward
    }

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(decorFitsSystemWindows = false)
    ) {
        DialogContent(modifier = modifier) {
            MegaTopAppBar(
                modifier = Modifier.wrapContentHeight(),
                title = "",
                navigationIcon = if (showCloseButton) painterResource(id = R.drawable.ic_close) else null,
                onNavigationIconClicked = onDismissRequest
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(scrollState, enabled = true),
                verticalArrangement = Arrangement.Center
            ) {
                if (illustration != null) {
                    Image(
                        modifier = Modifier
                            .padding(bottom = LocalSpacing.current.x32)
                            .size(illustrationMode.size)
                            .align(Alignment.CenterHorizontally),
                        painter = painterResource(id = illustration),
                        contentDescription = title
                    )
                }

                PromotionalContent(
                    modifier = Modifier,
                    title = title,
                    headline = headline,
                    listItems = listItems,
                    contentText = contentText,
                    footer = footer
                )
            }

            DialogActions(
                modifier = Modifier
                    .background(AppTheme.colors.background.pageBackground)
                    .wrapContentHeight(),
                primaryButton = primaryButton,
                secondaryButton = secondaryButton,
                isDividerVisible = isScrollable
            )
        }
    }
}

/**
 * Promotional dialog with only text and buttons.
 * This is specifically designed for tablets
 * Figma: https://www.figma.com/file/5ShSh0nuHWlYYjamj1dz4m/%5BDSN-1630%5D-What%E2%80%99s-new-dialog?node-id=742%3A6825
 */
@Composable
fun PromotionalPlainDialog(
    modifier: Modifier = Modifier,
    title: String,
    headline: String,
    showCloseButton: Boolean = true,
    illustrationMode: IllustrationIconSizeMode = IllustrationIconSizeMode.Small,
    primaryButton: DialogButtonAttribute? = null,
    secondaryButton: DialogButtonAttribute? = null,
    listItems: List<PromotionalListAttributes> = emptyList(),
    contentText: String? = null,
    footer: String? = null,
    onDismissRequest: () -> Unit = {},
) {
    PromotionalIllustrationDialog(
        modifier = modifier,
        title = title,
        headline = headline,
        showCloseButton = showCloseButton,
        illustrationMode = illustrationMode,
        primaryButton = primaryButton,
        secondaryButton = secondaryButton,
        listItems = listItems,
        contentText = contentText,
        footer = footer,
        onDismissRequest = onDismissRequest,
        illustration = null
    )
}

@Composable
private fun DialogContent(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {

    val configuration = LocalConfiguration.current
    val isLandscapeDevice by remember {
        mutableStateOf(configuration.screenWidthDp >= configuration.screenHeightDp)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = if (isLandscapeDevice) LocalSpacing.current.x48 else LocalSpacing.current.x80)
            .widthIn(max = DIALOG_MAXIMUM_WIDTH)
            .heightIn(max = DIALOG_MAXIMUM_HEIGHT)
            .clip(AppTheme.shapes.small)
            .background(AppTheme.colors.background.pageBackground),
        content = content
    )
}

@Composable
private fun DialogActions(
    modifier: Modifier,
    primaryButton: DialogButtonAttribute? = null,
    secondaryButton: DialogButtonAttribute? = null,
    isDividerVisible: Boolean = false
) {
    val borderStrokeColor = AppTheme.colors.border.strong

    Row(
        modifier = modifier
            .then(
                if (isDividerVisible) {
                    modifier.drawBehind {
                        drawLine(
                            color = borderStrokeColor,
                            start = Offset(0f, 0f),
                            end = Offset(size.width, 0f),
                            strokeWidth = 1.dp.toPx()
                        )
                    }
                } else {
                    modifier
                }
            )
            .padding(
                vertical = LocalSpacing.current.x24,
                horizontal = LocalSpacing.current.x16,
            ),
        horizontalArrangement = Arrangement.spacedBy(LocalSpacing.current.x24),
    ) {
        if (secondaryButton != null) {
            SecondaryFilledButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                text = secondaryButton.first,
                onClick = secondaryButton.second
            )
        }

        if (primaryButton != null) {
            PrimaryFilledButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                text = primaryButton.first,
                onClick = primaryButton.second
            )
        }
    }
}

@CombinedThemePreviewsTablet
@Composable
private fun PromotionalImageDialogComponent() {
    AndroidThemeForPreviews {
        PromotionalImageDialog(
            imageUrl = "https:images.unsplash.com/photo-1579353977828-2a4eab540b9a?q=80&w=1000&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8c2FtcGxlfGVufDB8fDB8fHww",
            title = "Title",
            headline = "Headline",
            primaryButton = "Button" to {},
            secondaryButton = "Button 2" to {},
            onDismissRequest = {},
            listItems = listItemSamples,
            footer = "*terms and conditions. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip"
        )
    }
}

@CombinedThemePreviewsTablet
@Composable
private fun PromotionalFullImageDialogComponent() {
    AndroidThemeForPreviews {
        PromotionalFullImageDialog(
            imageUrl = "https:images.unsplash.com/photo-1579353977828-2a4eab540b9a?q=80&w=1000&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8c2FtcGxlfGVufDB8fDB8fHww",
            title = "Title",
            headline = "Headline",
            primaryButton = "Button" to {},
            secondaryButton = "Button 2" to {},
            onDismissRequest = {},
            listItems = listItemSamples,
            footer = "*terms and conditions. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip"
        )
    }
}

@CombinedThemePreviewsTablet
@Composable
private fun PromotionalIllustrationDialogComponent() {
    AndroidThemeForPreviews {
        PromotionalIllustrationDialog(
            illustration = R.drawable.illustration_mega_anniversary,
            title = "Title",
            headline = "Headline",
            primaryButton = "Button" to {},
            secondaryButton = "Button 2" to {},
            onDismissRequest = {},
            listItems = listItemSamples,
            footer = "*terms and conditions. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip"
        )
    }
}

@CombinedThemePreviewsTablet
@Composable
private fun PromotionalPlainDialogComponent() {
    AndroidThemeForPreviews {
        PromotionalPlainDialog(
            title = "Title",
            headline = "Headline",
            primaryButton = "Button" to {},
            secondaryButton = "Button 2" to {},
            onDismissRequest = {},
            listItems = listItemSamples,
            footer = "*terms and conditions. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip"
        )
    }
}

private val listItemSamples = IntRange(1, 10).map {
    PromotionalListAttributes(
        title = "Title $it",
        subtitle = "Subtitle $it",
        icon = R.drawable.ic_check_circle
    )
}