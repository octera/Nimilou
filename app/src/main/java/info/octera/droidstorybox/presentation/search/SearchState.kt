package info.octera.droidstorybox.presentation.search

import androidx.paging.PagingData
import info.octera.droidstorybox.domain.model.Article
import kotlinx.coroutines.flow.Flow

data class SearchState(
    val searchQuery: String = "",
    val articles : Flow<PagingData<Article>>? = null
) {
}