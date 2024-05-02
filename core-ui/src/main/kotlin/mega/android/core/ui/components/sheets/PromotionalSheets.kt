package mega.android.core.ui.components.sheets

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import kotlinx.coroutines.launch
import mega.android.core.ui.R
import mega.android.core.ui.components.button.PrimaryFilledButton
import mega.android.core.ui.components.button.SecondaryFilledButton
import mega.android.core.ui.components.common.PromotionalContent
import mega.android.core.ui.components.common.PromotionalFullImage
import mega.android.core.ui.components.common.PromotionalImage
import mega.android.core.ui.components.common.PromotionalListAttributes
import mega.android.core.ui.components.image.MegaIcon
import mega.android.core.ui.components.toolbar.MegaTopAppBar
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.theme.tokens.IconColor


typealias SheetButtonAttribute = Pair<String, () -> Unit>

private val bottomSheetShape = RoundedCornerShape(
    topStart = 24.dp,
    topEnd = 24.dp,
)

/**
 * Promotional sheet with image in the center below the toolbar.
 * Figma: https://www.figma.com/file/5ShSh0nuHWlYYjamj1dz4m/%5BDSN-1630%5D-What%E2%80%99s-new-dialog?node-id=823%3A8511
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PromotionalImageSheet(
    imageUrl: String,
    title: String,
    headline: String,
    showCloseButton: Boolean = true,
    primaryButton: SheetButtonAttribute? = null,
    secondaryButton: SheetButtonAttribute? = null,
    listItems: List<PromotionalListAttributes> = emptyList(),
    contentText: String? = null,
    footer: String? = null,
    onDismissRequest: () -> Unit = {},
    isVisible: Boolean = false
) {
    val coroutineScope = rememberCoroutineScope()
    val spacing = LocalSpacing.current
    val density = LocalDensity.current
    val sheetState = remember {
        SheetState(
            skipPartiallyExpanded = true,
            density = density,
            initialValue = SheetValue.Expanded
        )
    }
    val scrollState = rememberScrollState()
    var isScrollable by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(scrollState) {
        isScrollable = scrollState.canScrollForward
    }

    ModalBottomSheetScaffold(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        isVisible = isVisible
    ) {
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            val (toolbar, content, buttonContainer) = createRefs()

            MegaTopAppBar(
                modifier = Modifier
                    .constrainAs(toolbar) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start, margin = spacing.x16)
                        end.linkTo(parent.end, margin = spacing.x16)
                    },
                title = "",
                navigationIcon = if (showCloseButton) painterResource(id = R.drawable.ic_close) else null,
                onNavigationIconClicked = {
                    coroutineScope.launch {
                        sheetState.hide()
                        onDismissRequest()
                    }
                }
            )

            Column(
                modifier = Modifier
                    .constrainAs(content) {
                        height = Dimension.preferredWrapContent
                        width = Dimension.fillToConstraints
                        top.linkTo(toolbar.bottom, margin = spacing.x16)
                        start.linkTo(parent.start, margin = spacing.x16)
                        end.linkTo(parent.end, margin = spacing.x16)
                        bottom.linkTo(buttonContainer.top)
                    }
                    .verticalScroll(scrollState, enabled = true)
            ) {
                PromotionalImage(
                    modifier = Modifier
                        .fillMaxWidth(),
                    imageUrl = imageUrl,
                    description = title
                )

                PromotionalContent(
                    modifier = Modifier.padding(top = spacing.x32),
                    title = title,
                    headline = headline,
                    listItems = listItems,
                    contentText = contentText,
                    footer = footer
                )
            }

            val bottomPadding =
                WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()

            SheetActions(
                modifier = Modifier
                    .constrainAs(buttonContainer) {
                        bottom.linkTo(parent.bottom, margin = bottomPadding)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        height = Dimension.wrapContent
                    },
                primaryButton = primaryButton,
                secondaryButton = secondaryButton,
                isDividerVisible = isScrollable
            )
        }
    }
}

/**
 * Promo sheet with full image that occupies the toolbar
 * Figma: https://www.figma.com/file/5ShSh0nuHWlYYjamj1dz4m/%5BDSN-1630%5D-What%E2%80%99s-new-dialog?type=design&node-id=823-8509
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PromotionalFullImageSheet(
    imageUrl: String,
    title: String,
    headline: String,
    showCloseButton: Boolean = true,
    primaryButton: SheetButtonAttribute? = null,
    secondaryButton: SheetButtonAttribute? = null,
    listItems: List<PromotionalListAttributes> = emptyList(),
    contentText: String? = null,
    footer: String? = null,
    onDismissRequest: () -> Unit = {},
    isVisible: Boolean = false
) {
    val spacing = LocalSpacing.current
    val density = LocalDensity.current
    val coroutineScope = rememberCoroutineScope()
    val sheetState = remember {
        SheetState(
            skipPartiallyExpanded = true,
            density = density,
            initialValue = SheetValue.Expanded
        )
    }
    val scrollState = rememberScrollState()
    var isScrollable by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(scrollState) {
        isScrollable = scrollState.canScrollForward
    }

    ModalBottomSheetScaffold(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        isVisible = isVisible
    ) {
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
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
                        .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)),
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
                        .size(32.dp)
                        .constrainAs(closeButton) {
                            top.linkTo(content.top, margin = spacing.x16)
                            start.linkTo(content.start, margin = spacing.x16)
                        },
                    onClick = {
                        coroutineScope.launch {
                            sheetState.hide()
                            onDismissRequest()
                        }
                    },
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

            val bottomPadding =
                WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()

            SheetActions(
                modifier = Modifier
                    .constrainAs(buttonContainer) {
                        bottom.linkTo(parent.bottom, margin = bottomPadding)
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
 * Figma: https://www.figma.com/file/5ShSh0nuHWlYYjamj1dz4m/%5BDSN-1630%5D-What%E2%80%99s-new-dialog?node-id=823%3A8511
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PromotionalIllustrationSheet(
    title: String,
    headline: String,
    showCloseButton: Boolean = true,
    @DrawableRes illustration: Int? = null,
    primaryButton: SheetButtonAttribute? = null,
    secondaryButton: SheetButtonAttribute? = null,
    listItems: List<PromotionalListAttributes> = emptyList(),
    contentText: String? = null,
    footer: String? = null,
    onDismissRequest: () -> Unit = {},
    isVisible: Boolean = false
) {
    val coroutineScope = rememberCoroutineScope()
    val spacing = LocalSpacing.current
    val density = LocalDensity.current
    val sheetState = remember {
        SheetState(
            skipPartiallyExpanded = true,
            density = density,
            initialValue = SheetValue.Expanded
        )
    }
    val scrollState = rememberScrollState()
    var isScrollable by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(scrollState) {
        isScrollable = scrollState.canScrollForward
    }

    ModalBottomSheetScaffold(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        isVisible = isVisible
    ) {
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            val (toolbar, content, buttonContainer) = createRefs()

            MegaTopAppBar(
                modifier = Modifier
                    .constrainAs(toolbar) {
                        top.linkTo(parent.top, margin = spacing.x8)
                        start.linkTo(parent.start, margin = spacing.x16)
                        end.linkTo(parent.end, margin = spacing.x16)
                    },
                title = "",
                navigationIcon = if (showCloseButton) painterResource(id = R.drawable.ic_close) else null,
                onNavigationIconClicked = {
                    coroutineScope.launch {
                        sheetState.hide()
                        onDismissRequest()
                    }
                }
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

            val bottomPadding =
                WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()

            SheetActions(
                modifier = Modifier
                    .constrainAs(buttonContainer) {
                        bottom.linkTo(parent.bottom, margin = bottomPadding)
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
 * Promotional sheet with only text and buttons.
 * Figma: https://www.figma.com/file/5ShSh0nuHWlYYjamj1dz4m/%5BDSN-1630%5D-What%E2%80%99s-new-dialog?node-id=823%3A8511
 */
