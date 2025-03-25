pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven {
            url =
                uri("${System.getenv("ARTIFACTORY_BASE_URL")}/artifactory/mega-gradle/megagradle")
        }
    }
    resolutionStrategy {
        eachPlugin {
            when (requested.id.id) {
                "mega.android.library.jacoco.convention",
                "mega.artifactory.publish.convention" -> useModule("mega.privacy:megagradle:${requested.version}")

                else -> {}
            }
        }
    }
}
rootProject.name = "Core UI"
include(":app")
include(":core-ui")
include(":core-ui-tokens")

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

