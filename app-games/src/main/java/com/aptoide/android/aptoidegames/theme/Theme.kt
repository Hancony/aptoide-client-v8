package com.aptoide.android.aptoidegames.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.aptoide.android.aptoidegames.R
import com.aptoide.android.aptoidegames.drawables.backgrounds.getSettingsDialogBackground
import com.aptoide.android.aptoidegames.drawables.backgrounds.getSettingsDialogDarkBackground
import com.aptoide.android.aptoidegames.drawables.icons.getAptoideGamesToolbarLogo
import com.aptoide.android.aptoidegames.drawables.icons.getAutoCompleteSuggestion
import com.aptoide.android.aptoidegames.drawables.icons.getCaretRight
import com.aptoide.android.aptoidegames.drawables.icons.getErrorBug
import com.aptoide.android.aptoidegames.drawables.icons.getErrorOutlined
import com.aptoide.android.aptoidegames.drawables.icons.getGamepad
import com.aptoide.android.aptoidegames.drawables.icons.getHistoryOutlined
import com.aptoide.android.aptoidegames.drawables.icons.getLeftArrow
import com.aptoide.android.aptoidegames.drawables.icons.getNoConnection
import com.aptoide.android.aptoidegames.drawables.icons.getNoConnectionSmall
import com.aptoide.android.aptoidegames.drawables.icons.getNotificationBell
import com.aptoide.android.aptoidegames.drawables.icons.getPlanetSearch
import com.aptoide.android.aptoidegames.drawables.icons.getSingleGamepad

private val darkMaterialColorPalette = darkColors(
  background = black1, //colorchanged already, then give it good name and change the rest
  onBackground = Color.White,
  primary = pinkishOrange,
  primaryVariant = pastelOrange,
  secondary = pinkishOrange,
  onPrimary = Color.White,
  onSecondary = Color.White,
  surface = blackDarkMode,
  onSurface = greyMedium,
  error = alertRed,
)

val darkColorPalette = AppColors(
  disabledButtonColor = gray3,
  disabledButtonTextColor = gray7,
  defaultButtonColor = richOrange,
  defaultButtonTextColor = pureWhite,
  redButtonColor = pinkRed,
  redButtonTextColor = pureWhite,
  grayButtonColor = gray3,
  grayButtonTextColor = pureWhite,
  unselectedLabelColor = greyMedium,
  dividerColor = negro,
  myGamesSeeAllViewColor = pureWhite,
  myGamesMessageTextColor = pureWhite,
  myGamesIconTintColor = pureWhite,
  moreAppsViewSeparatorColor = darkGray,
  moreAppsViewBackColor = gray5,
  installAppButtonColor = richOrange,
  materialColors = darkMaterialColorPalette,
  greyText = greyMedium,
  appCoinsColor = appCoins,
  downloadProgressBarBackgroundColor = grey,
  textFieldBackgroundColor = darkGray,
  textFieldBorderColor = pureWhite,
  textFieldPlaceholderTextColor = gray3,
  textFieldTextColor = pureWhite,
  placeholderColor = darkGray2,
  outOfSpaceDialogRequiredSpaceColor = richOrange,
  outOfSpaceDialogGoBackButtonColor = pureWhite,
  outOfSpaceDialogGoBackButtonTextColor = textBlack,
  outOfSpaceDialogGoBackButtonEnoughSpaceTextColor = pureWhite,
  outOfSpaceDialogGoBackButtonEnoughSpaceColor = richOrange,
  outOfSpaceDialogUninstallButtonColor = richOrange,
  outOfSpaceDialogAppNameColor = pureWhite,
  outOfSpaceDialogAppSizeColor = gray3,
  dialogBackgroundColor = textBlack,
  dialogTextColor = pureWhite,
  dialogDismissTextColor = gray3,
  searchBarTextColor = gray6,
  searchSuggestionHeaderTextColor = pureWhite,
  standardSecondaryTextColor = pureWhite,
  categoryBundleItemBackgroundColor = pinkishOrange,
  categoryBundleItemIconTint = pureWhite,
  categoryLargeItemTextColor = gray1,
)

