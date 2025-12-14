plugins {
    id("com.android.library")
}

android {
    namespace = "com.cyhh1029.hidden_api"
    compileSdk = 36

    defaultConfig {
        minSdk = 23
    }

    compileOptions {
        targetCompatibility = JavaVersion.VERSION_21
        sourceCompatibility = JavaVersion.VERSION_21
    }
}

dependencies {
}