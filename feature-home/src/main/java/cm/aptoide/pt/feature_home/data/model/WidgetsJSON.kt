package cm.aptoide.pt.feature_home.data.model

import androidx.annotation.Keep

@Keep
class WidgetsJSON {
  @Keep
  data class WidgetNetwork(
    var type: WidgetTypeJSON? = null,
    var title: String? = null, // Highlighted, Games, Categories, Timeline, Recommended for you,
    var tag: String, // Aptoide Publishers
    var view: String? = null,
    var actions: List<ActionJSON>? = null,
    var data: DataJSON? = null
  )

  @Keep
  data class DataJSON(
    var layout: Layout? = null,
    var icon: String? = null,
    var message: String? = null,
    var graphic: String? = null,
    var background: String? = null,
    var groupId: Long? = null, //only for eskills widget
    var url:String? = null
  )

  @Keep
  data class ActionJSON(
    var type: String? = null, // button
    var label: String? = null,
    var tag: String? = null,
    var event: EventJSON? = null
  )

  @Keep
  data class EventJSON(
    var type: WidgetActionEventType? = null,
    var name: WidgetActionEventName? = null,
    var action: String? = null,
    var data: DataJSON? = null
  )
}