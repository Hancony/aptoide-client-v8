package cm.aptoide.pt.aptoide_ui.textformatter

import java.util.*

class TextFormatter {

  companion object {

    fun withSuffix(count: Long): String {
      if (count < 1000) {
        return count.toString()
      }
      val exp = (Math.log(count.toDouble()) / Math.log(1000.0)).toInt()
      return String.format(
        Locale.ENGLISH, "%d %c",
        (count / Math.pow(1000.0, exp.toDouble())).toInt(),
        "kMBTPE"[exp - 1]
      )
    }

    fun formatBytes(bytes: Long): String {
      val unit = 1024
      if (bytes < unit) {
        return "$bytes B"
      }
      val exp = (Math.log(bytes.toDouble()) / Math.log(unit.toDouble())).toInt()
      val pre = "KMGTPE"[exp - 1].toString() + ""
      val string = String.format(
        Locale.ENGLISH,
        "%.1f %sB",
        bytes / Math.pow(unit.toDouble(), exp.toDouble()),
        pre
      )
      return string
    }

  }
}