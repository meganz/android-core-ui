package mega.android.core.ui.tokens.buildscripts.kotlingenerator.generic

/**
 * Defines what it will be generated
 */
internal sealed interface TokenGenerationType {
    /**
     * Base class for types that parses the objects hierarchy
     */
    sealed interface ObjectTokenGenerationType: TokenGenerationType
    /**
     * A nested objects with constant values will be generated (mainly for core colors)
     */
    data object NestedObjects : ObjectTokenGenerationType

    /**
     * An Interface and all related data classes will be generated (mainly for Semantic tokens interface)
     */
    data object InterfaceDefinition : ObjectTokenGenerationType

    /**
     * An object implementing the corresponding interface will be generated (mainly for semantic tokens theme values)
     * @param interfaceName the name of the interface to be implemented
     * @param interfacePackage the package of this interface
     *
     */
    data class InterfaceImplementation(
        val interfaceName: String,
        val interfacePackage: String,
    ) : ObjectTokenGenerationType

    /**
     * An enum for a specific group will be created, including a mapper to get the actual value from the semantic tokens
     * @param groupName the JSON group name that will be converted to enum
     * @param classSuffix the suffix that will be added to the [groupName] to create the kotlin class name
     * @param packageName the package of the created enum
     */
    data class EnumGroup(
        val groupName: String,
        val classSuffix: String,
        val packageName: String,
    ) : TokenGenerationType
}