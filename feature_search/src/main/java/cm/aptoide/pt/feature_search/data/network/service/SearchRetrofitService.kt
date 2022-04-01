package cm.aptoide.pt.feature_search.data.network.service

import cm.aptoide.pt.feature_apps.data.network.model.BaseV7DataListResponse
import cm.aptoide.pt.feature_search.data.network.RemoteSearchRepository
import cm.aptoide.pt.feature_search.data.network.model.SearchAppJsonList
import cm.aptoide.pt.feature_search.data.network.model.TopSearchAppJsonList
import cm.aptoide.pt.feature_search.data.network.response.SearchAutoCompleteSuggestionsResponse
import cm.aptoide.pt.feature_search.di.RetrofitBuzz
import cm.aptoide.pt.feature_search.di.RetrofitV7
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRetrofitService @Inject constructor(
  @RetrofitBuzz private val autoCompleteSearchSuggestionsService: AutoCompleteSearchRetrofitService,
  @RetrofitV7 private val searchAppRetrofitService: SearchAppRetrofitService
) :
  RemoteSearchRepository {

  override suspend fun getAutoCompleteSuggestions(keyword: String): Response<SearchAutoCompleteSuggestionsResponse> {
    return autoCompleteSearchSuggestionsService.getAutoCompleteSuggestions(keyword)
  }

  override suspend fun searchApp(keyword: String): Response<BaseV7DataListResponse<SearchAppJsonList>> {
    return searchAppRetrofitService.searchApp(keyword, 15)
  }

  interface AutoCompleteSearchRetrofitService {
    @GET("/v1/suggestion/app/{query}")
    suspend fun getAutoCompleteSuggestions(
      @Path(value = "query", encoded = true) query: String
    ): Response<SearchAutoCompleteSuggestionsResponse>
  }

  interface SearchAppRetrofitService {
    @GET("listSearchApps")
    suspend fun searchApp(
      @Query(value = "query", encoded = true) query: String,
      @Query(value = "limit") limit: Int
    ): Response<BaseV7DataListResponse<SearchAppJsonList>>
  }

  override fun getTopSearchedApps(): Flow<List<TopSearchAppJsonList>> {
    val fakeList = arrayListOf(
      TopSearchAppJsonList("security breach game"),
      TopSearchAppJsonList("Mimicry: Online Horror Action"),
      TopSearchAppJsonList("Eyzacraft: Craft Master"),
      TopSearchAppJsonList("Blockman GO - Adventures"),
      TopSearchAppJsonList("Naughty Puzzle: Tricky Test"),
      TopSearchAppJsonList("Yu-Gi-Oh! Master Duel"),
      TopSearchAppJsonList("Cleaner"),
      TopSearchAppJsonList("DEEMO II"),
      TopSearchAppJsonList("Security Breach Game Helper"),
    )
    return flowOf(fakeList)
  }


}