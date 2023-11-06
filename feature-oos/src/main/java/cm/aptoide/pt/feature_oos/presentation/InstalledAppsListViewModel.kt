package cm.aptoide.pt.feature_oos.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cm.aptoide.pt.feature_oos.domain.InstalledAppsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class InstalledAppsListViewModel @Inject constructor(
  private val installedAppsUseCase: InstalledAppsUseCase,
  private val filterPackages: List<String> = emptyList()
) : ViewModel() {

  private val viewModelState = MutableStateFlow<InstalledAppsUiState>(InstalledAppsUiState.Loading)

  val installedAppsState = viewModelState
    .stateIn(
      viewModelScope,
      SharingStarted.Eagerly,
      viewModelState.value
    )

  init {
    viewModelScope.launch {
      val installedApps = installedAppsUseCase.getInstalledApps(filterPackages)
      viewModelState.update { InstalledAppsUiState.Idle(installedApps) }
    }
  }
}