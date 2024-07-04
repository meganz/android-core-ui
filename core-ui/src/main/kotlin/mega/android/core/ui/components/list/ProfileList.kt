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
import mega.android.core.ui.components.MegaText
import mega.android.core.ui.components.profile.LargeProfilePicture
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.theme.values.TextColor
import java.io.File

@Composable
fun ProfileListItem(
    fullName: String,
    email: String,
    avatarFile: File?,
    modifier: Modifier = Modifier,
    avatarColor: Color = AppTheme.colors.background.surface3,
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
                style = AppTheme.typography.titleMedium,
                maxLines = 1
            )
            MegaText(
                text = email,
                textColor = TextColor.Secondary,
                style = AppTheme.typography.bodyMedium,
                maxLines = 1
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