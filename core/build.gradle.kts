plugins {
    id(PluginIds.ANDROID_LIBRARY)
    id(PluginIds.KOTLIN_ANDROID)
    id(PluginIds.KOTLIN_KAPT)
    id(PluginIds.KOTLIN_PARCELIZE)
}

android {
    compileSdk = AppVersions.COMPILE_SDK_VERSION
}

dependencies {
    implementation(project(":core-ui"))

    implementation(Dependencies.KTX_CORE)
    implementation(Dependencies.APPCOMPAT)
    implementation(Dependencies.ANDROID_MATERIAL)

    testImplementation(Dependencies.JUNIT)
    androidTestImplementation(Dependencies.JUNIT_EXT)
    androidTestImplementation(Dependencies.ESPRESSO)

    // Navigation Component
    implementation(Dependencies.NAV_FRAGMENT)
    implementation(Dependencies.NAV_UI)
}