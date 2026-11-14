plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "es.dam.gastrovote.rodriguezmorales"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "es.dam.gastrovote.rodriguezmorales"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.activity.ktx)
    implementation(libs.appcompat)
    implementation(libs.constraintlayout)
    implementation(libs.material)
}
