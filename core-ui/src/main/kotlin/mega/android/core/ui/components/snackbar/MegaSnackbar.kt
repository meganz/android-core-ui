package mega.android.core.ui.components.snackbar

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.tokens.theme.DSTokens

@Composable
fun MegaSnackbar(
    snackBarHostState: SnackbarHostState,
    safeAreaPadding: PaddingValues? = null,
) {
    SnackbarHost(snackBarHostState) { data ->
        val horizontalSpace = LocalSpacing.current.x8
        val snackbarModifier = Modifier
            .padding(
                safeAreaPadding ?: PaddingValues(
                    bottom = LocalSpacing.current.x12,
                    start = horizontalSpace,
                    end = horizontalSpace
                )
            )
            .testTag(MEGA_SNACKBAR_TAG)

        Snackbar(
            modifier = snackbarModifier,
            snackbarData = data,
            containerColor = DSTokens.colors.components.toastBackground,
            contentColor = DSTokens.colors.text.inverse,
            actionColor = DSTokens.colors.link.inverse
        )
    }
}

internal const val MEGA_SNACKBAR_TAG = "mega_snackbar:snackbar"
