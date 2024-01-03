//
// Generated automatically by KotlinTokensGenerator.
// Do not modify this file manually.
//
package mega.android.core.ui.shared.theme.tokens

import mega.android.core.ui.theme.tokens.Background
import mega.android.core.ui.theme.tokens.Border
import mega.android.core.ui.theme.tokens.Button
import mega.android.core.ui.theme.tokens.Components
import mega.android.core.ui.theme.tokens.Focus
import mega.android.core.ui.theme.tokens.Icon
import mega.android.core.ui.theme.tokens.Indicator
import mega.android.core.ui.theme.tokens.Link
import mega.android.core.ui.theme.tokens.Notifications
import mega.android.core.ui.theme.tokens.SemanticTokens
import mega.android.core.ui.theme.tokens.Support
import mega.android.core.ui.theme.tokens.Text

internal object CoreUISemanticTokensDark : SemanticTokens {
    override val background: Background = Background(
            pageBackground = Colors.Neutral.n900,
            surface1 = Colors.Neutral.n800,
            surface2 = Colors.Neutral.n700,
            surface3 = Colors.Neutral.n600,
            inverse = Colors.Neutral.n050,
            blur = Colors.BlackOpacity.n020,
            )

    override val button: Button = Button(
            primary = Colors.Accent.n050,
            primaryHover = Colors.Accent.n300,
            primaryPressed = Colors.Accent.n200,
            brand = Colors.Primary.n500,
            brandHover = Colors.Primary.n400,
            brandPressed = Colors.Primary.n300,
            secondary = Colors.Neutral.n700,
            secondaryHover = Colors.Neutral.n600,
            secondaryPressed = Colors.Neutral.n500,
            outline = Colors.Accent.n050,
            outlineHover = Colors.Accent.n300,
            outlineBackgroundHover = Colors.WhiteOpacity.n005,
            outlinePressed = Colors.Accent.n200,
            error = Colors.Error.n500,
            errorHover = Colors.Error.n400,
            errorPressed = Colors.Error.n300,
            disabled = Colors.WhiteOpacity.n010,
            )

    override val border: Border = Border(
            interactive = Colors.Primary.n400,
            strong = Colors.Neutral.n600,
            strongSelected = Colors.Accent.n050,
            subtle = Colors.Neutral.n800,
            subtleSelected = Colors.Accent.n050,
            disabled = Colors.Neutral.n700,
            )

    override val text: Text = Text(
            primary = Colors.Neutral.n050,
            secondary = Colors.Neutral.n300,
            accent = Colors.Accent.n025,
            placeholder = Colors.Neutral.n200,
            inverseAccent = Colors.Accent.n900,
            onColor = Colors.Neutral.n025,
            onColorDisabled = Colors.Neutral.n400,
            error = Colors.Error.n400,
            success = Colors.Success.n500,
            info = Colors.Secondary.Blue.n500,
            warning = Colors.Warning.n500,
            inverse = Colors.Neutral.n800,
            disabled = Colors.Neutral.n500,
            )

    override val icon: Icon = Icon(
            primary = Colors.Neutral.n050,
            secondary = Colors.Neutral.n300,
            accent = Colors.Accent.n025,
            inverseAccent = Colors.Accent.n900,
            onColor = Colors.Neutral.n025,
            onColorDisabled = Colors.Neutral.n400,
            inverse = Colors.Neutral.n800,
            disabled = Colors.Neutral.n500,
            )

    override val support: Support = Support(
            success = Colors.Success.n500,
            warning = Colors.Warning.n500,
            error = Colors.Error.n400,
            info = Colors.Secondary.Blue.n600,
            )

    override val components: Components = Components(
            selectionControl = Colors.Accent.n050,
            interactive = Colors.Primary.n400,
            toastBackground = Colors.Neutral.n200,
            )

    override val notifications: Notifications = Notifications(
            notificationSuccess = Colors.Success.n900,
            notificationWarning = Colors.Warning.n800,
            notificationError = Colors.Error.n900,
            notificationInfo = Colors.Secondary.Blue.n900,
            )

    override val indicator: Indicator = Indicator(
            pink = Colors.Error.n400,
            yellow = Colors.Warning.n400,
            green = Colors.Success.n400,
            blue = Colors.Secondary.Blue.n400,
            indigo = Colors.Secondary.Indigo.n400,
            magenta = Colors.Secondary.Magenta.n300,
            orange = Colors.Secondary.Orange.n300,
            )

    override val link: Link = Link(
            primary = Colors.Secondary.Indigo.n400,
            inverse = Colors.Secondary.Indigo.n600,
            visited = Colors.Secondary.Indigo.n100,
            )

    override val focus: Focus = Focus(
            colorFocus = Colors.Secondary.Indigo.n700,
            )
}
