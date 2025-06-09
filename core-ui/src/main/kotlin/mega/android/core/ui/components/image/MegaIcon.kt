package mega.android.core.ui.components.image

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import mega.android.core.ui.R
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.componentsColor
import mega.android.core.ui.theme.iconColor
import mega.android.core.ui.theme.linkColor
import mega.android.core.ui.theme.supportColor
import mega.android.core.ui.theme.textColor
import mega.android.core.ui.tokens.theme.DSTokens
import mega.android.core.ui.theme.values.ComponentsColor
import mega.android.core.ui.theme.values.IconColor
import mega.android.core.ui.theme.values.LinkColor
import mega.android.core.ui.theme.values.SupportColor
import mega.android.core.ui.theme.values.TextColor

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
        tint = Color.Unspecified,
        contentDescription = contentDescription,
        modifier = modifier
    )
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
