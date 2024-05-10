package com.aptoide.android.aptoidegames.theme

import androidx.compose.material.Colors
import androidx.compose.ui.graphics.Color

data class AppColors(
  val moreAppsViewSeparatorColor: Color,
  val grayButtonColor: Color,
  val disabledButtonColor: Color,
  val disabledButtonTextColor: Color,
  val defaultButtonColor: Color,
  val defaultButtonTextColor: Color,
  val redButtonTextColor: Color,
  val redButtonColor: Color,
  val greyText: Color,
  val grayButtonTextColor: Color,
  val materialColors: Colors,
  val dividerColor: Color,
  val myGamesSeeAllViewColor: Color,
  val installAppButtonColor: Color,
  val myGamesIconTintColor: Color,
  val myGamesMessageTextColor: Color,
  val unselectedLabelColor: Color,
  val moreAppsViewBackColor: Color,
  val appCoinsColor: Color,
  val downloadProgressBarBackgroundColor: Color,
  val textFieldBackgroundColor: Color,
  val textFieldBorderColor: Color,
  val textFieldPlaceholderTextColor: Color,
  val textFieldTextColor: Color,
  val searchBarTextColor: Color,
  val searchSuggestionHeaderTextColor: Color,
  val standardSecondaryTextColor: Color,
  val placeholderColor: Color,
  val outOfSpaceDialogRequiredSpaceColor: Color,
  val outOfSpaceDialogGoBackButtonColor: Color,
  val outOfSpaceDialogGoBackButtonEnoughSpaceColor: Color,
  val outOfSpaceDialogGoBackButtonTextColor: Color,
  val outOfSpaceDialogGoBackButtonEnoughSpaceTextColor: Color,
  val outOfSpaceDialogUninstallButtonColor: Color,
  val outOfSpaceDialogAppNameColor: Color,
  val outOfSpaceDialogAppSizeColor: Color,
  val dialogBackgroundColor: Color,
  val dialogTextColor:Color,
  val dialogDismissTextColor:Color,
  val categoryBundleItemBackgroundColor: Color,
  val categoryBundleItemIconTint: Color,
  val categoryLargeItemTextColor: Color,
) {
  val primary: Color
    get() = materialColors.primary
  val primaryVariant: Color
    get() = materialColors.primaryVariant
  val secondary: Color
    get() = materialColors.secondary
  val secondaryVariant: Color
    get() = materialColors.secondaryVariant
  val background: Color
    get() = materialColors.background
  val surface: Color
    get() = materialColors.surface
  val error: Color
    get() = materialColors.error
  val onPrimary: Color
    get() = materialColors.onPrimary
  val onSecondary: Color
    get() = materialColors.onSecondary
  val onBackground: Color
    get() = materialColors.onBackground
  val onSurface: Color
    get() = materialColors.onSurface
  val onError: Color
    get() = materialColors.onError
  val isLight: Boolean
    get() = materialColors.isLight
}