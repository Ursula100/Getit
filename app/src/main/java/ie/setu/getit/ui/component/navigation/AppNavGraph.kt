package ie.setu.getit.ui.component.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.navArgument
import ie.setu.getit.ui.screens.about.AboutScreen
import ie.setu.getit.ui.screens.bids.BidsScreen
import ie.setu.getit.ui.screens.home.HomeScreen
import ie.setu.getit.ui.screens.list.ListScreen
import ie.setu.getit.ui.screens.listing_details.ListingDetailScreen
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
            ListScreen(
                modifier = modifier,
                listingsViewModel = listings,
                paddingValues = paddingValues,
                navController = navController,
            )
        }
        composable(route = "${ListItem.route}/{id}", arguments = listOf(navArgument("id") { type = NavType.IntType })) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id")  // Retrieve the id argument
            ListScreen(
                modifier = modifier,
                listingsViewModel = listings,
                paddingValues = paddingValues,
                navController = navController,
                id = id // Pass id (nullable) to ListScreen
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
        composable(route = "${ListingDetail.route}/{id}", arguments = listOf(navArgument("id") { type = NavType.IntType })) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id")
            if (id != null) {
                ListingDetailScreen(
                    navController = navController,
                    modifier = modifier
                )
            }
        }
        composable(route = Bids.route) {
            BidsScreen(
                navController = navController,
                viewModel = hiltViewModel()
            )
        }
    }
}