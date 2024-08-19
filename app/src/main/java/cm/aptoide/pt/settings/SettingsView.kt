package cm.aptoide.pt.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Slider
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import cm.aptoide.pt.aptoide_ui.animations.animatedComposable
import cm.aptoide.pt.buildUrlViewRoute
import cm.aptoide.pt.settings.presentation.DeviceInfoViewModel
import cm.aptoide.pt.settings.presentation.adultContentPreferences
import cm.aptoide.pt.settings.presentation.appUpdatesPreferences
import cm.aptoide.pt.settings.presentation.autoUpdatePreferences
import cm.aptoide.pt.settings.presentation.betaVersionsPreferences
import cm.aptoide.pt.settings.presentation.cacheSizePreferences
import cm.aptoide.pt.settings.presentation.campaignsPreferences
import cm.aptoide.pt.settings.presentation.compatibleAppsPreferences
import cm.aptoide.pt.settings.presentation.downloadOverWifiPreferences
import cm.aptoide.pt.settings.presentation.nativeInstallerPreferences
import cm.aptoide.pt.settings.presentation.rootInstallationPreferences
import cm.aptoide.pt.settings.presentation.systemAppsPreferences
import cm.aptoide.pt.settings.presentation.themePreferences
import cm.aptoide.pt.settings.presentation.updateAptoidePreferences
import cm.aptoide.pt.settings.presentation.userPinPreferences
import cm.aptoide.pt.theme.AppTheme
import cm.aptoide.pt.theme.AptoideDialog
import cm.aptoide.pt.theme.grey
import cm.aptoide.pt.theme.greyMedium
import cm.aptoide.pt.theme.pastelOrange
import cm.aptoide.pt.theme.pinkishOrange
import cm.aptoide.pt.theme.shapes
import cm.aptoide.pt.toolbar.NavigationTopBar

const val settingsRoute = "settings"

fun NavGraphBuilder.settingsScreen(
  navigate: (String) -> Unit,
  navigateBack: () -> Unit,
  showSnack: (String) -> Unit,
  versionName: String,
) = animatedComposable(settingsRoute) {
  val settingsTitle = "Settings"
  SettingsScreen(
    title = settingsTitle,
    navigate = navigate,
    navigateBack = navigateBack,
    showSnack = showSnack,
    versionName = versionName
  )
}

