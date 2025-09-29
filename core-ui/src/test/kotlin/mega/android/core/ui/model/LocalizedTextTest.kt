package mega.android.core.ui.model

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LocalizedTextTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `test composite string`() {
        val expected = "Hello World"
        val string = LocalizedText.Composite(
            parts = listOf(
                LocalizedText.Literal("Hello "),
                LocalizedText.Literal("World"),
            ),
        )
        var actual = ""
        composeTestRule.setContent {
            actual = string.text
        }

        assertEquals(expected, actual)
    }

    @Test
    fun `test concatenating two literals string`() {
        val expected = "Hello World"
        val string = LocalizedText.Literal("Hello ") + LocalizedText.Literal("World")
        var actual = ""
        composeTestRule.setContent {
            actual = string.text
        }

        assertEquals(expected, actual)
    }

    @Test
    fun `test concatenating composite and literal string`() {
        val expected = "Hello World"
        val string = LocalizedText.Literal("Hello ") + LocalizedText.Composite(
            listOf(
                LocalizedText.Literal("Wo"),
                LocalizedText.Literal("rld")
            )
        )
        var actual = ""
        composeTestRule.setContent {
            actual = string.text
        }

        assertEquals(expected, actual)
    }
}