private val lightMaterialColorPalette = lightColors(
  background = pureWhite,
  onBackground = black,
  primary = pinkishOrange,
  primaryVariant = pastelOrange,
  secondary = pinkishOrange,
  onPrimary = Color.White,
  onSecondary = Color.White,
  surface = Color.White,
  onSurface = black,
  error = alertRed
)

val lightColorPalette = AppColors(
  disabledButtonColor = gray3,
  disabledButtonTextColor = gray7,
  defaultButtonColor = richOrange,
  defaultButtonTextColor = pureWhite,
  redButtonColor = pinkRed,
  redButtonTextColor = pureWhite,
  grayButtonColor = gray3,
  grayButtonTextColor = textBlack,
  unselectedLabelColor = grey,
  dividerColor = greyLight,
  myGamesSeeAllViewColor = gray5,
  myGamesMessageTextColor = gray8,
  myGamesIconTintColor = gray8,
  moreAppsViewSeparatorColor = gray2,
  moreAppsViewBackColor = gray5,
  installAppButtonColor = richOrange,
  materialColors = lightMaterialColorPalette,
  greyText = grey,
  appCoinsColor = appCoins,
  downloadProgressBarBackgroundColor = greyLight,
  textFieldBackgroundColor = gray1,
  textFieldBorderColor = textBlack,
  textFieldPlaceholderTextColor = darkGray3,
  textFieldTextColor = darkGray,
  placeholderColor = gray2,
  outOfSpaceDialogRequiredSpaceColor = richOrange,
  outOfSpaceDialogGoBackButtonColor = textBlack,
  outOfSpaceDialogGoBackButtonTextColor = pureWhite,
  outOfSpaceDialogGoBackButtonEnoughSpaceTextColor = pureWhite,
  outOfSpaceDialogGoBackButtonEnoughSpaceColor = richOrange,
  outOfSpaceDialogUninstallButtonColor = richOrange,
  outOfSpaceDialogAppNameColor = textBlack,
  outOfSpaceDialogAppSizeColor = darkGray3,
  dialogBackgroundColor = gray1,
  dialogTextColor = textBlack,
  dialogDismissTextColor = darkGray3,
  searchBarTextColor = darkGray3,
  searchSuggestionHeaderTextColor = textBlack,
  standardSecondaryTextColor = darkGray3,
  categoryBundleItemBackgroundColor = gray2,
  categoryBundleItemIconTint = pinkishOrange,
  categoryLargeItemTextColor = darkGray3,
)

private val dmSansFontFamily = FontFamily(
  Font(R.font.dmsans_regular, FontWeight.Normal),
  Font(R.font.dmsans_medium, FontWeight.Medium),
  Font(R.font.dmsans_mediumitalic, FontWeight.Medium, FontStyle.Italic),
  Font(R.font.dmsans_italic, FontWeight.Normal, FontStyle.Italic),
  Font(R.font.dmsans_bold, FontWeight.Bold),
  Font(R.font.dmsans_bolditalic, FontWeight.Bold, FontStyle.Italic)
)

private val robotoCondensedFontFamily = FontFamily(
  Font(R.font.roboto_condensed_light, FontWeight.Light),
  Font(R.font.roboto_condensed_lightitalic, FontWeight.Light, FontStyle.Italic),
  Font(R.font.roboto_condensed_regular, FontWeight.Normal),
  Font(R.font.roboto_condensed_italic, FontWeight.Normal, FontStyle.Italic),
  Font(R.font.roboto_condensed_medium, FontWeight.Medium),
  Font(R.font.roboto_condensed_mediumitalic, FontWeight.Medium, FontStyle.Italic),
  Font(R.font.roboto_condensed_bold, FontWeight.Bold),
  Font(R.font.roboto_condensed_bolditalic, FontWeight.Bold, FontStyle.Italic)
)

