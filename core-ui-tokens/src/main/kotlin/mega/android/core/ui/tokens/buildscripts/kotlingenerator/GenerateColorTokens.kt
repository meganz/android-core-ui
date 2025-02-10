package mega.android.core.ui.tokens.buildscripts.kotlingenerator

import mega.android.core.ui.tokens.buildscripts.json.JsonColor
import mega.android.core.ui.tokens.buildscripts.json.JsonColorRef
import mega.android.core.ui.tokens.buildscripts.kotlingenerator.generic.GenerateTokens


class GenerateColorTokens {

    private val generator = GenerateTokens<JsonColor, JsonColorRef>()

    /**
     * Generates the kotlin classes needed to use the color tokens:
     * - Core color tokens values
     * - Semantic tokens interface
     * - Semantic tokens implementation with light and dark values
     * - Enums to expose specific semantic tokens groups and the internal mappers to convert it to the actual color
     */
    internal fun generateThemeForAndroidNew() = generate(
        moduleNameForTokens = CORE_TOKENS_MODULE_NAME,
        moduleNameForEnumValues = CORE_UI_MODULE_NAME,
        packageNameForTokensImplementation = DEFAULT_PACKAGE_FOR_TOKENS,
        packageNameForSemanticTokensInterfaces = DEFAULT_PACKAGE_FOR_TOKENS,
        packageNameForEnumValues = DEFAULT_PACKAGE_FOR_TOKENS_ENUM_VALUES,
        themePrefix = "AndroidNew",
        jsonGroupNameForCoreColorTokens = "Core/Main",
        groupsToExpose = listOf("Text", "Background", "Icon", "Support", "Link", "Components"),
        includeSemanticTokensInterface = true,
        internalSemanticTokens = false,
    )

    /**
     * Generates the kotlin classes needed to use the TEMP color tokens:
     * - TEMP Core color tokens values
     * - Semantic tokens implementation with light and dark TEMP values
     * - Enums to expose specific semantic tokens groups and the internal mappers to convert it to the actual color
     * @param moduleName of the module where this kotlin classes will be generated
     * @param packageName where the kotlin classes will be generated inside the module [moduleName]
     * @param themePrefix a prefix that will be added to semantic tokens classes names to distinguish these tokens from others
     * @param jsonGroupNameForCoreColorTokens the name of the group that will generate the core colors
     * @param groupsToExpose a list of the JSON groups that will be exposed through enums
     */
    fun generateThemeForAndroidTemp(
        moduleName: String,
        packageName: String,
        themePrefix: String,
        jsonGroupNameForCoreColorTokens: String,
        groupsToExpose: List<String>,
    ) = generate(
        moduleNameForTokens = moduleName,
        moduleNameForEnumValues = moduleName,
        packageNameForTokensImplementation = packageName,
        packageNameForSemanticTokensInterfaces = DEFAULT_PACKAGE_FOR_TOKENS,
        packageNameForEnumValues = packageName,
        jsonGroupNameForCoreColorTokens= jsonGroupNameForCoreColorTokens,
        themePrefix = themePrefix,
        groupsToExpose = groupsToExpose,
        includeSemanticTokensInterface = false,
        internalSemanticTokens = true,
    )

    private fun generate(
        moduleNameForTokens: String,
        moduleNameForEnumValues: String,
        packageNameForTokensImplementation: String,
        packageNameForSemanticTokensInterfaces: String,
        packageNameForEnumValues: String,
        jsonGroupNameForCoreColorTokens: String,
        themePrefix: String,
        groupsToExpose: List<String>,
        includeSemanticTokensInterface: Boolean,
        internalSemanticTokens: Boolean,
    ) {

        if (includeSemanticTokensInterface) {
            generator.generateSemanticTokensInterface(
                moduleName = moduleNameForTokens,
                packageName = packageNameForSemanticTokensInterfaces,
            )
        }

        generator.generateCoreColorsTokens(
            moduleName = moduleNameForTokens,
            jsonGroupName = jsonGroupNameForCoreColorTokens,
            packageName = packageNameForTokensImplementation,
        )

        listOf("Light", "Dark").forEach {
            generator.generateSemanticTokensImplementation(
                moduleName = moduleNameForTokens,
                jsonGroupName = "Semantic tokens/$it",
                kotlinFileName = "${themePrefix}SemanticTokens$it",
                packageName = packageNameForTokensImplementation,
                tokensInterfacePackage = packageNameForSemanticTokensInterfaces,
                internal = internalSemanticTokens,
            )
        }

        groupsToExpose.forEach {
            generator.generateEnumClassForGroup(
                moduleName = moduleNameForEnumValues,
                jsonGroupName = "Semantic tokens/Light",
                jsonEnumName = it,
                suffix = "Color",
                packageName = packageNameForEnumValues,
                tokensInterfacePackage = packageNameForSemanticTokensInterfaces,
            )
        }
    }

    companion object {
        private const val CORE_TOKENS_MODULE_NAME = "core-ui-tokens"
        private const val CORE_UI_MODULE_NAME = "core-ui"
        private const val DEFAULT_PACKAGE_FOR_TOKENS = "mega.android.core.ui.tokens.theme.tokens"
        private const val DEFAULT_PACKAGE_FOR_TOKENS_ENUM_VALUES =
            "mega.android.core.ui.theme.values"
    }
}