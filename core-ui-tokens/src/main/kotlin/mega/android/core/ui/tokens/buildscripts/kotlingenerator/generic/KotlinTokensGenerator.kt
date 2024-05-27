package mega.android.core.ui.tokens.buildscripts.kotlingenerator.generic

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import mega.android.core.ui.tokens.buildscripts.json.JsonCoreUiObject
import mega.android.core.ui.tokens.buildscripts.json.JsonGroup
import mega.android.core.ui.tokens.buildscripts.json.JsonLeaf
import mega.android.core.ui.tokens.buildscripts.json.SemanticValueRef
import java.io.File
import java.security.InvalidParameterException
import kotlin.reflect.KClass

/**
 * Utility class to generate a kotlin file with all the tokens of the given type
 * @param generationType defines what it will be generated
 * @param coreObject the object we want to generate
 * @param type the type of [JsonCoreUiObject] we want to generate
 * @param fileName the name of the kotlin file that will be generated (with [appPrefix])
 * @param destinationPackageName the package where we want to put this file
 * @param destinationPath the root path where this file will be added (without packages)
 */

internal class KotlinTokensGenerator<T : JsonCoreUiObject>(
    private val generationType: TokenGenerationType,
    private val coreObject: JsonCoreUiObject,
    private val type: KClass<T>,
    private val fileName: String,
    private val destinationPackageName: String,
    private val destinationPath: String,
    private val rootInternal: Boolean
) {
    fun generateFile() {
        val coreUiFile = FileSpec
            .builder(destinationPackageName, fileName)
            .indent("    ")
        coreUiFile.addFileComment(
            """
            |
            |Generated automatically by ${this.javaClass.simpleName}.
            |Do not modify this file manually.
            |""".trimMargin()
        )
        if (generationType is TokenGenerationType.InterfaceDefinition) {
            addDataClasses(coreUiFile, coreObject, generationType)
        }
        if (generationType is TokenGenerationType.EnumGroup) {
            addEnumGroup(coreUiFile, coreObject, generationType)
        }
        addRootObject(coreUiFile, coreObject)
        val destination = File(destinationPath)
        coreUiFile.build().writeTo(destination)
    }

    private fun addDataClasses(
        file: FileSpec.Builder,
        coreUiObject: JsonCoreUiObject,
        interfaceDefinition: TokenGenerationType.InterfaceDefinition,
    ) {
        if (coreUiObject is JsonGroup) {
            coreUiObject.children
                .filterIsInstance(type.java)
                .filterIsInstance<SemanticValueRef>()
                .map {
                    Triple(
                        it.getPropertyName(coreUiObject.name),
                        it.getPropertyClass(),
                        it.getPropertyInitializer()
                    )
                }.takeIf { it.isNotEmpty() }?.let { properties ->
                    file.addType(
                        createDataClass(
                            coreUiObject.name.jsonNameToKotlinName(),
                            properties,
                        ).build()
                    )
                }
            coreUiObject.children.forEach {
                addDataClasses(file, it, interfaceDefinition)
            }
        }
    }

    private fun addEnumGroup(
        file: FileSpec.Builder,
        coreUiObject: JsonCoreUiObject,
        enumGroup: TokenGenerationType.EnumGroup,
    ) {
        if (coreUiObject is JsonGroup) {
            coreUiObject.children
                .filterIsInstance(type.java)
                .filterIsInstance<SemanticValueRef>()
                .map {
                    Triple(
                        it.getPropertyName(coreUiObject.name),
                        it.getPropertyClass(),
                        it.getPropertyInitializer()
                    )
                }.takeIf { it.isNotEmpty() }?.let { properties ->
                    val groupName = coreUiObject.name.jsonNameToKotlinName()
                    if (groupName == enumGroup.groupName) {
                        val enumName = groupName + enumGroup.classSuffix

                        val enum = createEnumClass(enumName, properties)
                        addEnumMapper(
                            enumName,
                            properties,
                            packageName = enumGroup.packageName,
                            groupName = groupName,
                            typeSpecBuilder = enum
                        )
                        file.addType(
                            enum.build()
                        )
                    }
                }
            coreUiObject.children.forEach {
                addEnumGroup(file, it, enumGroup)
            }
        }
    }

    private fun addRootObject(
        file: FileSpec.Builder,
        rootObject: JsonCoreUiObject
    ) {
        if (rootObject is JsonGroup) {
            if (rootObject.name.isNullOrEmpty()) {
                rootObject.children.forEach { child ->
                    if (child is JsonGroup) {
                        addObject(file, child)
                    } else {
                        throw InvalidParameterException("Root object children should be a group: $child")
                    }
                }
            } else {
                //named root group is add as it is
                addObject(file, rootObject)
            }
        } else {
            throw InvalidParameterException("Root object should be a group: $rootObject")
        }
    }

    private fun addObject(
        file: FileSpec.Builder,
        group: JsonGroup
    ) {
        if (generationType !is TokenGenerationType.ObjectTokenGenerationType || !group.hasChildOfType(type)) return
        val mainType = when (generationType) {
            is TokenGenerationType.InterfaceImplementation -> {
                TypeSpec.objectBuilder(fileName)
                    .addSuperinterface(
                        ClassName(
                            generationType.interfacePackage,
                            generationType.interfaceName
                        )
                    )
            }

            is TokenGenerationType.InterfaceDefinition -> {
                TypeSpec.interfaceBuilder(group.name.jsonNameToKotlinName())
            }

            is TokenGenerationType.NestedObjects -> {
                TypeSpec.objectBuilder(group.name.jsonNameToKotlinName())
            }
        }
        if (rootInternal) {
            mainType.addModifiers(KModifier.INTERNAL)
        }
        if (generationType is TokenGenerationType.NestedObjects) {
            group.children.forEach {
                addChildObjectRecursively(mainType, it, group.name)
            }
        } else {
            group.children
                .filterIsInstance<JsonGroup>()
                .filter { (it as? JsonGroup)?.hasChildOfType(type) == true }
                .mapNotNull { createProperty(it) }
                .takeIf { it.isNotEmpty() }?.let { properties ->
                    properties.forEach {
                        mainType.addProperty(it)
                    }
                }
        }
        file.addType(mainType.build())
    }

    private fun createProperty(child: JsonGroup): PropertySpec? {
        child.children
            .filter { it::class == type }
            .filterIsInstance<SemanticValueRef>()
            .takeIf { it.isNotEmpty() }?.let { properties ->
                val className = child.name.jsonNameToKotlinName()
                val propertyName = className.lowercaseFirstChar()
                val propSpecBuilder = PropertySpec.builder(
                    propertyName,
                    ClassName(
                        packageName = (generationType as? TokenGenerationType.InterfaceImplementation)?.interfacePackage
                            ?: destinationPackageName,
                        className,
                    )
                )
                if (generationType is TokenGenerationType.InterfaceImplementation) {
                    propSpecBuilder
                        .addModifiers(KModifier.OVERRIDE)
                        .initializer(
                            properties.joinToString(
                                separator = ",\n",
                                prefix = "$className(\n",
                                postfix = ",\n)"
                            ) { it.getValueForDataClassInitializer(child.name) }
                        )
                }
                return propSpecBuilder.build()
            }
        return null
    }

    private fun addChildObjectRecursively(
        groupObject: TypeSpec.Builder,
        coreObject: JsonCoreUiObject,
        groupParentName: String?,
    ) {
        when {
            coreObject is JsonGroup -> addGroup(groupObject, coreObject)
            (coreObject::class == type && coreObject is JsonLeaf) -> {
                groupObject.addProperty(
                    PropertySpec
                        .builder(
                            coreObject.getPropertyName(groupParentName ?: ""),
                            coreObject.getPropertyClass()
                        )
                        .initializer(coreObject.getPropertyInitializer()).build()
                )
            }
        }
    }

    private fun addGroup(
        parentGroupObject: TypeSpec.Builder,
        group: JsonGroup
    ) {
        createGroupObject(group)?.let {
            parentGroupObject.addType(it.build())
        }
    }

    private fun createGroupObject(group: JsonGroup) =
        if (group.hasChildOfType(type)) {
            val groupObject = TypeSpec
                .objectBuilder(group.name.jsonNameToKotlinName())
            group.children.forEach {
                addChildObjectRecursively(groupObject, it, group.name)
            }
            groupObject
        } else {
            null
        }
}