@Composable
fun PromotionalPlainSheet(
    title: String,
    headline: String,
    showCloseButton: Boolean = true,
    primaryButton: SheetButtonAttribute? = null,
    secondaryButton: SheetButtonAttribute? = null,
    listItems: List<PromotionalListAttributes> = emptyList(),
    contentText: String? = null,
    footer: String? = null,
    onDismissRequest: () -> Unit = {},
    isVisible: Boolean = false
) {
    PromotionalIllustrationSheet(
        title = title,
        headline = headline,
        showCloseButton = showCloseButton,
        primaryButton = primaryButton,
        secondaryButton = secondaryButton,
        listItems = listItems,
        contentText = contentText,
        footer = footer,
        onDismissRequest = onDismissRequest,
        isVisible = isVisible,
        illustration = null
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ModalBottomSheetScaffold(
    onDismissRequest: () -> Unit,
    sheetState: SheetState,
    isVisible: Boolean = false,
    content: @Composable ColumnScope.() -> Unit,
) {
    LaunchedEffect(isVisible) {
        if (isVisible) {
            sheetState.expand()
        } else {
            sheetState.hide()
        }
    }

    ModalBottomSheet(
        modifier = Modifier.statusBarsPadding(),
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        containerColor = AppTheme.colors.background.pageBackground,
        dragHandle = null,
        shape = bottomSheetShape,
        content = content
    )
}

@Composable
private fun SheetActions(
    modifier: Modifier,
    primaryButton: SheetButtonAttribute? = null,
    secondaryButton: SheetButtonAttribute? = null,
    isDividerVisible: Boolean = false
) {
    val borderStrokeColor = AppTheme.colors.border.strong

    Column(
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
            )
    ) {
        if (primaryButton != null) {
            PrimaryFilledButton(
                modifier = Modifier
                    .fillMaxWidth(),
                text = primaryButton.first,
                onClick = primaryButton.second
            )
        }

        if (secondaryButton != null) {
            SecondaryFilledButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = LocalSpacing.current.x16),
                text = secondaryButton.first,
                onClick = secondaryButton.second
            )
        }
    }
}

