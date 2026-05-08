/*
 * Code Connect mapping for the Figma `Tooltips` component set.
 *
 * Figma file : HagXfSTF94ICEFj8rfNyLc  (ANDROID - New)
 * Component  : Tooltips  (node 61134:613)
 * Variants matched:
 *   Type  = Simple | Interactive
 *   Arrow = Top-Left | Top-Centre | Top-Right | Bottom-Left | Bottom-Centre | Bottom-Right
 *           | Left | Right | None
 *
 * Arrow mapping:
 *   Top-Left / Top-Centre / Top-Right       → *TopDirectionTooltipPopup
 *   Bottom-Left / Bottom-Centre / Bottom-Right → *BottomDirectionTooltipPopup
 *   Left  → *LeftDirectionTooltipPopup
 *   Right → *RightDirectionTooltipPopup
 *   None  → *NoneTooltipPopup
 *
 * Each Top-* / Bottom-* gets a separate @FigmaConnect so the parser can emit a distinct
 * variant mapping per Figma arrow value (multiple classes delegating to the same
 * Compose function).
 *
 * Figma boolean properties are IGNORED for now: Step counter / Show X / Buttons /
 * Primary button / Secondary button / Title / Body. Tooltip popups require
 * LayoutCoordinates at call site so the example body is a TODO stub.
 *
 * Compose side:
 *   - Simple{Left,Top,Right,Bottom}DirectionTooltipPopup + SimpleNoneTooltipPopup
 *   - Interactive{Left,Top,Right,Bottom}DirectionTooltipPopup + InteractiveNoneTooltipPopup
 *
 * NOTE — this file requires the `com.figma.code.connect` Gradle plugin.
 *
 * Property names VERIFIED via MCP on 2026-04-23.
 */

package mega.android.core.ui.components.tooltip

import androidx.compose.runtime.Composable
import com.figma.code.connect.FigmaConnect
import com.figma.code.connect.FigmaVariant

// ─── Simple tooltips ───────────────────────────────────────────────────────

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=61134-613")
@FigmaVariant("Type", "Simple")
@FigmaVariant("Arrow", "Top-Left")
class SimpleTopLeftTooltipConnection {
    @Composable
    fun SimpleTopLeftTooltipExample() {
        // TODO: populate anchor LayoutCoordinates at call site
        // SimpleTopDirectionTooltipPopup(...)
    }
}

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=61134-613")
@FigmaVariant("Type", "Simple")
@FigmaVariant("Arrow", "Top-Centre")
class SimpleTopCentreTooltipConnection {
    @Composable
    fun SimpleTopCentreTooltipExample() {
        // TODO: populate anchor LayoutCoordinates at call site
        // SimpleTopDirectionTooltipPopup(...)
    }
}

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=61134-613")
@FigmaVariant("Type", "Simple")
@FigmaVariant("Arrow", "Top-Right")
class SimpleTopRightTooltipConnection {
    @Composable
    fun SimpleTopRightTooltipExample() {
        // TODO: populate anchor LayoutCoordinates at call site
        // SimpleTopDirectionTooltipPopup(...)
    }
}

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=61134-613")
@FigmaVariant("Type", "Simple")
@FigmaVariant("Arrow", "Bottom-Left")
class SimpleBottomLeftTooltipConnection {
    @Composable
    fun SimpleBottomLeftTooltipExample() {
        // TODO: populate anchor LayoutCoordinates at call site
        // SimpleBottomDirectionTooltipPopup(...)
    }
}

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=61134-613")
@FigmaVariant("Type", "Simple")
@FigmaVariant("Arrow", "Bottom-Centre")
class SimpleBottomCentreTooltipConnection {
    @Composable
    fun SimpleBottomCentreTooltipExample() {
        // TODO: populate anchor LayoutCoordinates at call site
        // SimpleBottomDirectionTooltipPopup(...)
    }
}

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=61134-613")
@FigmaVariant("Type", "Simple")
@FigmaVariant("Arrow", "Bottom-Right")
class SimpleBottomRightTooltipConnection {
    @Composable
    fun SimpleBottomRightTooltipExample() {
        // TODO: populate anchor LayoutCoordinates at call site
        // SimpleBottomDirectionTooltipPopup(...)
    }
}

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=61134-613")
@FigmaVariant("Type", "Simple")
@FigmaVariant("Arrow", "Left")
class SimpleLeftTooltipConnection {
    @Composable
    fun SimpleLeftTooltipExample() {
        // TODO: populate anchor LayoutCoordinates at call site
        // SimpleLeftDirectionTooltipPopup(...)
    }
}

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=61134-613")
@FigmaVariant("Type", "Simple")
@FigmaVariant("Arrow", "Right")
class SimpleRightTooltipConnection {
    @Composable
    fun SimpleRightTooltipExample() {
        // TODO: populate anchor LayoutCoordinates at call site
        // SimpleRightDirectionTooltipPopup(...)
    }
}

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=61134-613")
@FigmaVariant("Type", "Simple")
@FigmaVariant("Arrow", "None")
class SimpleNoneTooltipConnection {
    @Composable
    fun SimpleNoneTooltipExample() {
        // TODO: populate anchor LayoutCoordinates at call site
        // SimpleNoneTooltipPopup(...)
    }
}

