package mega.android.core.ui.components.banner

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import mega.android.core.ui.R
import mega.android.core.ui.components.image.MegaIcon
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidTheme
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.tokens.theme.DSTokens

/**
 * MegaBanner component that displays a banner with background image, title, description,
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
fun PromoBanner(
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
            .background(
                color = DSTokens.colors.background.surface3,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(horizontal = 10.dp) // 10dp horizontal margin
    ) {
        // Background image
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(backgroundImageUrl)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Fit
        )

        // Main content
        Row(
            modifier = Modifier
                .padding(
                    horizontal = spacing.x8, // 8dp horizontal margin for image
                    vertical = spacing.x12   // 12dp vertical margin for image
                )
        ) {
            // Left side content (70% of width)
            Column(
                modifier = Modifier
                    .weight(0.7f, fill = true)
                    .wrapContentHeight()
                    .padding(
                        start = spacing.x16, // 16dp start margin for title
                        top = spacing.x8     // 8dp top margin for title
                    ),
                verticalArrangement = Arrangement.Top
            ) {
                // Title
                Text(
                    text = title,
                    style = AppTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = DSTokens.colors.text.onColor,
                    maxLines = 2
                )

                BannerButton(
                    text = buttonText,
                    modifier = Modifier
                        .padding(vertical = 10.dp),
                    onClick = onClick
                )
            }

            Spacer(Modifier.weight(0.3f, fill = true))
        }


        // Dismiss button
        if (showDismissButton) {
            Icon(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(
                        horizontal = spacing.x8, // 8dp horizontal margin for image
                        vertical = spacing.x12   // 12dp vertical margin for image
                    )
                    .size(spacing.x24)
                    .clickable { onDismissClick() },
                painter = painterResource(id = R.drawable.ic_close_medium_thin_outline),
                contentDescription = "Dismiss",
                tint = DSTokens.colors.text.onColor
            )
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
            .padding(vertical = 10.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
        ),
        onClick = onClick,
    ) {
        Text(
            text = text,
            style = AppTheme.typography.labelLarge,
            color = DSTokens.colors.text.onColorInverse,
        )
    }
}

@CombinedThemePreviews
@Composable
private fun PromoBannerPreview() {
    AndroidThemeForPreviews {
        PromoBanner(
            modifier = Modifier.padding(16.dp),
            backgroundImageUrl = null, // In preview, you can use a drawable resource or URL
            imageUrl = null, // In preview, you can use a drawable resource or URL
            title = "Get 5GB extra with our password manager",
            buttonText = "Try it now",
            showDismissButton = true,
            onDismissClick = {}
        )
    }
}

@CombinedThemePreviews
@Composable
private fun PromoBannerWithoutDismissPreview() {
    AndroidThemeForPreviews {
        PromoBanner(
            modifier = Modifier.padding(16.dp),
            backgroundImageUrl = null,
            imageUrl = null,
            title = "Mega VPN included in your plan ",
            buttonText = "Learn more",
            showDismissButton = false,
            onDismissClick = {}
        )
    }
}