@Composable
fun SettingsScreen(
  title: String,
  navigate: (String) -> Unit,
  navigateBack: () -> Unit,
  showSnack: (String) -> Unit,
  versionName: String = "unknown",
) {
  val infoViewModel = hiltViewModel<DeviceInfoViewModel>()
  val deviceHardwareInfo by infoViewModel.uiState.collectAsState()
  val urlHandler = LocalUriHandler.current

  // General Section Variables
  val (isDarkTheme, setDarkTheme) =
    themePreferences(key = "settingsDarkTheme")
  val (compatibleApps, setCompatibleApps) =
    compatibleAppsPreferences(key = "compatibleApps")
  val (downloadOnlyOverWifi, setDownloadOnlyOverWifi) =
    downloadOverWifiPreferences(key = "downloadOnlyOverWifi")
  val (betaVersions, setBetaVersions) =
    betaVersionsPreferences(key = "betaVersions")
  val (useNativeInstaller, setUseNativeInstaller) =
    nativeInstallerPreferences(key = "useNativeInstaller")

  // Updates Section Arguments
  val (systemApps, setSystemApps) = systemAppsPreferences(key = "systemApps")

  // Notifications Section Arguments
  val (campaigns, setCampaigns) = campaignsPreferences(key = "campaigns")
  val (appUpdates, setAppUpdates) = appUpdatesPreferences(key = "appUpdates")
  val (updateAptoide, setUpdateAptoide) = updateAptoidePreferences(key = "updateAptoide")

  // Storage Section Arguments
  val (maxCacheSize, setMaxCacheSize) = cacheSizePreferences(key = "maxCacheSize")

  // Adult Content Section Arguments

  val (showAdultContent, setShowAdultContent) = adultContentPreferences(key = "showAdultContent")
  val (userPinCode, setUserPinCode) = userPinPreferences(key = "userPinCode")

  // Root Section Arguments
  val (allowRootInstallation, setAllowRootInstallation) =
    rootInstallationPreferences(key = "allowRootInstallation")
  val (enableAutoUpdate, setEnableAutoUpdate) =
    autoUpdatePreferences(key = "enableAutoUpdate")

  // Support Section Variables
  val clipboardManager: ClipboardManager = LocalClipboardManager.current

  // AUXILIARY VARIABLES
  var sliderPosition by remember { mutableStateOf(maxCacheSize) }
  var adultContentPinCode by remember { mutableStateOf(userPinCode) }

  // DIALOG VARIABLES
  var openClearCacheDialog by remember { mutableStateOf(false) }
  var openSetMaxCacheSizeDialog by remember { mutableStateOf(false) }
  var openSetAdultContentPinDialog by remember { mutableStateOf(false) }

  if (openClearCacheDialog) {
    ExtraOptionTextDialog(
      title = "Clear Aptoide Storage",
      text = "Are you sure you want to delete all Aptoide storage?" +
        "This includes images, download app files...",
      onPositiveClicked = { setMaxCacheSize(sliderPosition) },
      onDismissDialog = { openClearCacheDialog = false }
    )
  } else if (openSetMaxCacheSizeDialog) {
    ExtraOptionSliderDialog(
      title = "Set Max. Cache Size",
      sliderPosition = sliderPosition,
      sliderPositionOnClick = { sliderPosition = it },
      onPositiveClicked = {
        setMaxCacheSize(sliderPosition)
        openSetMaxCacheSizeDialog = false
      },
      onDismissDialog = { openSetMaxCacheSizeDialog = false },
    )
  } else if (openSetAdultContentPinDialog) {
    ExtraOptionInputDialog(
      title = "Would you like to set a pin to unlock adult content?",
      value = adultContentPinCode,
      onValueChange = { if (it.length <= 4) adultContentPinCode = it },
      onPositiveClicked = {
        setUserPinCode(adultContentPinCode)
        openSetAdultContentPinDialog = false
      },
      onDismissDialog = { openSetAdultContentPinDialog = false }
    )
  }

  Column(
    modifier = Modifier
      .fillMaxWidth()
      .wrapContentHeight(),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    NavigationTopBar(title, navigateBack)
    Column(
      modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState()),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Header(title = "General")
      FlagOption(
        title = "Only show compatible apps",
        description = "You’ll only see apps compatible with this device.",
        state = compatibleApps ?: false,
        onStateChange = setCompatibleApps
      )
      ShortDivider()
      FlagOption(
        title = "Download only over wifi",
        state = downloadOnlyOverWifi ?: true,
        onStateChange = setDownloadOnlyOverWifi
      )
      ShortDivider()
      FlagOption(
        title = "Beta Versions",
        description = "You’ll also see beta versions.",
        state = betaVersions ?: false,
        onStateChange = setBetaVersions
      )
      ShortDivider()
      ThemeOption(
        isDarkTheme = isDarkTheme,
        setIsDark = setDarkTheme
      )
      ShortDivider()
      FlagOption(
        title = "Use native installer",
        description = "Only if you’re using MIUI (from Xiaomi) " +
          "and are having problems to install apps. " +
          "You may stop seeing some apps versions.",
        state = useNativeInstaller ?: false,
        onStateChange = setUseNativeInstaller
      )

      LongDivider()

      Header(title = "Updates")
      FlagOption(
        title = "System apps",
        description = "You’ll see updates for system apps.",
        state = systemApps ?: false,
        onStateChange = setSystemApps
      )

      LongDivider()
      Header(title = "Notifications")
      FlagOption(
        title = "Campaigns",
        description = "Show notifications for app campaigns.",
        state = campaigns ?: false,
        onStateChange = setCampaigns
      )
      ShortDivider()
      FlagOption(
        title = "App updates",
        description = "Show notifications for app updates.",
        state = appUpdates ?: false,
        onStateChange = setAppUpdates
      )
      ShortDivider()
      FlagOption(
        title = "Update Aptoide",
        description = "Periodically check for new versions of Aptoide.",
        state = updateAptoide ?: false,
        onStateChange = setUpdateAptoide
      )

      LongDivider()
      Header(title = "Storage")
      DefaultExtraOption(
        title = "Clear cache",
        description = "Delete temporary files.",
        onClick = { openClearCacheDialog = true }
      )
      ShortDivider()
      DefaultExtraOption(
        title = "Set max. cache size (MB)",
        description = "Set a maximum size for your cache.",
        onClick = { openSetMaxCacheSizeDialog = true }
      )

      LongDivider()
      Header(title = "Adult Content")
      FlagOption(
        title = "Show adult content",
        state = showAdultContent ?: false,
        onStateChange = setShowAdultContent
      )
      ShortDivider()
      DefaultExtraOption(
        title = "Set adult content pin",
        description = "Set a pin code to unlock adult content.",
        onClick = { openSetAdultContentPinDialog = true }
      )

      LongDivider()
      Header(title = "Root")
      FlagOption(
        title = "Allow root installation",
        state = allowRootInstallation ?: false,
        onStateChange = setAllowRootInstallation
      )
      ShortDivider()
      FlagOption(
        title = "Enable auto update",
        description = "Enable automatic update of apps.",
        state = enableAutoUpdate ?: false,
        onStateChange = setEnableAutoUpdate
      )

      LongDivider()
      Header(title = "Support")
      DefaultExtraOption(
        title = "Send feedback",
        onClick = { navigate(sendFeedbackRoute) }
      )

      ShortDivider()
      ExtraOptionText(
        modifier = Modifier.padding(start = 16.dp, top = 24.dp, end = 16.dp),
        title = "About us"
      )
      ExtraOptionText(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
        description = "Version $versionName",
        buttonTitle = "CHECK UPDATES"
      )
      SmallExtraOption(
        title = "Source code & contributions",
        onClick = { navigate(buildUrlViewRoute("https://github.com/Aptoide")) }
      )
      SmallExtraOption(
        title = "About us",
        onClick = { navigate(buildUrlViewRoute("https://aptoide.com/company/about-us")) }
      )
      Spacer(modifier = Modifier.height(12.dp))
      ShortDivider()
      ExtraOptionText(
        modifier = Modifier
          .defaultMinSize(minHeight = 72.dp)
          .padding(start = 16.dp, top = 24.dp, end = 16.dp),
        title = "Hardware Specs",
        description = deviceHardwareInfo,
        buttonTitle = "COPY",
        onClick = {
          clipboardManager.setText(AnnotatedString(deviceHardwareInfo))
          showSnack("Copied to Clipboard")
        }
      )
      Spacer(modifier = Modifier.height(16.dp))

      LongDivider()
      Header(title = "Legal")
      DefaultExtraOption(
        title = "Terms and Conditions",
        onClick = { navigate(buildUrlViewRoute("https://aptoide.com/company/legal")) }
      )
      ShortDivider()
      DefaultExtraOption(
        title = "Privacy Policy",
        onClick = {
          navigate(
            buildUrlViewRoute("https://aptoide.com/company/legal?section=privacy")
          )
        }
      )
      ShortDivider()
      DeleteButton(
        onClick = {
          urlHandler.openUri(
            "https://aptoide.com/company/legal/account/delete?email=todo@gmail.com"
          )
        }
      )
    }
  }
}

