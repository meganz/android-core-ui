package mega.android.core.ui.gradle

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskAction
import java.io.File

/**
 * Gradle task that auto-generates llms.txt and llms-full.txt from Kotlin source files.
 *
 * Parses public @Composable function signatures, parameter lists, KDoc comments,
 * preview function bodies, and enum/sealed class definitions to produce AI-readable
 * component documentation that ships as a classpath resource in the AAR.
 */
abstract class GenerateLlmsTxtTask : DefaultTask() {

    @get:InputDirectory
    @get:PathSensitive(PathSensitivity.RELATIVE)
    abstract val sourceDir: DirectoryProperty

    @get:OutputDirectory
    abstract val outputDir: DirectoryProperty

    @get:OutputDirectory
    @get:Optional
    abstract val screenshotTestDir: DirectoryProperty

    @get:Internal
    abstract val screenshotRefDir: DirectoryProperty

    @TaskAction
    fun generate() {
        val source = sourceDir.get().asFile
        val output = outputDir.get().asFile
        output.mkdirs()

        val ktFiles = source.walkTopDown().filter { it.extension == "kt" }.toList()
        val allComponents = mutableListOf<ComponentInfo>()
        val allEnums = mutableListOf<EnumInfo>()
        val allExamples = mutableListOf<PreviewExample>()

        for (file in ktFiles) {
            val content = file.readText()
            val category = resolveCategory(file, source)
            allComponents.addAll(parsePublicComposables(content, category))
            allEnums.addAll(parseEnums(content, category))
            allExamples.addAll(parsePreviewExamples(content, category))
        }

        // Link examples to components
        linkExamples(allComponents, allExamples)

        // Group by category and sort
        val grouped = allComponents
            .groupBy { it.category }
            .toSortedMap()

        val enumsByCategory = allEnums
            .groupBy { it.category }
            .toSortedMap()

        // Build screenshot filename index from reference directory (if screenshots exist)
        val screenshotIndex = buildScreenshotIndex()

        File(output, "llms.txt").writeText(
            generateConciseIndex(grouped, enumsByCategory)
        )
        File(output, "llms-full.txt").writeText(
            generateFullReference(grouped, enumsByCategory, screenshotIndex)
        )

        // Generate @PreviewTest source files for screenshot generation
        if (screenshotTestDir.isPresent) {
            val testDir = screenshotTestDir.get().asFile
            val screenshotCount = generateScreenshotTestFiles(grouped, allExamples, testDir)
            logger.lifecycle("Generated $screenshotCount @PreviewTest functions")
        }

        val totalComponents = allComponents.size
        val totalCategories = grouped.size
        logger.lifecycle(
            "Generated llms.txt: $totalComponents components across $totalCategories categories"
        )
    }

    private fun resolveCategory(file: File, sourceRoot: File): String {
        val relative = file.parentFile.relativeTo(sourceRoot).path
        return if (relative.isEmpty()) "general" else relative.replace(File.separatorChar, '/')
    }

    // ---------------------------------------------------------------
    // Parsing: public @Composable functions
    // ---------------------------------------------------------------

