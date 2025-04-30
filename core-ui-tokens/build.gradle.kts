plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    alias(libs.plugins.mega.artifactory.publish.convention)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "mega.android.core.ui.tokens"
    compileSdk = 34

    defaultConfig {
        minSdk = 26
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    artifactId = "ui-tokens"
    version = libVersion
    libPath = "${layout.buildDirectory.get()}/outputs/aar/${project.name}-release.aar"
    sourcePath = "${layout.buildDirectory.get()}/libs/${project.name}-sources.jar"
    properties = mapOf(
        "commit" to commit,
        "builder" to builder,
    )
    dependentTasks = listOf("assembleRelease", "releaseSourcesJar")
}

dependencies {
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose.bom)
    implementation(libs.coil.compose)
    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.constraintlayout.compose)
    implementation(libs.google.font)
    implementation(libs.google.gson)
    implementation(libs.kotlinpoet)
    testImplementation(libs.junit)
    androidTestImplementation(libs.junit.test.ext)
    androidTestImplementation(libs.espresso.core)
}