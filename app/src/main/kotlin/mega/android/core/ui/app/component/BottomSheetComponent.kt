package mega.android.core.ui.app.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import mega.android.core.ui.app.R
import mega.android.core.ui.app.util.Section
import mega.android.core.ui.components.button.PrimaryFilledButton
import mega.android.core.ui.components.image.MegaIcon
import mega.android.core.ui.components.list.OneLineListItem
import mega.android.core.ui.components.sheets.BottomSheetScaffold
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.theme.tokens.IconColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetComponentCatalog() {
    Spacer(modifier = Modifier.height(LocalSpacing.current.x16))

    Section(header = "Bottom Sheet") {
        val modalBottomSheetState = rememberModalBottomSheetState()
        var showBottomSheet by remember { mutableStateOf(false) }

        PrimaryFilledButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = LocalSpacing.current.x16),
            text = "Show Bottom Sheet",
            onClick = { showBottomSheet = true }
        )

        if (showBottomSheet) {
            BottomSheetScaffold(
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = modalBottomSheetState,
                dragHandle = {
                    MegaIcon(
                        modifier = Modifier.padding(vertical = 8.dp),
                        painter = painterResource(id = R.drawable.ic_handlebar),
                        tint = IconColor.Secondary
                    )
                }
            ) {
                OneLineListItem(text = "Item 1")
                OneLineListItem(
                    text = "Item 2",
                    trailingElement = {
                        MegaIcon(
                            painter = painterResource(id = mega.android.core.ui.R.drawable.ic_check_filled),
                            contentDescription = null,
                            tint = IconColor.Secondary,
                            modifier = Modifier.size(24.dp)
                        )
                    },
                )
                OneLineListItem(
                    text = "Item 3",
                    leadingElement = {
                        MegaIcon(
                            painter = painterResource(id = R.drawable.ic_launcher_foreground),
                            tint = IconColor.Primary,
                            contentDescription = null,
                            modifier = Modifier
                                .size(24.dp)
                                .align(Alignment.Center)
                        )
                    },
                )
            }
        }
    }
}