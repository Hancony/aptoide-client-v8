package cm.aptoide.pt.feature_apps.data

import cm.aptoide.pt.feature_apps.domain.Bundle
import kotlinx.coroutines.flow.Flow

interface BundlesRepository {

  fun getHomeBundles(): Flow<BundlesResult>

}

sealed interface BundlesResult {
  data class Success(val data: List<Bundle>) : BundlesResult
  data class Error(val e: Throwable) : BundlesResult
}