@Composable
private fun Header(title: String) {
  Text(
    text = title,
    modifier = Modifier
      .fillMaxWidth()
      .padding(start = 16.dp, top = 24.dp, end = 16.dp, bottom = 8.dp),
    textAlign = TextAlign.Start,
    overflow = TextOverflow.Visible,
    maxLines = 1,
    style = AppTheme.typography.medium_M,
    fontWeight = FontWeight.Bold,
    color = AppTheme.colors.onBackground
  )
}

@Composable
private fun ShortDivider() =
  Divider(
    color = AppTheme.colors.dividerColor,
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp)
  )

@Composable
private fun LongDivider() =
  Divider(
    color = AppTheme.colors.secondaryGrey,
    modifier = Modifier
      .fillMaxWidth()
      .padding(bottom = 8.dp)
  )

@Composable
private fun FlagOption(
  title: String,
  description: String? = null,
  state: Boolean,
  onStateChange: (Boolean) -> Unit,
) {
  SectionRow(
    modifier = Modifier
      .defaultMinSize(minHeight = 72.dp)
      .padding(horizontal = 16.dp, vertical = 24.dp),
    title = title,
    description = description,
    onClick = { onStateChange(!state) }
  ) {
    Switch(
      checked = state,
      onCheckedChange = onStateChange,
      colors = SwitchDefaults.colors(
        checkedThumbColor = pinkishOrange,
        checkedTrackColor = pastelOrange,
        uncheckedThumbColor = AppTheme.colors.primaryGrey,
        uncheckedTrackColor = AppTheme.colors.secondaryGrey,
      )
    )
  }
}

