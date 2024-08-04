package info.octera.droidstorybox.presentation.navgraph

sealed class Route(val route: String) {
    data object OnBoardingScreen : Route(route = "onBoardingScreen")

    data object HomeScreen : Route(route = "homeScreen")

    data object SettingsScreen : Route(route = "settingsScreen")

    data object LocalPackScreen : Route(route = "localPackScreen")

    data object PackSourceScreen : Route(route = "packSourceScreen")

    data object RemotePackScreen : Route(route = "remotePackScreen")
}
