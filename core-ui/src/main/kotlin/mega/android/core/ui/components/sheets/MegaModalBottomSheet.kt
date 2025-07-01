package mega.android.core.ui.components.sheets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetDefaults
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import mega.android.core.ui.R
import mega.android.core.ui.components.button.MegaOutlinedButton
import mega.android.core.ui.components.image.MegaIcon
import mega.android.core.ui.components.list.OneLineListItem
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.theme.values.IconColor
import mega.android.core.ui.tokens.theme.DSTokens

enum class MegaModalBottomSheetBackground {
    PageBackground,
    Surface1
}

/**
 * Modal Bottom Sheet following our design system
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MegaModalBottomSheet(
    sheetState: SheetState,
    bottomSheetBackground: MegaModalBottomSheetBackground,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    windowInsets: WindowInsets? = null,
    properties: ModalBottomSheetProperties = ModalBottomSheetDefaults.properties,
    content: @Composable ColumnScope.() -> Unit,
) {
    val containerColor = when (bottomSheetBackground) {
        MegaModalBottomSheetBackground.PageBackground -> DSTokens.colors.background.pageBackground
        MegaModalBottomSheetBackground.Surface1 -> DSTokens.colors.background.surface1
    }

    ModalBottomSheet(
        modifier = modifier.fillMaxWidth(),
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        containerColor = containerColor,
        scrimColor = DSTokens.colors.background.blur,
        dragHandle = if (sheetState.hasPartiallyExpandedState) {
            { BottomSheetDefaults.DragHandle() }
        } else {
            { Spacer(Modifier.height(22.dp)) }
        },
        shape = RoundedCornerShape(
            topStart = LocalSpacing.current.x24,
            topEnd = LocalSpacing.current.x24,
        ),
        contentWindowInsets = {
            windowInsets ?: BottomSheetDefaults.windowInsets.only(WindowInsetsSides.Bottom)
        },
        properties = properties,
        content = content
    )
}

@Deprecated("Optional drag handle is deprecated, please use the component without showDragHandle parameter to let it decide if it's needed or not following our UX convention")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MegaModalBottomSheet(
    sheetState: SheetState,
    bottomSheetBackground: MegaModalBottomSheetBackground,
    showDragHandle: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    windowInsets: WindowInsets? = null,
    properties: ModalBottomSheetProperties = ModalBottomSheetDefaults.properties,
    content: @Composable ColumnScope.() -> Unit,
) {
    val containerColor = when (bottomSheetBackground) {
        MegaModalBottomSheetBackground.PageBackground -> DSTokens.colors.background.pageBackground
        MegaModalBottomSheetBackground.Surface1 -> DSTokens.colors.background.surface1
    }

    ModalBottomSheet(
        modifier = modifier.fillMaxWidth(),
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        containerColor = containerColor,
        scrimColor = DSTokens.colors.background.blur,
        dragHandle = if (showDragHandle) {
            { MegaBottomSheetDragHandler() }
        } else null,
        shape = RoundedCornerShape(
            topStart = LocalSpacing.current.x24,
            topEnd = LocalSpacing.current.x24,
        ),
        contentWindowInsets = {
            windowInsets ?: BottomSheetDefaults.windowInsets.only(WindowInsetsSides.Bottom)
        },
        properties = properties,
        content = content
    )
}

@Deprecated("Will be removed together with the bottom sheet with showDragHandle parameter")
@Composable
private fun MegaBottomSheetDragHandler() {
    MegaIcon(
        modifier = Modifier.padding(vertical = 8.dp),
        painter = painterResource(id = R.drawable.ic_handlebar),
        tint = IconColor.Disabled
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@CombinedThemePreviews
fun MegaModalBottomSheetPreview() {
    AndroidThemeForPreviews {
        var items by remember { mutableIntStateOf(0) }
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            MegaOutlinedButton(
                Modifier.padding(6.dp),
                text = "Show Bottom Sheet (NO expanded state)",
                onClick = {
                    items = 3
                })
            MegaOutlinedButton(
                Modifier.padding(6.dp),
                text = "Show Bottom Sheet (With expanded state)",
                onClick = {
                    items = 10
                })
        }
        if (items > 0) {
            MegaModalBottomSheet(
                rememberModalBottomSheetState(),
                MegaModalBottomSheetBackground.Surface1,
                { items = 0 },
            ) {
                (1..items).forEach {
                    OneLineListItem("Item $it")
                }
            }
        }
    }
}