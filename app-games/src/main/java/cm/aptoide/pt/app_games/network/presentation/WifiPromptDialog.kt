package cm.aptoide.pt.app_games.network.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import cm.aptoide.pt.app_games.R
import cm.aptoide.pt.app_games.network.presentation.WifiPromptType.UNMETERED_LARGE_FILE
import cm.aptoide.pt.app_games.network.presentation.WifiPromptType.UNMETERED_WIFI_ONLY
import cm.aptoide.pt.app_games.theme.AppTheme
import cm.aptoide.pt.app_games.theme.AptoideTheme
import cm.aptoide.pt.app_games.theme.richOrange
import cm.aptoide.pt.aptoide_ui.textformatter.TextFormatter.Companion.formatBytes
import cm.aptoide.pt.extensions.PreviewAll

@Composable
fun WifiPromptDialog(
  type: WifiPromptType,
  size: Long,
  onWaitForWifi: () -> Unit,
  onDownloadNow: () -> Unit,
  onDismiss: () -> Unit,
) {
  val sizeInfo = formatBytes(size)
  val message = type.getMessage(sizeInfo)
  val startIndex = message.indexOf(sizeInfo)
  val endIndex = message.indexOf(sizeInfo) + sizeInfo.length

  val messageText = buildAnnotatedString {
    append(message)
    addStyle(
      style = AppTheme.typography.bodyCopySmall.toSpanStyle(),
      start = startIndex,
      end = endIndex
    )
  }

  Dialog(
    onDismissRequest = { onDismiss() },
    properties = DialogProperties(
      dismissOnBackPress = true,
      dismissOnClickOutside = false,
      usePlatformDefaultWidth = false
    ),
  ) {
    Box(
      modifier = Modifier
        .padding(horizontal = 24.dp)
        .fillMaxWidth()
        .wrapContentHeight()
        .clip(RoundedCornerShape(24.dp))
        .background(AppTheme.colors.dialogBackgroundColor),
    ) {
      Column(
        modifier = Modifier
          .padding(horizontal = 24.dp, vertical = 16.dp)
          .fillMaxWidth()
          .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Image(
          imageVector = AppTheme.icons.NoConnectionSmall,
          modifier = Modifier
            .size(184.dp)
            .padding(bottom = 16.dp),
          contentDescription = null,
        )
        Text(
          text = stringResource(R.string.wifi_disclaimer_title),
          style = AppTheme.typography.headlineTitleText,
          color = AppTheme.colors.dialogTextColor,
          textAlign = TextAlign.Center,
          modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
          text = messageText,
          style = AppTheme.typography.bodyCopySmall,
          color = AppTheme.colors.dialogTextColor,
          textAlign = TextAlign.Center,
          modifier = Modifier.padding(bottom = 16.dp)
        )
        Button(
          onClick = onWaitForWifi,
          shape = RoundedCornerShape(30.dp),
          modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 48.dp)
            .padding(bottom = 8.dp),
          contentPadding = PaddingValues(horizontal = 16.dp, vertical = 15.dp),
          elevation = ButtonDefaults.elevation(defaultElevation = 0.dp),
          colors = ButtonDefaults.buttonColors(
            backgroundColor = richOrange
          )
        ) {
          Text(
            text = stringResource(R.string.wait_for_wifi_button),
            maxLines = 1,
            style = AppTheme.typography.buttonTextLight
          )
        }
        Text(
          modifier = Modifier
            .clickable { onDownloadNow() }
            .minimumInteractiveComponentSize()
            .fillMaxWidth(),
          text = stringResource(R.string.download_now_button),
          maxLines = 1,
          textAlign = TextAlign.Center,
          style = AppTheme.typography.bodyCopySmall,
          color = AppTheme.colors.dialogDismissTextColor
        )
      }
    }
  }
}

@Composable
fun WifiPromptType.getMessage(sizeInfo: String): String =
  when (this) {
    UNMETERED_WIFI_ONLY -> stringResource(R.string.wifi_disclaimer_on_body, sizeInfo)
    UNMETERED_LARGE_FILE -> stringResource(R.string.wifi_disclamer_off_body, sizeInfo)
  }

enum class WifiPromptType {
  UNMETERED_WIFI_ONLY,
  UNMETERED_LARGE_FILE,
}

@PreviewAll
@Composable
fun WifiPromptDialogPreview(
  @PreviewParameter(WifiPromptInfoProvider::class) info: Pair<WifiPromptType, Long>,
) {
  AptoideTheme {
    WifiPromptDialog(
      type = info.first,
      size = info.second,
      onWaitForWifi = {},
      onDownloadNow = {},
      onDismiss = {},
    )
  }
}

class WifiPromptInfoProvider
  : PreviewParameterProvider<Pair<WifiPromptType, Long>> {
  override val values: Sequence<Pair<WifiPromptType, Long>> = sequenceOf(
    UNMETERED_WIFI_ONLY to 200_000_000L,
    UNMETERED_LARGE_FILE to 130_000_000_0L,
  )
}
