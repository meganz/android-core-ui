---
name: figma-code-connect-compose
description: Use when a `.figma.kt` file under `core-ui/code-connect/` needs to be added, edited, or republished — e.g. a Figma component variant/property/value was renamed, a Compose function signature changed, a new Compose component now has a Figma counterpart, or `figma connect publish --dry-run` is failing. Covers the Compose parser flow specifically (annotation-based `.figma.kt` files compiled by the `com.figma.code.connect` Gradle plugin), not the React/TS template flow.
---

# Updating Code Connect mappings (Compose / core-ui)

## Overview

`.figma.kt` files under `core-ui/code-connect/` declare the mapping between Figma component variants and Compose functions. They are **annotation-based** (`@FigmaConnect`, `@FigmaVariant`, `@FigmaProperty`) — not template files. The Figma CLI compiles them on demand via a Gradle task; they never ship in the AAR.

**Project context** (mapping table per component, audit status, list of intentionally-unmapped Compose-only / Missing-in-Figma components): `core-ui/code-connect/README.md`.

**Hard constants for this repo:**
- Figma fileKey: `HagXfSTF94ICEFj8rfNyLc` (file `ANDROID - New`)
- Source root: `core-ui/code-connect/mega/android/core/ui/components/<category>/`
- Reference example: `core-ui/code-connect/mega/android/core/ui/components/banner/Banners.figma.kt`
- CLI config: `figma.config.json` (parser is `compose`)
- Token env var: `FIGMA_ACCESS_TOKEN`

## Iron rule: MCP is the source of truth

Never write or edit a `.figma.kt` file from a written description, a screenshot, or memory of how a property used to be named. Always re-pull authoritative data first:

```
get_context_for_code_connect(
    fileKey = "HagXfSTF94ICEFj8rfNyLc",
    nodeId  = "<changed component nodeId>"
)
```

(Tool name in this environment: `mcp__plugin_figma_figma__get_context_for_code_connect`.)

NodeIds: copy from the Figma URL (`?node-id=NNNNN-MMMMM` — convert `-` to `:` when calling MCP). The component → file mapping table at the top of `core-ui/code-connect/README.md` lists every existing mapping with its Figma URL.

## Workflow A — reacting to a Figma-side change

1. **Identify the changed component(s)** and pull the nodeId(s).
2. **Run MCP** `get_context_for_code_connect` for each nodeId. Capture the variants list and the property names verbatim.
3. **Find the affected `.figma.kt`** under `core-ui/code-connect/...`. Use `figma.config.json`'s `include` glob if unsure.
4. **Apply the change** using the classification table below — edit only what changed. Property names must match byte-for-byte (emojis, colons, trailing spaces, typos).
5. **Validate**: `figma connect publish --dry-run` — must end with `All Code Connect files are valid`.
6. **Publish**: `figma connect publish`.
7. **Notify the designer**: "Updated Code Connect bindings for `<component>`. Dev Mode should now show new properties — please spot-check variants `<list>`."

## Workflow B — reacting to a Compose-side change

1. **Compile fails or grep finds the rename** in a `.figma.kt`.
2. **Update the `.figma.kt`** — function name, parameter names, default values.
3. `figma connect publish --dry-run` → `figma connect publish`.
4. No designer notification needed; this side is invisible to design.

## Workflow C — adding a new mapping for an existing Compose component

1. **Confirm Figma has a counterpart.** If not, add it to the "No-Figma / Compose-only (intentionally not mapped)" section of `core-ui/code-connect/README.md` and stop — do not invent placeholder mappings.
2. Get the nodeId from Figma; run `get_context_for_code_connect`.
3. Create `core-ui/code-connect/mega/android/core/ui/components/<category>/<Name>.figma.kt`, modeled on `banner/Banners.figma.kt`.
4. If the file appears in `figma.config.json`'s `exclude` array, remove it.
5. Validate, publish, commit.

## Change classification — what to edit

| Design-side change | Where to edit | Notes |
|---|---|---|
| Component set renamed (same nodeId) | top-of-file comment only | URL stays |
| Component moved / duplicated (nodeId changed) | `@FigmaConnect(url = "…")` literal | refresh the URL in `core-ui/code-connect/README.md` table |
| Variant axis renamed (`Type` → `Placement`) | first arg of every `@FigmaVariant` in the file | |
| Variant value renamed (`Top alert` → `Top`) | second arg of `@FigmaVariant` | byte-for-byte |
| Property renamed (`✏️ Title` → `Title text`) | `@FigmaProperty(type, "name")` literal | keep emoji/case/colons exact |
| Property added | new `@FigmaProperty` + wire into `…Example()` | only if it maps meaningfully to a Compose param; otherwise skip |
| Property removed | delete `@FigmaProperty` + its use in `…Example()` | |
| Variant value added | new Connection class if Compose has a matching function; otherwise log gap in `core-ui/code-connect/README.md` | |
| Variant axis added | revisit every Connection in the file — match it, or skip if it's a runtime/theme axis (`State`, `Dark mode`, `Focus`) | |
| Component set split | split file → two files, each with its own nodeId | |
| Component set merged | merge → one file, surviving nodeId | |
| Component set deleted | delete the `.figma.kt`; remove the row from `core-ui/code-connect/README.md` and note it in the "No-Figma / Compose-only" list if the Compose function still exists | |