val darkMaterialTypography = Typography(
  h1 = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Bold,
    color = Color.White,
    fontSize = 21.sp
  ),
  h2 = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Bold,
    color = Color.White,
    fontSize = 16.sp
  ),
  body1 = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Normal,
    color = Color.White,
    fontSize = 14.sp
  ),
  body2 = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Normal,
    color = Color.White,
    fontSize = 14.sp
  )
)

val lightMaterialTypography = Typography(
  h1 = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Bold,
    color = Color.Black,
    fontSize = 28.sp
  ),
  h2 = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Bold,
    color = Color.Black,
    fontSize = 21.sp
  ),
  body1 = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Normal,
    color = Color.Black,
    fontSize = 14.sp
  ),
  body2 = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Normal,
    color = Color.Black,
    fontSize = 14.sp
  )
)

val lightTypography = AppTypography(
  materialTypography = lightMaterialTypography,

  bodyCopy = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight(400),
    fontSize = 16.sp,
    lineHeight = 24.sp,
    color = darkGray3
  ),
  bodyCopyXS = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight(400),
    fontSize = 12.sp,
    lineHeight = 16.sp,
    color = darkGray3
  ),
  bodyCopyBold = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight(700),
    fontSize = 16.sp,
    lineHeight = 24.sp,
    color = pureBlack
  ),
  bodyCopySmall = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight(400),
    fontSize = 14.sp,
    lineHeight = 22.sp,
    color = darkGray3
  ),
  bodyCopySmallBold = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight(700),
    fontSize = 14.sp,
    lineHeight = 22.sp,
    color = pureBlack
  ),
  headlineTitleText = TextStyle(
    fontFamily = dmSansFontFamily,
    fontWeight = FontWeight(700),
    fontSize = 20.sp,
    lineHeight = 26.sp,
    color = pureBlack
  ),
  headlineTitleTextSecondary = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight(500),
    fontSize = 16.sp,
    lineHeight = 24.sp,
    color = darkGray3
  ),
  buttonTextLight = TextStyle(
    fontFamily = dmSansFontFamily,
    fontWeight = FontWeight(500),
    fontSize = 16.sp,
    lineHeight = 18.sp,
    color = pureWhite
  ),
  gameTitleTextCondensedSmall = TextStyle(
    fontFamily = robotoCondensedFontFamily,
    fontWeight = FontWeight(500),
    fontSize = 12.sp,
    lineHeight = 16.sp,
    color = textBlack
  ),
  gameTitleTextCondensed = TextStyle(
    fontFamily = dmSansFontFamily,
    fontWeight = FontWeight(500),
    fontSize = 14.sp,
    lineHeight = 18.sp,
    color = textBlack
  ),
  gameTitleTextCondensedLarge = TextStyle(
    fontFamily = robotoCondensedFontFamily,
    fontWeight = FontWeight(500),
    fontSize = 16.sp,
    lineHeight = 21.sp,
    color = textBlack
  ),
  gameTitleTextCondensedXL = TextStyle(
    fontFamily = robotoCondensedFontFamily,
    fontWeight = FontWeight(500),
    fontSize = 20.sp,
    lineHeight = 26.sp,
    color = pureBlack
  ),
  buttonTextMedium = TextStyle(
    fontFamily = dmSansFontFamily,
    fontWeight = FontWeight(500),
    fontSize = 14.sp,
    lineHeight = 14.sp,
    color = textBlack
  ),
  medium_S = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight(500),
    fontSize = 14.sp,
    lineHeight = 20.sp,
    color = Color.Black
  ),
  medium_XS = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight(500),
    fontSize = 12.sp,
    lineHeight = 16.sp,
    color = Color.Black
  ),
  button_M = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight(500),
    fontSize = 11.sp,
    lineHeight = 14.sp,
    color = Color.White
  ),
  button_L = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight(700),
    fontSize = 14.sp,
    lineHeight = 13.sp,
    color = Color.White
  ),
)

