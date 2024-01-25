package mega.android.core.ui.components.sheets

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import kotlinx.coroutines.launch
import mega.android.core.ui.R
import mega.android.core.ui.components.MegaText
import mega.android.core.ui.components.button.PrimaryFilledButton
import mega.android.core.ui.components.button.SecondaryFilledButton
import mega.android.core.ui.components.list.MultiLineListItem
import mega.android.core.ui.components.toolbar.DefaultToolbar
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.theme.tokens.TextColor

typealias SheetButtonAttribute = Pair<String, () -> Unit>

/**
 *
 * Figma: https://www.figma.com/file/5ShSh0nuHWlYYjamj1dz4m/%5BTX-0000%5D-What%E2%80%99s-new-dialog?type=design&node-id=328-36229&mode=design&t=M2WnoRDiMOtfxpt6-0
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PromotionalIllustrationSheet(
    @DrawableRes illustration: Int,
    title: String,
    headline: String,
    primaryButton: SheetButtonAttribute,
    secondaryButton: SheetButtonAttribute? = null,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    listItems: List<SheetListAttributes> = emptyList(),
    contentText: String? = null,
    footer: String? = null,
    onDismissRequest: () -> Unit = {},
) {
    val coroutineScope = rememberCoroutineScope()
    val spacing = LocalSpacing.current

    ModalBottomSheet(
        modifier = Modifier
            .wrapContentHeight(),
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        containerColor = AppTheme.colors.background.pageBackground,
        dragHandle = null,
        shape = AppTheme.shapes.small,
    ) {
        ConstraintLayout(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
        ) {
            val (closeButton, content, buttonContainer) = createRefs()

            DefaultToolbar(
                modifier = Modifier
                    .constrainAs(closeButton) {
                        top.linkTo(parent.top, margin = spacing.x16)
                        start.linkTo(parent.start, margin = spacing.x4)
                    },
                title = "",
                icon = painterResource(id = R.drawable.ic_close),
                onIconClicked = {
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
                        top.linkTo(closeButton.bottom, margin = spacing.x16)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(buttonContainer.top)
                    }
                    .verticalScroll(rememberScrollState(), enabled = true),
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    modifier = Modifier
                        .size(120.dp)
                        .align(Alignment.CenterHorizontally),
                    painter = painterResource(id = illustration),
                    contentDescription = "Illustration"
                )

                SheetContent(
                    modifier = Modifier.padding(top = LocalSpacing.current.x32),
                    title = title,
                    headline = headline,
                    listItems = listItems,
                    contentText = contentText,
                    footer = footer
                )
            }

            SheetActions(
                modifier = Modifier.constrainAs(buttonContainer) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    height = Dimension.fillToConstraints
                },
                primaryButton = primaryButton,
                secondaryButton = secondaryButton
            )
        }
    }
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
                        contentDescription = "Icon",
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
    primaryButton: SheetButtonAttribute,
    secondaryButton: SheetButtonAttribute? = null,
) {
    val borderStrokeColor = AppTheme.colors.border.strong

    Column(
        modifier = modifier
            .drawBehind {
                drawLine(
                    color = borderStrokeColor,
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f),
                    strokeWidth = 1.dp.toPx()
                )

            }
            .padding(
                vertical = LocalSpacing.current.x24,
                horizontal = LocalSpacing.current.x16,
            )
    ) {
        PrimaryFilledButton(
            modifier = Modifier
                .fillMaxWidth(),
            text = primaryButton.first,
            onClick = primaryButton.second
        )

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

@Immutable
data class SheetListAttributes(
    val title: String,
    val subtitle: String,
    @DrawableRes val icon: Int,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@CombinedThemePreviews
private fun PreviewPromotionalFullImageSheetWithList() {
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@CombinedThemePreviews
private fun PreviewPromotionalFullImageSheetWithContentText() {
    AndroidThemeForPreviews {
        PromotionalIllustrationSheet(
            illustration = R.drawable.illustration_mega_anniversary,
            title = "Title",
            headline = "Headline",
            primaryButton = "Button" to {},
            contentText = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
            footer = "*terms and conditions. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip"
        )
    }
}