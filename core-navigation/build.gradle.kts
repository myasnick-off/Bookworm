plugins {
    id(PluginIds.ANDROID_LIBRARY)
    id(PluginIds.KOTLIN_ANDROID)
    id(PluginIds.KOTLIN_KAPT)
}

android {
    compileSdk = AppVersions.COMPILE_SDK_VERSION
}

dependencies {
    implementation(project(":core-di"))

    implementation(Dependencies.KTX_CORE)
    implementation(Dependencies.APPCOMPAT)
    implementation(Dependencies.ANDROID_MATERIAL)

    testImplementation(Dependencies.JUNIT)
    androidTestImplementation(Dependencies.JUNIT_EXT)
    androidTestImplementation(Dependencies.ESPRESSO)

    //Lifecycle and ViewModel
    implementation(Dependencies.LIVECYCLE_LIVEDATA)
    implementation(Dependencies.LIVECYCLE_VIEWMODEL)

    // Dagger 2
    implementation(Dependencies.DAGGER)
    kapt(Dependencies.DAGGER_COMPILER)

    // Navigation Component
    implementation(Dependencies.NAV_FRAGMENT)
    implementation(Dependencies.NAV_UI)
}