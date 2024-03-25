package cm.aptoide.pt.app_games

import android.app.Application
import android.content.Context
import android.preference.PreferenceManager
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import cm.aptoide.pt.install_manager.InstallManager
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

val Context.userPreferencesDataStore: DataStore<Preferences> by preferencesDataStore(
  name = "userPreferences",
  produceMigrations = { context ->
    listOf(SharedPreferencesMigration({ PreferenceManager.getDefaultSharedPreferences(context) }))
  }
)

val Context.userFeatureFlagsDataStore: DataStore<Preferences> by preferencesDataStore(name = "userFeatureFlags")

val Context.themeDataStore: DataStore<Preferences> by preferencesDataStore(name = "themePreferences")

@HiltAndroidApp
class AptoideApplication : Application() {

  @Inject
  lateinit var installManager: InstallManager

  override fun onCreate() {
    super.onCreate()
    initTimber()
    startInstallManager()
  }

  private fun startInstallManager() {
    CoroutineScope(Dispatchers.IO).launch {
      try {
        installManager.restore()
      } catch (e: Exception) {
        Timber.e(e)
        e.printStackTrace()
      }
    }
  }

  private fun initTimber() {
    if (BuildConfig.DEBUG) {
      Timber.plant(Timber.DebugTree())
    }
  }
}
