package ie.setu.getit.ui.component.listings

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import ie.setu.getit.data.ItemModel
import ie.setu.getit.data.fakeListings
import ie.setu.getit.ui.theme.GetitTheme

@Composable
fun ListingsList(
    listings: SnapshotStateList<ItemModel>,
    modifier: Modifier = Modifier
){
    LazyColumn {
        items(
            items = listings,
            key = { listing -> listing.id}
        ){
            listing ->
            ListCard(
                id = listing.id,
                name = listing.name,
                description = listing.description,
                price = listing.price,
                location = listing.location,
                listedON = listing.listedOn
            )
        }
    }
}

@Preview(showBackground = true, wallpaper = Wallpapers.BLUE_DOMINATED_EXAMPLE)
@Composable
fun ListingsListPreview(){
    GetitTheme {
        ListingsList(fakeListings.toMutableStateList())
    }
}