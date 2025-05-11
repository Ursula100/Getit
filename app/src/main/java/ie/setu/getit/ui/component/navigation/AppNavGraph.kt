package ie.setu.getit.ui.component.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.navArgument
import ie.setu.getit.firebase.service.AuthService
import ie.setu.getit.ui.screens.about.AboutScreen
import ie.setu.getit.ui.screens.bids.BidsScreen
import ie.setu.getit.ui.screens.home.HomeScreen
import ie.setu.getit.ui.screens.list.ListScreen
import ie.setu.getit.ui.screens.listing_details.ListingDetailScreen
import ie.setu.getit.ui.screens.listings.ListingScreen
import ie.setu.getit.ui.screens.login.LoginScreen
import ie.setu.getit.ui.screens.my_listings.MyListingsScreen
import ie.setu.getit.ui.screens.register.RegisterScreen

@Composable
fun NavHostProvider(
    modifier: Modifier,
    navController: NavHostController,
    paddingValues: PaddingValues,
    authService: AuthService
) {
    NavHost(
        navController = navController,
        startDestination = Home.route,
        modifier = Modifier.padding(paddingValues = paddingValues)) {

        composable(route = Home.route) {
            HomeScreen(
                modifier = modifier,
                paddingValues = paddingValues,
                navController = navController
            )
        }
        composable(route = ListItem.route) {
            ListScreen(
                modifier = modifier,
                paddingValues = paddingValues,
                navController = navController,
                authService = authService,
            )
        }
        composable(route = "${ListItem.route}/{id}", arguments = listOf(navArgument("id") { type = NavType.StringType })) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")  // Retrieve the id argument
            ListScreen(
                modifier = modifier,
                paddingValues = paddingValues,
                navController = navController,
                id = id,
                authService = authService // Pass id (nullable) to ListScreen
            )
        }
        composable(route = Listings.route) {
            ListingScreen(
                modifier = Modifier,
                paddingValues = paddingValues,
                navController = navController,
                authService = authService,
            )
        }

        composable(route = MyListings.route) {
            MyListingsScreen(
                modifier = Modifier,
                paddingValues = paddingValues,
                navController = navController,
                authService = authService,
            )
        }

        composable(route = About.route) {
            AboutScreen(
                modifier = Modifier,
            )
        }
        composable(route = "${ListingDetail.route}/{id}", arguments = listOf(navArgument("id") { type = NavType.StringType })) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")
            if (id != null) {
                ListingDetailScreen(
                    navController = navController,
                    modifier = modifier,
                    authService = authService
                )
            }
        }
        composable(route = Bids.route) {
            BidsScreen(
                navController = navController,
            )
        }
        composable(route = Login.route) {
            LoginScreen(
                navController = navController,
            )
        }

        composable(route = Register.route) {
            RegisterScreen(
                navController = navController,
            )
        }
    }
}