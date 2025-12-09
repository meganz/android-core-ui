package mega.android.core.ui.components.prompt

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import mega.android.core.ui.components.MegaText
import mega.android.core.ui.components.toolbar.ForceRiceTopAppBarEffect
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.theme.values.TextColor
import mega.android.core.ui.tokens.theme.DSTokens

private val promptHeight = 38.dp

/**
 * @param forceRiceTopAppBar if true, the [ForceRiceTopAppBarEffect] will be applied, causing the top app bar to be raised while the prompt is in the composition.
 * It should be true if the prompt is sticky under the TopAppBar or TabRows
 */
@Composable
fun SuccessPrompt(
    message: String,
    modifier: Modifier = Modifier,
    forceRiceTopAppBar: Boolean = false,
) {
    BasePrompt(
        modifier = modifier,
        backgroundColor = DSTokens.colors.notifications.notificationSuccess,
        message = message,
        forceRiceTopAppBar = forceRiceTopAppBar,
    )
}

/**
 * @param forceRiceTopAppBar if true, the [ForceRiceTopAppBarEffect] will be applied, causing the top app bar to be raised while the prompt is in the composition.
 * It should be true if the prompt is sticky under the TopAppBar or TabRows
 */
@Composable
fun ErrorPrompt(
    message: String,
    modifier: Modifier = Modifier,
    forceRiceTopAppBar: Boolean = false,
) {
    BasePrompt(
        modifier = modifier,
        backgroundColor = DSTokens.colors.notifications.notificationError,
        message = message,
        forceRiceTopAppBar = forceRiceTopAppBar,
    )
}

/**
 * @param forceRiceTopAppBar if true, the [ForceRiceTopAppBarEffect] will be applied, causing the top app bar to be raised while the prompt is in the composition.
 * It should be true if the prompt is sticky under the TopAppBar or TabRows
 */
@Composable
fun TransparentPrompt(
    message: String,
    modifier: Modifier = Modifier,
    forceRiceTopAppBar: Boolean = false,
) {
    BasePrompt(
        modifier = modifier,
        backgroundColor = Color.Transparent,
        message = message,
        forceRiceTopAppBar = forceRiceTopAppBar,
    )
}

/**
 * @param forceRiceTopAppBar if true, the [ForceRiceTopAppBarEffect] will be applied, causing the top app bar to be raised while the prompt is in the composition.
 * It should be true if the prompt is sticky under the TopAppBar or TabRows
 */
@Composable
internal fun BasePrompt(
    backgroundColor: Color,
    message: String,
    modifier: Modifier = Modifier,
    forceRiceTopAppBar: Boolean = false,
) {
    if (forceRiceTopAppBar) {
        @OptIn(ExperimentalMaterial3Api::class)
        ForceRiceTopAppBarEffect()
    }
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(promptHeight)
            .background(color = backgroundColor, shape = RectangleShape)
            .padding(horizontal = LocalSpacing.current.x16, vertical = LocalSpacing.current.x4),
        contentAlignment = Alignment.Center,
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
            message = "This is a prompt message",
        )
    }
}

@CombinedThemePreviews
@Composable
private fun ErrorPromptPreview() {
    AndroidThemeForPreviews {
        ErrorPrompt(
            message = "This is a prompt message",
        )
    }
}

@CombinedThemePreviews
@Composable
private fun TransparentPromptPreview() {
    AndroidThemeForPreviews {
        TransparentPrompt(
            message = "This is a prompt message",
        )
    }
}

