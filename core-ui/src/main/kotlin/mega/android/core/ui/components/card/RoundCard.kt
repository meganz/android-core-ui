package mega.android.core.ui.components.card

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import mega.android.core.ui.tokens.theme.DSTokens

/**
 * A rounded card view provides a container with borderWidth and content.
 */
@Composable
fun RoundCard(
    modifier: Modifier = Modifier,
    borderWidth: Dp = 1.dp,
    content: @Composable BoxScope.() -> Unit
) {

    Box(
        modifier = modifier
            .clip(DSTokens.shapes.small)
            .border(
                width = borderWidth,
                color = DSTokens.colors.border.strong,
                shape = DSTokens.shapes.small
            )
    ) {
        content()
    }
}
