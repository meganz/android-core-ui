package mega.android.core.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import mega.android.core.ui.components.prompt.ErrorPrompt
import mega.android.core.ui.components.prompt.SuccessPrompt
import mega.android.core.ui.preview.BooleanProvider
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme

@Composable
fun NetworkConnectionBanner(
    modifier: Modifier,
    message: String,
    isConnectedToNetwork: Boolean
) {
    AnimatedVisibility(
        visible = isConnectedToNetwork.not(),
        enter = expandVertically(),
        exit = shrinkVertically()
    ) {
        Box(
            modifier = modifier
                .height(38.dp)
                .background(AppTheme.colors.notifications.notificationError)
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = message,
                style = AppTheme.typography.bodyMedium,
                color = AppTheme.colors.text.primary
            )
        }
    }
}

@Composable
fun NetworkConnectionBanner(
    isBannerVisible: Boolean,
    isConnectedToNetwork: Boolean,
    message: String,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = isBannerVisible
    ) {
        if (isConnectedToNetwork) {
            SuccessPrompt(
                modifier = modifier,
                message = message
            )
        } else {
            ErrorPrompt(
                modifier = modifier,
                message = message
            )
        }
    }
}

@CombinedThemePreviews
@Composable
private fun NetworkConnectionBannerPreview(
    @PreviewParameter(BooleanProvider::class) isConnectedToNetwork: Boolean,
) {
    AndroidThemeForPreviews {
        NetworkConnectionBanner(
            isBannerVisible = true,
            isConnectedToNetwork = isConnectedToNetwork,
            message = "Message"
        )
    }
}