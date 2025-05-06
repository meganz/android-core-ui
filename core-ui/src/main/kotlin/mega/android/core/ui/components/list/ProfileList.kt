package mega.android.core.ui.components.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import mega.android.core.ui.components.MegaText
import mega.android.core.ui.components.profile.LargeProfilePicture
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.tokens.theme.DSTokens
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.theme.values.TextColor
import java.io.File

class ProfileListTextLineStyle(
    val overflow: TextOverflow,
    val maxLines: Int,
    val minLines: Int
)

object ProfileListTextLineStyleDefaults {

    fun lineStyle(
        overflow: TextOverflow = TextOverflow.Clip,
        maxLines: Int = Int.MAX_VALUE,
        minLines: Int = 1,
    ): ProfileListTextLineStyle = ProfileListTextLineStyle(
        overflow = overflow,
        maxLines = maxLines,
        minLines = minLines
    )
}

@Composable
fun ProfileListItem(
    fullName: String,
    email: String,
    avatarFile: File?,
    modifier: Modifier = Modifier,
    fullNameLineStyle: ProfileListTextLineStyle = ProfileListTextLineStyleDefaults.lineStyle(),
    emailLineStyle: ProfileListTextLineStyle = ProfileListTextLineStyleDefaults.lineStyle(),
    avatarColor: Color = DSTokens.colors.background.surface3,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        LargeProfilePicture(
            modifier = Modifier.padding(vertical = LocalSpacing.current.x16),
            imageFile = avatarFile,
            avatarColor = avatarColor,
            contentDescription = fullName,
            name = fullName,
        )

        Column(
            modifier = Modifier.padding(horizontal = LocalSpacing.current.x16),
            verticalArrangement = Arrangement.Center
        ) {
            MegaText(
                text = fullName,
                textColor = TextColor.Primary,
                overflow = fullNameLineStyle.overflow,
                maxLines = fullNameLineStyle.maxLines,
                minLines = fullNameLineStyle.minLines,
                style = AppTheme.typography.titleMedium,
            )
            MegaText(
                text = email,
                textColor = TextColor.Secondary,
                overflow = emailLineStyle.overflow,
                maxLines = emailLineStyle.maxLines,
                minLines = emailLineStyle.minLines,
                style = AppTheme.typography.bodyMedium,
            )
        }
    }
}

@CombinedThemePreviews
@Composable
fun ProfileListItemPreview() {
    AndroidThemeForPreviews {
        ProfileListItem(
            fullName = "FirstName LastName",
            email = "example@example.com",
            avatarFile = null
        )
    }
}

@CombinedThemePreviews
@Composable
fun ProfileListItemLongTextPreview() {
    AndroidThemeForPreviews {
        ProfileListItem(
            fullName = "Very very very long FirstName Very very very long LastName",
            email = "Very very very long Very very very long Very very very long example@example.com",
            avatarFile = null
        )
    }
}

@CombinedThemePreviews
@Composable
fun ProfileListItemLongTextWithLineStylePreview() {
    AndroidThemeForPreviews {
        ProfileListItem(
            fullName = "Very very very long FirstName Very very very long LastName",
            fullNameLineStyle = ProfileListTextLineStyleDefaults.lineStyle(
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            ),
            email = "Very very very long Very very very long Very very very long example@example.com",
            emailLineStyle = ProfileListTextLineStyleDefaults.lineStyle(
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            ),
            avatarFile = null
        )
    }
}
