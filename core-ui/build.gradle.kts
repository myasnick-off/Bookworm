plugins {
    id(PluginIds.ANDROID_LIBRARY)
    id(PluginIds.KOTLIN_ANDROID)
    id(PluginIds.KOTLIN_KAPT)
    id(PluginIds.KOTLIN_PARCELIZE)
}

android {
    compileSdk = AppVersions.COMPILE_SDK_VERSION

    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(Dependencies.KTX_CORE)
    implementation(Dependencies.APPCOMPAT)
    implementation(Dependencies.ANDROID_MATERIAL)

    testImplementation(Dependencies.JUNIT)
    androidTestImplementation(Dependencies.JUNIT_EXT)
    androidTestImplementation(Dependencies.ESPRESSO)

    // view
    implementation(Dependencies.FRAGMENT_KTX)
    implementation(Dependencies.CONSTRAINT_LAYOUT)
}