plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation(gradleApi())
    implementation(kotlin("android-extensions"))
    implementation(kotlin("gradle-plugin", "1.7.10"))
    implementation("com.android.tools.build:gradle:7.2.2")
}

gradlePlugin {
    plugins {
        register("kotlin-android-module") {
            id = "kotlin-android-module"
            description = "Gradle plugin for android kotlin module."
            implementationClass = "plugin.AndroidModulePlugin"
        }
        register("core-module") {
            id = "core-module"
            description = "Gradle plugin for core modules"
            implementationClass = "plugin.CoreModulePlugin"
        }
        register("feature-module") {
            id = "feature-module"
            description = "Gradle plugin for feature modules"
            implementationClass = "plugin.FeatureModulePlugin"
        }
        register("feature-api-module") {
            id = "feature-api-module"
            description = "Gradle plugin for feature-api modules"
            implementationClass = "plugin.FeatureApiModulePlugin"
        }
    }
}
