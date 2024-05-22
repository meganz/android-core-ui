package mega.android.core.ui.example.temp.theme

import mega.android.core.ui.tokens.buildscripts.kotlingenerator.GenerateColorTokens


/**
 * Runs the Script to generate Kotlin files with the tokens from json files
 */
fun main(args: Array<String>) {
    GenerateColorTokens().generateThemeForAndroidTemp(
        moduleName = "example/temp-theme",
        packageName = "mega.android.core.ui.example.temp.theme.tokens",
        themePrefix = "Temp",
        groupsToExpose = listOf("Text", "Icon"),
    )
}