    private fun parsePublicComposables(content: String, category: String): List<ComponentInfo> {
        val results = mutableListOf<ComponentInfo>()
        val lines = content.lines()

        var i = 0
        while (i < lines.size) {
            val line = lines[i].trim()

            // Look for top-level @Composable annotation (no indentation = not a class member)
            if (line == "@Composable" && lines[i].trimStart() == lines[i]) {
                // Scan backwards for KDoc and other annotations
                val kdoc = extractKdocBefore(lines, i)
                val isPreview = hasPreviewAnnotation(lines, i)

                // Scan forward for fun declaration, also checking for
                // preview annotations between @Composable and fun
                var isPreviewBetween = false
                var isOverride = false
                var funLineIdx = i + 1
                while (funLineIdx < lines.size) {
                    val funLine = lines[funLineIdx].trim()
                    if (funLine.startsWith("fun ") ||
                        funLine.startsWith("private fun ") ||
                        funLine.startsWith("internal fun ") ||
                        funLine.startsWith("public fun ") ||
                        funLine.startsWith("protected fun ") ||
                        funLine.startsWith("override fun ")
                    ) {
                        isOverride = funLine.startsWith("override ")
                        break
                    }
                    // Check for preview annotations after @Composable
                    if (funLine.startsWith("@CombinedThemePreviews") ||
                        funLine.startsWith("@Preview") ||
                        funLine.startsWith("@CombinedThemePreviewsTablet")
                    ) {
                        isPreviewBetween = true
                    }
                    // Allow annotations like @OptIn(...) between @Composable and fun
                    if (!funLine.startsWith("@") && funLine.isNotBlank()) break
                    funLineIdx++
                }

                if (funLineIdx < lines.size) {
                    val funLine = lines[funLineIdx].trim()
                    val isPrivate = funLine.startsWith("private ")
                    val isInternal = funLine.startsWith("internal ")

                    val effectivePreview = isPreview || isPreviewBetween
                    if (!isPrivate && !isInternal && !effectivePreview && !isOverride) {
                        val funName = extractFunctionName(funLine)
                        if (funName != null && funName[0].isUpperCase() &&
                            !funName.endsWith("Preview")
                        ) {
                            val params = extractParameters(lines, funLineIdx)
                            val firstSentence = extractFirstSentence(kdoc)
                            results.add(
                                ComponentInfo(
                                    name = funName,
                                    category = category,
                                    parameters = params,
                                    kdocSummary = firstSentence,
                                    fullKdoc = kdoc,
                                    example = null,
                                    nameDescription = camelCaseToDescription(funName)
                                )
                            )
                        }
                    }
                }
            }
            i++
        }
        return results
    }

    private fun extractFunctionName(funLine: String): String? {
        // Handles: "fun Name(", "public fun Name(", etc.
        val match = Regex("""(?:public\s+|protected\s+)?fun\s+(\w+)\s*\(""").find(funLine)
        return match?.groupValues?.get(1)
    }

    private fun extractParameters(lines: List<String>, funLineIdx: Int): List<ParameterInfo> {
        // Build the full parameter string by counting parentheses
        val fullText = StringBuilder()
        var depth = 0
        var started = false
        var lineIdx = funLineIdx

        while (lineIdx < lines.size) {
            val line = lines[lineIdx]
            for (ch in line) {
                if (ch == '(') {
                    if (started) fullText.append(ch)
                    depth++
                    started = true
                } else if (ch == ')') {
                    depth--
                    if (depth == 0) {
                        // Parse the collected parameter string
                        return parseParameterString(fullText.toString())
                    }
                    fullText.append(ch)
                } else if (started && depth > 0) {
                    fullText.append(ch)
                }
            }
            if (started) fullText.append('\n')
            lineIdx++
        }
        return emptyList()
    }

    private fun parseParameterString(paramStr: String): List<ParameterInfo> {
        if (paramStr.isBlank()) return emptyList()

        val params = mutableListOf<ParameterInfo>()
        // Split on commas at depth 0 (not inside <>, (), {})
        val segments = splitTopLevelCommas(paramStr)

        for (segment in segments) {
            val trimmed = segment.trim()
                .removePrefix("crossinline ")
                .removePrefix("noinline ")
                .removePrefix("vararg ")
                .trim()

            // Skip annotations-only segments
            if (trimmed.isEmpty()) continue

            // Strip parameter annotations like @DrawableRes, @Composable etc.
            val withoutAnnotations = trimmed
                .replace(Regex("""@\w+(\([^)]*\))?\s*"""), "")
                .trim()

            // Match: name: Type = default  OR  name: Type
            val match = Regex("""^(\w+)\s*:\s*(.+)$""").find(withoutAnnotations)
            if (match != null) {
                val name = match.groupValues[1]
                val typeAndDefault = match.groupValues[2].trim()

                // Find the top-level = for default value
                val eqIdx = findTopLevelEquals(typeAndDefault)
                if (eqIdx >= 0) {
                    val type = typeAndDefault.substring(0, eqIdx).trim()
                    val default = typeAndDefault.substring(eqIdx + 1).trim()
                    params.add(ParameterInfo(name, type, default))
                } else {
                    params.add(ParameterInfo(name, typeAndDefault, null))
                }
            }
        }
        return params
    }

