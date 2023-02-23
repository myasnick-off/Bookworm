plugins {
    id(PluginIds.ANDROID_LIBRARY)
    id(PluginIds.KOTLIN_ANDROID)
    id(PluginIds.KOTLIN_KAPT)
    id(PluginIds.KOTLIN_PARCELIZE)
    id(PluginIds.KOTLIN_SERIALIZATION).version(PluginIds.KOTLIN_SERIALIZATION_VERSION)
}

android {
    compileSdk = AppVersions.COMPILE_SDK_VERSION
}

dependencies {
    implementation(project(":core-di"))

    implementation(Dependencies.KTX_CORE)
    implementation(Dependencies.APPCOMPAT)

    testImplementation(Dependencies.JUNIT)
    androidTestImplementation(Dependencies.JUNIT_EXT)
    androidTestImplementation(Dependencies.ESPRESSO)

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