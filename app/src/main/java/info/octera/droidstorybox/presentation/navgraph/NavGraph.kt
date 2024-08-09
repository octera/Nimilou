package info.octera.droidstorybox.presentation.navgraph

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import info.octera.droidstorybox.domain.model.pack.PackMetadata
import info.octera.droidstorybox.presentation.home.HomeScreen
import info.octera.droidstorybox.presentation.home.HomeViewModel
import info.octera.droidstorybox.presentation.onboarding.OnBoardingViewModel
import info.octera.droidstorybox.presentation.onboarding.components.OnBoardingScreen
import info.octera.droidstorybox.presentation.read_pack.ReadPackScreen
import info.octera.droidstorybox.presentation.read_pack.ReadPackViewModel
import info.octera.droidstorybox.presentation.settings_navigation.SettingsNavigator
import java.net.URLEncoder

@Composable
fun NavGraph(startDestination: String) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        composable(route = Route.OnBoardingScreen.route) {
            val viewModel: OnBoardingViewModel = hiltViewModel()
            OnBoardingScreen(event = viewModel::onEvent)
        }
        composable(route = Route.HomeScreen.route) {
            val viewModel: HomeViewModel = hiltViewModel()
            val state = viewModel.state.value
            HomeScreen(
                packs = state.packs,
                onSettingsClicked = { navigateToSettings(navController) },
                onPackFocused = viewModel::setPackFocused,
                onPackSelected = { navigateToReadPack(navController, it)}
            )
        }
        composable(route = Route.SettingsScreen.route) {
            SettingsNavigator()
        }
        composable(
            route = Route.ReadScreen.route + "/{packUri}",
            arguments = listOf(
                navArgument("packUri") {
                    type = NavType.StringType
                    nullable = false
                }
            )
        ) {
            val viewModel: ReadPackViewModel = hiltViewModel()
            val state = viewModel.state.value
            ReadPackScreen(
                pack = state.pack,
                currentStages = state.currendStages,
                playerInfo = state.playerInfo,
                onBackPressed = {navController.popBackStack()},
                onPauseClick = {viewModel.pause()},
                onOkClick = {viewModel.ok()},
                setSelectedStage = {viewModel.setSelectedStage(it)}
            )
        }
    }
}

fun navigateToSettings(navController: NavController) {
    navController.navigate(
        route = Route.SettingsScreen.route
    )
}

fun navigateToReadPack(navController: NavController, packMetadata: PackMetadata) {
    navController.navigate(
        route =  Route.ReadScreen.withArgs(URLEncoder.encode(packMetadata.uri.toString()))
    )

}
