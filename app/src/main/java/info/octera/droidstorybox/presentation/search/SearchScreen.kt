package info.octera.droidstorybox.presentation.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.collectAsLazyPagingItems
import info.octera.droidstorybox.domain.model.Article
import info.octera.droidstorybox.presentation.Dimens.MediumPadding1
import info.octera.droidstorybox.presentation.common.ArticlesList
import info.octera.droidstorybox.presentation.common.SearchBar

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier, state: SearchState,
    event: (SearchEvent) -> Unit, navigateToDetails: (Article) -> Unit
) {

    Column(
        modifier = modifier
            .padding(top = MediumPadding1, start = MediumPadding1, end = MediumPadding1)
            .statusBarsPadding()
            .fillMaxSize()
    ) {
        SearchBar(
            text = state.searchQuery,
            readOnly = false,
            onValueChange = { event(SearchEvent.UpdateSearchQuery(it)) },
            onSearch = {
                event(SearchEvent.SearchNews)
            }
        )
        Spacer(modifier = modifier.height(MediumPadding1))
        state.articles?.let {
            val articles = it.collectAsLazyPagingItems()
            ArticlesList(
                articles = articles,
                onClick = {
            navigateToDetails(it)
                }
            )
        }
    }
}