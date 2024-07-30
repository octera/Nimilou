package info.octera.droidstorybox.domain.usecases.news

import info.octera.droidstorybox.domain.model.Article
import info.octera.droidstorybox.domain.repository.NewsRepository

class UpsertArticle(
    private val newsRepository: NewsRepository,
) {
    suspend operator fun invoke(article: Article)  {
        newsRepository.upsertArticle(article = article)
    }
}
