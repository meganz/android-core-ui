package mega.android.core.ui.components.sheets

import android.app.Activity
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.core.view.WindowCompat
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kotlinx.coroutines.launch
import mega.android.core.ui.R
import mega.android.core.ui.components.MegaText
import mega.android.core.ui.components.button.PrimaryFilledButton
import mega.android.core.ui.components.button.SecondaryFilledButton
import mega.android.core.ui.components.image.MegaIcon
import mega.android.core.ui.components.list.MultiLineListItem
import mega.android.core.ui.components.toolbar.MegaTopAppBar
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.theme.tokens.IconColor
import mega.android.core.ui.theme.tokens.TextColor

typealias SheetButtonAttribute = Pair<String, () -> Unit>

private val bottomSheetShape = RoundedCornerShape(
    topStart = 24.dp,
    topEnd = 24.dp,
)

@Immutable
data class SheetListAttributes(
    val title: String,
    val subtitle: String,
    @DrawableRes val icon: Int,
)

/**
 * Promo sheet with image in the center below the toolbar.
 * Figma: https://www.figma.com/file/5ShSh0nuHWlYYjamj1dz4m/%5BDSN-1630%5D-What%E2%80%99s-new-dialog?node-id=823%3A8511
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PromotionalImageSheet(
    imageUrl: String,
    title: String,
    headline: String,
    primaryButton: SheetButtonAttribute? = null,
    secondaryButton: SheetButtonAttribute? = null,
    listItems: List<SheetListAttributes> = emptyList(),
    contentText: String? = null,
    footer: String? = null,
    onDismissRequest: () -> Unit = {},
    isVisible: Boolean = false
) {
    val coroutineScope = rememberCoroutineScope()
    val spacing = LocalSpacing.current
    val sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
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
            modifier = Modifier
                .fillMaxSize()
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
                navigationIcon = painterResource(id = R.drawable.ic_close),
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
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16f / 9f)
                        .clip(AppTheme.shapes.small)
                        .background(AppTheme.colors.background.surface1),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = title,
                    contentScale = ContentScale.FillWidth,
                )

                SheetContent(
                    modifier = Modifier.padding(top = spacing.x32),
                    title = title,
                    headline = headline,
                    listItems = listItems,
                    contentText = contentText,
                    footer = footer
                )
            }

            SheetActions(
                modifier = Modifier
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
 * Promo sheet with full image that occupies the toolbar
 * Figma: https://www.figma.com/file/5ShSh0nuHWlYYjamj1dz4m/%5BDSN-1630%5D-What%E2%80%99s-new-dialog?type=design&node-id=823-8509
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PromotionalFullImageSheet(
    imageUrl: String,
    title: String,
    headline: String,
    primaryButton: SheetButtonAttribute? = null,
    secondaryButton: SheetButtonAttribute? = null,
    listItems: List<SheetListAttributes> = emptyList(),
    contentText: String? = null,
    footer: String? = null,
    onDismissRequest: () -> Unit = {},
    isVisible: Boolean = false
) {
    val spacing = LocalSpacing.current
    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
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
            modifier = Modifier
                .fillMaxSize()
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
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1.5f)
                        .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                        .background(AppTheme.colors.background.surface1),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = title,
                    contentScale = ContentScale.FillWidth,
                )

                SheetContent(
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

            SheetActions(
                modifier = Modifier
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
 * Figma: https://www.figma.com/file/5ShSh0nuHWlYYjamj1dz4m/%5BDSN-1630%5D-What%E2%80%99s-new-dialog?node-id=823%3A8511
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PromotionalIllustrationSheet(
    title: String,
    headline: String,
    @DrawableRes illustration: Int? = null,
    primaryButton: SheetButtonAttribute? = null,
    secondaryButton: SheetButtonAttribute? = null,
    listItems: List<SheetListAttributes> = emptyList(),
    contentText: String? = null,
    footer: String? = null,
    onDismissRequest: () -> Unit = {},
    isVisible: Boolean = false
) {
    val coroutineScope = rememberCoroutineScope()
    val spacing = LocalSpacing.current
    val sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
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
            modifier = Modifier
                .fillMaxSize()
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
                navigationIcon = painterResource(id = R.drawable.ic_close),
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

                SheetContent(
                    modifier = Modifier,
                    title = title,
                    headline = headline,
                    listItems = listItems,
                    contentText = contentText,
                    footer = footer
                )
            }

            SheetActions(
                modifier = Modifier
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
 * Promotional sheet with only text and buttons.
 * Figma: https://www.figma.com/file/5ShSh0nuHWlYYjamj1dz4m/%5BDSN-1630%5D-What%E2%80%99s-new-dialog?node-id=823%3A8511
 */
