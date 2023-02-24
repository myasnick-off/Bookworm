plugins {
    id("feature-module")
    id("org.jetbrains.kotlin.plugin.serialization").version(PluginVersions.KOTLIN_SERIALIZATION_VERSION)
    id("androidx.navigation.safeargs.kotlin")
}

dependencies {
    implementation(project(":core"))
    implementation(project(":core-di"))
    implementation(project(":core-network"))
    implementation(project(":core-navigation"))
    implementation(project(":core-ui"))
    implementation(project(":feature-tabs-api"))

    // Retrofit
    implementation(Dependencies.RETROFIT)
    implementation(Dependencies.RETROFIT_COROUTINES)
    implementation(Dependencies.RETROFIT_KOTLINX_SERIALIZATION)

    // Glide
    implementation(Dependencies.GLIDE)

    // Coroutines
    implementation(Dependencies.COROUTINES)

    // Room
    implementation(Dependencies.ROOM)
    implementation(Dependencies.ROOM_KTX)
    kapt(Dependencies.ROOM_COMPILER)
}