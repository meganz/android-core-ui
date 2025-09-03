package mega.android.core.ui.components.dialogs.internal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.tokens.theme.DSTokens

@Composable
internal fun MegaBasicDialogContent(
    buttons: @Composable () -> Unit,
    title: (@Composable () -> Unit)?,
    text: @Composable (() -> Unit)?,
    shape: Shape,
    modifier: Modifier = Modifier,
    inputContent: (@Composable () -> Unit)? = null,
    imageContent: (@Composable () -> Unit)? = null,
) {
    Surface(
        modifier = modifier,
        shape = shape,
        color = DSTokens.colors.background.surface1
    ) {
        Column(
            modifier = Modifier.padding(LocalSpacing.current.x24),
            verticalArrangement = Arrangement.spacedBy(LocalSpacing.current.x16),
        ) {
            title?.invoke()
            text?.let {
                Box(
                    Modifier.Companion
                        .weight(weight = 1f, fill = false)
                        .align(Alignment.Start)
                ) {
                    text()
                }
            }
            inputContent?.invoke()
            imageContent?.let {
                Box(
                    Modifier.Companion
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                ) {
                    it()
                }
            }
            Box(
                modifier = Modifier.Companion
                    .padding(top = LocalSpacing.current.x8)
                    .align(Alignment.End)
            ) {
                buttons()
            }
        }
    }
}