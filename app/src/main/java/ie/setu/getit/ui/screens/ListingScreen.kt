package ie.setu.getit.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import ie.setu.getit.R
import ie.setu.getit.data.ItemModel
import ie.setu.getit.data.fakeListings
import ie.setu.getit.ui.component.general.Centre
import ie.setu.getit.ui.component.listings.ListingsList
import ie.setu.getit.ui.theme.GetitTheme

@Composable
fun ListingScreen (
    modifier: Modifier = Modifier,
    listings: SnapshotStateList<ItemModel>
){
    Column {
        Column(
            modifier = modifier.padding(
                top = 72.dp,
                start = 24.dp,
                end = 24.dp
            ),
        ) {
            if(listings.isNotEmpty()) {
                ListingsList(listings = listings)
            }
            else {
                Centre(Modifier.fillMaxSize()){
                    Text(
                        color = MaterialTheme.colorScheme.secondary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                        lineHeight = 34.sp,
                        textAlign = TextAlign.Center,
                        text = stringResource(R.string.empty_list)
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
        ListingsList(
            modifier = Modifier,
            listings = fakeListings.toMutableStateList()
        )
    }
}