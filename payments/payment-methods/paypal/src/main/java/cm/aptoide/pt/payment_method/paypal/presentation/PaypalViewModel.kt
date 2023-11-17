package cm.aptoide.pt.payment_method.paypal.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.Factory
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import cm.aptoide.pt.payment_manager.manager.PaymentManager
import cm.aptoide.pt.payment_manager.transaction.TransactionStatus.COMPLETED
import cm.aptoide.pt.payment_method.paypal.PaypalPaymentMethod
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InjectionsProvider @Inject constructor(
  val paymentManager: PaymentManager,
) : ViewModel()

@Composable
fun paypalViewModel(
  paymentMethodId: String,
): PaypalScreenUiState {
  val viewModelProvider = hiltViewModel<InjectionsProvider>()
  val packageName = LocalContext.current.packageName
  val vm: PaypalViewModel = viewModel(
    key = paymentMethodId,
    factory = object : Factory {
      override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return PaypalViewModel(
          paymentManager = viewModelProvider.paymentManager,
          paymentMethodId = paymentMethodId,
          packageName = packageName
        ) as T
      }
    }
  )
  val uiState by vm.uiState.collectAsState()
  return uiState
}

class PaypalViewModel(
  private val packageName: String,
  private val paymentMethodId: String,
  private val paymentManager: PaymentManager,
) : ViewModel() {

  private val viewModelState =
    MutableStateFlow<PaypalScreenUiState>(PaypalScreenUiState.Loading)

  val uiState = viewModelState
    .stateIn(
      viewModelScope,
      SharingStarted.Eagerly,
      viewModelState.value
    )

  init {
    viewModelScope.launch {
      viewModelState.update { PaypalScreenUiState.Loading }

      try {
        val creditCardPaymentMethod =
          paymentManager.getPaymentMethod(paymentMethodId) as PaypalPaymentMethod

        val billingAgreementData = creditCardPaymentMethod.init()

        if (billingAgreementData != null) {
          viewModelState.update {
            PaypalScreenUiState.BillingAgreementAvailable(
              purchaseRequest = creditCardPaymentMethod.purchaseRequest,
              paymentMethodName = creditCardPaymentMethod.label,
              paymentMethodIconUrl = creditCardPaymentMethod.iconUrl,
              onBuyClick = ::makePurchase,
              onRemoveBillingAgreementClick = ::removeBillingAgreement
            )
          }
        } else {
          val billingAgreement = creditCardPaymentMethod.createToken(packageName)
          viewModelState.update {
            PaypalScreenUiState.LaunchWebViewActivity(
              url = billingAgreement.url,
              token = billingAgreement.token,
              onWebViewResult = ::onWebViewResult
            )
          }
        }
      } catch (e: Throwable) {
        viewModelState.update { PaypalScreenUiState.Error }
      }
    }
  }

  private fun removeBillingAgreement() {
    viewModelScope.launch {
      try {
        viewModelState.update { PaypalScreenUiState.Loading }
        val creditCardPaymentMethod =
          paymentManager.getPaymentMethod(paymentMethodId) as PaypalPaymentMethod

        val success = creditCardPaymentMethod.cancelBillingAgreement()

        if (success) {
          viewModelState.update { PaypalScreenUiState.PaypalAgreementRemoved }
        } else {
          viewModelState.update { PaypalScreenUiState.Error }
        }
      } catch (e: Throwable) {
        viewModelState.update { PaypalScreenUiState.Error }
      }
    }
  }

  private fun makePurchase() {
    viewModelScope.launch {
      try {
        viewModelState.update { PaypalScreenUiState.MakingPurchase }
        val paypalPaymentMethod =
          paymentManager.getPaymentMethod(paymentMethodId) as PaypalPaymentMethod

        val transaction = paypalPaymentMethod.createTransaction(Unit)

        transaction.status.collect {
          when (it) {
            COMPLETED ->

              viewModelState.update {
                PaypalScreenUiState.Success(
                  valueInDollars = paypalPaymentMethod.productInfo.priceInDollars,
                  uid = transaction.uid
                )

            }

            else -> viewModelState.update { PaypalScreenUiState.Error }
          }
        }
      } catch (e: Throwable) {
        viewModelState.update { PaypalScreenUiState.Error }
      }
    }
  }

  private fun onWebViewResult(
    token: String,
    success: Boolean,
  ) {
    viewModelScope.launch {
      try {
        viewModelState.update { PaypalScreenUiState.Loading }
        val paypalPaymentMethod =
          paymentManager.getPaymentMethod(paymentMethodId) as PaypalPaymentMethod

        if (success) {
          viewModelState.update { PaypalScreenUiState.MakingPurchase }

          paypalPaymentMethod.createBillingAgreement(token)

          val transaction = paypalPaymentMethod.createTransaction(Unit)

          transaction.status.collect {
            when (it) {
              COMPLETED ->

                viewModelState.update {
                  PaypalScreenUiState.Success(
                    valueInDollars = paypalPaymentMethod.productInfo.priceInDollars,
                    uid = transaction.uid
                  )

              }

              else -> viewModelState.update { PaypalScreenUiState.Error }
            }
          }
        } else {
          paypalPaymentMethod.cancelToken(token)

          viewModelState.update { PaypalScreenUiState.Error }
        }
      } catch (e: Throwable) {
        viewModelState.update { PaypalScreenUiState.Error }
      }
    }
  }
}
