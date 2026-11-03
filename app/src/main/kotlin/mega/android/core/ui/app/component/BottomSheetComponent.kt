package mega.android.core.ui.app.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
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
import mega.android.core.ui.components.list.FlexibleLineListItem
import mega.android.core.ui.components.list.OneLineListItem
import mega.android.core.ui.components.settings.SettingsOptionsModal
import mega.android.core.ui.components.sheets.MegaModalBottomSheet
import mega.android.core.ui.components.sheets.MegaModalBottomSheetBackground
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.theme.values.IconColor
import mega.android.core.ui.theme.values.SupportColor
import mega.android.core.ui.R as coreR

enum class Server {
    Production, Staging, Development
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetComponentCatalog() {
    Spacer(modifier = Modifier.height(LocalSpacing.current.x16))

    Section(header = "Bottom Sheet") {
        val modalBottomSheetState = rememberModalBottomSheetState()
        var showBottomSheet by remember { mutableStateOf(false) }
        var showSettingBottomSheet by remember { mutableStateOf(false) }
        var selectedItem by remember { mutableStateOf(Server.Production) }

        PrimaryFilledButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = LocalSpacing.current.x16),
            text = "Show Bottom Sheet",
            onClick = { showBottomSheet = true }
        )

        PrimaryFilledButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .padding(horizontal = LocalSpacing.current.x16),
            text = "Show Setting Bottom Sheet",
            onClick = { showSettingBottomSheet = true }
        )

        var showEditTopBarBottomSheet by remember { mutableStateOf(false) }
        PrimaryFilledButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .padding(horizontal = LocalSpacing.current.x16),
            text = "Show Bottom Sheet with edit top bar",
            onClick = { showEditTopBarBottomSheet = true }
        )
        if (showEditTopBarBottomSheet) {
            EditTopBarBottomSheet(onDismiss = { showEditTopBarBottomSheet = false })
        }

        if (showSettingBottomSheet) {
            SettingsOptionsModal(
                key = "server",
                content = {
                    addHeader(
                        "Change server",
                        "Are you sure you want to change a test server? Your account may server irrecoverable problems."
                    )
                    addItem(
                        isSelected = selectedItem == Server.Production,
                        value = Server.Production,
                        valueToString = { it.toString() })
                    addItem(
                        isSelected = selectedItem == Server.Staging,
                        value = Server.Staging,
                        valueToString = { it.toString() })
                    addItem(
                        isSelected = selectedItem == Server.Development,
                        value = Server.Development,
                        valueToString = { it.toString() })
                },
                onDismiss = {
                    showSettingBottomSheet = false
                },
            ) {
                selectedItem = it
            }
        }

        if (showBottomSheet) {
            MegaModalBottomSheet(
                sheetState = modalBottomSheetState,
                bottomSheetBackground = MegaModalBottomSheetBackground.Surface1,
                onDismissRequest = {
                    showBottomSheet = false
                },
            ) {
                OneLineListItem(text = "Item 1")
                OneLineListItem(
                    text = "Item 2",
                    trailingElement = {
                        MegaIcon(
                            painter = painterResource(id = coreR.drawable.ic_check_medium_thin_outline),
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

/**
 * Sheet with an edit top bar: the top bar is at the top edge of the sheet, without the 22dp space,
 * and has an edit action, or a close action in edit mode.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EditTopBarBottomSheet(onDismiss: () -> Unit) {
    var isEditMode by remember { mutableStateOf(false) }
    MegaModalBottomSheet(
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        bottomSheetBackground = MegaModalBottomSheetBackground.Surface1,
        onDismissRequest = onDismiss,
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .padding(horizontal = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (isEditMode) {
                    IconButton(onClick = { isEditMode = false }) {
                        MegaIcon(
                            painter = painterResource(id = coreR.drawable.ic_close_medium_thin_outline),
                            tint = IconColor.Primary,
                            contentDescription = "Close",
                        )
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                if (!isEditMode) {
                    IconButton(onClick = { isEditMode = true }) {
                        MegaIcon(
                            painter = painterResource(id = R.drawable.ic_pen_1_medium_thin_outline),
                            tint = IconColor.Primary,
                            contentDescription = "Edit",
                        )
                    }
                }
            }
        },
    ) {
        FlexibleLineListItem(
            title = "Item 1",
            enable = !isEditMode,
            trailingElement = {
                val checkPainter = painterResource(id = coreR.drawable.ic_check_medium_thin_outline)
                if (isEditMode) {
                    MegaIcon(
                        painter = checkPainter,
                        contentDescription = null,
                        tint = IconColor.Disabled,
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    MegaIcon(
                        painter = checkPainter,
                        contentDescription = null,
                        supportTint = SupportColor.Success,
                        modifier = Modifier.size(24.dp)
                    )
                }
            },
        )
        listOf("Item 2", "Item 3").forEach { title ->
            FlexibleLineListItem(
                title = title,
                trailingElement = if (isEditMode) {
                    {
                        MegaIcon(
                            painter = painterResource(id = R.drawable.ic_trash_medium_thin_outline),
                            contentDescription = null,
                            supportTint = SupportColor.Error,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                } else null,
            )
        }
    }
}
