plugins {
    id(PluginIds.ANDROID_LIBRARY)
    id(PluginIds.KOTLIN_ANDROID)
    id(PluginIds.KOTLIN_KAPT)
    id(PluginIds.KOTLIN_PARCELIZE)
    id(PluginIds.KOTLIN_SERIALIZATION).version(PluginIds.KOTLIN_SERIALIZATION_VERSION)
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
    implementation(project(":core-network"))
    implementation(project(":core-navigation"))
    implementation(project(":core-ui"))
    implementation(project(":feature-tabs-api"))

    implementation(Dependencies.KTX_CORE)
    implementation(Dependencies.APPCOMPAT)
    implementation(Dependencies.ANDROID_MATERIAL)

    // test
    testImplementation(Dependencies.JUNIT)
    androidTestImplementation(Dependencies.JUNIT_EXT)
    androidTestImplementation(Dependencies.ESPRESSO)
    testImplementation(Dependencies.MOCKITO)
    testImplementation(Dependencies.ARCH_TESTING)
    testImplementation(Dependencies.COROUTINES_TESTING)

    // view
    implementation(Dependencies.FRAGMENT_KTX)
    implementation(Dependencies.CONSTRAINT_LAYOUT)

    // Retrofit
    implementation(Dependencies.RETROFIT)
    implementation(Dependencies.RETROFIT_COROUTINES)
    implementation(Dependencies.RETROFIT_KOTLINX_SERIALIZATION)

    // Glide
    implementation(Dependencies.GLIDE)

    //Lifecycle and ViewModel
    implementation(Dependencies.LIVECYCLE_LIVEDATA)
    implementation(Dependencies.LIVECYCLE_VIEWMODEL)

    // Coroutines
    implementation(Dependencies.COROUTINES)

    // Room
    implementation(Dependencies.ROOM)
    implementation(Dependencies.ROOM_KTX)
    kapt(Dependencies.ROOM_COMPILER)

    // Dagger 2
    implementation(Dependencies.DAGGER)
    kapt(Dependencies.DAGGER_COMPILER)

    // Navigation Component
    implementation(Dependencies.NAV_FRAGMENT)
    implementation(Dependencies.NAV_UI)
}