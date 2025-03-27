package mega.android.core.ui.components.tooltip.popup.simple

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import mega.android.core.ui.components.tooltip.component.TooltipBody
import mega.android.core.ui.components.tooltip.component.TooltipTitle
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.spacing.LocalSpacing

@Immutable
sealed interface SimpleTooltipContent {

    @Composable
    fun Content()
}

@Immutable
class SimpleTooltipContentTitleAndBody(
    private val title: String,
    private val body: String
) : SimpleTooltipContent {

    @Composable
    override fun Content() {
        SimpleTooltipContentScope {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                TooltipTitle(value = title)

                TooltipBody(
                    modifier = Modifier.padding(top = LocalSpacing.current.x8),
                    value = body
                )
            }
        }
    }
}

@Immutable
class SimpleTooltipContentTitleOnly(private val value: String) : SimpleTooltipContent {

    @Composable
    override fun Content() {
        SimpleTooltipContentScope {
            TooltipTitle(value = value)
        }
    }
}

@Immutable
class SimpleTooltipContentBodyOnly(private val value: String) : SimpleTooltipContent {

    @Composable
    override fun Content() {
        SimpleTooltipContentScope {
            TooltipBody(value = value)
        }
    }
}

@Composable
private fun SimpleTooltipContentScope(
    content: @Composable () -> Unit
) {
    Box(modifier = Modifier.padding(LocalSpacing.current.x12)) {
        content()
    }
}

@CombinedThemePreviews
@Composable
private fun SimpleTooltipContentTitleAndBodyPreview() {
    AndroidThemeForPreviews {
        Box(modifier = Modifier.background(AppTheme.colors.background.inverse)) {
            SimpleTooltipContentTitleAndBody(title = "Simple text", body = "Simple text").Content()
        }
    }
}

@CombinedThemePreviews
@Composable
private fun SimpleTooltipContentTitleOnlyPreview() {
    AndroidThemeForPreviews {
        Box(modifier = Modifier.background(AppTheme.colors.background.inverse)) {
            SimpleTooltipContentTitleOnly(value = "Simple text").Content()
        }
    }
}

@CombinedThemePreviews
@Composable
private fun SimpleTooltipContentBodyOnlyPreview() {
    AndroidThemeForPreviews {
        Box(modifier = Modifier.background(AppTheme.colors.background.inverse)) {
            SimpleTooltipContentBodyOnly(value = "Simple text").Content()
        }
    }
}
