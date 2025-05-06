package mega.android.core.ui.components.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import mega.android.core.ui.preview.CombinedThemePreviewsTablet
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.tokens.theme.DSTokens

enum class MegaDialogBackground {
    PageBackground,
    Surface1
}

@Composable
fun MegaDialog(
    modifier: Modifier,
    dialogBackground: MegaDialogBackground,
    onDismissRequest: () -> Unit,
    content: @Composable ColumnScope.() -> Unit,
) {
    val containerColor = when (dialogBackground) {
        MegaDialogBackground.PageBackground -> DSTokens.colors.background.pageBackground
        MegaDialogBackground.Surface1 -> DSTokens.colors.background.surface1
    }

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(decorFitsSystemWindows = false)
    ) {
        Column(modifier = modifier
            .clip(DSTokens.shapes.small)
            .background(color = containerColor)
        ) {
            content()
        }
    }
}

@CombinedThemePreviewsTablet
@Composable
private fun MegaDialogPreview() {
    AndroidThemeForPreviews {
        MegaDialog(
            modifier = Modifier.fillMaxSize(),
            dialogBackground = MegaDialogBackground.PageBackground,
            onDismissRequest = {},
        ) {
            // Content
        }
    }
}