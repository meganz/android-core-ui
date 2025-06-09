package mega.android.core.ui.components.sheets

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import mega.android.core.ui.R
import mega.android.core.ui.components.button.PrimaryFilledButton
import mega.android.core.ui.components.button.SecondaryFilledButton
import mega.android.core.ui.components.common.PromotionalContent
import mega.android.core.ui.components.common.PromotionalFullImage
import mega.android.core.ui.components.common.PromotionalImage
import mega.android.core.ui.components.common.PromotionalListAttributes
import mega.android.core.ui.components.image.MegaIcon
import mega.android.core.ui.components.text.ContentText
import mega.android.core.ui.components.text.ContentTextDefaults
import mega.android.core.ui.components.toolbar.MegaTopAppBar
import mega.android.core.ui.model.IllustrationIconSizeMode
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.tokens.theme.DSTokens
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.theme.values.IconColor


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
    image: Any?,
    title: String,
    headline: String,
    modifier: Modifier = Modifier,
    description: ContentText? = null,
    windowsInsets: WindowInsets? = null,
    showCloseButton: Boolean = true,
    primaryButton: SheetButtonAttribute? = null,
    secondaryButton: SheetButtonAttribute? = null,
    contentText: String? = null,
    footer: ContentText? = null,
    listItems: List<PromotionalListAttributes> = emptyList(),
    onDismissRequest: () -> Unit = {},
    isVisible: Boolean = false,
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
    val isScrollable by remember {
        derivedStateOf { scrollState.canScrollForward || scrollState.canScrollBackward }
    }

    ModalBottomSheetScaffold(
        modifier = modifier,
        windowsInsets = windowsInsets,
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        isVisible = isVisible,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
                .padding(bottom = spacing.x16)
        ) {

            MegaTopAppBar(
                modifier = Modifier,
                title = "",
                navigationIcon = if (showCloseButton) painterResource(id = R.drawable.ic_close_medium_thin_outline) else null,
                onNavigationIconClicked = {
                    coroutineScope.launch {
                        sheetState.hide()
                        onDismissRequest()
                    }
                }
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(scrollState)
            ) {
                PromotionalImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = spacing.x16),
                    image = image,
                    description = title
                )

                PromotionalContent(
                    modifier = Modifier.padding(top = spacing.x32),
                    title = title,
                    headline = headline,
                    description = description,
                    listItems = listItems,
                    contentText = contentText,
                    footer = footer,
                )
            }

            SheetActions(
                modifier = Modifier,
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
    image: Any?,
    title: String,
    headline: String,
    modifier: Modifier = Modifier,
    description: ContentText? = null,
    windowsInsets: WindowInsets? = null,
    showCloseButton: Boolean = true,
    primaryButton: SheetButtonAttribute? = null,
    secondaryButton: SheetButtonAttribute? = null,
    listItems: List<PromotionalListAttributes> = emptyList(),
    contentText: String? = null,
    footer: ContentText? = null,
    onDismissRequest: () -> Unit = {},
    isVisible: Boolean = false,
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
    val isScrollable by remember {
        derivedStateOf { scrollState.canScrollForward || scrollState.canScrollBackward }
    }

    ModalBottomSheetScaffold(
        modifier = modifier,
        windowsInsets = windowsInsets,
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        isVisible = isVisible
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
                .padding(bottom = spacing.x16)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(scrollState)
                ) {
                    PromotionalFullImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)),
                        image = image,
                        description = title
                    )

                    PromotionalContent(
                        modifier = Modifier
                            .padding(top = spacing.x32),
                        title = title,
                        headline = headline,
                        description = description,
                        listItems = listItems,
                        contentText = contentText,
                        footer = footer,
                    )
                }
                SheetActions(
                    modifier = Modifier
                        .fillMaxWidth(),
                    primaryButton = primaryButton,
                    secondaryButton = secondaryButton,
                    isDividerVisible = isScrollable
                )
            }

            if (showCloseButton) {
                Button(
                    modifier = Modifier
                        .padding(spacing.x16)
                        .size(32.dp)
                        .align(Alignment.TopStart),
                    onClick = {
                        coroutineScope.launch {
                            sheetState.hide()
                            onDismissRequest()
                        }
                    },
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black.copy(alpha = 0.5f),
                        contentColor = DSTokens.colors.icon.onColor
                    ),
                    contentPadding = PaddingValues(4.dp)
                ) {
                    MegaIcon(
                        painter = painterResource(id = R.drawable.ic_close_medium_thin_outline),
                        tint = IconColor.OnColor
                    )
                }
            }
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
    modifier: Modifier = Modifier,
    description: ContentText? = null,
    windowsInsets: WindowInsets? = null,
    showCloseButton: Boolean = true,
    @DrawableRes illustration: Int? = null,
    illustrationMode: IllustrationIconSizeMode = IllustrationIconSizeMode.Small,
    primaryButton: SheetButtonAttribute? = null,
    secondaryButton: SheetButtonAttribute? = null,
    listItems: List<PromotionalListAttributes> = emptyList(),
    contentText: String? = null,
    footer: ContentText? = null,
    onDismissRequest: () -> Unit = {},
    isVisible: Boolean = false,
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
    val isScrollable by remember {
        derivedStateOf { scrollState.canScrollForward || scrollState.canScrollBackward }
    }

    ModalBottomSheetScaffold(
        modifier = modifier,
        windowsInsets = windowsInsets,
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        isVisible = isVisible,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
                .padding(bottom = spacing.x16)
        ) {
            MegaTopAppBar(
                modifier = Modifier.padding(top = spacing.x8),
                title = "",
                navigationIcon = if (showCloseButton) painterResource(id = R.drawable.ic_close_medium_thin_outline) else null,
                onNavigationIconClicked = {
                    coroutineScope.launch {
                        sheetState.hide()
                        onDismissRequest()
                    }
                }
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.Center
            ) {
                if (illustration != null) {
                    Image(
                        modifier = Modifier
                            .padding(bottom = spacing.x32)
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
                    isIllustration = illustration != null,
                    description = description,
                    listItems = listItems,
                    contentText = contentText,
                    footer = footer,
                )
            }

            SheetActions(
                modifier = Modifier
                    .fillMaxWidth(),
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
    modifier: Modifier = Modifier,
    description: ContentText? = null,
    windowsInsets: WindowInsets? = null,
    showCloseButton: Boolean = true,
    illustrationMode: IllustrationIconSizeMode = IllustrationIconSizeMode.Small,
    primaryButton: SheetButtonAttribute? = null,
    secondaryButton: SheetButtonAttribute? = null,
    listItems: List<PromotionalListAttributes> = emptyList(),
    contentText: String? = null,
    footer: ContentText? = null,
    onDismissRequest: () -> Unit = {},
    isVisible: Boolean = false,
) {
    PromotionalIllustrationSheet(
        modifier = modifier,
        title = title,
        headline = headline,
        description = description,
        windowsInsets = windowsInsets,
        showCloseButton = showCloseButton,
        illustrationMode = illustrationMode,
        primaryButton = primaryButton,
        secondaryButton = secondaryButton,
        listItems = listItems,
        contentText = contentText,
        footer = footer,
        onDismissRequest = onDismissRequest,
        isVisible = isVisible,
        illustration = null,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ModalBottomSheetScaffold(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    sheetState: SheetState,
    windowsInsets: WindowInsets? = null,
    isVisible: Boolean = false,
    content: @Composable ColumnScope.() -> Unit = {},
) {
    LaunchedEffect(isVisible) {
        if (isVisible) {
            sheetState.expand()
        } else {
            sheetState.hide()
        }
    }

    ModalBottomSheet(
        modifier = modifier,
        contentWindowInsets = {
            windowsInsets ?: BottomSheetDefaults.windowInsets.only(WindowInsetsSides.Bottom)
        },
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        containerColor = DSTokens.colors.background.pageBackground,
        dragHandle = null,
        shape = bottomSheetShape,
        content = content,
    )
}

@Composable
private fun SheetActions(
    modifier: Modifier,
    primaryButton: SheetButtonAttribute? = null,
    secondaryButton: SheetButtonAttribute? = null,
    isDividerVisible: Boolean = false
) {
    val borderStrokeColor = DSTokens.colors.border.strong

    Column(
        modifier = modifier
            .navigationBarsPadding()
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
                top = LocalSpacing.current.x24,
                start = LocalSpacing.current.x16,
                end = LocalSpacing.current.x16,
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
            footer = ContentTextDefaults.description("*terms and conditions. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip")
        )
    }
}

@Composable
@CombinedThemePreviews
private fun PreviewPromotionalFullImageSheetWithList() {
    AndroidThemeForPreviews {
        PromotionalFullImageSheet(
            image = "",
            title = "Title",
            headline = "Headline",
            primaryButton = "Button" to {},
            secondaryButton = "Button 2" to {},
            listItems = listItemSamples,
            isVisible = true,
            footer = ContentTextDefaults.description("*terms and conditions. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip")
        )
    }
}

@Composable
@CombinedThemePreviews
private fun PreviewPromotionalImageSheetWithList() {
    AndroidThemeForPreviews {
        PromotionalImageSheet(
            image = "",
            title = "Title",
            headline = "Headline",
            primaryButton = "Button" to {},
            secondaryButton = "Button 2" to {},
            listItems = listItemSamples,
            isVisible = true,
            footer = ContentTextDefaults.description("*terms and conditions. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip")
        )
    }
}

@Composable
@CombinedThemePreviews
private fun PreviewPromotionalIllustrationSheetWithList() {
    AndroidThemeForPreviews {
        PromotionalIllustrationSheet(
            illustration = R.drawable.illustration_mega_anniversary,
            illustrationMode = IllustrationIconSizeMode.Large,
            title = "Title",
            headline = "Headline",
            primaryButton = "Button" to {},
            secondaryButton = "Button 2" to {},
            listItems = listItemSamples,
            isVisible = true,
            footer = ContentTextDefaults.description("*terms and conditions. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip")
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