package mega.android.core.ui.components.dialogs.internal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.tokens.theme.DSTokens

@Composable
internal fun MegaBasicDialogContent(
    buttons: @Composable () -> Unit,
    title: (@Composable () -> Unit)?,
    text: @Composable (() -> Unit)?,
    modifier: Modifier = Modifier,
    content: (@Composable () -> Unit)? = null,
    imageContent: (@Composable () -> Unit)? = null,
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(28.dp),
        color = DSTokens.colors.background.surface1
    ) {
        Column(
            modifier = Modifier.padding(LocalSpacing.current.x24),
            verticalArrangement = Arrangement.spacedBy(LocalSpacing.current.x16),
        ) {
            title?.invoke()
            text?.let {
                Box(
                    Modifier
                        .weight(weight = 1f, fill = false)
                        .align(Alignment.Start)
                ) {
                    text()
                }
            }
            content?.invoke()
            imageContent?.let {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                ) {
                    it()
                }
            }
            Box(
                modifier = Modifier
                    .padding(top = LocalSpacing.current.x8)
                    .align(Alignment.End)
            ) {
                buttons()
            }
        }
    }
}