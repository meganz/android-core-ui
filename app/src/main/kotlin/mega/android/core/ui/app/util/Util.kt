package mega.android.core.ui.app.util

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import mega.android.core.ui.components.MegaText
import mega.android.core.ui.components.divider.SubtleDivider
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.theme.tokens.TextColor

@Composable
fun Section(header: String, content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier.padding(vertical = LocalSpacing.current.x2)
    ) {
        MegaText(
            modifier = Modifier.padding(
                start = LocalSpacing.current.x16,
                bottom = LocalSpacing.current.x4
            ),
            text = header,
            textColor = TextColor.Primary,
            style = AppTheme.typography.titleMedium
        )
        SubtleDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = LocalSpacing.current.x16)
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
        )
        content()
    }
}