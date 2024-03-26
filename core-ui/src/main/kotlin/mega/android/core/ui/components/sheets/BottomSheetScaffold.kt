package mega.android.core.ui.components.sheets

import android.app.Activity
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.spacing.LocalSpacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetScaffold(
    onDismissRequest: () -> Unit,
    sheetState: SheetState,
    dragHandle: @Composable (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    val activity = LocalView.current.context as Activity

    SideEffect {
        WindowCompat.setDecorFitsSystemWindows(activity.window, false)
    }

    ModalBottomSheet(
        modifier = Modifier.fillMaxWidth(),
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        containerColor = AppTheme.colors.background.surface1,
        scrimColor = AppTheme.colors.background.blur,
        dragHandle = dragHandle,
        shape = RoundedCornerShape(
            topStart = LocalSpacing.current.x24,
            topEnd = LocalSpacing.current.x24,
        ),
        content = content
    )
}