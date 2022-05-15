package cm.aptoide.pt.feature_apps.data.network.service

import cm.aptoide.pt.feature_apps.data.network.model.AppJSON
import cm.aptoide.pt.feature_apps.data.network.model.BaseV7DataListResponse
import cm.aptoide.pt.feature_apps.data.network.model.GetAppResponse
import retrofit2.Response

internal interface AppsRemoteService {

  suspend fun getAppsList(query: String): Response<BaseV7DataListResponse<AppJSON>>

  suspend fun getAppsList(groupId: Long): Response<BaseV7DataListResponse<AppJSON>>

  suspend fun getApp(packageName: String): Response<GetAppResponse>

  suspend fun getRecommended(url: String): Response<BaseV7DataListResponse<AppJSON>>

}