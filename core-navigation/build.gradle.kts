plugins {
    id("core-module")
}

dependencies {
    implementation(project(":core-di"))

    implementation(Dependencies.ANDROID_MATERIAL)
    
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