val darkTypography = AppTypography(
  materialTypography = darkMaterialTypography,

  bodyCopy = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight(400),
    fontSize = 16.sp,
    lineHeight = 24.sp,
    color = gray3
  ),
  bodyCopyXS = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight(400),
    fontSize = 12.sp,
    lineHeight = 16.sp,
    color = gray3
  ),
  bodyCopyBold = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight(700),
    fontSize = 16.sp,
    lineHeight = 24.sp,
    color = pureWhite
  ),
  bodyCopySmall = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight(400),
    fontSize = 14.sp,
    lineHeight = 22.sp,
    color = gray3
  ),
  bodyCopySmallBold = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight(700),
    fontSize = 14.sp,
    lineHeight = 22.sp,
    color = gray3
  ),
  headlineTitleText = TextStyle(
    fontFamily = dmSansFontFamily,
    fontWeight = FontWeight(700),
    fontSize = 20.sp,
    lineHeight = 26.sp,
    color = pureWhite
  ),
  headlineTitleTextSecondary = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight(500),
    fontSize = 16.sp,
    lineHeight = 24.sp,
    color = gray3
  ),
  buttonTextLight = TextStyle(
    fontFamily = dmSansFontFamily,
    fontWeight = FontWeight(500),
    fontSize = 16.sp,
    lineHeight = 18.sp,
    color = pureWhite
  ),
  gameTitleTextCondensedSmall = TextStyle(
    fontFamily = robotoCondensedFontFamily,
    fontWeight = FontWeight(500),
    fontSize = 12.sp,
    lineHeight = 16.sp,
    color = pureWhite
  ),
  gameTitleTextCondensed = TextStyle(
    fontFamily = dmSansFontFamily,
    fontWeight = FontWeight(500),
    fontSize = 14.sp,
    lineHeight = 18.sp,
    color = pureWhite
  ),
  gameTitleTextCondensedLarge = TextStyle(
    fontFamily = robotoCondensedFontFamily,
    fontWeight = FontWeight(500),
    fontSize = 16.sp,
    lineHeight = 21.sp,
    color = pureWhite
  ),
  gameTitleTextCondensedXL = TextStyle(
    fontFamily = robotoCondensedFontFamily,
    fontWeight = FontWeight(500),
    fontSize = 20.sp,
    lineHeight = 26.sp,
    color = pureWhite
  ),
  buttonTextMedium = TextStyle(
    fontFamily = dmSansFontFamily,
    fontWeight = FontWeight(500),
    fontSize = 14.sp,
    lineHeight = 14.sp,
    color = pureWhite
  ),
  medium_S = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight(500),
    fontSize = 14.sp,
    lineHeight = 20.sp,
    color = Color.White
  ),
  medium_XS = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight(500),
    fontSize = 12.sp,
    lineHeight = 16.sp,
    color = Color.White
  ),
  button_M = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight(500),
    fontSize = 11.sp,
    lineHeight = 14.sp,
    color = Color.White
  ),
  button_L = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight(700),
    fontSize = 14.sp,
    lineHeight = 13.sp,
    color = Color.White
  ),
)

private val lightGradientsPalette = AppGradients()

private val darkGradientsPalette = AppGradients()

private val lightIcons = AppIcons(
  LeftArrow = getLeftArrow(pureWhite, grey),
  ToolBarLogo = getAptoideGamesToolbarLogo(green1, black1),
  CaretRight = getCaretRight(),
  PlanetSearch = getPlanetSearch(),
  Gamepad = getGamepad(),
  SingleGamepad = getSingleGamepad(),
  ErrorBug = getErrorBug(),
  NoConnection = getNoConnection(),
  ErrorOutlined = getErrorOutlined(),
  NoConnectionSmall = getNoConnectionSmall(0.05f, 0.2f),
  NotificationBell = getNotificationBell(pureBlack),
  HistoryOutlined = getHistoryOutlined(),
  AutoCompleteSuggestion = getAutoCompleteSuggestion()
)

