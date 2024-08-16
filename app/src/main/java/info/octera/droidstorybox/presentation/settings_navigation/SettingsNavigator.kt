package info.octera.droidstorybox.presentation.settings_navigation

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import info.octera.droidstorybox.R
import info.octera.droidstorybox.presentation.local_packs.LocalPacksScreen
import info.octera.droidstorybox.presentation.local_packs.LocalPacksViewModel
import info.octera.droidstorybox.presentation.navgraph.Route
import info.octera.droidstorybox.presentation.pack_sources.PackSourcesScreen
import info.octera.droidstorybox.presentation.pack_sources.PackSourcesViewModel
import info.octera.droidstorybox.presentation.remote_pack.RemotePackScreen
import info.octera.droidstorybox.presentation.remote_pack.RemotePackViewModel
import info.octera.droidstorybox.presentation.settings_navigation.components.BottomNavigation
import info.octera.droidstorybox.presentation.settings_navigation.components.BottomNavigationItem
import info.octera.droidstorybox.presentation.settings_appinfo.AppInfoScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsNavigator() {
    val context = LocalContext.current as Activity
    val bottomNavigationItems = remember {
        listOf(
            BottomNavigationItem(icon = R.drawable.baseline_local_library_24, text = "Packs"),
            BottomNavigationItem(icon = R.drawable.baseline_search_24, text = "Remote Packs"),
            BottomNavigationItem(icon = R.drawable.baseline_cloud_download_24, text = "Pack sources"),
            BottomNavigationItem(icon = R.drawable.baseline_info_24, text = "Infos"),
        )
    }

    val navController = rememberNavController()
    val backStackState = navController.currentBackStackEntryAsState().value
    var selectedItem by rememberSaveable {
        mutableIntStateOf(0)
    }
    selectedItem = when (backStackState?.destination?.route) {
        Route.LocalPackScreen.route -> 0
        Route.RemotePackScreen.route -> 1
        Route.PackSourceScreen.route -> 2
        Route.InfoScreen.route -> 3
        else -> 0
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                        if (navController.previousBackStackEntry != null) {
                            navController.navigateUp()
                        } else {
                            context.onBackPressed()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                /*colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),*/
                title = {
                    Text(stringResource(R.string.settings))
                }
            )
        },

        bottomBar = {
            BottomNavigation(
                items = bottomNavigationItems,
                selectedItem = selectedItem,
                onItemClick = { index ->
                    when (index) {
                        0 -> navigateToTab(
                            navController = navController,
                            route = Route.LocalPackScreen.route,
                        )

                        1 -> navigateToTab(
                            navController = navController,
                            route = Route.RemotePackScreen.route,
                        )

                        2 -> navigateToTab(
                            navController = navController,
                            route = Route.PackSourceScreen.route
                        )
                        3 -> navigateToTab(
                            navController = navController,
                            route = Route.InfoScreen.route
                        )
                    }
                },
            )
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Route.LocalPackScreen.route,
            modifier = Modifier.padding(paddingValues),
        ) {
            composable(route = Route.LocalPackScreen.route) { backStackEntry ->
                val viewModel: LocalPacksViewModel = hiltViewModel()
                val state = viewModel.state.value
                LocalPacksScreen(
                    state = state,
                    addPack = viewModel::addPack,
                    deletePack = viewModel::deletePack
                )
            }
            composable(route = Route.RemotePackScreen.route) {
                val viewModel: RemotePackViewModel = hiltViewModel()
                val state = viewModel.state.value
                OnBackClickStateSaver(navController = navController)
                RemotePackScreen(
                    state = state,
                    fetchPacksFromPackSource = viewModel::fetchPacksFromPackSource,
                    fetchPack = viewModel::fetchPack,
                    onQueryChanged = viewModel::onQueryTextChanged
                )
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
            composable(route = Route.InfoScreen.route) {
                AppInfoScreen()
            }
        }
    }
}

@Composable
fun OnBackClickStateSaver(navController: NavController) {
    BackHandler(true) {
        navigateToTab(
            navController = navController,
            route = Route.LocalPackScreen.route,
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
