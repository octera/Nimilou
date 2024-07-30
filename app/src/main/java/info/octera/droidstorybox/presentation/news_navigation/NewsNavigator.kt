package info.octera.droidstorybox.presentation.news_navigation

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import info.octera.droidstorybox.R
import info.octera.droidstorybox.domain.model.Article
import info.octera.droidstorybox.presentation.bookmark.BookmarkScreen
import info.octera.droidstorybox.presentation.bookmark.BookmarkViewModel
import info.octera.droidstorybox.presentation.details.DetailViewModel
import info.octera.droidstorybox.presentation.details.DetailsEvent
import info.octera.droidstorybox.presentation.details.DetailsScreen
import info.octera.droidstorybox.presentation.home.HomeScreen
import info.octera.droidstorybox.presentation.home.HomeViewModel
import info.octera.droidstorybox.presentation.navgraph.Route
import info.octera.droidstorybox.presentation.news_navigation.components.BottomNavigationItem
import info.octera.droidstorybox.presentation.news_navigation.components.BottomNavigation
import info.octera.droidstorybox.presentation.pack_sources.PackSourcesScreen
import info.octera.droidstorybox.presentation.pack_sources.PackSourcesViewModel
import info.octera.droidstorybox.presentation.search.SearchScreen
import info.octera.droidstorybox.presentation.search.SearchViewModel

@Composable
fun NewsNavigator() {

    val bottomNavigationItems = remember {
        listOf(
            BottomNavigationItem(icon = R.drawable.baseline_home_24, text = "Home"),
            BottomNavigationItem(icon = R.drawable.baseline_search_24, text = "Search"),
            BottomNavigationItem(icon = R.drawable.baseline_cloud_download_24, text = "Pack sources"),
        )
    }

    val navController = rememberNavController()
    val backStackState = navController.currentBackStackEntryAsState().value
    var selectedItem by rememberSaveable {
        mutableIntStateOf(0)
    }
    selectedItem = when (backStackState?.destination?.route) {
        Route.HomeScreen.route -> 0
        Route.SearchScreen.route -> 1
        Route.PackSourceScreen.route -> 2
        else -> 0
    }

    // Hide the bottom navigation when the user is in the details screen
    val isBottomBarVisible =
        remember(key1 = backStackState) {
            backStackState?.destination?.route == Route.HomeScreen.route ||
                backStackState?.destination?.route == Route.SearchScreen.route ||
                backStackState?.destination?.route == Route.PackSourceScreen.route
    }

    Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = {
        if (isBottomBarVisible) {
            BottomNavigation(
                items = bottomNavigationItems,
                selectedItem = selectedItem,
                onItemClick = { index ->
                    when (index) {
                        0 ->
                            navigateToTab(
                                navController = navController,
                                route = Route.HomeScreen.route,
                            )

                        1 ->
                            navigateToTab(
                                navController = navController,
                                route = Route.SearchScreen.route,
                            )

                        2 -> navigateToTab(
                            navController = navController,
                            route = Route.PackSourceScreen.route
                        )
                    }
                },
            )
        }
    }) {
        val bottomPadding = it.calculateBottomPadding()
        NavHost(
            navController = navController,
            startDestination = Route.HomeScreen.route,
            modifier = Modifier.padding(bottom = bottomPadding),
        ) {
            composable(route = Route.HomeScreen.route) { backStackEntry ->
                val viewModel: HomeViewModel = hiltViewModel()
                val articles = viewModel.news.collectAsLazyPagingItems()
                HomeScreen(
                    articles = articles,
                    navigateToSearch = {
                        navigateToTab(
                            navController = navController,
                            route = Route.SearchScreen.route,
                        )
                    },
                    navigateToDetails = { article ->
                        navigateToDetails(
                            navController = navController,
                            article = article,
                        )
                    },
                )
            }
            composable(route = Route.SearchScreen.route) {
                val viewModel: SearchViewModel = hiltViewModel()
                val state = viewModel.state.value
                OnBackClickStateSaver(navController = navController)
                SearchScreen(
                    state = state,
                    event = viewModel::onEvent,
                    navigateToDetails = { article ->
                        navigateToDetails(
                            navController = navController,
                            article = article,
                        )
                    },
                )
            }
            composable(route = Route.DetailsScreen.route) {
                val viewModel: DetailViewModel = hiltViewModel()
                if (viewModel.sideEffect != null)
                    {
                        Toast.makeText(LocalContext.current, viewModel.sideEffect, Toast.LENGTH_SHORT).show()
                        viewModel.onEvent(DetailsEvent.RemoveSideEffect)
                    }
                navController.previousBackStackEntry?.savedStateHandle?.get<Article?>("article")
                    ?.let { article ->
                        DetailsScreen(
                            article = article,
                            event = viewModel::onEvent,
                            navigateUp = { navController.navigateUp() },
                        )
                    }
            }
            composable(route = Route.PackSourceScreen.route) {
                val viewModel: PackSourcesViewModel = hiltViewModel()
                val state = viewModel.state.value
                OnBackClickStateSaver(navController = navController)
                PackSourcesScreen(
                    state = state,
                    addPackSource = viewModel::addPackSource,
                    deletePackSource = viewModel::deletePackSource
                )
            }
        }
    }
}

@Composable
fun OnBackClickStateSaver(navController: NavController) {
    BackHandler(true) {
        navigateToTab(
            navController = navController,
            route = Route.HomeScreen.route,
        )
    }
}

private fun navigateToTab(
    navController: NavController,
    route: String,
) {
    navController.navigate(route) {
        navController.graph.startDestinationRoute?.let { screen_route ->
            popUpTo(screen_route) {
                saveState = true
            }
        }
        launchSingleTop = true
        restoreState = true
    }
}

private fun navigateToDetails(
    navController: NavController,
    article: Article,
) {
    navController.currentBackStackEntry?.savedStateHandle?.set("article", article)
    navController.navigate(
        route = Route.DetailsScreen.route,
    )
}
