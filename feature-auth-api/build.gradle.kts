plugins {
    id(PluginIds.ANDROID_LIBRARY)
    id(PluginIds.KOTLIN_ANDROID)
}

android {
    compileSdk = AppVersions.COMPILE_SDK_VERSION
}

dependencies {

    implementation(Dependencies.KTX_CORE)
    implementation(Dependencies.APPCOMPAT)

    testImplementation(Dependencies.JUNIT)
    androidTestImplementation(Dependencies.JUNIT_EXT)
    androidTestImplementation(Dependencies.ESPRESSO)
}