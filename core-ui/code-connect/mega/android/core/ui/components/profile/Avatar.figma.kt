/*
 * Code Connect mapping for the Figma `Avatar` component set.
 *
 * Figma file : HagXfSTF94ICEFj8rfNyLc  (ANDROID - New)
 * Component  : Avatar  (node 54486:43687)
 * Variants matched:
 *   Size = 16 | 24 | 32 | 40 | 58
 *   (Type axis NOT matched — Compose's nullable `imageFile` covers both Image + Initials.)
 *
 * Size mapping:
 *   Size=58       → LargeProfilePicture
 *   Size=40 or 32 → MediumProfilePicture
 *   Size=24 or 16 → SmallProfilePicture
 *
 * Figma properties IGNORED:
 *   Show availability / Show verified icon — overlays not currently in Compose API.
 *
 * Compose side:
 *   - LargeProfilePicture(imageFile, name, contentDescription, avatarColor)
 *   - MediumProfilePicture(imageFile, name, contentDescription, avatarColor[, avatarSecondaryColor])
 *   - SmallProfilePicture(imageFile, name, contentDescription, avatarColor)
 *
 * NOTE — this file requires the `com.figma.code.connect` Gradle plugin.
 *
 * Property names VERIFIED via MCP on 2026-04-23.
 */

package mega.android.core.ui.components.profile

import androidx.compose.runtime.Composable
import com.figma.code.connect.FigmaConnect
import com.figma.code.connect.FigmaVariant

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=54486-43687")
@FigmaVariant("Size", "58")
class LargeProfilePictureConnection {
    @Composable
    fun LargeProfilePictureExample() {
        LargeProfilePicture(
            imageFile = null,
            name = "U",
            contentDescription = null,
        )
    }
}

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=54486-43687")
@FigmaVariant("Size", "40")
class MediumProfilePicture40Connection {
    @Composable
    fun MediumProfilePicture40Example() {
        MediumProfilePicture(
            imageFile = null,
            name = "U",
            contentDescription = null,
        )
    }
}

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=54486-43687")
@FigmaVariant("Size", "32")
class MediumProfilePicture32Connection {
    @Composable
    fun MediumProfilePicture32Example() {
        MediumProfilePicture(
            imageFile = null,
            name = "U",
            contentDescription = null,
        )
    }
}

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=54486-43687")
@FigmaVariant("Size", "24")
class SmallProfilePicture24Connection {
    @Composable
    fun SmallProfilePicture24Example() {
        SmallProfilePicture(
            imageFile = null,
            name = "U",
            contentDescription = null,
        )
    }
}

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=54486-43687")
@FigmaVariant("Size", "16")
class SmallProfilePicture16Connection {
    @Composable
    fun SmallProfilePicture16Example() {
        SmallProfilePicture(
            imageFile = null,
            name = "U",
            contentDescription = null,
        )
    }
}
