import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import mega.android.core.ui.components.MegaText
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.theme.tokens.TextColor

@Composable
fun SuccessPrompt(
    modifier: Modifier,
    message: String,
) {
    BasePrompt(
        modifier = modifier,
        backgroundColor = AppTheme.colors.notifications.notificationSuccess,
        message = message,
    )
}

@Composable
fun ErrorPrompt(
    modifier: Modifier,
    message: String,
) {
    BasePrompt(
        modifier = modifier,
        backgroundColor = AppTheme.colors.notifications.notificationError,
        message = message,
    )
}

@Composable
fun TransparentPrompt(
    modifier: Modifier,
    message: String,
) {
    BasePrompt(
        modifier = modifier,
        backgroundColor = Color.Transparent,
        message = message,
    )
}

@Composable
internal fun BasePrompt(
    modifier: Modifier,
    backgroundColor: Color,
    message: String,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(color = backgroundColor, shape = RectangleShape)
            .padding(horizontal = LocalSpacing.current.x16, vertical = LocalSpacing.current.x4),
    ) {
        MegaText(
            text = message, textColor = TextColor.Primary, style = AppTheme.typography.bodyMedium
        )
    }
}

@CombinedThemePreviews
@Composable
private fun SuccessPromptPreview() {
    AndroidThemeForPreviews {
        SuccessPrompt(
            modifier = Modifier,
            message = "This is a prompt message",
        )
    }
}

@CombinedThemePreviews
@Composable
private fun ErrorPromptPreview() {
    AndroidThemeForPreviews {
        ErrorPrompt(
            modifier = Modifier,
            message = "This is a prompt message",
        )
    }
}

@CombinedThemePreviews
@Composable
private fun TransparentPromptPreview() {
    AndroidThemeForPreviews {
        TransparentPrompt(
            modifier = Modifier,
            message = "This is a prompt message",
        )
    }
}

