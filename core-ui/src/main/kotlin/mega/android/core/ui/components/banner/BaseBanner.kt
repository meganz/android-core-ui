package mega.android.core.ui.components.banner

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import mega.android.core.ui.R
import mega.android.core.ui.components.LinkSpannedText
import mega.android.core.ui.components.MegaClickableText
import mega.android.core.ui.components.text.SpannableText
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.theme.values.TextColor

@Composable
internal fun BaseBanner(
    modifier: Modifier,
    backgroundColor: Color,
    backgroundShape: Shape,
    @DrawableRes iconResId: Int,
    iconColor: Color,
    body: SpannableText,
    showCancelButton: Boolean,
    title: SpannableText? = null,
    buttonText: String? = null,
    onButtonClick: () -> Unit = {},
    onCancelButtonClick: () -> Unit = {},
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(color = backgroundColor, shape = backgroundShape)
            .padding(LocalSpacing.current.x24),
    ) {
        Row {
            Icon(
                modifier = Modifier.size(LocalSpacing.current.x24),
                painter = painterResource(id = iconResId),
                tint = iconColor,
                contentDescription = "Banner Icon"
            )

            Column(
                modifier = Modifier
                    .padding(
                        start = LocalSpacing.current.x8,
                        end = LocalSpacing.current.x16
                    )
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(LocalSpacing.current.x8)
            ) {
                title?.let {
                    LinkSpannedText(
                        value = title.text.orEmpty(),
                        spanStyles = title.annotations ?: emptyMap(),
                        onAnnotationClick = title.onAnnotationClick ?: {},
                        baseStyle = AppTheme.typography.titleMedium,
                        baseTextColor = TextColor.Primary,
                    )
                }

                LinkSpannedText(
                    value = body.text.orEmpty(),
                    spanStyles = body.annotations ?: emptyMap(),
                    onAnnotationClick = body.onAnnotationClick ?: {},
                    baseStyle = AppTheme.typography.bodyMedium,
                    baseTextColor = TextColor.Primary,
                )

                buttonText?.let {
                    MegaClickableText(
                        text = it,
                        onClick = onButtonClick,
                        style = AppTheme.typography.labelLarge.copy(textDecoration = TextDecoration.Underline)
                    )
                }
            }

            if (showCancelButton) {
                Icon(
                    modifier = Modifier
                        .size(LocalSpacing.current.x24)
                        .clickable { onCancelButtonClick() },
                    painter = painterResource(id = R.drawable.ic_close),
                    tint = AppTheme.colors.icon.primary,
                    contentDescription = "Banner Cancel"
                )
            }
        }
    }
}

val BannerPaddingProvider = compositionLocalOf<Dp> {
    0.dp
}