package info.octera.droidstorybox.data.remote.dto

import info.octera.droidstorybox.domain.model.Article

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)