    private fun splitTopLevelCommas(s: String): List<String> {
        val result = mutableListOf<String>()
        var parenDepth = 0
        var angleDepth = 0
        var braceDepth = 0
        val current = StringBuilder()

        for ((i, ch) in s.withIndex()) {
            when (ch) {
                '(' -> { parenDepth++; current.append(ch) }
                ')' -> { parenDepth--; current.append(ch) }
                '<' -> { angleDepth++; current.append(ch) }
                '>' -> {
                    // Only treat > as closing angle bracket if we're inside <>
                    // This avoids confusing -> (arrow operator) with angle brackets
                    if (angleDepth > 0) angleDepth--
                    current.append(ch)
                }
                '{' -> { braceDepth++; current.append(ch) }
                '}' -> { braceDepth--; current.append(ch) }
                ',' -> {
                    if (parenDepth == 0 && angleDepth == 0 && braceDepth == 0) {
                        result.add(current.toString())
                        current.clear()
                    } else {
                        current.append(ch)
                    }
                }
                else -> current.append(ch)
            }
        }
        if (current.isNotBlank()) result.add(current.toString())
        return result
    }

    private fun findTopLevelEquals(s: String): Int {
        var parenDepth = 0
        var angleDepth = 0
        var braceDepth = 0
        for (i in s.indices) {
            when (s[i]) {
                '(' -> parenDepth++
                ')' -> parenDepth--
                '<' -> angleDepth++
                '>' -> if (angleDepth > 0) angleDepth--
                '{' -> braceDepth++
                '}' -> braceDepth--
                '=' -> if (parenDepth == 0 && angleDepth == 0 && braceDepth == 0) return i
            }
        }
        return -1
    }

    // ---------------------------------------------------------------
    // KDoc extraction
    // ---------------------------------------------------------------

    private fun extractKdocBefore(lines: List<String>, composableLineIdx: Int): String? {
        // Walk backwards from @Composable to find /** ... */
        var endIdx = composableLineIdx - 1

        // Skip blank lines and other annotations
        while (endIdx >= 0) {
            val line = lines[endIdx].trim()
            if (line.isEmpty() || line.startsWith("@")) {
                endIdx--
            } else if (line.endsWith("*/")) {
                break
            } else {
                return null // Not a KDoc block
            }
        }

        if (endIdx < 0) return null
        if (!lines[endIdx].trim().endsWith("*/")) return null

        // Now walk backwards to find /**
        var startIdx = endIdx
        while (startIdx >= 0) {
            if (lines[startIdx].trim().startsWith("/**")) break
            startIdx--
        }
        if (startIdx < 0) return null

        val kdocLines = lines.subList(startIdx, endIdx + 1)
            .map { it.trim().removePrefix("/**").removePrefix("*").removePrefix(" */").removeSuffix("*/").trim() }
            .filter { it.isNotBlank() }

        return kdocLines.joinToString(" ")
    }

    private fun extractFirstSentence(kdoc: String?): String? {
        if (kdoc == null) return null
        // Take text before first @param or first period
        val beforeParams = kdoc.split("@param", "@see", "@return", "@sample")[0].trim()
        val firstSentence = beforeParams.split(". ").firstOrNull()?.trim()
        return if (firstSentence.isNullOrBlank()) null
        else firstSentence.removeSuffix(".")
    }

    // ---------------------------------------------------------------
    // Preview annotation detection
    // ---------------------------------------------------------------

