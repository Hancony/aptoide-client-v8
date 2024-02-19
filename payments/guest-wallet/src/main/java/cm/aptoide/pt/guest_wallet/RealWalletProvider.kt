package cm.aptoide.pt.guest_wallet

import cm.aptoide.pt.guest_wallet.repository.wallet.WalletRepository
import cm.aptoide.pt.guest_wallet.unique_id.UniqueIDProvider
import cm.aptoide.pt.payment_manager.wallet.WalletData
import cm.aptoide.pt.payment_manager.wallet.WalletProvider
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class RealWalletProvider @Inject constructor(
  private val uniqueIDProvider: UniqueIDProvider,
  private val walletRepository: WalletRepository,
) : WalletProvider {

  override suspend fun getOrCreateWallet(): WalletData =
    (uniqueIDProvider.getUniqueId() ?: uniqueIDProvider.createUniqueId())
      .let { walletRepository.getWallet(it) }

  override suspend fun getWallet(): WalletData? = uniqueIDProvider.getUniqueId()
    ?.let { walletRepository.getWallet(it) }
}
