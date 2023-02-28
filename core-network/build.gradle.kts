plugins {
    id("core-module")
    id("kotlin-parcelize")
    id("org.jetbrains.kotlin.plugin.serialization").version(PluginVersions.KOTLIN_SERIALIZATION_VERSION)
}

dependencies {
    implementation(project(":core-di"))

    // Retrofit
    implementation(Dependencies.RETROFIT)
    implementation(Dependencies.RETROFIT_COROUTINES)
    implementation(Dependencies.RETROFIT_KOTLINX_SERIALIZATION)

    // kotlinx-serialization
    implementation(Dependencies.KOTLINX_SERIALIZATION)

    // logging
    implementation(Dependencies.LOGGING_INTERCEPTOR)

    // Coroutines
    implementation(Dependencies.COROUTINES)

    // Dagger 2
    implementation(Dependencies.DAGGER)
    kapt(Dependencies.DAGGER_COMPILER)
}