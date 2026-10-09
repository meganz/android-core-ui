package mega.android.core.ui.components.indicators

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.Snapshot
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import mega.android.core.ui.theme.AndroidTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MegaAnimatedLinearProgressIndicatorTest {

    @get:Rule
    val composeRule = createComposeRule()

    private var isShown by mutableStateOf(true)

    @Test
    fun `test that progress does not restart from zero when state is restored`() {
        composeRule.setContent {
            val stateHolder = rememberSaveableStateHolder()
            AndroidTheme(isDark = false) {
                if (isShown) {
                    stateHolder.SaveableStateProvider(key = TAG) {
                        MegaAnimatedLinearProgressIndicator(
                            indicatorProgress = PROGRESS,
                            modifier = Modifier.testTag(TAG),
                        )
                    }
                }
            }
        }
        composeRule.waitForIdle()
        assertEquals(PROGRESS, currentProgress(), TOLERANCE)

        composeRule.mainClock.autoAdvance = false
        updateShown(false)
        composeRule.onNodeWithTag(TAG).assertDoesNotExist()
        updateShown(true)

        assertEquals(PROGRESS, currentProgress(), TOLERANCE)
    }

    @Test
    fun `test that progress animates from zero when first composed`() {
        composeRule.mainClock.autoAdvance = false
        composeRule.setContent {
            AndroidTheme(isDark = false) {
                MegaAnimatedLinearProgressIndicator(
                    indicatorProgress = PROGRESS,
                    modifier = Modifier.testTag(TAG),
                )
            }
        }

        assertEquals(0f, currentProgress(), TOLERANCE)
    }

    private fun updateShown(value: Boolean) {
        composeRule.runOnUiThread {
            isShown = value
            Snapshot.sendApplyNotifications()
        }
        composeRule.mainClock.advanceTimeByFrame()
    }

    private fun currentProgress(): Float = composeRule.onNodeWithTag(TAG)
        .fetchSemanticsNode()
        .config[SemanticsProperties.ProgressBarRangeInfo]
        .current

    private companion object {
        const val TAG = "progress_indicator"
        const val PROGRESS = 0.75f
        const val TOLERANCE = 0.001f
    }
}
