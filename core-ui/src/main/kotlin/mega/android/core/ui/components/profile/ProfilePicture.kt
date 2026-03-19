package mega.android.core.ui.components.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.geometry.Offset
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.addLastModifiedToFileCacheKey
import mega.android.core.ui.components.MegaText
import mega.android.core.ui.components.image.MegaIcon
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.graphics.vector.ImageVector
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.values.TextColor
import mega.android.core.ui.theme.values.IconColor
import mega.android.core.ui.tokens.theme.DSTokens
import androidx.compose.ui.graphics.Brush
import java.io.File

private val LARGE_PROFILE_PICTURE_SIZE = 56.dp
private val MEDIUM_PROFILE_PICTURE_SIZE = 32.dp
private val SMALL_PROFILE_PICTURE_SIZE = 24.dp

@Composable
private fun BaseProfilePicture(
    avatarSize: Dp,
    imageFile: File?,
    name: String?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    iconTint: IconColor = IconColor.Inverse,
    baseStyle: TextStyle = LocalTextStyle.current,
    avatarColor: Color = DSTokens.colors.background.surface3,
    avatarSecondaryColor: Color? = null,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        imageFile?.let {
            key(avatarSize, it.absoluteFile.absolutePath) {
                AsyncImage(
                    modifier = Modifier
                        .size(avatarSize)
                        .clip(CircleShape),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(it.absoluteFile.absolutePath)
                        .addLastModifiedToFileCacheKey(true)
                        .build(),
                    contentDescription = contentDescription,
                    contentScale = ContentScale.Crop
                )
            }
        } ?: run {
            Box(
                modifier =
                    Modifier
                        .size(avatarSize)
                        .clip(CircleShape)
                        .then(
                            if (avatarSecondaryColor != null) {
                                Modifier.background(
                                    brush = Brush.linearGradient(
                                        colors = listOf(
                                            avatarSecondaryColor,
                                            avatarColor
                                        ),
                                        start = Offset(0f, Float.POSITIVE_INFINITY),
                                        end = Offset(Float.POSITIVE_INFINITY, 0f)
                                    )
                                )
                            } else {
                                Modifier.background(
                                    color = if (avatarColor == Color.Unspecified) {
                                        DSTokens.colors.background.surface3
                                    } else {
                                        avatarColor
                                    }
                                )
                            }
                        ),
                contentAlignment = Alignment.Center,
            ) {
                icon?.let {
                    MegaIcon(
                        modifier = Modifier
                            .wrapContentSize(unbounded = true),
                        painter = rememberVectorPainter(icon),
                        tint = iconTint,
                        contentDescription = contentDescription,
                    )
                } ?: run {
                    MegaText(
                        modifier = Modifier
                            .wrapContentSize(unbounded = true),
                        text = name?.take(1).orEmpty().uppercase(),
                        textColor = TextColor.OnColor,
                        style = baseStyle,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
fun LargeProfilePicture(
    imageFile: File?,
    name: String?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    avatarColor: Color = DSTokens.colors.background.surface3,
) {
    BaseProfilePicture(
        avatarSize = LARGE_PROFILE_PICTURE_SIZE,
        imageFile = imageFile,
        name = name,
        contentDescription = contentDescription,
        modifier = modifier,
        baseStyle = AppTheme.typography.titleLarge.copy(fontSize = 22.sp),
        avatarColor = avatarColor
    )
}

@Composable
fun MediumProfileIcon(
    icon: ImageVector?,
    iconTint: IconColor = IconColor.Inverse,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    avatarColor: Color = DSTokens.colors.background.surface3,
    avatarSecondaryColor: Color? = null,
    ) {
    BaseProfilePicture(
        avatarSize = MEDIUM_PROFILE_PICTURE_SIZE,
        imageFile = null,
        icon = icon,
        iconTint = iconTint,
        name = null,
        contentDescription = contentDescription,
        modifier = modifier,
        baseStyle = AppTheme.typography.bodyMedium,
        avatarColor = avatarColor,
        avatarSecondaryColor = avatarSecondaryColor
    )
}

@Composable
fun MediumProfilePicture(
    imageFile: File?,
    name: String?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    avatarColor: Color = DSTokens.colors.background.surface3,
    avatarSecondaryColor: Color? = null,
    ) {
    BaseProfilePicture(
        avatarSize = MEDIUM_PROFILE_PICTURE_SIZE,
        imageFile = imageFile,
        name = name,
        contentDescription = contentDescription,
        modifier = modifier,
        baseStyle = AppTheme.typography.bodyMedium,
        avatarColor = avatarColor,
        avatarSecondaryColor = avatarSecondaryColor
    )
}

@Composable
fun MediumProfilePicture(
    imageFile: File?,
    name: String?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    avatarColor: Color = DSTokens.colors.background.surface3,
) {
    BaseProfilePicture(
        avatarSize = MEDIUM_PROFILE_PICTURE_SIZE,
        imageFile = imageFile,
        name = name,
        contentDescription = contentDescription,
        modifier = modifier,
        baseStyle = AppTheme.typography.bodyMedium,
        avatarColor = avatarColor
    )
}

@Composable
fun SmallProfilePicture(
    imageFile: File?,
    name: String?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    avatarColor: Color = DSTokens.colors.background.surface3,
) {
    BaseProfilePicture(
        avatarSize = SMALL_PROFILE_PICTURE_SIZE,
        imageFile = imageFile,
        name = name,
        contentDescription = contentDescription,
        modifier = modifier,
        baseStyle = AppTheme.typography.labelMedium,
        avatarColor = avatarColor
    )
}

@Composable
@CombinedThemePreviews
private fun LargeProfilePicturePreview() {
    AndroidThemeForPreviews {
        LargeProfilePicture(
            imageFile = null,
            name = "Large",
            contentDescription = "Avatar",
        )
    }
}

@Composable
@CombinedThemePreviews
private fun MediumProfilePicturePreview() {
    AndroidThemeForPreviews {
        MediumProfilePicture(
            imageFile = null,
            name = "Medium",
            contentDescription = "Avatar",
        )
    }
}

@Composable
@CombinedThemePreviews
private fun MediumProfileIconPreview() {
    AndroidThemeForPreviews {
        MediumProfileIcon(
            icon = null,
            contentDescription = "Icon",
        )
    }
}

@Composable
@CombinedThemePreviews
private fun MediumProfilePictureWithSecondaryColorPreview() {
    AndroidThemeForPreviews {
        MediumProfileIcon(
            icon = null,
            avatarColor = Color(-30327),
            avatarSecondaryColor = Color(-44462),
            contentDescription = "Icon",
        )
    }
}

@Composable
@CombinedThemePreviews
private fun SmallProfilePicturePreview() {
    AndroidThemeForPreviews {
        SmallProfilePicture(
            imageFile = null,
            name = "Small",
            contentDescription = "Avatar",
        )
    }
}


