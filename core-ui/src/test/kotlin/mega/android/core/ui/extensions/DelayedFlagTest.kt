package mega.android.core.ui.extensions

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.time.Duration.Companion.milliseconds

@RunWith(AndroidJUnit4::class)
class DelayedFlagTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun test_that_delayedState_changesValueAfterDelay() = runTest {
        val delay = 100.milliseconds

        lateinit var state: androidx.compose.runtime.State<Boolean>

        composeTestRule.setContent {
            state = delayedState(
                delayDuration = delay,
                initialValue = false,
                finalValue = true,
            )
        }

        composeTestRule.runOnIdle {
            assertEquals(false, state.value)
        }
        composeTestRule.mainClock.advanceTimeBy(delay.inWholeMilliseconds + 1L)
        composeTestRule.runOnIdle {
            assertEquals(true, state.value)
        }
    }
}