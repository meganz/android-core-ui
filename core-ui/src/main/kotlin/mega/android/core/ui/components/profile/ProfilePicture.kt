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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import mega.android.core.ui.components.MegaText
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.values.TextColor
import java.io.File

private val LARGE_PROFILE_PICTURE_SIZE = 58.dp
private val MEDIUM_PROFILE_PICTURE_SIZE = 32.dp
private val SMALL_PROFILE_PICTURE_SIZE = 24.dp

@Composable
private fun BaseProfilePicture(
    avatarSize: Dp,
    imageFile: File?,
    name: String?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    baseStyle: TextStyle = LocalTextStyle.current,
    avatarColor: Color = AppTheme.colors.background.surface3,
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
                    model = it.absoluteFile.absolutePath,
                    contentDescription = contentDescription,
                    contentScale = ContentScale.Crop
                )
            }
        } ?: run {
            Box(
                modifier = Modifier
                    .size(avatarSize)
                    .clip(CircleShape)
                    .background(
                        color = if (avatarColor == Color.Unspecified) {
                            AppTheme.colors.background.surface3
                        } else {
                            avatarColor
                        }
                    ),
                contentAlignment = Alignment.Center,
            ) {
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

@Composable
fun LargeProfilePicture(
    imageFile: File?,
    name: String?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    avatarColor: Color = AppTheme.colors.background.surface3,
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
fun MediumProfilePicture(
    imageFile: File?,
    name: String?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    avatarColor: Color = AppTheme.colors.background.surface3,
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
    avatarColor: Color = AppTheme.colors.background.surface3,
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
private fun SmallProfilePicturePreview() {
    AndroidThemeForPreviews {
        SmallProfilePicture(
            imageFile = null,
            name = "Small",
            contentDescription = "Avatar",
        )
    }
}
