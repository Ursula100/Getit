package ie.setu.getit.ui.screens.listings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ie.setu.getit.R
import ie.setu.getit.data.model.ListingModel
import ie.setu.getit.data.model.fakeListings
import ie.setu.getit.ui.component.general.Centre
import ie.setu.getit.ui.component.listings.ListingsList
import ie.setu.getit.ui.theme.GetitTheme

@Composable
fun ListingScreen (
    modifier: Modifier = Modifier,
    listingsViewModel: ListingsViewModel = hiltViewModel(),
    paddingValues: PaddingValues,
    navController: NavHostController
){
    val listings = listingsViewModel.uiListings.collectAsState().value

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = modifier.padding(
                start = 24.dp,
                end = 24.dp
            )
        ) {
            if (listings.isNotEmpty()) {
                ListingsList(
                    listings = listings,
                    navController = navController,
                    onDeleteListing = { listing: ListingModel ->
                        listingsViewModel.deleteListing(listing)
                    },
                )
            } else {
                Centre(Modifier.fillMaxSize()) {
                    Text(
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                        lineHeight = 34.sp,
                        textAlign = TextAlign.Center,
                        text = stringResource((R.string.no_general_listings_text))
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, wallpaper = Wallpapers.BLUE_DOMINATED_EXAMPLE)
@Composable
fun ListingScreenPreview(){
    GetitTheme {
        PreviewListingScreen(
            modifier = Modifier,
            listings = fakeListings.toMutableStateList(),
        )
    }
}

@Composable
fun PreviewListingScreen(modifier: Modifier = Modifier,
                         listings: SnapshotStateList<ListingModel>){
    Column {
        Column(
            modifier = modifier.padding(
                start = 24.dp,
                end = 24.dp
            )
        ) {
            if(listings.isNotEmpty()) {
                ListingsList(
                    listings = listings,
                    navController = rememberNavController(),
                    onDeleteListing = {},
                )
            }
            else {
                Centre(Modifier.fillMaxSize()){
                    Text(
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                        lineHeight = 34.sp,
                        textAlign = TextAlign.Center,
                        text = stringResource(R.string.no_general_listings_text)
                    )
                }
            }
        }
    }
}