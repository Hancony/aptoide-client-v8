package cm.aptoide.pt.apkfy

data class ApkfyModel(
  val packageName: String?,
  val appId: Long?,
  val oemId: String?,
  val guestUid: String,
  val utmSource: String?,
  val utmMedium: String?,
  val utmCampaign: String?,
  val utmTerm: String?,
  val utmContent: String?,
) {
  fun hasUTMs() = this.run {
    utmSource != null || utmMedium != null || utmCampaign != null || utmTerm != null || utmContent != null
  }
}
