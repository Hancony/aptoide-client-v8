package cm.aptoide.pt.osp_handler

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import cm.aptoide.pt.osp_handler.handler.OSPHandler
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PaymentActivity : AppCompatActivity() {

  private val uri by lazy { intent?.data }

  @Inject
  lateinit var ospHandler: OSPHandler

  @Inject
  lateinit var contentProvider: PaymentScreenContentProvider

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val purchaseRequest = ospHandler.extract(uri)
    setContent {
      contentProvider.content(purchaseRequest) {
        setResult(if (it) RESULT_OK else RESULT_CANCELED)
        finish()
      }
    }
  }

  override fun onNewIntent(intent: Intent?) {
    super.onNewIntent(intent)
    handleIntent(intent)
  }

  private fun handleIntent(intent: Intent?) {
    contentProvider.handleIntent(this, intent)
  }
}