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
import androidx.compose.ui.unit.dp
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