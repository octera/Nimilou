package info.octera.droidstorybox.presentation.bookmark

import info.octera.droidstorybox.domain.model.Article

data class BookmarkState(
    val articles: List<Article> = emptyList(),
)
