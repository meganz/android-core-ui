package mega.android.core.ui.components.banner

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import mega.android.core.ui.R
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.tokens.theme.DSTokens

/**
 * MegaBanner component that displays a banner with background image, title, button
 * main image, and optional dismiss button.
 *
 * @param modifier Modifier to be applied to the banner
 * @param backgroundImageUrl URL or resource for the background image
 * @param imageUrl URL or resource for the main image displayed on the right side
 * @param title Title text to display
 * @param buttonText Description text to display (max 3 lines)
 * @param showDismissButton Whether to show the dismiss button
 * @param onDismissClick Callback when dismiss button is clicked
 */
@Composable
fun HomeBanner(
    modifier: Modifier = Modifier,
    backgroundImageUrl: Any?,
    imageUrl: Any?,
    title: String,
    buttonText: String,
    showDismissButton: Boolean = true,
    onClick: () -> Unit = {},
    onDismissClick: () -> Unit = {},
) {
    val spacing = LocalSpacing.current
    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .heightIn(min = HOME_BANNER_MIN_HEIGHT_DP.dp)
            .testTag(HOME_BANNER_TEST_TAG)
    ) {
        // Inner Box with background and rounded corners
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .heightIn(min = HOME_BANNER_MIN_HEIGHT_DP.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(
                    color = DSTokens.colors.background.surface3,
                )
        ) {
            // Background image
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(backgroundImageUrl)
                    .crossfade(true)
                    .build(),
                modifier = Modifier
                    .matchParentSize()
                    .testTag(HOME_BANNER_BACKGROUND_IMAGE_TEST_TAG),
                contentDescription = "Banner background image",
                contentScale = ContentScale.Crop
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .heightIn(min = HOME_BANNER_MIN_HEIGHT_DP.dp)
                    .padding(
                        horizontal = spacing.x8,
                        vertical = spacing.x12
                    ),
                verticalAlignment = Alignment.Top
            ) {
                // Left side content (70% of width)
                Column(
                    modifier = Modifier
                        .weight(0.7f)
                        .wrapContentHeight()
                        .padding(
                            start = spacing.x4,
                            top = spacing.x2
                        ),
                    verticalArrangement = Arrangement.Top
                ) {
                    // Title
                    Text(
                        text = title,
                        style = AppTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        color = DSTokens.colors.text.onColor,
                        maxLines = 2,
                        modifier = Modifier.testTag(HOME_BANNER_TITLE_TEST_TAG)
                    )

                    BannerButton(
                        text = buttonText,
                        onClick = onClick,
                    )
                }

                // Right side image (30% of width)
                Box(
                    modifier = Modifier
                        .weight(0.3f)
                        .fillMaxHeight()
                        .padding(
                            horizontal = spacing.x2,
                            vertical = spacing.x4
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(imageUrl)
                            .crossfade(true)
                            .build(),
                        modifier = Modifier
                            .size(50.dp)
                            .testTag(HOME_BANNER_MAIN_IMAGE_TEST_TAG),
                        contentDescription = "Banner item image",
                        contentScale = ContentScale.Fit
                    )
                }
            }

            if (showDismissButton) {
                Icon(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(
                            end = 4.dp,
                            top = 4.dp
                        )
                        .size(spacing.x24)
                        .clickable { onDismissClick() }
                        .testTag(HOME_BANNER_DISMISS_BUTTON_TEST_TAG),
                    painter = painterResource(id = R.drawable.ic_close_medium_thin_outline),
                    contentDescription = "Dismiss",
                    tint = DSTokens.colors.text.onColor
                )
            }
        }
    }
}

@Composable
private fun BannerButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    val interactionSource = remember { MutableInteractionSource() }
    val isButtonPressed by interactionSource.collectIsPressedAsState()
    val containerColor = when {
        isButtonPressed -> DSTokens.colors.button.onColorPressed
        else -> DSTokens.colors.button.onColor
    }
    Button(
        modifier = modifier
            .padding(top = 10.dp)
            .heightIn(min = 24.dp)
            .testTag(HOME_BANNER_BUTTON_TEST_TAG),
        shape = RoundedCornerShape(4.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
        ),
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
        onClick = onClick,
        interactionSource = interactionSource
    ) {
        Text(
            text = text,
            style = AppTheme.typography.labelMedium,
            color = DSTokens.colors.text.onColorInverse,
        )
    }
}

@CombinedThemePreviews
@Composable
private fun HomeBannerPreview() {
    AndroidThemeForPreviews {
        HomeBanner(
            modifier = Modifier.padding(16.dp),
            backgroundImageUrl = "https://eu.static.mega.co.nz/banners/transferit_background@2x.png",
            imageUrl = "https://eu.static.mega.co.nz/banners/transferit_image@2x.png",
            title = "Get 5GB extra with our password manager",
            buttonText = "Try it now",
            showDismissButton = true,
            onDismissClick = {}
        )
    }
}

@CombinedThemePreviews
@Composable
private fun HomeBannerWithoutDismissPreview() {
    AndroidThemeForPreviews {
        HomeBanner(
            modifier = Modifier.padding(16.dp),
            backgroundImageUrl = "https://eu.static.mega.co.nz/banners/transferit_background@2x.png",
            imageUrl = "https://eu.static.mega.co.nz/banners/transferit_image@2x.png",
            title = "Mega VPN included in your plan ",
            buttonText = "Learn more",
            showDismissButton = false,
            onDismissClick = {}
        )
    }
}

@CombinedThemePreviews
@Composable
private fun HomeBannerLargeFontPreview() {
    val density = LocalDensity.current
    CompositionLocalProvider(
        LocalDensity provides Density(density.density, fontScale = 1.5f)
    ) {
        AndroidThemeForPreviews {
            HomeBanner(
                modifier = Modifier.padding(16.dp),
                backgroundImageUrl = "https://eu.static.mega.co.nz/banners/transferit_background@2x.png",
                imageUrl = "https://eu.static.mega.co.nz/banners/transferit_image@2x.png",
                title = "Get 5GB extra with our password manager",
                buttonText = "Try it now",
                showDismissButton = true,
                onDismissClick = {}
            )
        }
    }
}

/** Minimum height of the banner. Use the same value for shimmer/placeholder to keep alignment when font size changes. */
const val HOME_BANNER_MIN_HEIGHT_DP = 90

internal const val HOME_BANNER_TEST_TAG = "home_banner"
internal const val HOME_BANNER_TITLE_TEST_TAG = "${HOME_BANNER_TEST_TAG}:title"
internal const val HOME_BANNER_BUTTON_TEST_TAG = "${HOME_BANNER_TEST_TAG}:button"
internal const val HOME_BANNER_DISMISS_BUTTON_TEST_TAG = "${HOME_BANNER_TEST_TAG}:dismiss_button"
internal const val HOME_BANNER_BACKGROUND_IMAGE_TEST_TAG = "${HOME_BANNER_TEST_TAG}:background_image"
internal const val HOME_BANNER_MAIN_IMAGE_TEST_TAG = "${HOME_BANNER_TEST_TAG}:main_image"
