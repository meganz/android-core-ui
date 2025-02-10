package mega.android.core.ui.tokens.buildscripts.kotlingenerator.generic

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import kotlin.reflect.KClass


internal fun createDataClass(
    name: String,
    properties: List<Triple<String, KClass<*>, String>>,
    packageName: String = "",
    enumName: String? = null,
): TypeSpec.Builder {
    val dataClassBuilder = TypeSpec.classBuilder(name)
        .addModifiers(KModifier.DATA)
    val constructor = FunSpec.constructorBuilder()
    properties.forEach { (propName, propType, defaultValue) ->
        constructor.addParameter(
            ParameterSpec.builder(propName, propType)
                .defaultValue(defaultValue)
                .build()
        )
        dataClassBuilder.addProperty(
            PropertySpec.builder(propName, propType)
                .initializer(propName)
                .build()
        )
    }
    dataClassBuilder.primaryConstructor(constructor.build())
    return dataClassBuilder
}

internal fun addEnumMapper(
    enumName: String,
    properties: List<Triple<String, KClass<*>, String>>,
    packageName: String = "",
    groupName: String? = null,
    typeSpecBuilder: TypeSpec.Builder,
) {

    if (groupName != null) {
        val funCode = StringBuilder()
        val paramName = groupName.lowercaseFirstChar()
        funCode.appendLine("return when (this) {")
        properties.forEach { (propName, _, _) ->
            funCode.appendLine("    ${propName.uppercaseFirstChar()} -> $paramName.$propName")
        }
        funCode.appendLine("}")
        typeSpecBuilder.addFunction(
            FunSpec
                .builder("get$enumName")
                //.addModifiers(KModifier.INTERNAL) this should be back to Internal once `MegaOriginalTheme` is not needed anymore
                .returns(properties.first().second)
                .addParameter(
                    ParameterSpec.builder(
                        paramName,
                        ClassName(packageName, groupName)
                    ).build()
                )
                .addCode(funCode.toString())
                .build()
        )
    }
}

internal fun createInterface(
    name: String,
    properties: List<Triple<String, KClass<*>, String>>,
): TypeSpec.Builder {
    val dataClassBuilder = TypeSpec.interfaceBuilder(name)
    properties.forEach { (propName, propType, defaultValue) ->
        dataClassBuilder.addProperty(
            PropertySpec
                .builder(propName, propType)
                .getter(FunSpec.getterBuilder().addStatement("return $defaultValue").build())
                .build()
        )
    }

    return dataClassBuilder
}

internal fun createEnumClass(
    name: String,
    properties: List<Triple<String, KClass<*>, String>>,
): TypeSpec.Builder {
    val enumClassBuilder = TypeSpec.enumBuilder(name)
    properties.forEach { (propName, _, _) ->
        enumClassBuilder.addEnumConstant(propName.uppercaseFirstChar())
    }
    return enumClassBuilder
}

/**
 * Returns a String with Kotlin idiomatic format:
 *  - remove spaces
 *  - remove "--" prefix
 *  - snake_case to camelCase
 *  - add an "n" prefix in case it does not starts with a letter
 */
internal fun String?.jsonNameToKotlinName(): String {
    if (this == null) return "Unknown"
    val pattern = "([_\\-])[a-z,0-9]".toRegex()
    val camelCase = this
        .removePrefix("--") //remove "--" prefixes
        .replace(" ", "_") //convert spaces to underscore
        .replace(pattern) { it.value.last().uppercase() } //snake_case to camelCase
        .trim()
    return if (camelCase.matches("^[^a-zA-Z].*".toRegex())) {
        "n$camelCase" //if is not starting with a letter (usually a number) add "n" prefix
    } else {
        camelCase
    }
}

internal fun String.lowercaseFirstChar(): String =
    this.replaceFirstChar { it.lowercase() }

internal fun String.uppercaseFirstChar(): String =
    this.replaceFirstChar { it.uppercase() }

internal fun String?.getPropertyName(typePrefix: String, groupParentName: String?): String =
    (this.jsonNameToKotlinName()
        .removePrefix(typePrefix)
        .removePrefix(typePrefix.lowercaseFirstChar())
        .removePrefix(groupParentName ?: "")
        .takeIf { it.isNotBlank() } ?: this.jsonNameToKotlinName()
            ).lowercaseFirstChar()