    private fun hasPreviewAnnotation(lines: List<String>, composableLineIdx: Int): Boolean {
        // Check lines above @Composable for preview annotations
        var idx = composableLineIdx - 1
        while (idx >= 0) {
            val line = lines[idx].trim()
            if (line.startsWith("@CombinedThemePreviews") ||
                line.startsWith("@Preview") ||
                line.startsWith("@CombinedThemePreviewsTablet")
            ) {
                return true
            }
            if (line.isEmpty() || line.startsWith("@") || line.startsWith("*") || line.startsWith("/**") || line.startsWith("*/")) {
                idx--
                continue
            }
            break
        }
        return false
    }

    // ---------------------------------------------------------------
    // Preview example extraction
    // ---------------------------------------------------------------

    private fun parsePreviewExamples(content: String, category: String): List<PreviewExample> {
        val results = mutableListOf<PreviewExample>()
        val lines = content.lines()

        // Capture all imports from this file
        val fileImports = lines
            .filter { it.trimStart().startsWith("import ") }
            .map { it.trim() }

        var i = 0
        while (i < lines.size) {
            val line = lines[i].trim()
            if (line == "@Composable" && hasPreviewAnnotation(lines, i)) {
                // Find the function body
                var funLineIdx = i + 1
                while (funLineIdx < lines.size && !lines[funLineIdx].trim().startsWith("fun ") &&
                    !lines[funLineIdx].trim().startsWith("private fun ")
                ) {
                    funLineIdx++
                }
                if (funLineIdx < lines.size) {
                    val funLine = lines[funLineIdx].trim().removePrefix("private ")
                    val previewName = extractFunctionName(funLine)
                    // Check if the preview function has parameters (from @PreviewParameter)
                    val hasParams = funLine.contains("@PreviewParameter") ||
                            (funLineIdx + 1 < lines.size &&
                                    lines[funLineIdx + 1].contains("@PreviewParameter"))
                    val fullBody = extractFunctionBody(lines, funLineIdx)
                    if (fullBody != null && previewName != null) {
                        // Find the component call inside AndroidThemeForPreviews { ... }
                        val example = extractComponentCallFromPreview(fullBody)
                        if (example != null) {
                            results.add(
                                PreviewExample(
                                    previewName, example,
                                    dedentExample(fullBody),
                                    category, fileImports, hasParams
                                )
                            )
                        }
                    }
                }
            }
            i++
        }
        return results
    }

    private fun extractFunctionBody(lines: List<String>, funLineIdx: Int): String? {
        var depth = 0
        var started = false
        val body = StringBuilder()
        var lineIdx = funLineIdx

        while (lineIdx < lines.size) {
            val line = lines[lineIdx]
            for (ch in line) {
                if (ch == '{') {
                    depth++
                    if (depth == 1) {
                        started = true
                        continue
                    }
                }
                if (ch == '}') {
                    depth--
                    if (depth == 0 && started) {
                        return body.toString()
                    }
                }
                if (started && depth > 0) {
                    body.append(ch)
                }
            }
            if (started) body.append('\n')
            lineIdx++
        }
        return null
    }

    private fun extractComponentCallFromPreview(body: String): String? {
        // Look for content inside AndroidThemeForPreviews { ... }
        val themeBlockStart = body.indexOf("AndroidThemeForPreviews")
        if (themeBlockStart < 0) return null

        val braceStart = body.indexOf('{', themeBlockStart)
        if (braceStart < 0) return null

        var depth = 0
        val inner = StringBuilder()
        for (i in braceStart until body.length) {
            when (body[i]) {
                '{' -> {
                    depth++
                    if (depth > 1) inner.append(body[i])
                }
                '}' -> {
                    depth--
                    if (depth == 0) break
                    inner.append(body[i])
                }
                else -> if (depth > 0) inner.append(body[i])
            }
        }

        val trimmed = inner.toString().trim()
        return if (trimmed.isNotBlank()) dedentExample(trimmed) else null
    }

    private fun dedentExample(text: String): String {
        val lines = text.lines()
        val minIndent = lines.filter { it.isNotBlank() }
            .minOfOrNull { it.length - it.trimStart().length } ?: 0
        return lines.joinToString("\n") {
            if (it.length >= minIndent) it.substring(minIndent) else it
        }.trim()
    }