| Compose-side change | Where to edit |
|---|---|
| Function renamed | the `…Example()` body's call site |
| Parameter renamed / reordered | named arguments in `…Example()` |
| Parameter type changed | `val` declaration + the `…Example()` use site |
| Compose function deleted | delete the matching Connection class; if the Figma component still exists, log it under "No-Figma / Compose-only" in `core-ui/code-connect/README.md` (or note as Missing-in-Compose) |

## File skeleton

Model after `banner/Banners.figma.kt`. One Connection class per variant combination Compose splits on:

```kotlin
@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=54390-26198")
@FigmaVariant("Placement", "Inline")
@FigmaVariant("Severity", "Error")
class InlineErrorBannerConnection {
    @FigmaProperty(FigmaType.Boolean, "Has title")   val showTitle: Boolean = true
    @FigmaProperty(FigmaType.Text, "Title text")     val titleText: String = "Title"
    @FigmaProperty(FigmaType.Text, "Body text")      val bodyText: String = "Body"
    @FigmaProperty(FigmaType.Boolean, "Dismissible") val showCancel: Boolean = true

    @Composable
    fun InlineErrorBannerExample() {
        InlineErrorBanner(
            title = if (showTitle) titleText else null,
            body = bodyText,
            showCancelButton = showCancel,
        )
    }
}
```

## Gotchas — required reading

1. **`@FigmaConnect(url = …)` must be a string literal.** A `const val` is uploaded *as the constant's name*, not its value. The parser does not resolve constants.
2. **Property names match byte-for-byte.** `"✏️ Title"` ≠ `"Title"`. `"Type:"` ≠ `"Type"`. Includes literal Figma typos (`"2 (Stacked"` with the missing paren is the real string).
3. **Do not match runtime/theme axes** (`State`, `Dark mode`, `Focus`, `Hover`). Only match axes where Compose forks into distinct functions. State and theming are runtime concerns.
4. **Boolean-visibility + text-content idiom** maps to a nullable Compose param:
   ```kotlin
   @FigmaProperty(FigmaType.Boolean, "Has title") val showTitle: Boolean = true
   @FigmaProperty(FigmaType.Text, "Title text")   val titleText: String = "Title"
   …
   title = if (showTitle) titleText else null
   ```
5. **Use `Figma.mapping(...)` for enum-to-enum.** Every Figma value must be listed; an unmapped value silently becomes `null`.
   ```kotlin
   @FigmaProperty(FigmaType.Enum, "Type:")
   val badgeType: BadgeType = Figma.mapping(
       "Info" to BadgeType.Info,
       "Success" to BadgeType.Success,
       "Mega-primary" to BadgeType.MegaPrimary,
   )
   ```
6. **Files live outside `src/main`** (`core-ui/code-connect/`) on purpose — do not move them under `src/main`, or they will ship in the AAR.

## Validation & publish

```bash
figma connect publish --dry-run    # last line must read: All Code Connect files are valid
figma connect publish              # uploads to Figma server
```

Token: `FIGMA_ACCESS_TOKEN` exported in shell (`export FIGMA_ACCESS_TOKEN=<your-token>`), or pass `--token <value>` per command. Generate / rotate at https://www.figma.com/settings/personal-access-tokens — the token must have `Code Connect: Write` scope on the file's team.

## Common dry-run / publish errors → fix

| Error text | Meaning | Fix (row in §"Change classification") |
|---|---|---|
| `Property 'X' not found on component Y` | Figma renamed/removed the property | property renamed / property removed |
| `Variant 'X' not found on component Y` | Figma renamed the axis | variant axis renamed |
| `Value 'V' not found for variant 'X'` | Figma renamed a variant value | variant value renamed |
| `Component node 12345:6789 not found` | nodeId changed or component deleted | nodeId changed / component deleted |
| Kotlin: `Unresolved reference: X` | Compose-side rename, not Figma | Workflow B |

## Emergency rollback

```bash
figma connect unpublish --token "$FIGMA_ACCESS_TOKEN"
```

Wipes every published mapping from the Figma server. Does not touch code or the Figma file.

## Red flags — stop and re-check

- About to write a property name from a chat message instead of from MCP → re-pull MCP.
- About to add `@FigmaVariant("State", …)` or `("Dark mode", …)` → those are runtime axes; don't match.
- About to use `const val` for the URL → it must be a literal.
- About to invent a Compose function call to "fill in" a Figma variant with no real Compose counterpart → log in `core-ui/code-connect/README.md` ("No-Figma / Compose-only" section) instead.
- About to commit without running `--dry-run` → don't.
