package info.octera.droidstorybox.presentation.navgraph

sealed class Route(val route: String) {

    data object OnBoardingScreen : Route(route = "onBoardingScreen")

    data object HomeScreen : Route(route = "homeScreen")

    data object PackSourceScreen : Route(route = "packSourceScreen")
    /*TO BE REMOVED*/
    data object SearchScreen : Route(route = "searchScreen")

    data object DetailsScreen : Route(route = "detailsScreen")

    data object AppStartNavigation : Route(route = "appStartNavigation")

    data object NewsNavigation : Route(route = "newsNavigation")

    data object NewsNavigatorScreen : Route(route = "newsNavigator")

}
