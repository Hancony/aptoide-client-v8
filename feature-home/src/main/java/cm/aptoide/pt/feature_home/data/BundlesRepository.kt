package cm.aptoide.pt.feature_home.data

import cm.aptoide.pt.feature_apps.data.App
import cm.aptoide.pt.feature_home.domain.Bundle
import kotlinx.coroutines.flow.Flow

interface BundlesRepository {

  fun getHomeBundles(): Flow<List<Bundle>>

  fun getHomeBundleActionListApps(bundleTag: String): Flow<List<App>>

}
