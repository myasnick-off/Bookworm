plugins {
    id(PluginIds.ANDROID_LIBRARY)
    id(PluginIds.KOTLIN_ANDROID)
}

android {
    compileSdk = AppVersions.COMPILE_SDK_VERSION

    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(project(":core"))
    implementation(project(":core-navigation"))
    implementation(project(":core-ui"))

    implementation(Dependencies.KTX_CORE)
    implementation(Dependencies.APPCOMPAT)
    implementation(Dependencies.ANDROID_MATERIAL)

    // test
    testImplementation(Dependencies.JUNIT)
    androidTestImplementation(Dependencies.JUNIT_EXT)
    androidTestImplementation(Dependencies.ESPRESSO)

    // view
    implementation(Dependencies.FRAGMENT_KTX)
    implementation(Dependencies.CONSTRAINT_LAYOUT)

    // Navigation Component
    implementation(Dependencies.NAV_FRAGMENT)
    implementation(Dependencies.NAV_UI)
}