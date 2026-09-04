package mega.android.core.ui.modifiers

import androidx.compose.animation.core.tween
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ScrollToHideBehaviorTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private fun collapsedBehavior() = ScrollToHideBehavior().apply {
        heightOffsetLimit = -100f
        nestedScrollConnection.onPreScroll(Offset(0f, -100f), NestedScrollSource.UserInput)
    }

    @Test
    fun `test that pre scroll collapse consumes the delta applied to the height offset`() {
        val behavior = ScrollToHideBehavior().apply { heightOffsetLimit = -100f }

        val consumed = behavior.nestedScrollConnection
            .onPreScroll(Offset(0f, -60f), NestedScrollSource.UserInput)

        assertEquals(Offset(0f, -60f), consumed)
        assertEquals(-60f, behavior.heightOffset)
    }

    @Test
    fun `test that expand animates the height offset back to zero`() {
        val behavior = collapsedBehavior()
        assertEquals(-100f, behavior.heightOffset)

        composeTestRule.setContent {
            LaunchedEffect(behavior) {
                behavior.expand()
            }
        }
        composeTestRule.waitForIdle()

        assertEquals(0f, behavior.heightOffset)
        assertEquals(0f, behavior.scrollDirectionOffset)
    }

    @Test
    fun `test that expand returns immediately when already expanded`() {
        val behavior = ScrollToHideBehavior().apply { heightOffsetLimit = -100f }

        composeTestRule.setContent {
            LaunchedEffect(behavior) {
                behavior.expand()
            }
        }
        composeTestRule.waitForIdle()

        assertEquals(0f, behavior.heightOffset)
    }

    @Test
    fun `test that post fling snaps a mostly hidden component to fully hidden when snapping is enabled`() {
        val behavior = ScrollToHideBehavior().apply {
            heightOffsetLimit = -100f
            snapAnimationSpec = tween(50)
            nestedScrollConnection.onPreScroll(Offset(0f, -70f), NestedScrollSource.UserInput)
        }

        composeTestRule.setContent {
            LaunchedEffect(behavior) {
                behavior.nestedScrollConnection.onPostFling(Velocity.Zero, Velocity.Zero)
            }
        }
        composeTestRule.waitForIdle()

        assertEquals(-100f, behavior.heightOffset)
    }

    @Test
    fun `test that post fling snaps a mostly shown component back to fully shown when snapping is enabled`() {
        val behavior = ScrollToHideBehavior().apply {
            heightOffsetLimit = -100f
            snapAnimationSpec = tween(50)
            nestedScrollConnection.onPreScroll(Offset(0f, -30f), NestedScrollSource.UserInput)
        }

        composeTestRule.setContent {
            LaunchedEffect(behavior) {
                behavior.nestedScrollConnection.onPostFling(Velocity.Zero, Velocity.Zero)
            }
        }
        composeTestRule.waitForIdle()

        assertEquals(0f, behavior.heightOffset)
    }

    @Test
    fun `test that post fling leaves a partial offset unchanged when snapping is disabled`() {
        val behavior = ScrollToHideBehavior().apply {
            heightOffsetLimit = -100f
            nestedScrollConnection.onPreScroll(Offset(0f, -70f), NestedScrollSource.UserInput)
        }

        composeTestRule.setContent {
            LaunchedEffect(behavior) {
                behavior.nestedScrollConnection.onPostFling(Velocity.Zero, Velocity.Zero)
            }
        }
        composeTestRule.waitForIdle()

        assertEquals(-70f, behavior.heightOffset)
    }
}
