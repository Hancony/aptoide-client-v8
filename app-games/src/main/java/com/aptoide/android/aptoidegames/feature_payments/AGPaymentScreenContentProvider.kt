package com.aptoide.android.aptoidegames.feature_payments

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import cm.aptoide.pt.extensions.staticComposable
import com.appcoins.payments.arch.PaymentMethod
import com.appcoins.payments.arch.PaymentsResult
import com.appcoins.payments.arch.PurchaseRequest
import com.appcoins.payments.methods.adyen.CreditCardPaymentMethod
import com.appcoins.payments.methods.paypal.PaypalPaymentMethod
import com.appcoins.payments.uri_handler.PaymentScreenContentProvider
import com.aptoide.android.aptoidegames.feature_payments.credit_card.buildCreditCardRoute
import com.aptoide.android.aptoidegames.feature_payments.credit_card.creditCardPaymentScreen
import com.aptoide.android.aptoidegames.feature_payments.payment_methods.paymentsRoute
import com.aptoide.android.aptoidegames.feature_payments.payment_methods.paymentsScreen
import com.aptoide.android.aptoidegames.feature_payments.paypal.buildPaypalRoute
import com.aptoide.android.aptoidegames.feature_payments.paypal.paypalPaymentScreen
import com.aptoide.android.aptoidegames.feature_payments.transaction.ongoingTransactionScreen
import com.aptoide.android.aptoidegames.feature_payments.wallet.WalletPaymentMethod
import com.aptoide.android.aptoidegames.feature_payments.wallet.getPaymentsWalletInstallationRoute
import com.aptoide.android.aptoidegames.feature_payments.wallet.paymentsWalletInstallationScreen
import com.aptoide.android.aptoidegames.feature_payments.wallet.paymentsWalletInstalledScreen
import com.aptoide.android.aptoidegames.theme.AptoideTheme

class AGPaymentScreenContentProvider : PaymentScreenContentProvider {

  override val setContent: (ComponentActivity, PurchaseRequest?, onClosePayments: (PaymentsResult) -> Unit) -> Unit =
    { context: ComponentActivity, purchaseRequest: PurchaseRequest?, onFinish: (PaymentsResult) -> Unit ->
      context.setContent {
        val navController = rememberNavController()

        AptoideTheme(darkTheme = false) {
          purchaseRequest
            ?.also {
              NavigationGraph(
                navController = navController,
                purchaseRequest = it,
                onFinish = onFinish
              )
            }
            ?: PaymentsErrorView(onFinish)
        }
      }
    }
}

@Composable
fun NavigationGraph(
  navController: NavHostController,
  purchaseRequest: PurchaseRequest,
  onFinish: (PaymentsResult) -> Unit,
) {
  NavHost(
    navController = navController,
    startDestination = paymentsRoute
  ) {
    staticComposable(
      navigate = navController::navigate,
      goBack = navController::popBackStack,
      screenData = paymentsScreen(
        onFinish = onFinish,
        purchaseRequest = purchaseRequest,
      )
    )

    staticComposable(
      navigate = navController::navigate,
      goBack = navController::popBackStack,
      screenData = paypalPaymentScreen(onFinish = onFinish)
    )

    staticComposable(
      navigate = navController::navigate,
      goBack = navController::popBackStack,
      screenData = paymentsWalletInstallationScreen(
        onFinish = onFinish,
        purchaseRequest = purchaseRequest,
      )
    )

    staticComposable(
      navigate = navController::navigate,
      goBack = navController::popBackStack,
      screenData = paymentsWalletInstalledScreen(
        purchaseRequest = purchaseRequest,
        onFinish = onFinish,
      )
    )

    staticComposable(
      navigate = navController::navigate,
      goBack = navController::popBackStack,
      screenData = creditCardPaymentScreen(
        onFinish = onFinish,
      )
    )

    staticComposable(
      navigate = navController::navigate,
      goBack = navController::popBackStack,
      screenData = ongoingTransactionScreen(
        purchaseRequest = purchaseRequest,
        onFinish = onFinish,
      )
    )
  }
}

fun PaymentMethod<*>.getRoute(isPreSelected: Boolean = false) =
  when (this) {
    is PaypalPaymentMethod -> buildPaypalRoute(isPreSelected)
    is CreditCardPaymentMethod -> buildCreditCardRoute(isPreSelected)
    is WalletPaymentMethod -> getPaymentsWalletInstallationRoute()
    else -> ""
  }