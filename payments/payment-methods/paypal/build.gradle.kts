plugins {
  id(GradlePluginId.ANDROID_LIBRARY)
  id(GradlePluginId.ANDROID_MODULE)
  id(GradlePluginId.HILT)
}

android {
  namespace = "com.appcoins.payment_method.paypal"

  buildFeatures {
    buildConfig = true
  }
}

dependencies {
  api(project(ModuleDependency.MAGNES))
  api(project(ModuleDependency.PAYMENT_MANAGER))
  implementation(project(ModuleDependency.PAYMENTS_NETWORK))
  implementation(LibraryDependency.ACCOMPANIST_WEBVIEW)
}