// ─── Interactive tooltips ──────────────────────────────────────────────────

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=61134-613")
@FigmaVariant("Type", "Interactive")
@FigmaVariant("Arrow", "Top-Left")
class InteractiveTopLeftTooltipConnection {
    @Composable
    fun InteractiveTopLeftTooltipExample() {
        // TODO: populate anchor LayoutCoordinates at call site
        // InteractiveTopDirectionTooltipPopup(...)
    }
}

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=61134-613")
@FigmaVariant("Type", "Interactive")
@FigmaVariant("Arrow", "Top-Centre")
class InteractiveTopCentreTooltipConnection {
    @Composable
    fun InteractiveTopCentreTooltipExample() {
        // TODO: populate anchor LayoutCoordinates at call site
        // InteractiveTopDirectionTooltipPopup(...)
    }
}

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=61134-613")
@FigmaVariant("Type", "Interactive")
@FigmaVariant("Arrow", "Top-Right")
class InteractiveTopRightTooltipConnection {
    @Composable
    fun InteractiveTopRightTooltipExample() {
        // TODO: populate anchor LayoutCoordinates at call site
        // InteractiveTopDirectionTooltipPopup(...)
    }
}

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=61134-613")
@FigmaVariant("Type", "Interactive")
@FigmaVariant("Arrow", "Bottom-Left")
class InteractiveBottomLeftTooltipConnection {
    @Composable
    fun InteractiveBottomLeftTooltipExample() {
        // TODO: populate anchor LayoutCoordinates at call site
        // InteractiveBottomDirectionTooltipPopup(...)
    }
}

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=61134-613")
@FigmaVariant("Type", "Interactive")
@FigmaVariant("Arrow", "Bottom-Centre")
class InteractiveBottomCentreTooltipConnection {
    @Composable
    fun InteractiveBottomCentreTooltipExample() {
        // TODO: populate anchor LayoutCoordinates at call site
        // InteractiveBottomDirectionTooltipPopup(...)
    }
}

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=61134-613")
@FigmaVariant("Type", "Interactive")
@FigmaVariant("Arrow", "Bottom-Right")
class InteractiveBottomRightTooltipConnection {
    @Composable
    fun InteractiveBottomRightTooltipExample() {
        // TODO: populate anchor LayoutCoordinates at call site
        // InteractiveBottomDirectionTooltipPopup(...)
    }
}

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=61134-613")
@FigmaVariant("Type", "Interactive")
@FigmaVariant("Arrow", "Left")
class InteractiveLeftTooltipConnection {
    @Composable
    fun InteractiveLeftTooltipExample() {
        // TODO: populate anchor LayoutCoordinates at call site
        // InteractiveLeftDirectionTooltipPopup(...)
    }
}

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=61134-613")
@FigmaVariant("Type", "Interactive")
@FigmaVariant("Arrow", "Right")
class InteractiveRightTooltipConnection {
    @Composable
    fun InteractiveRightTooltipExample() {
        // TODO: populate anchor LayoutCoordinates at call site
        // InteractiveRightDirectionTooltipPopup(...)
    }
}

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=61134-613")
@FigmaVariant("Type", "Interactive")
@FigmaVariant("Arrow", "None")
class InteractiveNoneTooltipConnection {
    @Composable
    fun InteractiveNoneTooltipExample() {
        // TODO: populate anchor LayoutCoordinates at call site
        // InteractiveNoneTooltipPopup(...)
    }
}
