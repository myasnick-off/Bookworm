package plugin

import org.gradle.api.Project

class FeatureApiModulePlugin : BaseModulePlugin() {

    override fun apply(project: Project) {
        project.applyPlugins()
        super.apply(project)
    }

    private fun Project.applyPlugins() {
        plugins.run {
            apply("com.android.library")
        }
    }
}