    private fun linkExamples(
        components: MutableList<ComponentInfo>,
        examples: List<PreviewExample>
    ) {
        for (component in components) {
            val match = examples.find { preview ->
                preview.category == component.category &&
                        preview.body.contains(component.name + "(")
            }
            if (match != null) {
                component.example = match.body
            }
        }
    }

    // ---------------------------------------------------------------
    // Enum parsing
    // ---------------------------------------------------------------

    private fun parseEnums(content: String, category: String): List<EnumInfo> {
        val results = mutableListOf<EnumInfo>()
        val enumRegex = Regex("""enum\s+class\s+(\w+)\s*(?:\([^)]*\)\s*)?\{([^}]*)\}""")

        for (match in enumRegex.findAll(content)) {
            val name = match.groupValues[1]
            val body = match.groupValues[2]
            val values = body.split(",")
                .map { it.trim().split("(")[0].trim() }
                .filter { it.isNotBlank() && it[0].isUpperCase() }
            if (values.isNotEmpty()) {
                results.add(EnumInfo(name, values, category))
            }
        }
        return results
    }

    // ---------------------------------------------------------------
    // Screenshot index: map component names to existing screenshot files
    // ---------------------------------------------------------------

    private fun buildScreenshotIndex(): Map<String, String> {
        if (!screenshotRefDir.isPresent) return emptyMap()
        val refDir = screenshotRefDir.get().asFile
        if (!refDir.exists()) return emptyMap()

        val index = mutableMapOf<String, String>()
        refDir.walkTopDown().filter { it.extension == "png" }.forEach { file ->
            // Screenshot filenames follow: {ComponentName}_Screenshot_{hash}_{index}.png
            // in subdirectories like: mega/android/core/ui/screenshots/ButtonScreenshotsKt/
            val nameWithoutExt = file.nameWithoutExtension
            val match = Regex("""^(\w+)_Screenshot_""").find(nameWithoutExt)
            if (match != null) {
                val componentName = match.groupValues[1]
                // Store relative path from refDir
                val relativePath = file.relativeTo(refDir).path
                index[componentName] = relativePath
            }
        }
        return index
    }

    // ---------------------------------------------------------------
    // Screenshot test file generation
    // ---------------------------------------------------------------

