package cm.aptoide.pt.install_manager

import android.content.pm.PackageInfo
import cm.aptoide.pt.install_manager.dto.InstallPackageInfo
import cm.aptoide.pt.install_manager.dto.InstallationFile
import cm.aptoide.pt.install_manager.dto.TaskInfo
import cm.aptoide.pt.install_manager.repository.AppDetailsRepository
import cm.aptoide.pt.install_manager.repository.PackageInfoRepository
import cm.aptoide.pt.install_manager.repository.TaskInfoRepository
import cm.aptoide.pt.install_manager.workers.PackageDownloader
import cm.aptoide.pt.install_manager.workers.PackageInstaller
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.currentTime
import org.junit.jupiter.params.provider.Arguments
import java.util.stream.Stream
import kotlin.random.Random
import kotlin.random.nextLong
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

@ExperimentalCoroutinesApi
internal fun createBuilderWithMocks(scope: TestScope) = InstallManager.Builder<String>()
  .apply {
    this.packageInfoRepository = PackageInfoRepositoryMock()
    this.appDetailsRepository = AppDetailsRepositoryMock()
    this.taskInfoRepository = TaskInfoRepositoryMock()
    this.packageDownloader = PackageDownloaderMock()
    this.packageInstaller = PackageInstallerMock()
    this.context = scope.coroutineContext
    clock = Clock { scope.currentTime }
  }

internal fun savedPackageAppInfo() = Stream.of(
  Arguments.arguments(
    "No info for the 'package0'",
    "package0",
    mapOf<String, PackageInfo>(),
    mapOf<String, String>(),
  ),
  Arguments.arguments(
    "Not installed with details for the 'package1'",
    "package1",
    mapOf<String, PackageInfo>(),
    mapOf("package1" to "details1"),
  ),
  Arguments.arguments(
    "Installed without details for the 'package2'",
    "package2",
    mapOf("package2" to installedInfo("package2")),
    mapOf<String, String>(),
  ),
  Arguments.arguments(
    "Installed with details for the 'package3'",
    "package3",
    mapOf("package3" to installedInfo("package3")),
    mapOf("package3" to "details3"),
  )
)

@Suppress("DEPRECATION")
internal fun installedInfo(packageName: String) = PackageInfo().apply {
  this.packageName = packageName
  versionName = "1.0.0"
  versionCode = 1
}

internal val installInfo = InstallPackageInfo(
  versionCode = 2,
  downloadSize = 12345,
  installationFiles = setOf(
    InstallationFile(
      name = "base.apk",
      md5 = "md5-base.apk",
      fileSize = 1560,
      type = InstallationFile.Type.BASE,
      url = "http://base.apk",
      altUrl = "https://base.apk",
      localPath = "file://base.apk"
    ),
    InstallationFile(
      name = "pfd.apk",
      md5 = "md5-pfd.apk",
      fileSize = 560,
      type = InstallationFile.Type.PFD_INSTALL_TIME,
      url = "http://pfd.apk",
      altUrl = "https://pfd.apk",
      localPath = "file://pfd.apk"
    ), InstallationFile(
      name = "pad.apk",
      md5 = "md5-pad.apk",
      fileSize = 760,
      type = InstallationFile.Type.PAD_INSTALL_TIME,
      url = "http://pad.apk",
      altUrl = "https://pad.apk",
      localPath = "file://pad.apk"
    )
  )
)

internal val uninstallInfo = InstallPackageInfo(
  versionCode = 1,
  downloadSize = Long.MIN_VALUE,
  installationFiles = setOf()
)

// Crashes on duplicated calls for optimization reasons
internal class PackageInfoRepositoryMock(
  initial: Map<String, PackageInfo> = emptyMap(),
  private val letItCrash: Boolean = false,
  private val speed: Speed = Speed.RANDOM,
) : PackageInfoRepository {
  private var allCalled = false
  val info: MutableMap<String, PackageInfo> =
    mutableMapOf<String, PackageInfo>().apply { putAll(initial) }

  override suspend fun getAll(): Set<PackageInfo> {
    wait()
    if (allCalled) throw java.lang.IllegalStateException("Duplicate call")
    if (letItCrash) throw RuntimeException("Problem!")
    allCalled = true
    return info.values.toSet()
  }

  override suspend fun get(packageName: String): PackageInfo? {
    wait()
    if (letItCrash) throw RuntimeException("Problem!")
    return info[packageName]
  }

  // Delay to emulate real duration
  private suspend fun wait() = delay(
    when (speed) {
      Speed.SLOW -> 2.toLong().seconds
      Speed.NORMAL -> 1.toLong().seconds
      Speed.FAST -> 20.toLong().milliseconds
      Speed.RANDOM -> Random.nextLong(LongRange(20, 2000)).milliseconds
    }
  )
}

