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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import mega.android.core.ui.R
import mega.android.core.ui.components.MegaClickableText
import mega.android.core.ui.components.MegaText
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
    body: String,
    showCancelButton: Boolean,
    title: String? = null,
    buttonText: String? = null,
    onButtonClick: () -> Unit = {},
    onCancelButtonClick: () -> Unit = {},
) {
    Box(
        modifier = modifier.fillMaxWidth()
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
                    MegaText(
                        text = it,
                        textColor = TextColor.Primary,
                        style = AppTheme.typography.titleMedium
                    )
                }

                MegaText(
                    text = body,
                    textColor = TextColor.Primary,
                    style = AppTheme.typography.bodyMedium
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