    private fun generateScreenshotTestFiles(
        components: Map<String, List<ComponentInfo>>,
        examples: List<PreviewExample>,
        testDir: File
    ): Int {
        testDir.mkdirs()
        // Clean previous generated files
        testDir.walkTopDown().filter { it.extension == "kt" }.forEach { it.delete() }

        val packageDir = File(testDir, "mega/android/core/ui/screenshots")
        packageDir.mkdirs()

        var count = 0
        for ((category, comps) in components) {
            val className = categoryToClassName(category)
            val sb = StringBuilder()

            // Collect all imports from source files in this category
            val categoryExamples = examples.filter { it.category == category }
            val collectedImports = mutableSetOf(
                "import androidx.compose.runtime.Composable",
                "import androidx.compose.ui.tooling.preview.Preview",
                "import com.android.tools.screenshot.PreviewTest",
                "import androidx.compose.runtime.mutableStateOf",
                "import androidx.compose.runtime.remember",
                "import androidx.compose.runtime.getValue",
                "import androidx.compose.runtime.setValue",
                "import androidx.compose.foundation.layout.*",
                "import androidx.compose.ui.Modifier",
                "import androidx.compose.ui.res.painterResource",
                "import androidx.compose.ui.unit.dp",
                "import mega.android.core.ui.theme.AndroidThemeForPreviews",
                "import mega.android.core.ui.R",
            )
            // Add wildcard imports for this category and common packages
            collectedImports.add("import ${categoryToImportPath(category)}.*")
            collectedImports.add("import mega.android.core.ui.components.*")
            collectedImports.add("import mega.android.core.ui.model.*")
            collectedImports.add("import mega.android.core.ui.theme.values.*")
            collectedImports.add("import mega.android.core.ui.theme.spacing.*")
            collectedImports.add("import mega.android.core.ui.tokens.theme.*")

            // Add all imports from original source files in this category
            for (ex in categoryExamples) {
                for (imp in ex.fileImports) {
                    // Skip preview-specific imports
                    if (!imp.contains("CombinedThemePreviews") &&
                        !imp.contains("PreviewParameter") &&
                        !imp.startsWith("import mega.android.core.ui.preview.")
                    ) {
                        collectedImports.add(imp)
                    }
                }
            }

            sb.appendLine("@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)")
            sb.appendLine("@file:Suppress(\"UNCHECKED_CAST\", \"unused\")")
            sb.appendLine()
            sb.appendLine("package mega.android.core.ui.screenshots")
            sb.appendLine()
            // Add common library imports
            collectedImports.add("import kotlinx.collections.immutable.*")

            for (imp in collectedImports.sorted()) {
                sb.appendLine(imp)
            }
            sb.appendLine()

            // Track generated function names to avoid duplicates
            val usedNames = mutableSetOf<String>()
            // Track component names already rendered (skip duplicate overloads)
            val renderedComponents = mutableSetOf<String>()

            for (comp in comps) {
                val hasOverloads = comps.count { it.name == comp.name } > 1

                // Skip additional overloads — they look identical visually
                if (hasOverloads && comp.name in renderedComponents) continue
                renderedComponents.add(comp.name)

                val funcName = uniqueFuncName(comp.name, usedNames)
                usedNames.add(funcName)

                // Find a matching example without @PreviewParameter
                // Skip examples that reference file-private symbols (won't compile in isolation)
                val example = examples.find { ex ->
                    ex.category == category &&
                            ex.body.contains(comp.name + "(") &&
                            !ex.hasParameters &&
                            !ex.fullBody.contains("listItemSamples") &&
                            !ex.fullBody.contains("enterAnimation()") &&
                            !ex.fullBody.contains("exitAnimation()")
                }

                // Skip components where we can't generate valid rendering code
                if (example == null) {
                    val minimalCall = generateMinimalCall(comp)
                    // Skip if needs complex types, overloaded, or has receiver lambdas
                    if (minimalCall.contains("TODO()") || hasOverloads ||
                        minimalCall.contains("_ ->")
                    ) continue
                }

                sb.appendLine("@PreviewTest")
                sb.appendLine("@Preview(showBackground = true)")
                sb.appendLine("@Composable")
                sb.appendLine("fun ${funcName}_Screenshot() {")

                if (example != null) {
                    // Use the full preview body (includes AndroidThemeForPreviews wrapper)
                    val indented = example.fullBody.lines().joinToString("\n") { "    $it" }
                    sb.appendLine(indented)
                } else {
                    // Generate a minimal call with required params
                    sb.appendLine("    AndroidThemeForPreviews {")
                    sb.appendLine("        ${generateMinimalCall(comp)}")
                    sb.appendLine("    }")
                }

                sb.appendLine("}")
                sb.appendLine()
                count++
            }

            File(packageDir, "$className.kt").writeText(sb.toString())
        }
        return count
    }

    private fun categoryToClassName(category: String): String {
        return category.split("/")
            .joinToString("") { it.replaceFirstChar { c -> c.uppercase() } } + "Screenshots"
    }

    private fun categoryToImportPath(category: String): String {
        val subPackage = category.replace("/", ".")
        return if (subPackage == "general") {
            "mega.android.core.ui.components"
        } else {
            "mega.android.core.ui.components.$subPackage"
        }
    }

    private fun uniqueFuncName(name: String, used: Set<String>): String {
        if (name !in used) return name
        var i = 2
        while ("${name}$i" in used) i++
        return "${name}$i"
    }

