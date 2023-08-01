import java.io.FileInputStream
import java.util.*
import java.io.File

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}


fun getPropertyValueFromPropertiesFile(propertyFile: File, propertyName: String): String {
    try {
        val properties = Properties()
        FileInputStream(propertyFile).use { inputStream ->
            properties.load(inputStream)
        }
        return properties.getProperty(propertyName) ?: error("Property not found: $propertyName")
    } catch (e: Exception) {
        error("Error reading properties file: ${propertyFile.absolutePath}, Reason: ${e.message}")
    }
}

android {
    namespace = "com.example.salessparrow"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.salessparrow"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    flavorDimensions += "version"

    productFlavors {

        create("production") {
            versionCode = 1
            versionName = "1.0"
            buildConfigField(
                "String",
                "BASE_URL",
                "\"${
                    getPropertyValueFromPropertiesFile(
                        file("prod.properties"),
                        "PRODUCTION_API_URL"
                    )
                }\""
            )
        }
        create("staging") {
            applicationIdSuffix = ".staging"
            versionCode = 1
            versionName = "1.0"
            buildConfigField(
                "String",
                "BASE_URL",
                "\"${
                    getPropertyValueFromPropertiesFile(
                        file("staging.properties"),
                        "STAGING_API_URL"
                    )
                }\""
            )
        }
    }


    buildTypes {
        release {
            isMinifyEnabled = false
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = false
            isDebuggable = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    val nav_version = "2.6.0"

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    //Firebase Library
    implementation(platform("com.google.firebase:firebase-bom:32.2.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-crashlytics-ktx")
    implementation("com.google.firebase:firebase-messaging-ktx")

    // Navigation
    implementation("androidx.navigation:navigation-compose:$nav_version")

    //Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9")

    //Dagger Hilt
    implementation("com.google.dagger:hilt-android:2.44")
    kapt("com.google.dagger:hilt-android-compiler:2.44")




    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}

kapt {
    correctErrorTypes = true
}