private val darkIcons = AppIcons(
  LeftArrow = getLeftArrow(pureBlack, pureWhite),
  ToolBarLogo = getAptoideGamesToolbarLogo(black1, green1),
  CaretRight = getCaretRight(),
  PlanetSearch = getPlanetSearch(),
  Gamepad = getGamepad(),
  SingleGamepad = getSingleGamepad(),
  ErrorBug = getErrorBug(),
  NoConnection = getNoConnection(),
  ErrorOutlined = getErrorOutlined(),
  NoConnectionSmall = getNoConnectionSmall(0.3f, 0.45f),
  NotificationBell = getNotificationBell(pureWhite),
  HistoryOutlined = getHistoryOutlined(),
  AutoCompleteSuggestion = getAutoCompleteSuggestion()
)

private val lightDrawables = AppDrawables(
  SettingsDialogBackground = getSettingsDialogBackground(),
  MyGamesBundleBackground = R.drawable.my_games_bundle_background_light,
)

private val darkDrawables = AppDrawables(
  SettingsDialogBackground = getSettingsDialogDarkBackground(),
  MyGamesBundleBackground = R.drawable.my_games_bundle_background_dark,
)

object AppTheme {
  val colors: AppColors
    @Composable
    @ReadOnlyComposable
    get() = LocalAppColors.current

  val drawables: AppDrawables
    @Composable
    @ReadOnlyComposable
    get() = LocalDrawables.current

  val typography: AppTypography
    @Composable
    @ReadOnlyComposable
    get() = LocalTypography.current

  val gradients: AppGradients
    @Composable
    @ReadOnlyComposable
    get() = LocalGradients.current

  val icons: AppIcons
    @Composable
    @ReadOnlyComposable
    get() = LocalIcons.current
}

private val LocalAppColors = staticCompositionLocalOf {
  lightColorPalette //default
}

private val LocalTypography = staticCompositionLocalOf {
  lightTypography //default
}

private val LocalGradients = staticCompositionLocalOf {
  lightGradientsPalette
}

private val LocalIcons = staticCompositionLocalOf {
  lightIcons
}

private val LocalDrawables = staticCompositionLocalOf {
  lightDrawables //default
}

@Composable
fun AptoideTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  content: @Composable () -> Unit,
) {
  val colors = if (darkTheme) darkColorPalette else lightColorPalette
  val typography = if (darkTheme) darkTypography else lightTypography
  val gradients = if (darkTheme) darkGradientsPalette else lightGradientsPalette
  val icons = if (darkTheme) darkIcons else lightIcons
  val drawables = if (darkTheme) darkDrawables else lightDrawables

  SetupStatusBarColor(colors.background, darkTheme)

  CompositionLocalProvider(
    LocalAppColors provides colors,
    LocalTypography provides typography,
    LocalGradients provides gradients,
    LocalIcons provides icons,
    LocalDrawables provides drawables
  ) {
    MaterialTheme(
      colors = colors.materialColors,
      typography = typography.materialTypography,
      shapes = shapes,
      content = content
    )
  }
}

@Composable
private fun SetupStatusBarColor(
  backgroundColor: Color,
  darkTheme: Boolean,
) {
  val context = LocalContext.current
  LaunchedEffect(key1 = darkTheme) {
    context.let { if (it is Activity) it else null }
      ?.window
      ?.run {
        statusBarColor = backgroundColor.toArgb()
        WindowCompat.getInsetsController(this, decorView)
          .isAppearanceLightStatusBars = !darkTheme
      }
  }
}