plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id ("kotlin-kapt")
    id ("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.pokedoxapplication"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.pokedoxapplication"
        minSdk = 24
        targetSdk = 33
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
        kotlinCompilerExtensionVersion = "1.4.3"
    }


    packaging {
        exclude ("META-INF/DEPENDENCIES.txt")
        exclude ("META-INF/LICENSE.txt")
        exclude ("META-INF/NOTICE.txt")
        exclude ("META-INF/NOTICE")
        exclude ("META-INF/LICENSE")
        exclude ("META-INF/DEPENDENCIES")
        exclude ("META-INF/notice.txt")
        exclude ("META-INF/license.txt")
        exclude ("META-INF/dependencies.txt")
        exclude ("META-INF/LGPL2.1")
        exclude ("META-INF/services/org.xmlpull.v1.XmlPullParserFactory")
        exclude ("MANIFEST.MF")
        exclude ("META-INF/LICENSE.md")
        exclude ("META-INF/LICENSE-notice.md")
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    testOptions {
        packagingOptions {
            jniLibs {
                useLegacyPackaging = true
            }
        }
    }
}

dependencies {

    implementation ("androidx.core:core-ktx:1.7.0")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
    implementation ("androidx.activity:activity-compose:1.3.1")
    implementation ("androidx.compose.ui:ui:1.2.0")
    implementation ("androidx.compose.ui:ui-tooling-preview:1.2.0")
    implementation ("androidx.compose.material:material:1.2.0")

    implementation ("androidx.compose.material:material-icons-core:1.3.1")
    implementation ("androidx.compose.material:material-icons-extended:1.3.1")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.3.1")
    implementation ("androidx.navigation:navigation-compose:2.5.3")
    implementation ("androidx.constraintlayout:constraintlayout-compose:1.0.1")

    //Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.10.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")

    //Timber
    implementation ("com.jakewharton.timber:timber:5.0.1")

    //Coroutines
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")

    //Coroutines Lifecycle Scopes
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1")
    //Dagger-Hilt
    implementation ("com.google.dagger:hilt-android:2.44.2")
    implementation("androidx.privacysandbox.tools:tools-core:1.0.0-alpha06")
//    implementation ("com.google.dagger:hilt-compiler:2.37")
 //   implementation ("androidx.hilt:hilt-compiler:1.0.0")

    kapt ("com.google.dagger:hilt-android-compiler:2.44.2")
    kapt ("androidx.hilt:hilt-compiler:1.0.0")

    implementation ("androidx.hilt:hilt-navigation-compose:1.1.0-alpha01")

    //Palette
    implementation ("androidx.palette:palette:1.0.0")

    //Coil
    implementation ("io.coil-kt:coil-compose:2.2.2")

    testImplementation ("junit:junit:4.13.2")
    testImplementation ("org.junit.jupiter:junit-jupiter:5.8.1")
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation ("androidx.compose.ui:ui-test-junit4:1.2.0")
    debugImplementation ("androidx.compose.ui:ui-tooling:1.2.0")
    debugImplementation ("androidx.compose.ui:ui-test-manifest:1.2.0")

    testImplementation ("android.arch.core:core-testing:1.1.1")
    androidTestImplementation ("android.arch.core:core-testing:1.1.1")
    androidTestImplementation ("androidx.navigation:navigation-testing:2.5.3")

    testImplementation ("io.mockk:mockk:1.12.5")
    androidTestImplementation ("io.mockk:mockk:1.12.5")
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.1")
}


