package info.octera.droidstorybox.domain.usecases.news

import androidx.paging.PagingData
import info.octera.droidstorybox.domain.model.Article
import info.octera.droidstorybox.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetNews(
    private val newsRepository: NewsRepository
) {

    operator fun invoke(sources: List<String>): Flow<PagingData<Article>> {
        return newsRepository.getNews(sources = sources)

    }
}