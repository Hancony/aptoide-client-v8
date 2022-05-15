package cm.aptoide.pt.feature_apps.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cm.aptoide.pt.feature_apps.data.App
import cm.aptoide.pt.feature_apps.domain.Bundle
import cm.aptoide.pt.feature_apps.domain.Store
import cm.aptoide.pt.feature_apps.domain.Type
import java.util.*

@Composable
fun AppsScreen(viewModel: BundlesViewModel, type: ScreenType) {
  val bundles: List<Bundle> by viewModel.bundlesList.collectAsState(initial = emptyList())
  val isLoading: Boolean by viewModel.isLoading
  BundlesScreen(isLoading, bundles)
}

@Composable
private fun BundlesScreen(
  isLoading: Boolean,
  bundles: List<Bundle>,
) {

//  val navController =  rememberNavController()


  Column(
    modifier = Modifier
      .fillMaxSize()
      .wrapContentSize(Alignment.TopCenter)
  ) {
    //NavigationGraph(navController)
    if (isLoading)
      CircularProgressIndicator()
    else
      LazyColumn(
        modifier = Modifier
          .fillMaxSize()
//        .verticalScroll(rememberScrollState())   Error: Nesting scrollable in the same direction layouts like LazyColumn and Column(Modifier.verticalScroll())
          .wrapContentSize(Alignment.TopCenter), verticalArrangement = Arrangement.spacedBy(16.dp)
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
                it.title,
                style = MaterialTheme.typography.h2,
                modifier = Modifier.padding(bottom = 8.dp)
              )
              when (it.type) {
                Type.APP_GRID -> AppsListView(it.appsList)
                Type.FEATURE_GRAPHIC -> AppsGraphicListView(it.appsList, false)
                Type.ESKILLS -> AppsListView(it.appsList)
                Type.FEATURED_APPC -> AppsGraphicListView(it.appsList, true)
                Type.UNKNOWN_BUNDLE -> {}
              }
            }
          }
        }
      }
  }
}

/*@Composable
fun NavigationGraph(navController: NavHostController) {

}*/

@Composable
fun AppsListView(appsList: List<App>) {
  LazyRow(
    modifier = Modifier
      .fillMaxWidth()
      .wrapContentHeight(), horizontalArrangement = Arrangement.spacedBy(16.dp)
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
      .wrapContentHeight(), horizontalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    items(appsList) {
      AppGraphicView(it, bonusBanner)
    }
  }
}


@Preview
@Composable
internal fun AppsScreenPreview() {
  BundlesScreen(
    false,
    listOf(
      createFakeBundle(),
      createFakeBundle(),
      createFakeBundle(),
      createFakeBundle(),
      createFakeBundle()
    )
  )
}

fun createFakeBundle(): Bundle {
  val appsList: MutableList<App> = ArrayList()
  for (i in 0..9) {
    appsList.add(
      App(
        "app name $i app name 2",
        "packagename",
        123,
        "https://pool.img.aptoide.com/catappult/8c9974886cca4ae0169d260f441640ab_icon.jpg",
        "trusted",
        2.3,
        11113,
        "alfa",
        "https://pool.img.aptoide.com/catappult/934323636c0247af73ecfcafd46aefc3_feature_graphic.jpg",
        true,
        listOf("", ""),
        "app with the name 1 descpription",
        Store("rmota", "rmota url", 123, 123123, 1312132314),
        "18 of may",
        "18 of may",
        "www.aptoide.com",
        "aptoide@aptoide.com",
        "none",
        listOf("Permission 1", "permission 2")
      )
    )
  }
  val pick: Int = Random().nextInt(Type.values().size)
  return Bundle(title = "Widget title", appsList, Type.values()[pick])
}

enum class ScreenType {
  APPS, GAMES, BONUS
}