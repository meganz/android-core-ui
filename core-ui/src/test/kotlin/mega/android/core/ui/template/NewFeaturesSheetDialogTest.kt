package mega.android.core.ui.template

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import mega.android.core.ui.R
import mega.android.core.ui.components.text.ContentTextDefaults
import mega.android.core.ui.model.FeatureListItem
import mega.android.core.ui.model.IllustrationIconSizeMode
import mega.android.core.ui.model.NewFeaturesAttributes
import mega.android.core.ui.theme.devicetype.DeviceType
import mega.android.core.ui.theme.devicetype.LocalDeviceType
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NewFeaturesSheetDialogTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `test that promotional dialog full image shown when device tablet`() {
        composeTestRule.setContent {
            CompositionLocalProvider(LocalDeviceType provides DeviceType.Tablet) {
                NewFeaturesSheetDialog(
                    attributes = NewFeaturesAttributes.FullImage(
                        title = "title",
                        headline = "headline",
                        description = ContentTextDefaults.description("description"),
                        footer = "footer",
                        showCloseButton = true,
                        featuresList = listOf(
                            FeatureListItem(
                                title = "feature_title",
                                subtitle = "feature_subtitle",
                                icon = R.drawable.ic_eye
                            )
                        ),
                        primaryButtonText = "primary_button",
                        secondaryButtonText = "secondary_button",
                        image = null
                    )
                )
            }
        }

        composeTestRule
            .onNodeWithTag(TestTags.NEW_FEATURES_SHEET_DIALOG_FULL_IMAGE_DIALOG)
            .assertIsDisplayed()
    }

    @Test
    fun `test that promotional sheet full image shown when device not tablet`() {
        composeTestRule.setContent {
            CompositionLocalProvider(LocalDeviceType provides DeviceType.Phone) {
                NewFeaturesSheetDialog(
                    attributes = NewFeaturesAttributes.FullImage(
                        title = "title",
                        headline = "headline",
                        description = ContentTextDefaults.description("description"),
                        footer = "footer",
                        showCloseButton = true,
                        featuresList = listOf(
                            FeatureListItem(
                                title = "feature_title",
                                subtitle = "feature_subtitle",
                                icon = R.drawable.ic_eye
                            )
                        ),
                        primaryButtonText = "primary_button",
                        secondaryButtonText = "secondary_button",
                        image = null
                    )
                )
            }
        }

        composeTestRule
            .onNodeWithTag(TestTags.NEW_FEATURES_SHEET_DIALOG_FULL_IMAGE_SHEET)
            .assertIsDisplayed()
    }

    @Test
    fun `test that promotional dialog image shown when device tablet`() {
        composeTestRule.setContent {
            CompositionLocalProvider(LocalDeviceType provides DeviceType.Tablet) {
                NewFeaturesSheetDialog(
                    attributes = NewFeaturesAttributes.Image(
                        title = "title",
                        headline = "headline",
                        description = ContentTextDefaults.description("description"),
                        footer = "footer",
                        showCloseButton = true,
                        featuresList = listOf(
                            FeatureListItem(
                                title = "feature_title",
                                subtitle = "feature_subtitle",
                                icon = R.drawable.ic_eye
                            )
                        ),
                        primaryButtonText = "primary_button",
                        secondaryButtonText = "secondary_button",
                        image = null
                    )
                )
            }
        }

        composeTestRule
            .onNodeWithTag(TestTags.NEW_FEATURES_SHEET_DIALOG_IMAGE_DIALOG)
            .assertIsDisplayed()
    }

    @Test
    fun `test that promotional sheet image shown when device phone`() {
        composeTestRule.setContent {
            CompositionLocalProvider(LocalDeviceType provides DeviceType.Phone) {
                NewFeaturesSheetDialog(
                    attributes = NewFeaturesAttributes.Image(
                        title = "title",
                        headline = "headline",
                        description = ContentTextDefaults.description("description"),
                        footer = "footer",
                        showCloseButton = true,
                        featuresList = listOf(
                            FeatureListItem(
                                title = "feature_title",
                                subtitle = "feature_subtitle",
                                icon = R.drawable.ic_eye
                            )
                        ),
                        primaryButtonText = "primary_button",
                        secondaryButtonText = "secondary_button",
                        image = null
                    )
                )
            }
        }

        composeTestRule
            .onNodeWithTag(TestTags.NEW_FEATURES_SHEET_DIALOG_IMAGE_SHEET)
            .assertIsDisplayed()
    }

    @Test
    fun `test that promotional dialog illustration shown when device tablet`() {
        composeTestRule.setContent {
            CompositionLocalProvider(LocalDeviceType provides DeviceType.Tablet) {
                NewFeaturesSheetDialog(
                    attributes = NewFeaturesAttributes.Illustration(
                        title = "title",
                        headline = "headline",
                        description = ContentTextDefaults.description("description"),
                        footer = "footer",
                        showCloseButton = true,
                        featuresList = listOf(
                            FeatureListItem(
                                title = "feature_title",
                                subtitle = "feature_subtitle",
                                icon = R.drawable.ic_eye
                            )
                        ),
                        primaryButtonText = "primary_button",
                        secondaryButtonText = "secondary_button",
                        illustration = R.drawable.ic_eye,
                        illustrationMode = IllustrationIconSizeMode.Small
                    )
                )
            }
        }

        composeTestRule
            .onNodeWithTag(TestTags.NEW_FEATURES_SHEET_DIALOG_ILLUSTRATION_DIALOG)
            .assertIsDisplayed()
    }

    @Test
    fun `test that promotional sheet illustration shown when device phone`() {
        composeTestRule.setContent {
            CompositionLocalProvider(LocalDeviceType provides DeviceType.Phone) {
                NewFeaturesSheetDialog(
                    attributes = NewFeaturesAttributes.Illustration(
                        title = "title",
                        headline = "headline",
                        description = ContentTextDefaults.description("description"),
                        footer = "footer",
                        showCloseButton = true,
                        featuresList = listOf(
                            FeatureListItem(
                                title = "feature_title",
                                subtitle = "feature_subtitle",
                                icon = R.drawable.ic_eye
                            )
                        ),
                        primaryButtonText = "primary_button",
                        secondaryButtonText = "secondary_button",
                        illustration = R.drawable.ic_eye,
                        illustrationMode = IllustrationIconSizeMode.Small
                    )
                )
            }
        }

        composeTestRule
            .onNodeWithTag(TestTags.NEW_FEATURES_SHEET_DIALOG_ILLUSTRATION_SHEET)
            .assertIsDisplayed()
    }

    @Test
    fun `test that promotional dialog plain shown when device tablet`() {
        composeTestRule.setContent {
            CompositionLocalProvider(LocalDeviceType provides DeviceType.Tablet) {
                NewFeaturesSheetDialog(
                    attributes = NewFeaturesAttributes.Plain(
                        title = "title",
                        headline = "headline",
                        description = ContentTextDefaults.description("description"),
                        footer = "footer",
                        showCloseButton = true,
                        featuresList = listOf(
                            FeatureListItem(
                                title = "feature_title",
                                subtitle = "feature_subtitle",
                                icon = R.drawable.ic_eye
                            )
                        ),
                        primaryButtonText = "primary_button",
                        secondaryButtonText = "secondary_button"
                    )
                )
            }
        }

        composeTestRule
            .onNodeWithTag(TestTags.NEW_FEATURES_SHEET_DIALOG_PLAIN_DIALOG)
            .assertIsDisplayed()
    }

    @Test
    fun `test that promotional sheet plain shown when device phone`() {
        composeTestRule.setContent {
            CompositionLocalProvider(LocalDeviceType provides DeviceType.Phone) {
                NewFeaturesSheetDialog(
                    attributes = NewFeaturesAttributes.Plain(
                        title = "title",
                        headline = "headline",
                        description = ContentTextDefaults.description("description"),
                        footer = "footer",
                        showCloseButton = true,
                        featuresList = listOf(
                            FeatureListItem(
                                title = "feature_title",
                                subtitle = "feature_subtitle",
                                icon = R.drawable.ic_eye
                            )
                        ),
                        primaryButtonText = "primary_button",
                        secondaryButtonText = "secondary_button"
                    )
                )
            }
        }

        composeTestRule
            .onNodeWithTag(TestTags.NEW_FEATURES_SHEET_DIALOG_PLAIN_SHEET)
            .assertIsDisplayed()
    }
}