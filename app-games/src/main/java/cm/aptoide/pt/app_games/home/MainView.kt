package cm.aptoide.pt.app_games.home

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.LocalElevationOverlay
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import cm.aptoide.pt.app_games.appview.appViewScreen
import cm.aptoide.pt.app_games.installer.UserActionDialog
import cm.aptoide.pt.app_games.search.presentation.searchScreen
import cm.aptoide.pt.app_games.settings.settingsScreen
import cm.aptoide.pt.app_games.theme.AppTheme
import cm.aptoide.pt.app_games.theme.AptoideSnackBar
import cm.aptoide.pt.app_games.theme.AptoideTheme
import cm.aptoide.pt.app_games.toolbar.AppGamesToolBar
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainView(navController: NavHostController) {
  val themeViewModel = hiltViewModel<AppThemeViewModel>()
  val isDarkTheme by themeViewModel.uiState.collectAsState()
  val snackBarHostState = remember { SnackbarHostState() }
  val coroutineScope = rememberCoroutineScope()
  val goBackHome: () -> Unit =
    { navController.popBackStack(navController.graph.startDestinationId, false) }

  AptoideTheme(darkTheme = isDarkTheme ?: isSystemInDarkTheme()) {
    Scaffold(
      snackbarHost = {
        SnackbarHost(
          hostState = snackBarHostState,
          snackbar = { AptoideSnackBar(it) }
        )
      },
      bottomBar = {
        BottomNavigation(navController)
      },
      topBar = {
        AppGamesToolBar(navigate = navController::navigate, goBackHome)
      }
    ) { padding ->
      Box(modifier = Modifier.padding(padding)) {
        NavigationGraph(
          navController,
          showSnack = {
            coroutineScope.launch {
              snackBarHostState.showSnackbar(message = it)
            }
          }
        )
      }
    }
    UserActionDialog()
  }
}

@Composable
private fun BottomNavigation(navController: NavHostController) {
  val items = listOf(
    BottomBarMenus.Games,
    BottomBarMenus.Search
  )
  CompositionLocalProvider(LocalElevationOverlay provides null) {
    BottomNavigation(backgroundColor = AppTheme.colors.background) {
      val navBackStackEntry by navController.currentBackStackEntryAsState()
      val currentDestination = navBackStackEntry?.destination
      items.forEach { screen ->
        val selected = currentDestination?.hierarchy?.any { it.route == screen.route }
        BottomNavigationItem(
          icon = { Icon(imageVector = screen.icon, contentDescription = null) },
          selected = selected == true,
          label = {
            Text(
              text = stringResource(id = screen.resourceId),
              color = if (selected == true) AppTheme.colors.primary else AppTheme.colors.unselectedLabelColor
            )
          },
          selectedContentColor = AppTheme.colors.primary,
          unselectedContentColor = AppTheme.colors.unselectedLabelColor,
          alwaysShowLabel = true,
          onClick = {
            navController.navigate(screen.route) {
              // Pop up to the start destination of the graph to
              // avoid building up a large stack of destinations
              // on the back stack as users select items
              popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
              }
              // Avoid multiple copies of the same destination when
              // reselecting the same item
              launchSingleTop = true
              // Restore state when reselecting a previously selected item
              restoreState = true
            }
          })
      }
    }
  }
}

@Composable
private fun NavigationGraph(
  navController: NavHostController,
  showSnack: (String) -> Unit,
) {
  NavHost(
    navController = navController,
    startDestination = gamesRoute
  ) {
    gamesScreen(
      navigate = navController::navigate
    )

    settingsScreen(
      navigateBack = navController::popBackStack,
    )

    appViewScreen(
      navigateBack = navController::popBackStack,
    )

    searchScreen(
      navigate = navController::navigate,
    )

  }
}