@Composable
private fun DefaultExtraOption(
  title: String,
  description: String? = null,
  onClick: () -> Unit
) {
  ExtraOption(
    modifier = Modifier
      .defaultMinSize(minHeight = 72.dp)
      .padding(horizontal = 16.dp, vertical = 24.dp),
    title = title,
    description = description,
    onClick = onClick
  )
}

@Composable
private fun SmallExtraOption(
  title: String,
  onClick: () -> Unit
) {
  ExtraOption(
    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
    description = title,
    onClick = onClick
  )
}

@Composable
private fun ExtraOption(
  modifier: Modifier = Modifier,
  title: String? = null,
  description: String? = null,
  onClick: () -> Unit,
) {
  SectionRow(
    modifier = modifier,
    title = title,
    description = description,
    onClick = onClick
  ) {
    Image(
      imageVector = Icons.Filled.KeyboardArrowRight,
      colorFilter = ColorFilter.tint(AppTheme.colors.primary),
      contentDescription = "Foward",
      contentScale = ContentScale.Fit,
      modifier = Modifier.size(28.dp)
    )
  }
}

@Composable
private fun ExtraOptionText(
  modifier: Modifier = Modifier,
  title: String? = null,
  description: String? = null,
  buttonTitle: String? = null,
  onClick: (() -> Unit)? = null,
) {
  SectionRow(
    modifier = modifier,
    title = title,
    description = description,
    verticalAlignment = Alignment.Top
  ) {
    buttonTitle?.let {
      Text(
        text = it,
        modifier = Modifier
          .wrapContentWidth()
          .run {
            if (onClick != null) clickable(onClick = onClick) else this
          },
        textAlign = TextAlign.End,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        style = AppTheme.typography.button_M,
        color = AppTheme.colors.primary
      )
    }
  }
}

@Composable
private fun ThemeOption(
  isDarkTheme: Boolean?,
  setIsDark: (Boolean?) -> Unit,
) {
  Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp)) {
    Text(
      text = "Theme",
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp),
      textAlign = TextAlign.Start,
      overflow = TextOverflow.Visible,
      maxLines = 1,
      style = AppTheme.typography.regular_M,
      color = AppTheme.colors.onBackground
    )
    ThemeOption(
      title = "System default",
      selected = isDarkTheme == null,
      onClick = { setIsDark(null) }
    )
    ThemeOption(
      title = "Light",
      selected = isDarkTheme == false,
      onClick = { setIsDark(false) }
    )
    ThemeOption(
      title = "Dark",
      selected = isDarkTheme == true,
      onClick = { setIsDark(true) }
    )
  }
}

@Composable
private fun ExtraOptionTextDialog(
  title: String,
  text: String,
  onPositiveClicked: () -> Unit,
  onDismissDialog: () -> Unit,
) = AptoideDialog(
  title = title,
  titleStyle = AppTheme.typography.regular_M,
  dialogHeight = 208.dp,
  onPositiveClicked = onPositiveClicked,
  onDismissDialog = onDismissDialog,
) {
  Text(
    text = text,
    modifier = Modifier
      .fillMaxWidth()
      .padding(end = 8.dp),
    textAlign = TextAlign.Start,
    overflow = TextOverflow.Visible,
    maxLines = 3,
    style = AppTheme.typography.regular_S,
    color = AppTheme.colors.onBackground,
  )
}

@Composable
private fun ExtraOptionSliderDialog(
  title: String,
  sliderPosition: Int,
  sliderPositionOnClick: (Int) -> Unit,
  onPositiveClicked: () -> Unit,
  onDismissDialog: () -> Unit,
) = AptoideDialog(
  title = title,
  dialogHeight = 216.dp,
  onPositiveClicked = onPositiveClicked,
  onDismissDialog = onDismissDialog,
) {
  Box(
    modifier = Modifier
      .fillMaxSize()
      .border(
        width = 1.dp,
        color = AppTheme.colors.onBackground,
        shape = shapes.large
      )
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(start = 16.dp, top = 10.dp, end = 16.dp, bottom = 20.dp),
      verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      Row(modifier = Modifier.fillMaxWidth()) {
        Text(
          text = "$sliderPosition",
          textAlign = TextAlign.Start,
          overflow = TextOverflow.Visible,
          maxLines = 1,
          style = AppTheme.typography.regular_S,
          color = AppTheme.colors.onBackground
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
          text = "MB",
          textAlign = TextAlign.End,
          overflow = TextOverflow.Visible,
          maxLines = 1,
          style = AppTheme.typography.regular_XS,
          color = AppTheme.colors.onBackground
        )
      }
      Slider(
        value = sliderPosition.toFloat(),
        valueRange = 0f..300f,
        onValueChange = { sliderPositionOnClick(it.toInt()) }
      )
    }
  }
}

