package info.octera.droidstorybox.domain.usecases.news

import info.octera.droidstorybox.domain.model.Article
import info.octera.droidstorybox.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class SelectArticles(
    private val newsRepository: NewsRepository,
) {
    operator fun invoke(): Flow<List<Article>> {
        return newsRepository.getArticles()
    }
}
