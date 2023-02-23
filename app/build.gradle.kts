plugins {
    id(PluginIds.ANDROID_APPLICATION)
    id(PluginIds.KOTLIN_ANDROID)
    id(PluginIds.KOTLIN_KAPT)
    id(PluginIds.KOTLIN_PARCELIZE)
    id(PluginIds.KOTLIN_SERIALIZATION).version(PluginIds.KOTLIN_SERIALIZATION_VERSION)
    id(PluginIds.NAVIGATION_SAVEARGS)
}

android {
    compileSdk = AppVersions.COMPILE_SDK_VERSION

    defaultConfig {
        applicationId = AppVersions.APP_ID
        minSdk = AppVersions.MIN_SDK_VERSION
        targetSdk = AppVersions.TARGET_SDK_VERSION
        versionCode = AppVersions.VERSION_CODE
        versionName = AppVersions.VERSION_NAME

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":core-di"))
    implementation(project(":core-navigation"))
    implementation(project(":core-ui"))
    implementation(project(":core-network"))
    implementation(project(":feature-tabs"))
    implementation(project(":feature-tabs-api"))
    implementation(project(":feature-auth"))
    implementation(project(":feature-auth-api"))
    implementation(project(":feature-splash"))

    implementation(Dependencies.KTX_CORE)
    implementation(Dependencies.APPCOMPAT)
    implementation(Dependencies.ANDROID_MATERIAL)

    testImplementation(Dependencies.JUNIT)
    androidTestImplementation(Dependencies.JUNIT_EXT)
    androidTestImplementation(Dependencies.ESPRESSO)

    // view
    implementation(Dependencies.FRAGMENT_KTX)
    implementation(Dependencies.CONSTRAINT_LAYOUT)

    // Retrofit
    implementation(Dependencies.RETROFIT)
    implementation(Dependencies.RETROFIT_COROUTINES)
    implementation(Dependencies.RETROFIT_KOTLINX_SERIALIZATION)

    // kotlinx-serialization
    implementation(Dependencies.KOTLINX_SERIALIZATION)

    // logging
    implementation(Dependencies.LOGGING_INTERCEPTOR)

    //Lifecycle and ViewModel
    implementation(Dependencies.LIVECYCLE_LIVEDATA)
    implementation(Dependencies.LIVECYCLE_VIEWMODEL)

    // Coroutines
    implementation(Dependencies.COROUTINES)

    // Dagger 2
    implementation(Dependencies.DAGGER)
    kapt(Dependencies.DAGGER_COMPILER)

    // Navigation Component
    implementation(Dependencies.NAV_FRAGMENT)
    implementation(Dependencies.NAV_UI)
}