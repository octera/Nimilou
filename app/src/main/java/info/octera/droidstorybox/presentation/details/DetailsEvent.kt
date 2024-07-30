package info.octera.droidstorybox.presentation.details

import info.octera.droidstorybox.domain.model.Article

sealed class DetailsEvent {
    data class UpsertDeleteArticle(val article: Article) : DetailsEvent()

    object RemoveSideEffect : DetailsEvent()
}