@Composable
fun PromotionalPlainSheet(
    title: String,
    headline: String,
    primaryButton: SheetButtonAttribute? = null,
    secondaryButton: SheetButtonAttribute? = null,
    listItems: List<SheetListAttributes> = emptyList(),
    contentText: String? = null,
    footer: String? = null,
    onDismissRequest: () -> Unit = {},
    isVisible: Boolean = false
) {
    PromotionalIllustrationSheet(
        title = title,
        headline = headline,
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
    val activity = LocalView.current.context as Activity

    SideEffect {
        WindowCompat.setDecorFitsSystemWindows(activity.window, false)
    }

    LaunchedEffect(isVisible) {
        if (isVisible) {
            sheetState.expand()
        } else {
            sheetState.hide()
        }
    }

    ModalBottomSheet(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding(),
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        containerColor = AppTheme.colors.background.pageBackground,
        scrimColor = AppTheme.colors.background.surface1,
        dragHandle = null,
        shape = bottomSheetShape,
        content = content
    )
}

@Composable
private fun SheetContent(
    modifier: Modifier,
    title: String,
    headline: String,
    listItems: List<SheetListAttributes> = emptyList(),
    contentText: String? = null,
    footer: String? = null,
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = title,
            color = AppTheme.colors.button.brand,
            style = AppTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )

        MegaText(
            modifier = Modifier
                .padding(top = LocalSpacing.current.x8)
                .fillMaxWidth(),
            text = headline,
            textColor = TextColor.Primary,
            style = AppTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )

        listItems.forEach { item ->
            MultiLineListItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = LocalSpacing.current.x24),
                title = item.title,
                subtitle = item.subtitle,
                leadingElement = {
                    Icon(
                        modifier = Modifier.size(48.dp),
                        painter = painterResource(id = item.icon),
                        contentDescription = item.title,
                    )
                }
            )
        }

        if (!contentText.isNullOrBlank()) {
            MegaText(
                modifier = Modifier
                    .padding(
                        top = LocalSpacing.current.x24,
                        start = LocalSpacing.current.x16,
                        end = LocalSpacing.current.x16
                    )
                    .fillMaxWidth(),
                text = contentText,
                textColor = TextColor.Secondary,
                style = AppTheme.typography.bodyMedium,
            )
        }

        if (!footer.isNullOrBlank()) {
            MegaText(
                modifier = Modifier
                    .padding(
                        top = LocalSpacing.current.x24,
                        start = LocalSpacing.current.x16,
                        end = LocalSpacing.current.x16
                    )
                    .fillMaxWidth(),
                text = footer,
                textColor = TextColor.Secondary,
                style = AppTheme.typography.bodySmall,
            )
        }

        Spacer(
            modifier = Modifier
                .height(LocalSpacing.current.x32)
                .fillMaxWidth()
        )
    }
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
            primaryButton = "Button" to {},
            secondaryButton = "Button 2" to {},
            listItems = listOf(
                SheetListAttributes(
                    title = "Title",
                    subtitle = "Subtitle",
                    icon = R.drawable.ic_check_circle
                ),
                SheetListAttributes(
                    title = "Title 2",
                    subtitle = "Subtitle 2",
                    icon = R.drawable.ic_check_circle
                ),
                SheetListAttributes(
                    title = "Title 3",
                    subtitle = "Subtitle 3",
                    icon = R.drawable.ic_check_circle
                ),
                SheetListAttributes(
                    title = "Title 4",
                    subtitle = "Subtitle 4",
                    icon = R.drawable.ic_check_circle
                ),
            ),
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
            listItems = listOf(
                SheetListAttributes(
                    title = "Title",
                    subtitle = "Subtitle",
                    icon = R.drawable.ic_check_circle
                ),
                SheetListAttributes(
                    title = "Title 2",
                    subtitle = "Subtitle 2",
                    icon = R.drawable.ic_check_circle
                ),
                SheetListAttributes(
                    title = "Title 3",
                    subtitle = "Subtitle 3",
                    icon = R.drawable.ic_check_circle
                ),
                SheetListAttributes(
                    title = "Title 4",
                    subtitle = "Subtitle 4",
                    icon = R.drawable.ic_check_circle
                ),
            ),
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
            listItems = listOf(
                SheetListAttributes(
                    title = "Title",
                    subtitle = "Subtitle",
                    icon = R.drawable.ic_check_circle
                ),
                SheetListAttributes(
                    title = "Title 2",
                    subtitle = "Subtitle 2",
                    icon = R.drawable.ic_check_circle
                ),
                SheetListAttributes(
                    title = "Title 3",
                    subtitle = "Subtitle 3",
                    icon = R.drawable.ic_check_circle
                ),
                SheetListAttributes(
                    title = "Title 4",
                    subtitle = "Subtitle 4",
                    icon = R.drawable.ic_check_circle
                ),
            ),
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
            listItems = listOf(
                SheetListAttributes(
                    title = "Title",
                    subtitle = "Subtitle",
                    icon = R.drawable.ic_check_circle
                ),
                SheetListAttributes(
                    title = "Title 2",
                    subtitle = "Subtitle 2",
                    icon = R.drawable.ic_check_circle
                ),
                SheetListAttributes(
                    title = "Title 3",
                    subtitle = "Subtitle 3",
                    icon = R.drawable.ic_check_circle
                ),
                SheetListAttributes(
                    title = "Title 4",
                    subtitle = "Subtitle 4",
                    icon = R.drawable.ic_check_circle
                ),
            ),
            footer = "*terms and conditions. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip"
        )
    }
}