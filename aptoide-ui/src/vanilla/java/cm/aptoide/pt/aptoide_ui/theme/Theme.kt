package cm.aptoide.pt.aptoide_ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import cm.aptoide.pt.theme.*

private val darkMaterialColorPalette = darkColors(
  background = black,
  onBackground = Color.White,
  primary = pinkishOrange,
  primaryVariant = pastelOrange,
  secondary = pinkishOrange,
  onPrimary = Color.White,
  onSecondary = Color.White,
  surface = blackDarkMode,
  onSurface = greyMedium,
  error = error,
)

val darkColorPalette = AppColors(
  unselectedLabelColor = greyMedium,
  appCoinsColor = appCoins,
  greyText = greyMedium,
  downloadViewAppCoinsText = Color.White,
  downloadProgressBarBackgroundColor = grey,
  dividerColor = negro,
  editorialLabelColor = blackDarkMode,
  editorialDateColor = grey,
  trustedColor = green,
  downloadBannerBackgroundColor = blackDarkMode,
  appViewTabRowColor = pinkishOrange,
  reportAppCardBackgroundColor = blackDarkMode,
  reportAppButtonTextColor = pinkishOrange,
  materialColors = darkMaterialColorPalette
)

private val lightMaterialColorPalette = lightColors(
  background = Color.White,
  onBackground = black,
  primary = pinkishOrange,
  primaryVariant = pastelOrange,
  onPrimary = Color.White,
  onSecondary = Color.White,
  surface = Color.White,
  onSurface = black,
  error = error
)

val lightColorPalette = AppColors(
  unselectedLabelColor = grey,
  appCoinsColor = appCoins, greyText = grey,
  downloadViewAppCoinsText = appCoins,
  downloadProgressBarBackgroundColor = greyLight,
  dividerColor = greyLight,
  editorialLabelColor = blackDarkMode,
  editorialDateColor = grey,
  trustedColor = green,
  downloadBannerBackgroundColor = Color.White,
  appViewTabRowColor = pinkishOrange,
  reportAppCardBackgroundColor = Color.White,
  reportAppButtonTextColor = pinkishOrange,
  materialColors = lightMaterialColorPalette
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
  regular_XXL = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight(400),
    fontSize = 34.sp,
    lineHeight = 40.sp,
    color = Color.Black
  ),
  regular_XL = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight(400),
    fontSize = 24.sp,
    lineHeight = 32.sp,
    color = Color.Black
  ),
  regular_L = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight(400),
    fontSize = 20.sp,
    lineHeight = 28.sp,
    color = Color.Black
  ),
  regular_M = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight(400),
    fontSize = 18.sp,
    lineHeight = 24.sp,
    color = Color.Black
  ),
  regular_S = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight(400),
    fontSize = 14.sp,
    lineHeight = 20.sp,
    color = Color.Black
  ),
  regular_XS = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight(400),
    fontSize = 12.sp,
    lineHeight = 16.sp,
    color = Color.Black
  ),
  regular_XXS = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight(400),
    fontSize = 10.sp,
    lineHeight = 14.sp,
    color = Color.Black
  ),
  bold_XXL = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight(700),
    fontSize = 34.sp,
    lineHeight = 40.sp,
    color = Color.Black
  ),
  bold_XL = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight(700),
    fontSize = 24.sp,
    lineHeight = 32.sp,
    color = Color.Black
  ),
  medium_L = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight(500),
    fontSize = 20.sp,
    lineHeight = 28.sp,
    color = Color.Black
  ),
  medium_M = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight(500),
    fontSize = 16.sp,
    lineHeight = 24.sp,
    color = Color.Black
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
  medium_XXS = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight(500),
    fontSize = 10.sp,
    lineHeight = 14.sp,
    color = Color.Black
  ),
  button_L = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight(700),
    fontSize = 14.sp,
    lineHeight = 13.sp,
    color = Color.White
  ),
  button_M = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight(500),
    fontSize = 11.sp,
    lineHeight = 14.sp,
    color = Color.White
  ),
  button_S = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight(500),
    fontSize = 9.sp,
    lineHeight = 14.sp,
    color = Color.White
  )
)

val darkTypography = AppTypography(
  materialTypography = darkMaterialTypography,
  regular_XXL = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight(400),
    fontSize = 34.sp,
    lineHeight = 40.sp,
    color = Color.White
  ),
  regular_XL = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight(400),
    fontSize = 24.sp,
    lineHeight = 32.sp,
    color = Color.White
  ),
  regular_L = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight(400),
    fontSize = 20.sp,
    lineHeight = 28.sp,
    color = Color.White
  ),
  regular_M = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight(400),
    fontSize = 18.sp,
    lineHeight = 24.sp,
    color = Color.White
  ),
  regular_S = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight(400),
    fontSize = 14.sp,
    lineHeight = 20.sp,
    color = Color.White
  ),
  regular_XS = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight(400),
    fontSize = 12.sp,
    lineHeight = 16.sp,
    color = Color.White
  ),
  regular_XXS = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight(400),
    fontSize = 10.sp,
    lineHeight = 14.sp,
    color = Color.White
  ),
  bold_XXL = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight(700),
    fontSize = 34.sp,
    lineHeight = 40.sp,
    color = Color.White
  ),
  bold_XL = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight(700),
    fontSize = 24.sp,
    lineHeight = 32.sp,
    color = Color.White
  ),
  medium_L = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight(500),
    fontSize = 20.sp,
    lineHeight = 28.sp,
    color = Color.White
  ),
  medium_M = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight(500),
    fontSize = 16.sp,
    lineHeight = 24.sp,
    color = Color.White
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
  medium_XXS = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight(500),
    fontSize = 10.sp,
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
  button_M = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight(500),
    fontSize = 11.sp,
    lineHeight = 14.sp,
    color = Color.White
  ),
  button_S = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight(500),
    fontSize = 9.sp,
    lineHeight = 14.sp,
    color = Color.White
  )
)


object AppTheme {
  val colors: AppColors
    @Composable
    @ReadOnlyComposable
    get() = LocalAppColors.current

  val typography: AppTypography
    @Composable
    @ReadOnlyComposable
    get() = LocalTypography.current
}

private val LocalAppColors = staticCompositionLocalOf {
  lightColorPalette //default
}

private val LocalTypography = staticCompositionLocalOf {
  lightTypography //default
}

@Composable
fun AptoideTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  content: @Composable () -> Unit
) {
  val colors = if (darkTheme) darkColorPalette else lightColorPalette
  val typography = if (darkTheme) darkTypography else lightTypography

  CompositionLocalProvider(
    LocalAppColors provides colors,
    LocalTypography provides typography
  ) {
    MaterialTheme(
      colors = colors.materialColors,
      typography = typography.materialTypography,
      shapes = shapes,
      content = content
    )
  }
}


