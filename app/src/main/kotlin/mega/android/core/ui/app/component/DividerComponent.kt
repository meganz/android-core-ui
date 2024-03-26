package mega.android.core.ui.app.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import mega.android.core.ui.app.util.Section
import mega.android.core.ui.components.MegaText
import mega.android.core.ui.components.divider.StrongDivider
import mega.android.core.ui.components.divider.SubtleDivider
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.theme.tokens.TextColor

@Composable
internal fun DividerComponentCatalog() {
    Spacer(modifier = Modifier.height(LocalSpacing.current.x16))

    Section(header = "Divider") {
        Column {
            MegaText(
                text = "1. Subtle Divider",
                modifier = Modifier.padding(horizontal = 16.dp),
                textColor = TextColor.Primary
            )
            Box(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .background(Color.Green.copy(alpha = 0.4f))
            ) {
                SubtleDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
            Spacer(modifier = Modifier.height(LocalSpacing.current.x8))

            MegaText(
                text = "2. Strong Divider",
                modifier = Modifier.padding(horizontal = 16.dp),
                textColor = TextColor.Primary
            )

            Box(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .background(Color.Green.copy(alpha = 0.4f))
            ) {
                StrongDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
        }
    }
}