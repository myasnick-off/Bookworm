plugins {
    id("kotlin-android-module")
    id("org.jetbrains.kotlin.plugin.serialization").version("1.7.20")
    id("androidx.navigation.safeargs.kotlin")
}

dependencies {
    implementation(project(":core"))
    implementation(project(":core-di"))
    implementation(project(":core-navigation"))
    implementation(project(":core-ui"))
    implementation(project(":core-network"))
    implementation(project(":feature-tabs"))
    implementation(project(":feature-tabs-api"))
    implementation(project(":feature-auth"))
    implementation(project(":feature-auth-api"))
    implementation(project(":feature-splash"))

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
}