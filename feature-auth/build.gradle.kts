plugins {
    id(PluginIds.ANDROID_LIBRARY)
    id(PluginIds.KOTLIN_ANDROID)
    id(PluginIds.KOTLIN_KAPT)
    id(PluginIds.KOTLIN_PARCELIZE)
    id(PluginIds.NAVIGATION_SAVEARGS)
}

android {
    compileSdk = AppVersions.COMPILE_SDK_VERSION

    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(project(":core"))
    implementation(project(":core-di"))
    implementation(project(":core-navigation"))
    implementation(project(":core-ui"))
    implementation(project(":feature-auth-api"))

    implementation(Dependencies.KTX_CORE)
    implementation(Dependencies.APPCOMPAT)
    implementation(Dependencies.ANDROID_MATERIAL)

    // view
    implementation(Dependencies.FRAGMENT_KTX)
    implementation(Dependencies.CONSTRAINT_LAYOUT)

    // test
    testImplementation(Dependencies.JUNIT)
    androidTestImplementation(Dependencies.JUNIT_EXT)
    androidTestImplementation(Dependencies.ESPRESSO)
    testImplementation(Dependencies.MOCKITO)
    testImplementation(Dependencies.ARCH_TESTING)

    //Lifecycle and ViewModel
    implementation(Dependencies.LIVECYCLE_LIVEDATA)
    implementation(Dependencies.LIVECYCLE_VIEWMODEL)

    // Retrofit
    implementation(Dependencies.RETROFIT)

    // RxJava
    implementation(Dependencies.RXJAVA)
    implementation(Dependencies.RX_ANDROID)
    implementation(Dependencies.RX_BINDING)

    // Dagger 2
    implementation(Dependencies.DAGGER)
    kapt(Dependencies.DAGGER_COMPILER)

    // Navigation Component
    implementation(Dependencies.NAV_FRAGMENT)
    implementation(Dependencies.NAV_UI)
}