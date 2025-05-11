package ie.setu.getit.ui.component.listings

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ie.setu.getit.data.model.ListingModel
import ie.setu.getit.data.model.fakeListings
import ie.setu.getit.ui.theme.GetitTheme

@Composable
fun ListingsList(
    listings: List<ListingModel>,
    modifier: Modifier = Modifier,
    navController: NavHostController,
    onDeleteListing: (ListingModel) -> Unit,
    loggedUserId: String,
){
    LazyColumn {
        items(
            items = listings,
            key = { listing -> listing.id}
        ){
            listing ->
            ListCard(
                id = listing.id,
                uid = listing.uid,
                title = listing.title,
                description = listing.description,
                price = listing.price,
                location = listing.location,
                listedON = listing.listedOn,
                onDeleteClick = { onDeleteListing(listing) },
                navController = navController,
                loggedUserId = loggedUserId
            )
        }
    }
}

@Preview(showBackground = true, wallpaper = Wallpapers.BLUE_DOMINATED_EXAMPLE)
@Composable
fun ListingsListPreview(){
    GetitTheme {
        ListingsList(
            fakeListings.toMutableStateList(),
            navController = rememberNavController(),
            onDeleteListing = {},
            loggedUserId = "1" //built-in function from Jetpack Navigation Compose that provides a mock NavController
        )
    }
}