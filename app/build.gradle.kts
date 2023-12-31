plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id("kotlin-kapt")
}

android {
    namespace = "com.example.madcamp_week01"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.madcamp_week01"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.appcompat:appcompat:1.4.0")
    implementation("de.hdodenhof:circleimageview:3.1.0")

    implementation("androidx.room:room-runtime:2.2.6")
    kapt("androidx.room:room-compiler:2.2.6")
    implementation("androidx.room:room-ktx:2.2.6")
    implementation("androidx.room:room-rxjava2:2.2.6")
    implementation("androidx.room:room-guava:2.2.6")
    kapt("org.xerial:sqlite-jdbc:3.34.0")
    androidTestImplementation("androidx.room:room-testing:2.2.6")
    implementation("androidx.lifecycle:lifecycle-viewmodel:2.4.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.2.0")
    kapt ("androidx.lifecycle:lifecycle-compiler:2.2.0")
    // UI
    implementation ("com.google.android.material:material:1.1.0")
    // Testng
    androidTestImplementation("androidx.arch.core:core-testing:2.1.0")
    implementation("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.12.0")
    implementation("jp.wasabeef:glide-transformations:4.3.0")
    implementation("jp.wasabeef:glide-transformations:4.3.0")
    
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("com.github.bumptech.glide:glide:4.11.0")
    implementation("androidx.core:core-splashscreen:1.0.1")
}

