package plugin

import AppVersions
import Dependencies
import com.android.build.gradle.BaseExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

open class AndroidModulePlugin : BaseModulePlugin() {

    override fun apply(project: Project) {
        project.applyPlugins()
        super.apply(project)
        project.applyLibraryConfig()
        project.applyDefaultDependencies()
    }

    private fun Project.applyPlugins() {
        plugins.run {
            apply("com.android.application")
            apply("kotlin-kapt")
            apply("kotlin-parcelize")
        }
    }

    private fun Project.applyLibraryConfig() {
        val androidExtension = extensions.getByName("android")
        (androidExtension as? BaseExtension)?.let { android ->
            android.defaultConfig {
                applicationId = AppVersions.APP_ID
            }
            android.buildFeatures.apply {
                viewBinding = true
            }
        }
    }

    private fun Project.applyDefaultDependencies() {
        dependencies {
            // view
            add("implementation", Dependencies.ANDROID_MATERIAL)
            add("implementation", Dependencies.FRAGMENT_KTX)
            add("implementation", Dependencies.CONSTRAINT_LAYOUT)

            //Lifecycle and ViewModel
            add("implementation", Dependencies.LIVECYCLE_LIVEDATA)
            add("implementation", Dependencies.LIVECYCLE_VIEWMODEL)

            // Dagger 2
            add("implementation", Dependencies.DAGGER)
            add("kapt", Dependencies.DAGGER_COMPILER)

            // Navigation Component
            add("implementation", Dependencies.NAV_FRAGMENT)
            add("implementation", Dependencies.NAV_UI)
        }
    }
}
