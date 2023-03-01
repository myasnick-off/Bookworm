package plugin

import Dependencies
import com.android.build.gradle.BaseExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class FeatureModulePlugin : BaseModulePlugin() {

    override fun apply(project: Project) {
        project.applyPlugins()
        super.apply(project)
        project.applyLibraryConfig()
        project.applyDefaultDependencies()
    }

    private fun Project.applyPlugins() {
        plugins.run {
            apply("com.android.library")
            apply("kotlin-kapt")
            apply("kotlin-parcelize")
        }
    }

    private fun Project.applyLibraryConfig() {
        val androidExtension = extensions.getByName("android")
        (androidExtension as? BaseExtension)?.let { android ->
            android.buildFeatures.apply {
                viewBinding = true
            }
        }
    }

    private fun Project.applyDefaultDependencies() {
        dependencies {
            // test
            add("testImplementation", Dependencies.MOCKITO)
            add("testImplementation", Dependencies.ARCH_TESTING)
            add("testImplementation", Dependencies.COROUTINES_TESTING)

            // view
            add("implementation", Dependencies.ANDROID_MATERIAL)
            add("implementation", Dependencies.FRAGMENT_KTX)
            add("implementation", Dependencies.CONSTRAINT_LAYOUT)
            add("implementation", Dependencies.SWIPE_REFRESH)

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
