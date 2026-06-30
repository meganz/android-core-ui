package mega.android.core.ui.components.image

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import mega.android.core.ui.R
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.componentsColor
import mega.android.core.ui.theme.iconColor
import mega.android.core.ui.theme.indicatorColor
import mega.android.core.ui.theme.linkColor
import mega.android.core.ui.theme.supportColor
import mega.android.core.ui.theme.textColor
import mega.android.core.ui.theme.values.ComponentsColor
import mega.android.core.ui.theme.values.IconColor
import mega.android.core.ui.theme.values.IndicatorColor
import mega.android.core.ui.theme.values.LinkColor
import mega.android.core.ui.theme.values.SupportColor
import mega.android.core.ui.theme.values.TextColor
import mega.android.core.ui.tokens.theme.DSTokens


@Composable
fun MegaIcon(
    imageVector: ImageVector,
    tint: IconColor,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
) {
    MegaIcon(
        painter = rememberVectorPainter(image = imageVector),
        tint = tint,
        modifier = modifier,
        contentDescription = contentDescription
    )
}

@Composable
fun MegaIcon(
    imageVector: ImageVector,
    tint: SupportColor,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
) {
    MegaIcon(
        painter = rememberVectorPainter(image = imageVector),
        supportTint = tint,
        modifier = modifier,
        contentDescription = contentDescription
    )
}

@Composable
fun MegaIcon(
    painter: Painter,
    tint: IconColor,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
) {
    Icon(
        painter = painter,
        tint = DSTokens.iconColor(iconColor = tint),
        contentDescription = contentDescription,
        modifier = modifier
    )
}

@Composable
fun MegaIcon(
    painter: Painter,
    textColorTint: TextColor,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
) {
    Icon(
        painter = painter,
        tint = DSTokens.textColor(textColor = textColorTint),
        contentDescription = contentDescription,
        modifier = modifier
    )
}

@Composable
fun MegaIcon(
    painter: Painter,
    supportTint: SupportColor,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
) {
    Icon(
        painter = painter,
        tint = DSTokens.supportColor(supportColor = supportTint),
        contentDescription = contentDescription,
        modifier = modifier
    )
}

@Composable
fun MegaIcon(
    painter: Painter,
    linkColorTint: LinkColor,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
) {
    Icon(
        painter = painter,
        tint = DSTokens.linkColor(linkColor = linkColorTint),
        contentDescription = contentDescription,
        modifier = modifier
    )
}

@Composable
fun MegaIcon(
    painter: Painter,
    componentsColorTint: ComponentsColor,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
) {
    Icon(
        painter = painter,
        tint = DSTokens.componentsColor(componentsColor = componentsColorTint),
        contentDescription = contentDescription,
        modifier = modifier
    )
}

@Composable
fun MegaIcon(
    painter: Painter,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
) {
    Icon(
        painter = painter,
        tint = LocalContentColor.current,
        contentDescription = contentDescription,
        modifier = modifier
    )
}

@Composable
fun MegaIcon(
    imageVector: ImageVector,
    indicatorColorTint: IndicatorColor,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
) {
    Icon(
        painter = rememberVectorPainter(image = imageVector),
        tint = DSTokens.indicatorColor(indicatorColorTint),
        modifier = modifier,
        contentDescription = contentDescription
    )
}

@Composable
fun MegaIconWithIndicator(
    imageVector: ImageVector,
    tint: IconColor,
    showIndicator: Boolean,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
) {
    MegaIconWithIndicator(
        painter = rememberVectorPainter(image = imageVector),
        tint = tint,
        showIndicator = showIndicator,
        modifier = modifier,
        contentDescription = contentDescription
    )
}

@Composable
fun MegaIconWithIndicator(
    painter: Painter,
    tint: IconColor,
    showIndicator: Boolean,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
) {
    val iconColor = DSTokens.iconColor(iconColor = tint)
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            painter = painter,
            tint = iconColor,
            contentDescription = contentDescription,
        )

        if (showIndicator) {
            Box(
                modifier = Modifier
                    .padding(top = 1.dp)
                    .size(4.dp)
                    .background(
                        color = iconColor,
                        shape = CircleShape,
                    )
            )
        }
    }
}

@Composable
@CombinedThemePreviews
private fun MegaIconPreview() {
    AndroidThemeForPreviews {
        MegaIcon(
            painter = painterResource(id = R.drawable.ic_close_medium_thin_outline),
            tint = IconColor.Primary
        )
    }
}

@Composable
@CombinedThemePreviews
private fun MegaIconWithSupportTintPreview() {
    AndroidThemeForPreviews {
        MegaIcon(
            painter = painterResource(id = R.drawable.ic_close_medium_thin_outline),
            supportTint = SupportColor.Success
        )
    }
}

@Composable
@CombinedThemePreviews
private fun MegaIconWithLinkColorTintPreview() {
    AndroidThemeForPreviews {
        MegaIcon(
            painter = painterResource(id = R.drawable.ic_close_medium_thin_outline),
            linkColorTint = LinkColor.Primary
        )
    }
}

@Composable
@CombinedThemePreviews
private fun MegaIconPreviewUnspecified() {
    AndroidThemeForPreviews {
        MegaIcon(
            painter = painterResource(id = R.drawable.ic_close_medium_thin_outline),
        )
    }
}

@Composable
@CombinedThemePreviews
private fun MegaIconWithComponentsTintPreview() {
    AndroidThemeForPreviews {
        MegaIcon(
            painter = painterResource(id = R.drawable.ic_close_medium_thin_outline),
            componentsColorTint = ComponentsColor.Interactive
        )
    }
}

@Composable
@CombinedThemePreviews
private fun MegaIconWithIndicatorShowingPreview() {
    AndroidThemeForPreviews {
        MegaIconWithIndicator(
            painter = painterResource(id = R.drawable.ic_close_medium_thin_outline),
            tint = IconColor.Brand,
            showIndicator = true
        )
    }
}

@Composable
@CombinedThemePreviews
private fun MegaIconWithIndicatorHiddenPreview() {
    AndroidThemeForPreviews {
        MegaIconWithIndicator(
            painter = painterResource(id = R.drawable.ic_close_medium_thin_outline),
            tint = IconColor.Primary,
            showIndicator = false
        )
    }
}
