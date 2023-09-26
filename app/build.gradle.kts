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
    id ("kotlin-parcelize")
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
    namespace = "com.truesparrow.sales"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.truesparrow.sales"
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
            versionCode = 4
            versionName = "1.0.0"
            resValue("string", "app_name", "SalesSparrow")
            buildConfigField(
                "String",
                "IS_MOCK",
                "\"${
                    getPropertyValueFromPropertiesFile(
                        file("prod.properties"),
                        "IS_MOCK"
                    )
                }\""
            )
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
            buildConfigField(
                "String",
                "SALESFORCE_LOGIN_URL",
                "\"${
                    getPropertyValueFromPropertiesFile(
                        file("prod.properties"),
                        "SALESFORCE_LOGIN_URL"
                    )
                }\""
            )
            buildConfigField(
                "String",
                "REDIRECT_URI",
                "\"${
                    getPropertyValueFromPropertiesFile(
                        file("prod.properties"),
                        "REDIRECT_URI"
                    )
                }\""
            )
        }
        create("staging") {
            applicationIdSuffix = ".dev"
            versionCode = 10
            versionName = "1.0.0"
            resValue("string", "app_name", "Sales-Dev")
            buildConfigField(
                "String",
                "IS_MOCK",
                "\"${
                    getPropertyValueFromPropertiesFile(
                        file("staging.properties"),
                        "IS_MOCK"
                    )
                }\""
            )
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
            buildConfigField(
                "String",
                "SALESFORCE_LOGIN_URL",
                "\"${
                    getPropertyValueFromPropertiesFile(
                        file("staging.properties"),
                        "SALESFORCE_LOGIN_URL"
                    )
                }\""
            )
            buildConfigField(
                "String",
                "REDIRECT_URI",
                "\"${
                    getPropertyValueFromPropertiesFile(
                        file("staging.properties"),
                        "REDIRECT_URI"
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
    val room_version = "2.5.2"

    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation(platform("androidx.compose:compose-bom:2023.06.01"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3:1.1.1")
    implementation("androidx.compose.material:material:$1.4.3")
    implementation("androidx.compose.runtime:runtime:1.1.1")
    implementation("androidx.compose.runtime:runtime-livedata:1.5.0")

    //Firebase Library
    implementation(platform("com.google.firebase:firebase-bom:32.2.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-crashlytics-ktx")
    implementation("com.google.firebase:firebase-messaging-ktx")

    // Navigation
    implementation("androidx.navigation:navigation-compose:$nav_version")

    //Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    //Dagger Hilt
    implementation("com.google.dagger:hilt-android:2.47")
    kapt("com.google.dagger:hilt-android-compiler:2.47")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0-alpha01")

    //Network Calls Library
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("com.squareup.okhttp3:okhttp:4.10.0")
    implementation("androidx.core:core-ktx:1.10.1")
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")


    //Dagger - Hilt
    implementation("com.google.dagger:hilt-android:2.47")
    implementation("androidx.fragment:fragment-ktx:1.4.0")
    implementation("com.google.devtools.ksp:symbol-processing-api:1.9.0-1.0.11")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
    implementation("androidx.core:core-ktx:1.10.1")

    //Room Database
    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")
    kapt("androidx.room:room-compiler:$room_version")


    //Test Libraries
    testImplementation("com.squareup.okhttp3:mockwebserver:4.10.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.06.01"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    implementation("io.coil-kt:coil-compose:2.2.2")
    implementation("io.coil-kt:coil-gif:2.2.2")
}

kapt {
    correctErrorTypes = true
}