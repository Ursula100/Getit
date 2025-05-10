package ie.setu.getit.ui.screens.my_listings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import ie.setu.getit.R
import ie.setu.getit.ui.component.general.Centre
import ie.setu.getit.ui.component.listings.ListingsList

@Composable
fun MyListingsScreen(
    modifier: Modifier = Modifier,
    viewModel: MyListingsViewModel = hiltViewModel(),
    paddingValues: PaddingValues,
    navController: NavHostController
) {
    val listings = viewModel.userListings.collectAsState().value

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = modifier.padding(start = 24.dp, end = 24.dp)) {
            if (listings.isNotEmpty()) {
                ListingsList(
                    listings = listings,
                    navController = navController,
                    onDeleteListing = { viewModel.deleteListing(it) }
                )
            } else {
                Centre(Modifier.fillMaxSize()) {
                    Text(
                        text = stringResource(R.string.empty_list),
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        FloatingActionButton(
            onClick = { navController.navigate("list") },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = MaterialTheme.colorScheme.primary
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add Listing")
        }
    }
}
