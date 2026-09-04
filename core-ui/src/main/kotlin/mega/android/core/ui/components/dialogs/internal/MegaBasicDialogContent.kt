package mega.android.core.ui.components.dialogs.internal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.tokens.theme.DSTokens

/**
 * @param content extra body content, laid out below [text]. It is hosted in a vertically
 * scrolling container, so it must not itself scroll vertically.
 * @param imageContent body image, laid out below [content]. Subject to the same constraint.
 */
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
            if (text != null || content != null || imageContent != null) {
                // The body is the only part of the dialog allowed to shrink; the title and the
                // buttons always keep their intrinsic height.
                Column(
                    modifier = Modifier
                        .weight(weight = 1f, fill = false)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(LocalSpacing.current.x16),
                ) {
                    text?.let {
                        Box(Modifier.align(Alignment.Start)) {
                            it()
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
