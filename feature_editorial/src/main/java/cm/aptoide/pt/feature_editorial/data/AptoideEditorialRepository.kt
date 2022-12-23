package cm.aptoide.pt.feature_editorial.data

import cm.aptoide.pt.aptoide_network.di.RetrofitV7ActionItem
import cm.aptoide.pt.feature_apps.data.toDomainModel
import cm.aptoide.pt.feature_editorial.data.network.ContentJSON
import cm.aptoide.pt.feature_editorial.data.network.Data
import cm.aptoide.pt.feature_editorial.data.network.EditorialRemoteService
import cm.aptoide.pt.feature_editorial.data.network.model.EditorialJson
import cm.aptoide.pt.feature_editorial.domain.ArticleContent
import cm.aptoide.pt.feature_editorial.domain.ArticleDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AptoideEditorialRepository @Inject constructor(
  @RetrofitV7ActionItem private val editorialRemoteService: EditorialRemoteService,
) : EditorialRepository {
  override fun getLatestArticle(): Flow<List<Article>> = flow {

    val result = editorialRemoteService.getLatestEditorial()
      .datalist?.list?.map(EditorialJson::toDomainModel) ?: throw IllegalStateException()
    emit(result)
  }

  override fun getArticleDetail(articleId: String): Flow<ArticleDetail> =
    flow {
      val result = editorialRemoteService.getEditorialDetail(articleId)
        .data?.toDomainModel() ?: throw IllegalStateException()
      emit(result)
    }

  override fun getArticleMeta(
    editorialWidgetUrl: String,
    subtype: String?
  ): Flow<List<Article>> =
    flow {
      if (editorialWidgetUrl.contains("cards/")) {
        val result = editorialRemoteService
          .getArticleMeta(editorialWidgetUrl.split("cards/")[1], subtype)
          .datalist?.list?.map(EditorialJson::toDomainModel) ?: throw IllegalStateException()
        emit(result)
      } else {
        throw IllegalStateException()
      }
    }
}

private fun Data.toDomainModel(): ArticleDetail = ArticleDetail(
  title = this.title,
  subtype = ArticleType.valueOf(this.subtype),
  image = this.background,
  date = this.date,
  views = this.views,
  content = map(this.content)
)

fun map(content: List<ContentJSON>): List<ArticleContent> {
  val contentList = ArrayList<ArticleContent>()

  content.forEach {
    contentList.add(
      ArticleContent(
        title = it.title,
        message = it.message,
        action = it.action,
        media = it.media,
        app = it.app?.toDomainModel()
      )
    )
  }

  return contentList
}

private fun EditorialJson.toDomainModel(): Article = Article(
  id = this.id,
  title = this.title,
  subtype = ArticleType.valueOf(this.subtype),
  summary = this.summary,
  image = this.icon,
  date = this.date,
  views = this.views
)
