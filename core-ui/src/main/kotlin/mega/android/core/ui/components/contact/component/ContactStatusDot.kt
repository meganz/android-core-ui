package mega.android.core.ui.components.contact.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.tokens.theme.DSTokens
import mega.privacy.android.feature.contact.components.ContactItemStatus

/**
 * Contact status dot
 *
 * @param status the status of the contact to display
 */
@Composable
fun ContactStatusDot(status: ContactItemStatus) {
    val color = when (status) {
        ContactItemStatus.Online -> DSTokens.colors.indicator.green
        ContactItemStatus.Away -> DSTokens.colors.indicator.yellow
        ContactItemStatus.Busy -> DSTokens.colors.indicator.pink
        ContactItemStatus.Offline -> DSTokens.colors.icon.secondary
        ContactItemStatus.Unknown -> return
    }
    Box(
        modifier = Modifier
            .size(10.dp)
            .border(2.dp, DSTokens.colors.background.pageBackground, CircleShape)
            .background(color, CircleShape)
            .testTag(CONTACT_ITEM_VIEW_STATUS_DOT),
    )
}

private class ContactItemStatusProvider : PreviewParameterProvider<ContactItemStatus> {
    override val values = ContactItemStatus.entries.asSequence()
}

/**
 * Preview for [ContactStatusDot]
 *
 * @param status the status to preview
 */
@CombinedThemePreviews
@Composable
private fun ContactStatusDotPreview(
    @PreviewParameter(ContactItemStatusProvider::class) status: ContactItemStatus
) {
    AndroidThemeForPreviews {
        ContactStatusDot(status = status)
    }
}

internal const val CONTACT_ITEM_VIEW_STATUS_DOT = "contact_item_view:status_dot"
