package com.aptoide.android.aptoidegames.feature_payments

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import cm.aptoide.pt.extensions.PreviewDark
import com.aptoide.android.aptoidegames.R
import com.aptoide.android.aptoidegames.design_system.PrimaryButton
import com.aptoide.android.aptoidegames.design_system.SecondaryUnderlinedTextButton
import com.aptoide.android.aptoidegames.theme.AptoideTheme
import kotlin.random.Random

@PreviewDark
@Composable
private fun PreviewPaymentButtons() {
  val onBuyClickEnabled = Random.nextBoolean()
  AptoideTheme {
    PaymentButtons(
      onBuyClickEnabled = onBuyClickEnabled,
      onBuyClick = { },
      onOtherPaymentMethodsClick = { }
    )
  }
}

@Composable
fun PaymentButtons(
  modifier: Modifier = Modifier,
  onBuyClickEnabled: Boolean = true,
  onBuyClick: () -> Unit,
  onOtherPaymentMethodsClick: () -> Unit,
) {
  Column(
    modifier = modifier,
    verticalArrangement = Arrangement.Bottom
  ) {
    PrimaryButton(
      modifier = Modifier.fillMaxWidth(),
      onClick = onBuyClick,
      title = stringResource(id = R.string.buy_button),
      enabled = onBuyClickEnabled
    )
    AppGamesOtherPaymentMethodsButton(
      onOtherPaymentMethodsClick = onOtherPaymentMethodsClick
    )
  }
}

@Composable
fun AppGamesOtherPaymentMethodsButton(
  modifier: Modifier = Modifier,
  onOtherPaymentMethodsClick: () -> Unit,
) = SecondaryUnderlinedTextButton(
  modifier = modifier,
  onClick = onOtherPaymentMethodsClick,
  text = stringResource(id = R.string.iap_other_payment_methods_button)
)