// Crashes on duplicated calls for optimization reasons
internal class AppDetailsRepositoryMock(
  initial: Map<String, String> = emptyMap(),
  private val letItCrash: Boolean = false,
  private val speed: Speed = Speed.RANDOM,
) : AppDetailsRepository<String> {
  val details: MutableMap<String, String> = mutableMapOf<String, String>().apply { putAll(initial) }
  private var allCalled = false
  private val getCalledFor: MutableSet<String> = mutableSetOf()
  private val saveCalledFor: MutableSet<String> = mutableSetOf()
  private val removeCalledFor: MutableSet<String> = mutableSetOf()

  override suspend fun getAll(): Set<Pair<String, String>> {
    wait()
    if (allCalled) throw java.lang.IllegalStateException("Duplicate call")
    if (letItCrash) throw RuntimeException("Problem!")
    allCalled = true
    return details.entries.map { it.key to it.value }.toSet()
  }

  override suspend fun get(packageName: String): String? {
    wait()
    if (getCalledFor.contains(packageName)) throw java.lang.IllegalStateException("Duplicate call for $packageName")
    if (letItCrash) throw RuntimeException("Problem!")
    getCalledFor.add(packageName)
    return details[packageName]
  }

  override suspend fun save(packageName: String, details: String) {
    wait()
    if (saveCalledFor.contains(packageName)) throw java.lang.IllegalStateException("Duplicate call for $packageName")
    if (letItCrash) throw RuntimeException("Problem!")
    allCalled = false
    getCalledFor.remove(packageName)
    saveCalledFor.add(packageName)
    removeCalledFor.remove(packageName)
    this.details[packageName] = details
  }

  override suspend fun remove(packageName: String) {
    wait()
    if (removeCalledFor.contains(packageName)) throw java.lang.IllegalStateException("Duplicate call for $packageName")
    if (letItCrash) throw RuntimeException("Problem!")
    allCalled = false
    getCalledFor.remove(packageName)
    saveCalledFor.remove(packageName)
    removeCalledFor.add(packageName)
    details.remove(packageName)
  }

  // Delay to emulate real duration
  private suspend fun wait() = delay(
    when (speed) {
      Speed.SLOW -> 2.toLong().seconds
      Speed.NORMAL -> 1.toLong().seconds
      Speed.FAST -> 20.toLong().milliseconds
      Speed.RANDOM -> Random.nextLong(LongRange(20, 2000)).milliseconds
    }
  )
}

// Crashes on duplicated calls for optimization reasons
internal class TaskInfoRepositoryMock(
  initial: Set<TaskInfo> = emptySet(),
  private val letItCrash: Boolean = false,
  private val speed: Speed = Speed.RANDOM,
) : TaskInfoRepository {
  val info: MutableSet<TaskInfo> = mutableSetOf<TaskInfo>().apply { addAll(initial) }
  private var allCalled = false
  private val saveCalledFor: MutableSet<TaskInfo> = mutableSetOf()
  private val removeAllCalledFor: MutableSet<String> = mutableSetOf()

  override suspend fun getAll(): Set<TaskInfo> {
    wait()
    if (allCalled) throw java.lang.IllegalStateException("Duplicate call")
    if (letItCrash) throw RuntimeException("Problem!")
    allCalled = true
    return info
  }

  override suspend fun saveJob(taskInfo: TaskInfo) {
    wait()
    if (saveCalledFor.contains(taskInfo)) throw java.lang.IllegalStateException("Duplicate call for ${taskInfo.packageName}")
    if (letItCrash) throw RuntimeException("Problem!")
    allCalled = false
    saveCalledFor.add(taskInfo)
    removeAllCalledFor.remove(taskInfo.packageName)
    info.add(taskInfo)
  }

  override suspend fun removeAll(packageName: String) {
    wait()
    if (removeAllCalledFor.contains(packageName)) throw java.lang.IllegalStateException("Duplicate call for $packageName")
    if (letItCrash) throw RuntimeException("Problem!")
    allCalled = false
    removeAllCalledFor.add(packageName)
    saveCalledFor.removeIf { it.packageName == packageName }
    info.removeAll { it.packageName == packageName }
  }

  // Delay to emulate real duration
  private suspend fun wait() = delay(
    when (speed) {
      Speed.SLOW -> 2.toLong().seconds
      Speed.NORMAL -> 1.toLong().seconds
      Speed.FAST -> 20.toLong().milliseconds
      Speed.RANDOM -> Random.nextLong(LongRange(20, 2000)).milliseconds
    }
  )
}

