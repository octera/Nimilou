package info.octera.droidstorybox.presentation.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import info.octera.droidstorybox.R
import info.octera.droidstorybox.domain.model.Article
import info.octera.droidstorybox.presentation.Dimens.MediumPadding1
import info.octera.droidstorybox.presentation.Dimens.MediumPadding2
import info.octera.droidstorybox.presentation.common.ArticlesList
import info.octera.droidstorybox.presentation.common.SearchBar

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    articles: LazyPagingItems<Article>,
    navigateToSearch: () -> Unit,
    navigateToDetails: (Article) -> Unit,
) {
    val titles by remember {
        derivedStateOf {
            if (articles.itemCount > 10) {
                articles.itemSnapshotList.items
                    .slice(IntRange(start = 0, endInclusive = 9))
                    .joinToString(separator = " 🔷 ") { it.title }
            } else {
                ""
            }
        }
    }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(top = 10.dp)
                .statusBarsPadding(),
    ) {
        Image(
            painter = painterResource(id = R.drawable.banner),
            contentDescription = null,
            modifier =
                Modifier
                    .size(width = 900.dp, height = 30.dp)
                    .align(Alignment.CenterHorizontally),
            contentScale = ContentScale.Crop,
        )

        Spacer(modifier = Modifier.padding(10.dp))

        SearchBar(
            modifier =
                Modifier
                    .padding(horizontal = MediumPadding2)
                    .fillMaxWidth(),
            text = "",
            readOnly = true,
            onValueChange = {},
            onSearch = {},
            onClick = {
                navigateToSearch()
            },
        )

        Spacer(modifier = Modifier.padding(bottom = 20.dp))

        Text(
            text = titles,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(start = MediumPadding1)
                    .basicMarquee(),
            fontSize = 14.sp,
            color = colorResource(id = R.color.placeholder),
        )

        Spacer(modifier = Modifier.height(MediumPadding1))

        ArticlesList(
            modifier = Modifier.padding(horizontal = MediumPadding1),
            articles = articles,
            onClick = {
                navigateToDetails(it)
            },
        )
    }
}
