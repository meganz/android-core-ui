package mega.android.core.ui.tokens.buildscripts.kotlingenerator.generic

import androidx.compose.ui.graphics.Color
import com.google.gson.GsonBuilder
import mega.android.core.ui.tokens.buildscripts.deserializers.ColorDeserializer
import mega.android.core.ui.tokens.buildscripts.deserializers.JsonCoreUiObjectDeserializer
import mega.android.core.ui.tokens.buildscripts.deserializers.JsonTokenNameDeserializer
import mega.android.core.ui.tokens.buildscripts.json.JsonColor
import mega.android.core.ui.tokens.buildscripts.json.JsonColorRef
import mega.android.core.ui.tokens.buildscripts.json.JsonCoreUiObject
import mega.android.core.ui.tokens.buildscripts.json.JsonGroup
import mega.android.core.ui.tokens.buildscripts.json.JsonTokenName
import java.io.File
import kotlin.reflect.KClass


/**
 * Given the exported JSON Figma color tokens, it generates the related Kotlin files
 * @property T Core token type
 * @property R Value token type
 */
internal class GenerateTokens<T,R> {

    private val gson = GsonBuilder()
        .registerTypeAdapter(Color::class.java, ColorDeserializer())
        .registerTypeAdapter(JsonCoreUiObject::class.java, JsonCoreUiObjectDeserializer())
        .registerTypeAdapter(JsonTokenName::class.java, JsonTokenNameDeserializer())
        .create()

    /**
     * Generate the core color tokens
     */
    fun generateCoreColorsTokens(
        moduleName: String,
        jsonGroupName: String,
        kotlinFileName: String = "CoreColors",
        packageName: String,
    ) = generateTokensKotlinFile(
        type = JsonColor::class,
        jsonChild = jsonGroupName,
        kotlinOutputName = kotlinFileName,
        generationType = TokenGenerationType.NestedObjects,
        rootGroupName = "", //we only want children
        packageName = packageName,
        destinationPath = "$moduleName/src/main/kotlin",
        rootInternal = true,
    )

    fun generateSemanticTokensInterface(
        moduleName: String,
        jsonGroupName: String = "Semantic tokens/Light",
        kotlinFileName: String = "SemanticTokens",
        packageName: String,
    ) = generateTokensKotlinFile(
        type = JsonColorRef::class,
        jsonChild = jsonGroupName,
        kotlinOutputName = kotlinFileName,
        generationType = TokenGenerationType.InterfaceDefinition,
        packageName = packageName,
        destinationPath = "$moduleName/src/main/kotlin",
        rootGroupName = SEMANTIC_TOKENS_PREFIX
    )

    fun generateSemanticTokensImplementation(
        moduleName: String,
        jsonGroupName: String,
        kotlinFileName: String,
        packageName: String,
        tokensInterfacePackage: String,
        internal: Boolean,
    )= generateTokensKotlinFile(
        type = JsonColorRef::class,
        jsonChild = jsonGroupName,
        kotlinOutputName = kotlinFileName,
        generationType = TokenGenerationType.InterfaceImplementation(
            SEMANTIC_TOKENS_PREFIX,
            tokensInterfacePackage,
        ),
        packageName = packageName,
        destinationPath = "$moduleName/src/main/kotlin",
        rootGroupName = SEMANTIC_TOKENS_PREFIX,
        rootInternal = internal,
    )

    fun generateEnumClassForGroup(
        moduleName: String,
        jsonGroupName: String,
        jsonEnumName: String,
        suffix: String,
        packageName: String,
        tokensInterfacePackage: String,
    )= generateTokensKotlinFile(
        type = JsonColorRef::class,
        jsonChild = jsonGroupName,
        kotlinOutputName = jsonEnumName+suffix,
        generationType = TokenGenerationType.EnumGroup(
            jsonEnumName,
            suffix,
            tokensInterfacePackage
        ),
        packageName = packageName,
        destinationPath = "$moduleName/src/main/kotlin",
        rootGroupName = SEMANTIC_TOKENS_PREFIX
    )

    private fun <T : JsonCoreUiObject> generateTokensKotlinFile(
        type: KClass<T>,
        jsonChild: String,
        kotlinOutputName: String,
        generationType: TokenGenerationType,
        packageName: String,
        destinationPath: String,
        rootGroupName: String?,
        rootInternal: Boolean = false,
    ) {
        val json =
            File("$DEFAULT_ASSETS_FOLDER/$DEFAULT_JSON_CORE_FILE_NAME.json").bufferedReader().readText()
        val rootObject = gson.fromJson(json, JsonCoreUiObject::class.java)
        val coreObject = (rootObject as? JsonGroup)?.children?.first { it.name == jsonChild }
            ?: throw RuntimeException("Child $jsonChild not found")
        if (rootGroupName != null) {
            coreObject.name = rootGroupName
        }
        KotlinTokensGenerator(
            generationType = generationType,
            coreObject = coreObject,
            type = type,
            fileName = kotlinOutputName,
            destinationPackageName = packageName,
            destinationPath = destinationPath,
            rootInternal = rootInternal,
        ).generateFile()
    }

    companion object {
        const val DEFAULT_ASSETS_FOLDER = "designSystemAssets"
        private const val DEFAULT_JSON_CORE_FILE_NAME = "tokens"
        private const val SEMANTIC_TOKENS_PREFIX = "SemanticTokens"
    }
}

