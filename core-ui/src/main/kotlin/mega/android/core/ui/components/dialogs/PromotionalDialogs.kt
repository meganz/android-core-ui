package mega.android.core.ui.components.dialogs

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintLayoutScope
import androidx.constraintlayout.compose.Dimension
import mega.android.core.ui.R
import mega.android.core.ui.components.button.PrimaryFilledButton
import mega.android.core.ui.components.button.SecondaryFilledButton
import mega.android.core.ui.components.common.PromotionalContent
import mega.android.core.ui.components.common.PromotionalFullImage
import mega.android.core.ui.components.common.PromotionalImage
import mega.android.core.ui.components.common.PromotionalListAttributes
import mega.android.core.ui.components.image.MegaIcon
import mega.android.core.ui.components.toolbar.MegaTopAppBar
import mega.android.core.ui.preview.CombinedThemePreviewsTablet
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.theme.tokens.IconColor

typealias DialogButtonAttribute = Pair<String, () -> Unit>

/**
 * Promo dialog with image in the center below the toolbar.
 * This is specifically designed for tablets
 * Figma: https://www.figma.com/file/5ShSh0nuHWlYYjamj1dz4m/%5BDSN-1630%5D-What%E2%80%99s-new-dialog?node-id=742%3A7109
 */
@Composable
fun PromotionalImageDialog(
    imageUrl: String,
    title: String,
    headline: String,
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

        DialogContent {
            val (toolbar, content, buttonContainer) = createRefs()

            MegaTopAppBar(
                modifier = Modifier
                    .constrainAs(toolbar) {
                        top.linkTo(parent.top, margin = spacing.x8)
                        linkTo(
                            start = parent.start,
                            end = parent.end,
                            startMargin = spacing.x16,
                            endMargin = spacing.x16
                        )
                    },
                title = "",
                navigationIcon = painterResource(id = R.drawable.ic_close),
                onNavigationIconClicked = onDismissRequest
            )

            Column(
                modifier = Modifier
                    .constrainAs(content) {
                        height = Dimension.preferredWrapContent
                        linkTo(
                            top = toolbar.bottom,
                            bottom = buttonContainer.top,
                            topMargin = spacing.x16,
                            bias = 0f
                        )
                        start.linkTo(parent.start, margin = spacing.x16)
                        end.linkTo(parent.end, margin = spacing.x16)
                    }
                    .verticalScroll(scrollState, enabled = true)
            ) {
                PromotionalImage(
                    modifier = Modifier
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
                    .background(AppTheme.colors.background.pageBackground)
                    .constrainAs(buttonContainer) {
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        height = Dimension.fillToConstraints
                    },
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
    imageUrl: String,
    title: String,
    headline: String,
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
        DialogContent {
            val (closeButton, content, buttonContainer) = createRefs()

            Column(
                modifier = Modifier
                    .constrainAs(content) {
                        height = Dimension.preferredWrapContent
                        width = Dimension.fillToConstraints
                        linkTo(
                            top = parent.top,
                            bottom = buttonContainer.top,
                            bias = 0f
                        )
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .verticalScroll(scrollState, enabled = true)
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

            Button(
                modifier = Modifier
                    .size(32.dp)
                    .constrainAs(closeButton) {
                        top.linkTo(content.top, margin = spacing.x16)
                        start.linkTo(content.start, margin = spacing.x16)
                    },
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

            DialogActions(
                modifier = Modifier
                    .background(AppTheme.colors.background.pageBackground)
                    .constrainAs(buttonContainer) {
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        height = Dimension.fillToConstraints
                    },
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
    title: String,
    headline: String,
    @DrawableRes illustration: Int? = null,
    primaryButton: DialogButtonAttribute? = null,
    secondaryButton: DialogButtonAttribute? = null,
    listItems: List<PromotionalListAttributes> = emptyList(),
    contentText: String? = null,
    footer: String? = null,
    onDismissRequest: () -> Unit = {}
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
        DialogContent {
            val (toolbar, content, buttonContainer) = createRefs()

            MegaTopAppBar(
                modifier = Modifier
                    .constrainAs(toolbar) {
                        top.linkTo(parent.top, margin = spacing.x8)
                        linkTo(
                            start = parent.start,
                            end = parent.end,
                            startMargin = spacing.x16,
                            endMargin = spacing.x16
                        )
                    },
                title = "",
                navigationIcon = painterResource(id = R.drawable.ic_close),
                onNavigationIconClicked = onDismissRequest
            )

            Column(
                modifier = Modifier
                    .constrainAs(content) {
                        height = Dimension.preferredWrapContent
                        linkTo(
                            top = toolbar.bottom,
                            bottom = buttonContainer.top,
                            topMargin = spacing.x16,
                            bias = 0f
                        )
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .verticalScroll(scrollState, enabled = true),
                verticalArrangement = Arrangement.Center
            ) {
                if (illustration != null) {
                    Image(
                        modifier = Modifier
                            .padding(bottom = LocalSpacing.current.x32)
                            .size(120.dp)
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
                    .constrainAs(buttonContainer) {
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        height = Dimension.fillToConstraints
                    },
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
    title: String,
    headline: String,
    primaryButton: DialogButtonAttribute? = null,
    secondaryButton: DialogButtonAttribute? = null,
    listItems: List<PromotionalListAttributes> = emptyList(),
    contentText: String? = null,
    footer: String? = null,
    onDismissRequest: () -> Unit = {},
) {
    PromotionalIllustrationDialog(
        title = title,
        headline = headline,
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
    content: @Composable ConstraintLayoutScope.() -> Unit
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = LocalSpacing.current.x48)
            .clip(AppTheme.shapes.small)
            .background(AppTheme.colors.background.pageBackground)
            .navigationBarsPadding(),
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