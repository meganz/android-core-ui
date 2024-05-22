pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
rootProject.name = "Core UI"
include(":app")
include(":core-ui")
include(":core-ui-tokens")
include(":example:temp-theme")

dependencyResolutionManagement {
    versionCatalogs {
        for (file in fileTree("./gradle/catalogs").matching { include("**/*.toml") }) {
            val name = file.name.split(".")[0]
            create(name) {
                from(files(file))
            }
        }
    }
}

