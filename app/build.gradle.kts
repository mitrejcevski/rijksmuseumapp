plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.kotlin.compose)
  alias(libs.plugins.kotlin.serialization)
}

android {
  namespace = "nl.jovmit.rmapp"
  compileSdk = libs.versions.targetSdkVersion.get().toInt()

  defaultConfig {
    applicationId = "nl.jovmit.rmapp"
    minSdk = libs.versions.minSdkVersion.get().toInt()
    targetSdk = libs.versions.targetSdkVersion.get().toInt()
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }

  buildFeatures {
    compose = true
  }

  compileOptions {
    val javaVersion = libs.versions.javaVersion.get()
    sourceCompatibility = JavaVersion.toVersion(javaVersion)
    targetCompatibility = JavaVersion.toVersion(javaVersion)
  }

  kotlinOptions {
    jvmTarget = libs.versions.javaVersion.get()
  }

  testOptions.unitTests {
    isReturnDefaultValues = true
    all { tests ->
      tests.useJUnitPlatform()
    }
  }
}

dependencies {
  implementation(platform(libs.androidx.compose.bom))
  implementation(libs.bundles.androidx)
  implementation(libs.bundles.compose)
  implementation(libs.bundles.retrofit)
  implementation(libs.bundles.koin)
  implementation(libs.bundles.navigation)

  debugImplementation(libs.bundles.compose.debug)

  androidTestImplementation(platform(libs.androidx.compose.bom))
  androidTestImplementation(libs.bundles.ui.test)

  testImplementation(libs.bundles.unit.test)

  testRuntimeOnly(libs.junit.jupiter.engine)
}