    private fun generateMinimalCall(comp: ComponentInfo): String {
        val sb = StringBuilder()
        sb.append("${comp.name}(")
        val requiredParams = comp.parameters.filter { it.defaultValue == null }
        val args = requiredParams.map { p ->
            "${p.name} = ${defaultValueForType(p.type)}"
        }
        sb.append(args.joinToString(", "))
        sb.append(")")
        return sb.toString()
    }

    private fun defaultValueForType(type: String): String {
        return when {
            type == "String" -> "\"Sample\""
            type == "String?" -> "null"
            type == "Boolean" -> "false"
            type == "Int" -> "0"
            type == "Float" -> "0f"
            type == "Long" -> "0L"
            type == "Modifier" -> "Modifier"
            type.endsWith("?") -> "null"
            type.contains("-> Unit") -> {
                // Extract param list from (Type1, Type2) -> Unit
                val cleaned = type.removePrefix("@Composable ").trim()
                val paramMatch = Regex("""\(([^)]*)\)\s*->""").find(cleaned)
                val paramText = paramMatch?.groupValues?.get(1)?.trim() ?: ""
                val paramCount = if (paramText.isEmpty()) 0
                else paramText.split(",").count { it.isNotBlank() }
                if (paramCount <= 0) "{}"
                else "{ ${(1..paramCount).joinToString(", ") { "_" }} -> }"
            }
            type.startsWith("List") -> "emptyList()"
            type.startsWith("Map") -> "emptyMap()"
            type.startsWith("ImmutableList") -> "persistentListOf()"
            else -> "TODO()"
        }
    }

    // ---------------------------------------------------------------
    // Name-to-description conversion
    // ---------------------------------------------------------------

    private fun camelCaseToDescription(name: String): String {
        return name.replace(Regex("([a-z])([A-Z])"), "$1 $2")
            .replace(Regex("([A-Z]+)([A-Z][a-z])"), "$1 $2")
            .lowercase()
            .replaceFirstChar { it.uppercase() }
    }

    // ---------------------------------------------------------------
    // Output generation: concise index
    // ---------------------------------------------------------------

    private fun generateConciseIndex(
        components: Map<String, List<ComponentInfo>>,
        enums: Map<String, List<EnumInfo>>
    ): String {
        val sb = StringBuilder()
        sb.appendLine("# mega.android.core.ui - Compose Component Library")
        sb.appendLine()
        sb.appendLine("> Android Compose UI components. Wrap all usage in AndroidTheme(isDark) { }.")
        sb.appendLine("> Theming via DSTokens. Spacing via LocalSpacing.current.")
        sb.appendLine()

        for ((category, comps) in components) {
            sb.appendLine("## $category")
            val categoryEnums = enums[category].orEmpty()

            // Group overloads so we can differentiate them
            val byName = comps.sortedBy { it.name }.groupBy { it.name }
            for ((name, overloads) in byName) {
                if (overloads.size == 1) {
                    val comp = overloads.first()
                    val desc = comp.kdocSummary ?: comp.nameDescription
                    val paramSummary = formatParamSummary(comp.parameters)
                    sb.appendLine("- $name($paramSummary) - $desc")
                } else {
                    // Show typed params to differentiate overloads
                    for (comp in overloads) {
                        val desc = comp.kdocSummary ?: comp.nameDescription
                        val typedSummary = formatTypedParamSummary(comp.parameters)
                        sb.appendLine("- $name($typedSummary) - $desc")
                    }
                }
            }

            for (enum in categoryEnums) {
                sb.appendLine("  [${enum.name}: ${enum.values.joinToString(", ")}]")
            }
            sb.appendLine()
        }
        return sb.toString()
    }

    private fun formatParamSummary(params: List<ParameterInfo>): String {
        if (params.isEmpty()) return ""

        return params.joinToString(", ") { p ->
            if (p.defaultValue != null) "${p.name}?" else p.name
        }
    }

