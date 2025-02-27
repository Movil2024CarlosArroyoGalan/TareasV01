plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.jetbrainsKotlinSerialization)
    //Room que vamos a usar para la base de datos
    id("com.google.devtools.ksp") version "1.9.20-1.0.14"
}

android {
    namespace = "net.iessochoa.carlosarroyogalan.tareasv01"
    compileSdk = 35

    defaultConfig {
        applicationId = "net.iessochoa.carlosarroyogalan.tareasv01"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    //Implementacion de Viewmodel lifecycle
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.6")
    //Navigation
    implementation("androidx.navigation:navigation-compose:2.8.3")
// ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.6")
//Serializacion
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json: 1.6.3")
    //Corrutinas
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core: 1.8.1")

    implementation("androidx.room:room-runtime:2.6.1")
    annotationProcessor("androidx.room:room-compiler:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    //coil, librería para cargar imágenes desde internet
    implementation("io.coil-kt.coil3:coil-compose:3.0.4")
    implementation("io.coil-kt.coil3:coil-network-okhttp:3.0.4")

    implementation ("com.google.accompanist:accompanist-permissions:0.36.0")
}