package mega.android.core.ui.model

import androidx.annotation.DrawableRes
import mega.android.core.ui.components.text.ContentText

sealed class NewFeaturesAttributes {
    abstract val title: String
    abstract val headline: String
    abstract val description: ContentText?
    abstract val footer: ContentText?
    abstract val showCloseButton: Boolean
    abstract val featuresList: List<FeatureListItem>?
    abstract val primaryButtonText: String?
    abstract val secondaryButtonText: String?

    data class FullImage(
        override val title: String,
        override val headline: String,
        override val description: ContentText? = null,
        override val footer: ContentText? = null,
        override val showCloseButton: Boolean = true,
        override val featuresList: List<FeatureListItem>? = null,
        override val primaryButtonText: String? = null,
        override val secondaryButtonText: String? = null,
        val image: Any?,
    ) : NewFeaturesAttributes()

    data class Image(
        override val title: String,
        override val headline: String,
        override val description: ContentText? = null,
        override val footer: ContentText? = null,
        override val showCloseButton: Boolean = true,
        override val featuresList: List<FeatureListItem>? = null,
        override val primaryButtonText: String? = null,
        override val secondaryButtonText: String? = null,
        val image: Any?,
    ) : NewFeaturesAttributes()

    data class Illustration(
        override val title: String,
        override val headline: String,
        override val description: ContentText? = null,
        override val footer: ContentText? = null,
        override val showCloseButton: Boolean = true,
        override val featuresList: List<FeatureListItem>? = null,
        override val primaryButtonText: String? = null,
        override val secondaryButtonText: String? = null,
        val illustration: Int,
        val illustrationMode: IllustrationIconSizeMode = IllustrationIconSizeMode.Small
    ) : NewFeaturesAttributes()

    data class Plain(
        override val title: String,
        override val headline: String,
        override val description: ContentText? = null,
        override val footer: ContentText? = null,
        override val showCloseButton: Boolean = true,
        override val featuresList: List<FeatureListItem>? = null,
        override val primaryButtonText: String? = null,
        override val secondaryButtonText: String? = null
    ) : NewFeaturesAttributes()
}

data class FeatureListItem(
    val title: String,
    val subtitle: String,
    @DrawableRes val icon: Int
)