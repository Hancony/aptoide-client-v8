package cm.aptoide.pt.feature_apps.domain

data class Widget(
  val title: String,
  val type: WidgetType,
  val layout: WidgetLayout,
  val view: String?,
  val tag: String?,
  val action: List<WidgetAction>?,
  val icon: String?,
  val graphic: String?,
  val background: String?
)

enum class WidgetType {
  APPS_GROUP, ESKILLS, ADS, ACTION_ITEM, APPCOINS_ADS, DISPLAYS, MY_APPS
}

enum class WidgetLayout {
  BRICK, GRID, CURATION_1, UNDEFINED, GRAPHIC, PUBLISHER_TAKEOVER, CAROUSEL, CAROUSEL_LARGE, LIST
}

data class WidgetAction(
  var type: String? = null,
  var label: String? = null,
  var tag: String? = null, var event: Event?
)


data class Event(
  var type: WidgetActionEventType,
  var name: WidgetActionEventName,
  var action: String?, val layout: WidgetLayout?
)

enum class WidgetActionEventType {
  API
}

enum class WidgetActionEventName {
  listApps, getStoreWidgets, getMoreBundle, getAds, getAppCoinsAds, eSkills
}