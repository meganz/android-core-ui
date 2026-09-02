# Core UI Architecture

How this library fits into MEGA's mobile design system.

## Design-system approach

MEGA's design does not follow Material 2/3 theme attributes, so this is a **fully custom design system** using Compose's ["replacing Material systems"](https://developer.android.com/jetpack/compose/designsystems/custom#replacing-systems) strategy: components still build on Material 3 widgets, but all colors come from MEGA tokens injected via `CompositionLocal`, not from `MaterialTheme.colorScheme`.

## Tokens

Design tokens are the smallest design decisions, grouped into systems (color, typography, spacing). Color is the only system that varies by theme (light/dark). Two layers:

- **Core tokens** — the raw palette, hardcoded values grouped by concept ("Primary", "Warning", …). Generated into `CoreColors.kt`.
- **Semantic tokens** — context-specific usage ("Background", "Text", "Button", …) that *reference* core tokens. A palette change propagates automatically without touching the semantic layer. Generated as the `SemanticTokens` interface plus its light/dark implementations (`AndroidNewSemanticTokensLight` / `AndroidNewSemanticTokensDark`).

### Token generation pipeline

The design team exports tokens from Figma to `designSystemAssets/tokens.json` (kept outside Android resources — it is never built into any app). Then:

```bash
./gradlew :core-ui-tokens:generateDefaultTokens
```

runs `GenerateColorTokens.generateThemeForAndroidNew()`, which generates Kotlin into **both modules**:

| Output | Module / package | Contents |
|---|---|---|
| `CoreColors.kt` | `core-ui-tokens` · `tokens.theme.tokens` | Raw palette values |
| `SemanticTokens.kt` | same | Interface: `focus`, `indicator`, `support`, `button`, `text`, `background`, `icon`, `components`, `link`, `notifications`, `border`, `brand` groups |
| `AndroidNewSemanticTokensLight/Dark.kt` | same | Light/dark implementations mapping semantic → core |
| `TextColor.kt`, `BackgroundColor.kt`, `IconColor.kt`, … | `core-ui` · `theme.values` | Public enums exposing selected semantic groups, with internal mappers to the actual `Color` |

All generated files are committed. The generator source lives in `core-ui-tokens/src/main/kotlin/.../buildscripts/`; the entry point is in `src/debug/` so it never ships in a release build.

**Procedure for token changes**: create a ticket with the relevant Figma links, replace the JSON on a branch, rerun the generator, commit JSON + regenerated Kotlin together, and include before/after screenshots in the MR. Avoid bundling unrelated changes; a design-team member reviews during QA.

## Component taxonomy

Three tiers, of which **only the first lives in this repo**:

- **Core components** (`:core-ui`) — generic enough for all MEGA apps: buttons, text fields, dialogs, cards, banners, etc. Usually extend Material 3 components, applying MEGA tokens. They read semantic tokens but are agnostic about actual values — those are injected by the theme above them.
- **Snowflake components** — app-specific components that still need tokens (e.g. a Chat Bubble, a Drive list item). They live in feature modules of the consuming app, depend on `:core-ui-tokens` (and optionally `:core-ui`), and exist to keep this library lean.
- **Feature components** — compositions of core/snowflake components with content. They must **not** use tokens or implement their own styling.

## Module hierarchy

```
designSystemAssets/tokens.json  (design export, not built)
        │  generateDefaultTokens
        ▼
:core-ui-tokens ──── DSTokens, DSColors, CoreColors, SemanticTokens, shapes, spacings
        │ implementation()          │ (also usable by snowflake modules and the
        ▼                           │  MEGA app's original-core-ui, without leaking
:core-ui ─────────── components,   │  tokens to app code)
        │            AndroidTheme, theme/values enums
        ▼
consuming apps / feature modules   (:app here is the demo catalog)
```

`:core-ui-tokens` is a separate module *by design*: it lets `core-ui`, snowflake modules, and the MEGA app's `original-core-ui` all build themed components, while apps that only consume components never see raw tokens — `:core-ui` depends on it via `implementation`, so tokens are not part of the library's public ABI. The layering minimizes blast radius: e.g. adding a new theme only requires a new semantic-token set plus a visual check of core-component previews.

## Theme injection

`AndroidTheme(isDark) { … }` (`core-ui/.../theme/Theme.kt`) is where actual token values are injected — typically at Activity/Fragment level, though it can wrap a single screen. It selects `AndroidNewSemanticTokensLight/Dark`, wraps them in `DSColors`, and provides them through `LocalColorPalette`, alongside `LocalSpacing`, `LocalDeviceType` (phone/tablet from window size class), `LocalIsDark`, and `LocalActivity`. `AndroidThemeForPreviews` exists for previews inside this library and modules depending on it.

Inside components, tokens are read through the `DSTokens` accessor (`DSTokens.colors`, `DSTokens.shapes`, `DSTokens.spacings`). The `LocalColorPalette` default is an **all-magenta palette** so a component rendered outside `AndroidTheme` is immediately obvious.

Consumers of the library pick colors via the public `theme/values` enums (`TextColor`, `IconColor`, `BackgroundColor`, …) that components accept as parameters — never raw `Color` values. When implementing a design, take the token names from the Figma component definition.

## TEMP tokens / original-core-ui (context, not in this repo)

The MEGA app still contains an older Material 2-based component library (`original-core-ui`) using TEMP tokens through `TempTheme`. The rule during migration: original components use `TempTheme`, new components use `AndroidTheme`. TEMP tokens differ from the new ones mainly in accent colors. That code lives in the MEGA app repo, not here — but this repo's token generator retains support for generating TEMP token sets (`GenerateColorTokens` has a TEMP variant), and the split of this repo into `:core-ui` + `:core-ui-tokens` was step two of that migration plan.
