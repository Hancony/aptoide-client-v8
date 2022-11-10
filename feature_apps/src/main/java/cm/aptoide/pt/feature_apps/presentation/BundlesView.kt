package cm.aptoide.pt.feature_apps.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cm.aptoide.pt.aptoide_ui.theme.AppTheme
import cm.aptoide.pt.aptoide_ui.theme.AptoideTheme
import cm.aptoide.pt.aptoide_ui.toolbar.AptoideActionBar
import cm.aptoide.pt.feature_apps.data.App
import cm.aptoide.pt.feature_apps.data.File
import cm.aptoide.pt.feature_apps.domain.*
import cm.aptoide.pt.feature_editorial.presentation.EditorialViewCard
import cm.aptoide.pt.feature_editorial.presentation.EditorialViewModel
import cm.aptoide.pt.feature_editorial.presentation.EditorialViewScreen
import java.util.*

@Composable
fun BundlesScreen(viewModel: BundlesViewModel, type: ScreenType) {
  val bundles: List<Bundle> by viewModel.bundlesList.collectAsState(initial = emptyList())
  val isLoading: Boolean by viewModel.isLoading

  val topAppBarState = rememberSaveable { (mutableStateOf(true)) }
  val navController = rememberNavController()
  AptoideTheme {
    Scaffold(
      topBar = {
        AnimatedVisibility(
          visible = topAppBarState.value,
          enter = slideInVertically(initialOffsetY = { -it }),
          exit = slideOutVertically(targetOffsetY = { -it }),
          content = { AptoideActionBar() }
        )
      }
    ) {
      NavigationGraph(
        navController = navController,
        isLoading = isLoading,
        bundles = bundles,
        topAppBarState = topAppBarState
      )
    }
  }
}

@Composable
private fun BundlesView(
  isLoading: Boolean,
  bundles: List<Bundle>,
  nav: NavHostController,
) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .wrapContentSize(Alignment.TopCenter)
  ) {
    if (isLoading) {
      CircularProgressIndicator()
    } else {
      LazyColumn(
        modifier = Modifier
          .fillMaxSize()
//        .verticalScroll(rememberScrollState())   Error: Nesting scrollable in the same direction layouts like LazyColumn and Column(Modifier.verticalScroll())
          .wrapContentSize(Alignment.TopCenter)
          .padding(start = 16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
      ) {
        items(bundles) {
          Box {
//            if (it.type == Type.ESKILLS) {
//              Box(modifier = Modifier
//                .fillMaxWidth()
//                .padding(start = 16.dp)
//                .background(Color(0xFFFEF2D6))
//                .height(112.dp))
//            }
            Column {
              Text(
                text = it.title,
                style = AppTheme.typography.medium_M,
                modifier = Modifier.padding(bottom = 8.dp)
              )
              when (it.type) {
                Type.APP_GRID -> AppsListView(it.appsList)
                Type.FEATURE_GRAPHIC -> AppsGraphicListView(it.appsList, false)
                Type.ESKILLS -> AppsListView(it.appsList)
                Type.FEATURED_APPC -> AppsGraphicListView(it.appsList, true)
                Type.EDITORIAL -> {
                  if (it is EditorialBundle) {
                    EditorialViewCard(
                      articleId = it.articleId,
                      title = it.editorialTitle,
                      image = it.image,
                      subtype = it.subtype,
                      summary = it.summary,
                      date = it.date,
                      views = it.views,
                      reactionsNumber = it.reactionsNumber,
                      navController = nav,
                    )
                  }
                }
                Type.UNKNOWN_BUNDLE -> {}
              }
            }
          }
        }
      }
    }
  }
}

@Composable
fun AppsListView(appsList: List<App>) {
  LazyRow(
    modifier = Modifier
      .fillMaxWidth()
      .wrapContentHeight(),
    horizontalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    items(appsList) {
      AppGridView(it)
    }
  }
}

@Composable
fun AppsGraphicListView(appsList: List<App>, bonusBanner: Boolean) {
  LazyRow(
    modifier = Modifier
      .fillMaxWidth()
      .wrapContentHeight(),
    horizontalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    items(appsList) {
      AppGraphicView(it, bonusBanner)
    }
  }
}


@Preview
@Composable
internal fun AppsScreenPreview() {
  BundlesView(
    false,
    listOf(
      createFakeBundle(),
      createFakeBundle(),
      createFakeBundle(),
      createFakeBundle(),
      createFakeBundle()
    ),
    rememberNavController()
  )
}

fun createFakeBundle(): Bundle {
  val appsList: MutableList<App> = ArrayList()
  for (i in 0..9) {
    appsList.add(
      App(
        name = "app name $i app name 2",
        packageName = "packagename",
        md5 = "md5",
        appSize = 123,
        icon = "https://pool.img.aptoide.com/catappult/8c9974886cca4ae0169d260f441640ab_icon.jpg",
        malware = "trusted",
        rating = Rating(
          avgRating = 2.3,
          totalVotes = 12321,
          votes = listOf(
            Votes(1, 3),
            Votes(2, 8),
            Votes(3, 123),
            Votes(4, 100),
            Votes(5, 1994)
          )
        ),
        downloads = 11113,
        versionName = "alfa",
        versionCode = 123,
        featureGraphic = "https://pool.img.aptoide.com/catappult/934323636c0247af73ecfcafd46aefc3_feature_graphic.jpg",
        isAppCoins = true,
        screenshots = listOf("", ""),
        description = "app with the name 1 descpription",
        store = Store(
          storeName = "rmota",
          icon = "rmota url",
          apps = 123,
          subscribers = 123123,
          downloads = 1312132314
        ),
        releaseDate = "18 of may",
        updateDate = "18 of may",
        website = "www.aptoide.com",
        email = "aptoide@aptoide.com",
        privacyPolicy = "none",
        permissions = listOf("Permission 1", "permission 2"),
        file = File(
          vername = "asdas",
          vercode = 123,
          md5 = "md5",
          filesize = 123,
          path = null,
          path_alt = null
        ),
        obb = null
      )
    )
  }
  val pick: Int = Random().nextInt(Type.values().size)
  return Bundle(title = "Widget title", appsList, Type.values()[pick])
}

enum class ScreenType {
  APPS, GAMES, BONUS
}

@Composable
private fun NavigationGraph(
  navController: NavHostController,
  isLoading: Boolean,
  bundles: List<Bundle>,
  topAppBarState: MutableState<Boolean>,
) {
  NavHost(
    navController = navController,
    startDestination = "games"
  ) {
    composable("games") {
      topAppBarState.value = true
      BundlesView(isLoading, bundles, navController)
    }
    composable("editorial/{articleId}") {
      topAppBarState.value = false
      val viewModel = hiltViewModel<EditorialViewModel>()
      EditorialViewScreen(viewModel)
    }
  }
}