    private fun formatTypedParamSummary(params: List<ParameterInfo>): String {
        if (params.isEmpty()) return ""

        // For typed summaries (overloads), show only non-callback params
        // to highlight what differentiates the overloads
        val meaningful = params.filter { p ->
            val type = simplifyType(p.type)
            type != "callback" && !type.startsWith("callback")
        }
        val display = meaningful.ifEmpty { params }

        return display.joinToString(", ") { p ->
            val type = simplifyType(p.type)
            val suffix = if (p.defaultValue != null && !type.endsWith("?")) "?" else ""
            "${p.name}: $type$suffix"
        }
    }

    private fun simplifyType(type: String): String {
        // Shorten common long types for readability
        return type
            .replace("androidx.compose.ui.Modifier", "Modifier")
            .replace("androidx.compose.ui.graphics.painter.Painter", "Painter")
            .replace("@Composable ", "")
            .replace(Regex("""\(.*?\) -> Unit"""), "callback")
    }

    // ---------------------------------------------------------------
    // Output generation: full reference
    // ---------------------------------------------------------------

    private fun generateFullReference(
        components: Map<String, List<ComponentInfo>>,
        enums: Map<String, List<EnumInfo>>,
        screenshotIndex: Map<String, String> = emptyMap()
    ): String {
        val sb = StringBuilder()
        sb.appendLine("# mega.android.core.ui - Full Component Reference")
        sb.appendLine()
        sb.appendLine("> Android Compose UI components. Wrap all usage in AndroidTheme(isDark) { }.")
        sb.appendLine("> Theming via DSTokens. Spacing via LocalSpacing.current.")
        sb.appendLine()

        for ((category, comps) in components) {
            sb.appendLine("## $category")
            sb.appendLine()

            val categoryEnums = enums[category].orEmpty()
            if (categoryEnums.isNotEmpty()) {
                for (enum in categoryEnums) {
                    sb.appendLine("enum ${enum.name} { ${enum.values.joinToString(", ")} }")
                }
                sb.appendLine()
            }

            for (comp in comps.sortedBy { it.name }) {
                sb.appendLine("### ${comp.name}")
                sb.appendLine()
                val desc = comp.kdocSummary ?: comp.nameDescription
                sb.appendLine(desc)
                sb.appendLine()

                // Add screenshot reference if available
                val screenshotFile = screenshotIndex[comp.name]
                if (screenshotFile != null) {
                    sb.appendLine("![${comp.name}](screenshots/$screenshotFile)")
                    sb.appendLine()
                }

                sb.appendLine("```kotlin")
                sb.appendLine("@Composable")
                sb.appendLine("fun ${comp.name}(")
                for ((idx, p) in comp.parameters.withIndex()) {
                    val trailing = if (idx < comp.parameters.size - 1) "," else ","
                    val defaultStr = if (p.defaultValue != null) " = ${p.defaultValue}" else ""
                    sb.appendLine("    ${p.name}: ${p.type}$defaultStr$trailing")
                }
                sb.appendLine(")")
                sb.appendLine("```")

                if (comp.example != null) {
                    sb.appendLine()
                    sb.appendLine("Example:")
                    sb.appendLine("```kotlin")
                    sb.appendLine(comp.example)
                    sb.appendLine("```")
                }

                sb.appendLine()
                sb.appendLine("---")
                sb.appendLine()
            }
        }
        return sb.toString()
    }
}

// ---------------------------------------------------------------
// Data classes
// ---------------------------------------------------------------

data class ComponentInfo(
    val name: String,
    val category: String,
    val parameters: List<ParameterInfo>,
    val kdocSummary: String?,
    val fullKdoc: String?,
    var example: String?,
    val nameDescription: String,
)

data class ParameterInfo(
    val name: String,
    val type: String,
    val defaultValue: String?,
)

data class EnumInfo(
    val name: String,
    val values: List<String>,
    val category: String,
)

data class PreviewExample(
    val previewName: String,
    val body: String,           // extracted component call (for llms-full.txt examples)
    val fullBody: String,       // full preview function body (for screenshot generation)
    val category: String,
    val fileImports: List<String> = emptyList(),
    val hasParameters: Boolean = false,
)
