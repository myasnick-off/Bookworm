plugins {
    id("feature-module")
    id("androidx.navigation.safeargs.kotlin")
}

dependencies {

    implementation(project(":core"))
    implementation(project(":core-di"))
    implementation(project(":core-navigation"))
    implementation(project(":core-ui"))
    implementation(project(":feature-auth-api"))

    // Retrofit
    implementation(Dependencies.RETROFIT)

    // RxJava
    implementation(Dependencies.RXJAVA)
    implementation(Dependencies.RX_ANDROID)
    implementation(Dependencies.RX_BINDING)
}