@Composable
private fun ExtraOptionInputDialog(
  title: String,
  value: String,
  onValueChange: (String) -> Unit,
  onPositiveClicked: () -> Unit,
  onDismissDialog: () -> Unit,
) = AptoideDialog(
  title = title,
  isPositiveEnabled = value.length == 4,
  onPositiveClicked = onPositiveClicked,
  onDismissDialog = onDismissDialog,
) {
  TextField(
    value = value,
    onValueChange = onValueChange,
    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
    visualTransformation = PasswordVisualTransformation(),
    maxLines = 1,
    modifier = Modifier.border(
      width = 1.dp,
      color = greyMedium,
      shape = shapes.large
    ),
    colors = TextFieldDefaults.textFieldColors(
      textColor = AppTheme.colors.onBackground,
      backgroundColor = AppTheme.colors.background,
      placeholderColor = AppTheme.colors.greyText,
      focusedIndicatorColor = AppTheme.colors.background,
      unfocusedIndicatorColor = AppTheme.colors.background,
      disabledIndicatorColor = AppTheme.colors.background
    ),
    placeholder = {
      Text(
        text = "****",
        textAlign = TextAlign.Start,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        style = AppTheme.typography.regular_S,
        color = AppTheme.colors.greyText
      )
    },
  )
}

@Composable
private fun SectionRow(
  modifier: Modifier,
  title: String?,
  titleColor: Color = AppTheme.colors.onBackground,
  description: String? = null,
  verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
  onClick: (() -> Unit)? = null,
  component: @Composable (() -> Unit)?,
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .run {
        if (onClick != null)
          clickable(onClick = onClick) else this
      }
      .padding(horizontal = 16.dp)
      .then(modifier),
    verticalAlignment = verticalAlignment
  ) {
    Column(
      modifier = Modifier.weight(1f),
      verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
      title?.let {
        Text(
          text = it,
          modifier = Modifier.fillMaxWidth(),
          textAlign = TextAlign.Start,
          overflow = TextOverflow.Ellipsis,
          maxLines = 1,
          style = AppTheme.typography.regular_M,
          color = titleColor
        )
      }

      description?.let {
        Text(
          text = it,
          modifier = Modifier.fillMaxWidth(),
          textAlign = TextAlign.Start,
          overflow = TextOverflow.Ellipsis,
          style = AppTheme.typography.regular_XS,
          color = AppTheme.colors.greyText,
        )
      }
    }
    component?.let { it() }
  }
}

@Composable
private fun DeleteButton(
  onClick: () -> Unit,
) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .clickable(onClick = onClick)
      .defaultMinSize(minHeight = 72.dp)
      .padding(horizontal = 32.dp),
    contentAlignment = Alignment.CenterStart
  ) {
    Text(
      text = "Delete account",
      textAlign = TextAlign.Start,
      overflow = TextOverflow.Ellipsis,
      maxLines = 1,
      style = AppTheme.typography.medium_M,
      color = AppTheme.colors.error
    )
  }
}

@Composable
private fun ThemeOption(
  title: String,
  selected: Boolean,
  onClick: () -> Unit,
) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier
      .clickable(onClick = onClick)
      .fillMaxWidth()
  ) {
    RadioButton(
      selected = selected,
      onClick = onClick,
      colors = RadioButtonDefaults.colors(
        selectedColor = AppTheme.colors.primary,
        unselectedColor = grey
      )
    )
    Text(
      text = title,
      textAlign = TextAlign.Start,
      overflow = TextOverflow.Ellipsis,
      maxLines = 1,
      style = AppTheme.typography.regular_XS,
      color = AppTheme.colors.onBackground
    )
  }
}