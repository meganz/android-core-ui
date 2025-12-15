package mega.android.core.ui.components.dialogs

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import mega.android.core.ui.components.MegaText
import mega.android.core.ui.components.indicators.LargeInfiniteSpinnerIndicator
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.theme.values.TextColor
import mega.android.core.ui.tokens.theme.DSTokens
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicSpinnerDialog(
    modifier: Modifier = Modifier,
    contentText: String? = null,
    dismissOnClickOutside: Boolean = false,
    dismissOnBackPress: Boolean = false,
    onDismiss: () -> Unit = {},
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = dismissOnBackPress,
            dismissOnClickOutside = dismissOnClickOutside
        )
    ) {
        Card(
            modifier = modifier,
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(containerColor = DSTokens.colors.background.surface1)
        ) {
            Column(
                modifier = Modifier.padding(LocalSpacing.current.x24),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LargeInfiniteSpinnerIndicator()
                contentText?.let {
                    val coroutineScope = rememberCoroutineScope()
                    var revealText by rememberSaveable { mutableStateOf(false) }
                    var measuredHeightPx by remember { mutableStateOf<Int?>(null) }

                    LaunchedEffect(Unit) {
                        if (!revealText) {
                            coroutineScope.launch {
                                delay(3.seconds)
                                revealText = true
                            }
                        }
                    }

                    val animatedHeight by animateDpAsState(
                        targetValue = with(LocalDensity.current) {
                            if (revealText && measuredHeightPx != null) measuredHeightPx?.toDp()?:0.dp else 0.dp
                        },
                        animationSpec = tween(durationMillis = 500)
                    )

                    val animatedAlpha by animateFloatAsState(
                        targetValue = if (revealText) 1f else 0f,
                        animationSpec = tween(durationMillis = 500)
                    )

                    Box(
                        modifier = Modifier
                            .then(
                                if (measuredHeightPx == null) Modifier else Modifier.height(
                                    animatedHeight
                                )
                            )
                            .alpha(animatedAlpha)
                            .onSizeChanged { size ->
                                if (measuredHeightPx == null) measuredHeightPx = size.height
                            }
                    ) {
                        MegaText(
                            modifier = Modifier.padding(top = LocalSpacing.current.x12),
                            text = contentText,
                            style = AppTheme.typography.bodyMedium,
                            textColor = TextColor.Secondary,
                        )
                    }
                }
            }
        }
    }
}

@CombinedThemePreviews
@Composable
private fun BasicSpinnerDialogPreview() {
    AndroidThemeForPreviews {
        BasicSpinnerDialog(contentText = "Basic spinner dialog")
    }
}