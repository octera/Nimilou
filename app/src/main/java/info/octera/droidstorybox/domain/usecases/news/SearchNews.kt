package info.octera.droidstorybox.domain.usecases.news

import androidx.paging.PagingData
import info.octera.droidstorybox.domain.model.Article
import info.octera.droidstorybox.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class SearchNews(
    private val newsRepository: NewsRepository,
) {
    operator fun invoke(
        searchQuery: String,
        sources: List<String>,
    ): Flow<PagingData<Article>> {
        return newsRepository.searchNews(searchQuery = searchQuery, sources = sources)
    }
}
