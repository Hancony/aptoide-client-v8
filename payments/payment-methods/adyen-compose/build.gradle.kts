plugins {
  id("com.android.library")
  id("org.jetbrains.kotlin.android")
  id("kotlin-kapt")
  id("dagger.hilt.android.plugin")
}

apply("../../versions.gradle.kts")

android {
  val compileSdkVersion = getVersionFor("compileSdkVersion").toInt()
  val minSdkVersion = getVersionFor("minSdkVersion").toInt()

  namespace = "com.appcoins.payment_method.adyen.presentation"
  compileSdk = compileSdkVersion

  defaultConfig {
    minSdk = minSdkVersion

    consumerProguardFiles("consumer-rules.pro")
  }

  buildTypes {
    release {
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }

  buildFeatures {
    // Enables Jetpack Compose for this module
    compose = true
    buildConfig = true
  }

  composeOptions {
    kotlinCompilerExtensionVersion = getVersionFor("kotlinCompilerExtensionVersion")
  }
}

fun getVersionFor(versionName: String) =
  runCatching { rootProject.extra[versionName] }
    .getOrDefault(project.extra[versionName])
    .toString()

dependencies {
  implementation(project(":payments:base:arch"))
  implementation(project(":payments:base:payment-manager"))
  api(project(":payments:payment-methods:adyen"))

  //Hilt
  val hiltAndroidVersion = getVersionFor("hiltAndroidVersion")
  implementation("com.google.dagger:hilt-android:$hiltAndroidVersion")
  val daggerHiltCompilerVersion = getVersionFor("daggerHiltCompilerVersion")
  kapt("com.google.dagger:hilt-compiler:$daggerHiltCompilerVersion")

  // Compose
  val composeMaterialVersion = getVersionFor("composeMaterialVersion")
  implementation("androidx.compose.material:material:$composeMaterialVersion")
  val lifecycleViewModelComposeVersion = getVersionFor("lifecycleViewModelComposeVersion")
  implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycleViewModelComposeVersion")

  // Compose navigation
  val navigationComposeVersion = getVersionFor("navigationComposeVersion")
  implementation("androidx.navigation:navigation-compose:$navigationComposeVersion")
  val hiltNavigationComposeVersion = getVersionFor("hiltNavigationComposeVersion")
  implementation("androidx.hilt:hilt-navigation-compose:$hiltNavigationComposeVersion")
}
