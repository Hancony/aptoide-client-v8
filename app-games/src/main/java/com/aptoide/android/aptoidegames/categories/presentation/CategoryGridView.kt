package com.aptoide.android.aptoidegames.categories.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.semantics.CollectionInfo
import androidx.compose.ui.semantics.collectionInfo
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.aptoide.android.aptoidegames.AptoideAsyncImage
import com.aptoide.android.aptoidegames.R
import com.aptoide.android.aptoidegames.home.BundleHeader
import com.aptoide.android.aptoidegames.home.EmptyBundleView
import com.aptoide.android.aptoidegames.home.LoadingBundleView
import com.aptoide.android.aptoidegames.theme.AppTheme
import cm.aptoide.pt.extensions.PreviewAll
import cm.aptoide.pt.feature_categories.domain.Category
import cm.aptoide.pt.feature_categories.presentation.rememberCategoriesState
import cm.aptoide.pt.feature_home.domain.Bundle

@Composable
fun CategoriesBundle(
  bundle: Bundle,
  navigate: (String) -> Unit,
) = bundle.view?.let {
  val uiState = rememberCategoriesState(requestUrl = it)

  Column(
    modifier = Modifier
      .fillMaxWidth()
      .wrapContentHeight()
      .padding(bottom = 16.dp)
  ) {
    BundleHeader(
      bundle = bundle
    )
    CategoriesListView(
      loading = uiState.loading,
      categories = uiState.categories,
      navigate = navigate
    )
  }
}

@Composable
fun CategoriesListView(
  loading: Boolean,
  categories: List<Category>,
  navigate: (String) -> Unit
) {
  val lazyListState = rememberLazyListState()

  if (loading) {
    LoadingBundleView(height = 132.dp)
  } else if (categories.isEmpty()) {
    EmptyBundleView(height = 132.dp)
  } else {
    LazyRow(
      modifier = Modifier
        .semantics {
          collectionInfo = CollectionInfo(1, categories.size)
        }
        .fillMaxWidth()
        .wrapContentHeight(),
      state = lazyListState,
      contentPadding = PaddingValues(horizontal = 16.dp),
      horizontalArrangement = Arrangement.spacedBy(24.dp),
    ) {
      itemsIndexed(categories) { index, category ->
        CategoryGridView(
          title = category.title,
          icon = category.icon,
          onClick = {
            navigate(buildCategoryDetailRoute(category.title, category.name))
          }
        )
      }
    }
  }
}

@Composable
fun CategoryGridView(
  title: String,
  icon: String?,
  onClick: () -> Unit = {},
) {
  Column(
    modifier = Modifier
      .semantics(mergeDescendants = true) { }
      .clickable(onClick = onClick)
      .width(88.dp)
      .wrapContentSize(Alignment.TopCenter)
  ) {
    Box(
      contentAlignment = Alignment.TopEnd,
      modifier = Modifier
        .padding(bottom = 8.dp)
        .size(88.dp)
        .clip(RoundedCornerShape(16.dp))
        .background(color = AppTheme.colors.categoryBundleItemBackgroundColor)
    ) {
      AptoideAsyncImage(
        modifier = Modifier
          .size(57.dp)
          .align(Alignment.Center),
        data = icon ?: R.drawable.category_default_icon,
        placeholder = false,
        contentDescription = null,
        colorFilter = ColorFilter.tint(AppTheme.colors.categoryBundleItemIconTint)
      )
    }
    Text(
      text = title,
      maxLines = 2,
      overflow = TextOverflow.Ellipsis,
      modifier = Modifier
        .defaultMinSize(minHeight = 36.dp),
      style = AppTheme.typography.gameTitleTextCondensed
    )
  }
}

@PreviewAll
@Composable
fun CategoriesViewPreview() {
  CategoryGridView(title = "Action", icon = "", onClick = {})
}