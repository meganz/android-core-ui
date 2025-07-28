package mega.android.core.ui.app.screen.floatingtoolbar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import mega.android.core.ui.R
import mega.android.core.ui.components.MegaScaffold
import mega.android.core.ui.components.MegaText
import mega.android.core.ui.components.toolbar.MegaFloatingToolbar
import mega.android.core.ui.model.TopAppBarAction
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.privacy.android.shared.original.core.ui.model.TopAppBarActionWithClick

@Composable
fun FloatingToolbarScreen() {
    val horizontalActions = listOf(
        R.drawable.ic_alert_circle_medium_thin_outline,
        R.drawable.ic_alert_triangle_medium_thin_outline,
        R.drawable.ic_check_medium_thin_outline,
        R.drawable.ic_close_medium_thin_outline,
        R.drawable.ic_help_circle_medium_thin_outline
    ).mapIndexed { i, iconRes ->
        object : TopAppBarAction {
            @Composable
            override fun getDescription() = "Action $i"

            override val testTag = getDescription()

            @Composable
            override fun getIconPainter() = painterResource(id = iconRes)
        }
    }

    MegaScaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(LocalSpacing.current.x16),
        ) {
            // Content area
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(LocalSpacing.current.x16)
            ) {
                MegaText(
                    text = "MegaFloatingToolbar Demo",
                    style = AppTheme.typography.headlineMedium,
                )

                MegaText(
                    text = "This demonstrates the floating toolbar components following Material Design 3 specifications",
                    style = AppTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
            }

            // Horizontal floating toolbar at bottom
            MegaFloatingToolbar(
                actions = horizontalActions.map { TopAppBarActionWithClick(it) {} },
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
} 