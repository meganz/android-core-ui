/*
 * Code Connect mapping for the Figma indicator component sets.
 *
 * Figma file : HagXfSTF94ICEFj8rfNyLc  (ANDROID - New)
 * Components :
 *   - Linear progress indicator    (node 54416:48852) → ProgressBarIndicator,
 *                                                       InfiniteProgressBarIndicator,
 *                                                       MegaAnimatedLinearProgressIndicator
 *   - Circular progress indicator  (node 54416:48864) → Small/LargeSpinnerIndicator
 *                                                       + Infinite variants
 *   - Page controls                (node 54486:43440) → PageControlsIndicator
 *   - sf_Timer radial              (node 61107:1615)  → TimerRadialProgressBar
 *   - Skeleton loading             (node 61176:9753)  → (no direct Compose counterpart — left TODO)
 *
 * Compose-only (no Figma counterpart): HUD / LargeHUD / SmallHUD — no Figma HUD set found.
 *
 * Property names VERIFIED via MCP on 2026-04-23.
 */

package mega.android.core.ui.components.indicators

import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import com.figma.code.connect.FigmaConnect

// ─── Linear progress indicator ─────────────────────────────────────────────
//
// Figma exposes Progress variants (25%/50%/75%/100%/Custom) but Compose drives this
// via a single `progressPercentage` Float param — no variant axis to match.

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=54416-48852")
class ProgressBarIndicatorConnection {
    @Composable
    fun ProgressBarIndicatorExample() {
        ProgressBarIndicator(
            progressPercentage = 50f,
        )
    }
}

// ─── Circular progress indicator ───────────────────────────────────────────
//
// Figma exposes Size (Large | Small) × Progress variants. Compose splits on Size only.

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=54416-48864")
@com.figma.code.connect.FigmaVariant("Size", "Large")
class LargeSpinnerIndicatorConnection {
    @Composable
    fun LargeSpinnerIndicatorExample() {
        LargeSpinnerIndicator(
            progressPercentage = 50f,
        )
    }
}

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=54416-48864")
@com.figma.code.connect.FigmaVariant("Size", "Small")
class SmallSpinnerIndicatorConnection {
    @Composable
    fun SmallSpinnerIndicatorExample() {
        SmallSpinnerIndicator(
            progressPercentage = 50f,
        )
    }
}

// ─── Page controls ─────────────────────────────────────────────────────────
//
// Figma exposes Pages × Selected variants. Compose handles both via runtime pagerState.

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=54486-43440")
class PageControlsIndicatorConnection {
    @Composable
    fun PageControlsIndicatorExample() {
        PageControlsIndicator(
            pagerState = rememberPagerState { 3 },
            itemCount = 3,
            onClick = {},
        )
    }
}

// ─── Timer radial ──────────────────────────────────────────────────────────
//
// Figma exposes `%` variant (1..100). Compose drives via remainingTimeInMilliSeconds().

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=61107-1615")
class TimerRadialProgressBarConnection {
    @Composable
    fun TimerRadialProgressBarExample() {
        TimerRadialProgressBar(
            totalTimeInSeconds = 30,
            remainingTimeInMilliSeconds = { 15_000L },
        )
    }
}
