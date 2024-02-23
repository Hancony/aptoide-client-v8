package com.appcoins.guest_wallet

import com.appcoins.guest_wallet.repository.WalletRepository
import com.appcoins.guest_wallet.unique_id.UniqueIDProvider
import com.appcoins.payments.arch.WalletData
import com.appcoins.payments.arch.WalletProvider
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