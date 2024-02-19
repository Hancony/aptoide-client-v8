package cm.aptoide.pt.guest_wallet.unique_id

import cm.aptoide.pt.guest_wallet.unique_id.generator.IDGenerator
import cm.aptoide.pt.guest_wallet.unique_id.repository.UniqueIdRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UniqueIDProviderImpl @Inject constructor(
  private val generator: IDGenerator,
  private val uniqueIdRepository: UniqueIdRepository,
) : UniqueIDProvider {
  override suspend fun createUniqueId(): String = generator.generateUniqueID()
    .also { uniqueIdRepository.storeUniqueId(it) }

  override suspend fun getUniqueId(): String? = uniqueIdRepository.getUniqueId()
}

interface UniqueIDProvider {
  suspend fun createUniqueId(): String

  suspend fun getUniqueId(): String?
}
