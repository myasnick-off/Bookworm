package plugin

import com.android.build.gradle.BaseExtension
import com.android.build.gradle.AppExtension
import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType

abstract class BaseModulePlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.applyPlugins()
        project.applyLibraryConfig()
        project.setupTasks()
        project.applyDefaultDependencies()
    }

    private fun Project.applyPlugins() {
        plugins.run {
            apply("org.jetbrains.kotlin.android")
        }
    }

    private fun Project.applyLibraryConfig() {
        val androidExtension = extensions.getByName("android")
        (androidExtension as? BaseExtension)?.let { android ->
            android.compileSdkVersion(AppVersions.COMPILE_SDK_VERSION)
            android.defaultConfig {
                minSdk = AppVersions.MIN_SDK_VERSION
                targetSdk = AppVersions.TARGET_SDK_VERSION
                versionCode = AppVersions.VERSION_CODE
                versionName = AppVersions.VERSION_NAME

                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            }
            val proguardFile = "proguard-rules.pro"
            when (android) {
                is LibraryExtension -> android.defaultConfig {
                    consumerProguardFiles(proguardFile)
                }
                is AppExtension -> android.buildTypes {
                    getByName("release") {
                        isMinifyEnabled = false
                        proguardFiles(
                            android.getDefaultProguardFile("proguard-android-optimize.txt"),
                            proguardFile
                        )
                    }
                }
            }
            android.compileOptions {
                sourceCompatibility = JavaVersion.VERSION_1_8
                targetCompatibility = JavaVersion.VERSION_1_8
            }
        }
    }

    private fun Project.setupTasks() {
        tasks.withType<KotlinCompile>().configureEach {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    private fun Project.applyDefaultDependencies() {
        dependencies {
            add("implementation", Dependencies.KTX_CORE)
            add("implementation", Dependencies.APPCOMPAT)

            add("testImplementation", Dependencies.JUNIT)
            add("androidTestImplementation", Dependencies.JUNIT_EXT)
            add("androidTestImplementation", Dependencies.ESPRESSO)
        }
    }
}
