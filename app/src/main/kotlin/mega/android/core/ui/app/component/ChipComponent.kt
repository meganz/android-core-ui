package mega.android.core.ui.app.component

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import mega.android.core.ui.R
import mega.android.core.ui.app.util.Section
import mega.android.core.ui.components.chip.ChipTrailingAction
import mega.android.core.ui.components.chip.MegaChip
import mega.android.core.ui.theme.spacing.LocalSpacing

@Composable
fun ChipComponent() {
    val context = LocalContext.current
    val spacing = LocalSpacing.current
    Spacer(modifier = Modifier.height(spacing.x16))

    Section(header = "Chip") {
        var selected by remember { mutableStateOf(true) }
        Row(
            modifier = Modifier.padding(horizontal = spacing.x16),
            horizontalArrangement = Arrangement.spacedBy(spacing.x12)
        ) {
            MegaChip(
                selected = selected,
                text = "Type",
                onClick = { selected = !selected },
            )
            MegaChip(
                selected = false,
                text = "Disabled",
                enabled = false,
            )
        }
    }

    Spacer(modifier = Modifier.height(spacing.x16))

    Section(header = "Chip with a trailing dismiss") {
        val extensions = remember { mutableStateListOf("pdf", "mp4", "zip") }
        Row(
            modifier = Modifier.padding(horizontal = spacing.x16),
            horizontalArrangement = Arrangement.spacedBy(spacing.x12)
        ) {
            extensions.forEach { extension ->
                MegaChip(
                    selected = false,
                    text = extension,
                    trailingIcon = R.drawable.ic_x_medium_thin_outline,
                    onClick = {
                        Toast.makeText(context, "$extension tapped", Toast.LENGTH_SHORT).show()
                    },
                    trailingAction = ChipTrailingAction(
                        contentDescription = "Remove $extension",
                        onClick = { extensions.remove(extension) },
                    ),
                )
            }
        }
    }
}
