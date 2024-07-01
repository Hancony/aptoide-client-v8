package cm.aptoide.pt.feature_mmp.apkfy.domain

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import cm.aptoide.pt.feature_mmp.MMPPreferencesRepository
import cm.aptoide.pt.feature_mmp.apkfy.repository.ApkfyRepository
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "mmp")

class ApkfyManager @Inject constructor(
  private val apkfyRepository: ApkfyRepository,
  private val mmpPreferencesRepository: MMPPreferencesRepository,
) {

  suspend fun getApkfy(): ApkfyModel? = if (mmpPreferencesRepository.shouldRunApkfy()) {
    mmpPreferencesRepository.setApkfyRan()
    apkfyRepository.getApkfy()
  } else {
    null
  }
}