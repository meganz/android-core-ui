package mega.android.core.ui.modifiers

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import mega.android.core.ui.components.LocalTopAppBarScrollBehavior
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalMaterial3Api::class)
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

    @Test
    fun `test that content sharing a behavior adds its heights up into the height offset limit`() {
        val behavior = ScrollToHideBehavior()

        composeTestRule.setContent {
            Column {
                CollapsibleBox(behavior, heightPx = 40, tag = "first")
                CollapsibleBox(behavior, heightPx = 60, tag = "second")
            }
        }
        composeTestRule.waitForIdle()

        assertEquals(-100f, behavior.heightOffsetLimit)
    }

    @Test
    fun `test that content sharing a behavior collapses from the top as one block`() {
        val behavior = ScrollToHideBehavior()
        lateinit var density: Density

        composeTestRule.setContent {
            density = LocalDensity.current
            Column {
                CollapsibleBox(behavior, heightPx = 40, tag = "first")
                CollapsibleBox(behavior, heightPx = 60, tag = "second")
            }
        }
        composeTestRule.runOnIdle {
            behavior.nestedScrollConnection.onPreScroll(Offset(0f, -30f), NestedScrollSource.UserInput)
        }
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("first").assertHeightIsEqualTo(with(density) { 10.toDp() })
        composeTestRule.onNodeWithTag("second").assertHeightIsEqualTo(with(density) { 60.toDp() })

        composeTestRule.runOnIdle {
            behavior.nestedScrollConnection.onPreScroll(Offset(0f, -20f), NestedScrollSource.UserInput)
        }
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("first").assertHeightIsEqualTo(with(density) { 0.toDp() })
        composeTestRule.onNodeWithTag("second").assertHeightIsEqualTo(with(density) { 50.toDp() })
    }

    @Test
    fun `test that content not contributing height moves with the block without extending the limit`() {
        val behavior = ScrollToHideBehavior()
        lateinit var density: Density

        composeTestRule.setContent {
            density = LocalDensity.current
            Column {
                CollapsibleBox(behavior, heightPx = 40, tag = "tabs")
                Row {
                    CollapsibleBox(behavior, heightPx = 60, tag = "active", width = 100)
                    CollapsibleBox(
                        behavior,
                        heightPx = 60,
                        tag = "follower",
                        width = 100,
                        contributesHeight = false,
                    )
                }
            }
        }
        composeTestRule.runOnIdle {
            behavior.nestedScrollConnection.onPreScroll(Offset(0f, -70f), NestedScrollSource.UserInput)
        }
        composeTestRule.waitForIdle()

        assertEquals(-100f, behavior.heightOffsetLimit)
        composeTestRule.onNodeWithTag("active").assertHeightIsEqualTo(with(density) { 30.toDp() })
        composeTestRule.onNodeWithTag("follower").assertHeightIsEqualTo(with(density) { 30.toDp() })
    }

    @Test
    fun `test that removing content keeps the height offset when the limit shrinks`() {
        val behavior = ScrollToHideBehavior()
        var showSecond by mutableStateOf(true)

        composeTestRule.setContent {
            Column {
                CollapsibleBox(behavior, heightPx = 40, tag = "first")
                if (showSecond) CollapsibleBox(behavior, heightPx = 60, tag = "second")
            }
        }
        composeTestRule.runOnIdle {
            behavior.nestedScrollConnection.onPreScroll(Offset(0f, -50f), NestedScrollSource.UserInput)
        }
        composeTestRule.runOnIdle { showSecond = false }
        composeTestRule.waitForIdle()

        assertEquals(-40f, behavior.heightOffsetLimit)
        assertEquals(-50f, behavior.heightOffset)
        assertEquals(-40f, behavior.offset)
    }

    @Test
    fun `test that pre scroll after the limit shrank consumes only the visible distance`() {
        val behavior = ScrollToHideBehavior()
        var showSecond by mutableStateOf(true)

        composeTestRule.setContent {
            Column {
                CollapsibleBox(behavior, heightPx = 40, tag = "first")
                if (showSecond) CollapsibleBox(behavior, heightPx = 60, tag = "second")
            }
        }
        composeTestRule.runOnIdle {
            behavior.nestedScrollConnection.onPreScroll(Offset(0f, -100f), NestedScrollSource.UserInput)
        }
        composeTestRule.runOnIdle { showSecond = false }
        composeTestRule.waitForIdle()

        val consumed = composeTestRule.runOnIdle {
            behavior.nestedScrollConnection.onPreScroll(Offset(0f, 10f), NestedScrollSource.UserInput)
        }

        assertEquals(Offset(0f, 10f), consumed)
        assertEquals(-30f, behavior.heightOffset)
    }

    @Test
    fun `test that detaching one of several contents keeps the top app bar hooks`() {
        val behavior = ScrollToHideBehavior()
        var showSecond by mutableStateOf(true)

        composeTestRule.setContent {
            CompositionLocalProvider(
                LocalTopAppBarScrollBehavior provides TopAppBarDefaults.pinnedScrollBehavior()
            ) {
                Column {
                    CollapsibleBox(behavior, heightPx = 40, tag = "first")
                    if (showSecond) CollapsibleBox(behavior, heightPx = 60, tag = "second")
                }
            }
        }
        composeTestRule.runOnIdle { showSecond = false }
        composeTestRule.waitForIdle()

        assertNotNull(behavior.shouldConsumePreScroll)
    }

    @Composable
    private fun CollapsibleBox(
        behavior: ScrollToHideBehavior,
        heightPx: Int,
        tag: String,
        width: Int? = null,
        contributesHeight: Boolean = true,
    ) {
        val density = LocalDensity.current
        Box(
            modifier = Modifier
                .testTag(tag)
                .applyScrollToHideBehavior(behavior, contributesHeight)
                .then(
                    if (width == null) Modifier.fillMaxWidth()
                    else Modifier.requiredWidth(with(density) { width.toDp() })
                )
                .requiredHeight(with(density) { heightPx.toDp() })
        )
    }
}
