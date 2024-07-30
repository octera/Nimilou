package info.octera.droidstorybox.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import info.octera.droidstorybox.domain.usecases.news.NewsUseCases
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
    @Inject
    constructor(
        private val newsUseCases: NewsUseCases,
    ) : ViewModel() {
        val news =
            newsUseCases.getNews(
                sources = listOf("bbc-news", "abc-news", "al-jazeera-english"),
            ).cachedIn(viewModelScope)
    }
