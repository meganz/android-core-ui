package mega.android.core.ui.components.sheets

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetDefaults
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import mega.android.core.ui.R
import mega.android.core.ui.components.image.MegaIcon
import mega.android.core.ui.tokens.theme.DSTokens
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.theme.values.IconColor

enum class MegaModalBottomSheetBackground {
    PageBackground,
    Surface1
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MegaModalBottomSheet(
    sheetState: SheetState,
    bottomSheetBackground: MegaModalBottomSheetBackground,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    windowInsets: WindowInsets? = null,
    dragHandle: @Composable (() -> Unit)? = null,
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
        dragHandle = dragHandle,
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

@Composable
fun MegaBottomSheetDragHandler() {
    MegaIcon(
        modifier = Modifier.padding(vertical = 8.dp),
        painter = painterResource(id = R.drawable.ic_handlebar),
        tint = IconColor.Disabled
    )
}