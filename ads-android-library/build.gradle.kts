plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id ("maven-publish")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android") version "2.48.1"
}

android {
    namespace = "com.nhatnguyenba.ads"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
}

dependencies {

    // AdMob
    implementation (libs.play.services.ads)

    // Facebook Mediation
    implementation (libs.audience.network.sdk)

    // Jetpack Compose
    implementation (libs.ui)
    implementation (libs.material3)

    // Hilt
    implementation(libs.hilt.android.v248) // Phiên bản mới nhất
    kapt(libs.hilt.android.compiler.v248)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "com.nhatnguyenba.ads"
            artifactId = "ads-library"
            version = "1.0.0"

            // Đường dẫn đến file AAR
            artifact(file("${layout.buildDirectory}/outputs/aar/ads-library-release.aar"))
        }
    }
}