@Composable
@CombinedThemePreviews
private fun PreviewPromotionalPlainSheet() {
    AndroidThemeForPreviews {
        PromotionalIllustrationSheet(
            illustration = null,
            title = "Title",
            headline = "Headline",
            showCloseButton = false,
            primaryButton = "Button" to {},
            secondaryButton = "Button 2" to {},
            listItems = listItemSamples,
            isVisible = true,
            footer = "*terms and conditions. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip"
        )
    }
}

@Composable
@CombinedThemePreviews
private fun PreviewPromotionalFullImageSheetWithList() {
    AndroidThemeForPreviews {
        PromotionalFullImageSheet(
            imageUrl = "",
            title = "Title",
            headline = "Headline",
            primaryButton = "Button" to {},
            secondaryButton = "Button 2" to {},
            listItems = listItemSamples,
            isVisible = true,
            footer = "*terms and conditions. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip"
        )
    }
}

@Composable
@CombinedThemePreviews
private fun PreviewPromotionalImageSheetWithList() {
    AndroidThemeForPreviews {
        PromotionalImageSheet(
            imageUrl = "",
            title = "Title",
            headline = "Headline",
            primaryButton = "Button" to {},
            secondaryButton = "Button 2" to {},
            listItems = listItemSamples,
            isVisible = true,
            footer = "*terms and conditions. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip"
        )
    }
}

@Composable
@CombinedThemePreviews
private fun PreviewPromotionalIllustrationSheetWithList() {
    AndroidThemeForPreviews {
        PromotionalIllustrationSheet(
            illustration = R.drawable.illustration_mega_anniversary,
            title = "Title",
            headline = "Headline",
            primaryButton = "Button" to {},
            secondaryButton = "Button 2" to {},
            listItems = listItemSamples,
            isVisible = true,
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