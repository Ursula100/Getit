package ie.setu.getit.ui.component.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import ie.setu.getit.ui.screens.about.AboutScreen
import ie.setu.getit.ui.screens.home.HomeScreen
import ie.setu.getit.ui.screens.list.ListScreen
import ie.setu.getit.ui.screens.listings.ListingScreen
import ie.setu.getit.ui.screens.listings.ListingsViewModel

@Composable
fun NavHostProvider(
    modifier: Modifier,
    navController: NavHostController,
    paddingValues: PaddingValues,
    listings: ListingsViewModel,
) {
    NavHost(
        navController = navController,
        startDestination = Home.route,
        modifier = Modifier.padding(paddingValues = paddingValues)) {

        composable(route = Home.route) {
            HomeScreen( modifier = modifier, paddingValues = paddingValues)
        }
        composable(route = ListItem.route) {
            //call our 'Listings' Screen Here
            ListScreen(
                modifier = modifier,
                listingsViewModel = listings,
                paddingValues = paddingValues,
                navController = navController
            )
        }
        composable(route = Listings.route) {
            ListingScreen(
                modifier = Modifier,
                listingsViewModel = listings,
                paddingValues = paddingValues,
                navController = navController
            )
        }
        composable(route = About.route) {
            AboutScreen(
                modifier = Modifier,
            )
        }
    }
}