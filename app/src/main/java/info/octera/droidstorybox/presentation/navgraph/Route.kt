package info.octera.droidstorybox.presentation.navgraph

sealed class Route(val route: String) {

    data object OnBoardingScreen : Route(route = "onBoardingScreen")

    data object HomeScreen : Route(route = "homeScreen")

    data object PackSourceScreen : Route(route = "packSourceScreen")

    data object RemotePackScreen : Route(route = "remotePackScreen")

    /*TO BE REMOVED*/
    data object DetailsScreen : Route(route = "detailsScreen")

    data object AppStartNavigation : Route(route = "appStartNavigation")

    data object NewsNavigation : Route(route = "newsNavigation")

    data object NewsNavigatorScreen : Route(route = "newsNavigator")

}
