plugins {
  id(GradlePluginId.ANDROID_LIBRARY)
  id(GradlePluginId.KOTLIN_ANDROID)
  id(GradlePluginId.KOTLIN_KAPT)
  id(GradlePluginId.KOTLIN_KSP) version GradlePluginVersion.KSP
  id(GradlePluginId.HILT_PLUGIN)
}

android {
  compileSdk = AndroidConfig.COMPILE_SDK

  defaultConfig {
    minSdk = AndroidConfig.MIN_SDK
    targetSdk = AndroidConfig.TARGET_SDK
  }

  buildTypes {
    getByName(BuildType.RELEASE) {
      isMinifyEnabled = BuildTypeRelease.isMinifyEnabled
      proguardFiles("proguard-android.txt", "proguard-rules.pro")
    }
    getByName(BuildType.DEBUG) {
      isMinifyEnabled = BuildTypeDebug.isMinifyEnabled
    }
  }
  compileOptions {
    sourceCompatibility = JavaLibrary.SOURCE_COMPATIBILITY_JAVA_VERSION
    targetCompatibility = JavaLibrary.TARGET_COMPATIBILITY_JAVA_VERSION
  }

  composeOptions {
    kotlinCompilerExtensionVersion = CoreVersion.KT_COMPILER_EXTENSION
  }
  namespace = "cm.aptoide.pt.downloads_database"
}

dependencies {
  implementation(project(ModuleDependency.INSTALL_MANAGER))
  implementation(LibraryDependency.KOTLIN)

  //di
  implementation(LibraryDependency.HILT)
  kapt(LibraryDependency.HILT_COMPILER)

  //room
  implementation(LibraryDependency.ROOM)
  ksp(LibraryDependency.ROOM_COMPILER)
  implementation(LibraryDependency.ROOM_KTX)
  androidTestImplementation(TestLibraryDependency.ROOM_TESTING)
}