// Crashes on duplicated calls for optimization reasons
internal class PackageDownloaderMock(
  private val waitForCancel: Boolean = false,
  private val letItCrash: Boolean = false,
  private val speed: Speed = Speed.RANDOM,
) : PackageDownloader {
  private val downloaded: MutableSet<String> = mutableSetOf()
  private val cancelled: MutableList<String> = mutableListOf()
  private val blocker = Blocker()

  override suspend fun download(
    packageName: String,
    installPackageInfo: InstallPackageInfo
  ): Flow<Int> {
    if (!downloaded.add(packageName)) throw java.lang.IllegalStateException("Duplicate call for $packageName")
    return flow {
      for (it in 0..4) {
        wait()
        if (it > 1 && waitForCancel) blocker.await()
        if (cancelled.contains(packageName)) throw CancellationException("Cancelled")
        emit(it * 25)
        if (it > 1 && letItCrash) throw RuntimeException("Problem!")
      }
    }
  }

  override fun cancel(packageName: String) {
    if (cancelled.contains(packageName)) throw IllegalStateException("Duplicate call for $packageName")
    cancelled.add(packageName)
    blocker.yield()
  }

  // Delay to emulate real duration
  private suspend fun wait() = delay(
    when (speed) {
      Speed.SLOW -> 4.toLong().minutes
      Speed.NORMAL -> 1.toLong().minutes
      Speed.FAST -> 12.toLong().seconds
      Speed.RANDOM -> Random.nextLong(LongRange(12, 240)).seconds
    }
  )
}

// Crashes on duplicated calls for optimization reasons
internal class PackageInstallerMock(
  private val waitForCancel: Boolean = false,
  private val letItCrash: Boolean = false,
  private val speed: Speed = Speed.RANDOM,
) : PackageInstaller {
  private val installed: MutableSet<String> = mutableSetOf()
  private val uninstalled: MutableSet<String> = mutableSetOf()
  private val cancelled: MutableSet<String> = mutableSetOf()
  private val blocker = Blocker()

  override suspend fun install(
    packageName: String,
    installPackageInfo: InstallPackageInfo
  ): Flow<Int> {
    if (!installed.add(packageName)) throw IllegalStateException("Duplicate call for $packageName")
    return flow {
      for (it in 0..4) {
        wait()
        if (it > 1 && waitForCancel) blocker.await()
        if (cancelled.contains(packageName)) throw CancellationException("Cancelled")
        emit(it * 25)
        if (it > 1 && letItCrash) throw RuntimeException("Problem!")
      }
      uninstalled.remove(packageName)
    }
  }

  override suspend fun uninstall(packageName: String): Flow<Int> {
    if (!uninstalled.add(packageName)) throw IllegalStateException("Duplicate call for $packageName")
    return flow {
      for (it in 0..4) {
        wait()
        if (it > 1 && waitForCancel) blocker.await()
        if (cancelled.contains(packageName)) throw CancellationException("Cancelled")
        emit(it * 25)
        if (it > 1 && letItCrash) throw RuntimeException("Problem!")
      }
      installed.remove(packageName)
    }
  }

  override fun cancel(packageName: String) {
    if (cancelled.contains(packageName)) throw IllegalStateException("Duplicate call for $packageName")
    cancelled.add(packageName)
    blocker.yield()
  }

  // Delay to emulate real duration
  private suspend fun wait() = delay(
    when (speed) {
      Speed.SLOW -> 24.toLong().seconds
      Speed.NORMAL -> 16.toLong().seconds
      Speed.FAST -> 4.toLong().seconds
      Speed.RANDOM -> Random.nextLong(LongRange(4, 24)).seconds
    }
  )
}

enum class Speed {
  SLOW,
  NORMAL,
  FAST,
  RANDOM
}

@JvmInline
value class Blocker(private val channel: Channel<Unit> = Channel(0)) {
  suspend fun await() = channel.receive()
  fun yield() = channel.trySend(Unit)
}