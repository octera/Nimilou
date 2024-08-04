package info.octera.droidstorybox.presentation.navgraph

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import info.octera.droidstorybox.domain.model.Article
import info.octera.droidstorybox.presentation.home.HomeScreen
import info.octera.droidstorybox.presentation.home.HomeViewModel
import info.octera.droidstorybox.presentation.settings_navigation.SettingsNavigator
import info.octera.droidstorybox.presentation.onboarding.OnBoardingViewModel
import info.octera.droidstorybox.presentation.onboarding.components.OnBoardingScreen

@Composable
fun NavGraph(startDestination: String) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        composable(route = Route.OnBoardingScreen.route) {
            val viewModel: OnBoardingViewModel = hiltViewModel()
            OnBoardingScreen(event = viewModel::onEvent)
        }
        composable(route = Route.HomeScreen.route) {
            val viewModel : HomeViewModel = hiltViewModel()
            val state = viewModel.state.value
            HomeScreen(
                packs = state.packs,
                onSettingsClicked = {navigateToSettings(navController)}
            )
        }
        composable(route = Route.SettingsScreen.route) {
            SettingsNavigator()
        }
    }
}

fun navigateToSettings(navController: NavController) {
    navController.navigate(
        route = Route.SettingsScreen.route
    )
}
