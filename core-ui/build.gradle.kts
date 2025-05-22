import mega.privacy.megagradle.plugin.extension.Dependency

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    alias(libs.plugins.mega.artifactory.publish.convention)
    alias(libs.plugins.mega.android.library.jacoco.convention)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "mega.android.core.ui"
    compileSdk = 35

    defaultConfig {
        minSdk = 26
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    val jdk: String by rootProject.extra
    val javaVersion: JavaVersion by rootProject.extra

    compileOptions {
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }
    kotlinOptions {
        jvmTarget = jdk
    }
}


val libVersion: String by rootProject.extra
val commit: String by rootProject.extra
val mavenRepoKey: String by rootProject.extra
val mavenGroupId: String by rootProject.extra
val builder: String by rootProject.extra

megaPublish {
    repoKey = mavenRepoKey
    groupId = mavenGroupId
    artifactId = "ui"
    version = libVersion
    libPath = "${layout.buildDirectory.get()}/outputs/aar/${project.name}-release.aar"
    sourcePath = "${layout.buildDirectory.get()}/libs/${project.name}-sources.jar"
    properties = mapOf(
        "commit" to commit,
        "builder" to builder,
    )
    dependentTasks = listOf("assembleRelease", "releaseSourcesJar")
    // Add runtime dependencies in the Maven POM to ensure the dependent libraries are available to the client app at runtime, while remaining invisible at compile time.
    dependencies = listOf(
        Dependency(
            groupId = mavenGroupId,
            artifactId = "ui-tokens",
            version = libs.versions.mega.core.ui.tokens.get(),
            scope = "runtime"
        ),
        Dependency(
            groupId = "sh.calvin.reorderable",
            artifactId = "reorderable",
            version = libs.versions.reorderable.get(),
            scope = "runtime"
        ),
    )
}

dependencies {
    implementation(project(":core-ui-tokens"))
    implementation(libs.androidx.material3.window)
    implementation(libs.material3)
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose.bom)
    implementation(libs.coil.compose)
    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.constraintlayout.compose)
    implementation(libs.kotlinx.collections.immutable)
    implementation(libs.google.font)
    testImplementation(libs.compose.junit)
    debugImplementation(libs.compose.manifest)
    debugImplementation(libs.roboelectric)
    testImplementation(libs.junit)
    androidTestImplementation(libs.junit.test.ext)
    androidTestImplementation(libs.espresso.core)
    testImplementation(libs.mockito)
    testImplementation(libs.mockito.kotlin)
    implementation(libs.reorderable)
}
