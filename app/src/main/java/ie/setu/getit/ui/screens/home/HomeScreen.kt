package ie.setu.getit.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ie.setu.getit.R
import ie.setu.getit.ui.component.home.ListingMiniCard
import ie.setu.getit.ui.component.navigation.Listings
import ie.setu.getit.ui.theme.GetitTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen (
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel()
){

    val featuredListings = viewModel.featuredListings.collectAsState().value

    //For now, just visual purposes
    //will later break into potentially reusable and more maintainable components
    Column {
        Column(
            modifier = modifier.padding( // Extra space
                start = 24.dp,
                end = 24.dp
            )
        ) {

            SearchBar(
                query = "",
                onQueryChange = {},
                placeholder = {
                    Text("Hunting for something ?")
                },
                onSearch = {},
                active = false,
                onActiveChange = {},
                leadingIcon = {
                    IconButton(onClick = {}) {
                        // Display the user's profile image (using a drawable image for now)
                        Image(
                            painter = painterResource(id = R.drawable.profile_avatar), // Replace with your image
                            contentDescription = "Profile Image",
                            modifier = Modifier
                                .size(40.dp) // Adjust size of the profile image
                                .clip(CircleShape) // Make it circular
                        )
                    }
                },
                trailingIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = "Search" // Add a valid content description
                        )
                    }
                },
                colors = SearchBarDefaults.colors(containerColor = MaterialTheme.colorScheme.tertiary)
            ) { }
            Spacer(Modifier.height(24.dp))
            // Box with Background Image & Button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(20.dp))
            ) {
                // Background Image with Primary Color Overlay
                Image(
                    painter = painterResource(id = R.drawable.list_your_item), // Ensure this image exists
                    contentDescription = "Box Background",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Overlay with Primary Color (60% transparency)
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(
                            Brush.
                            horizontalGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.4f),
                                    MaterialTheme.colorScheme.secondary.copy(alpha = 0.4f)
                                )
                            )
                        )
                )

                // Button inside the Box
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        onClick = {  },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xB9000000), // Black with 50% Transparency
                            contentColor = Color.White
                        ),
                        modifier = Modifier.padding(horizontal = 24.dp)
                    ) {
                        Text(text = "Create New Listing", color = Color.White)
                    }
                }
            }
            Spacer(Modifier.height(24.dp))
            // Featured listings (4) and a explore all card
            Column{
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Explore Listings",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(featuredListings) { listing ->
                        ListingMiniCard(listing = listing, onClick = {
                            navController.navigate("listing-detail/${listing.id}")
                        })
                    }

                    item {
                        OutlinedButton(onClick = {
                            navController.navigate(Listings.route){
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }) {
                            Text("Show All")
                        }
                    }
                }
            }

        }
    }
}

@Preview(showBackground = true, wallpaper = Wallpapers.BLUE_DOMINATED_EXAMPLE)
@Composable
fun HomeScreenPreview(){
    GetitTheme {
        HomeScreen(
            modifier = Modifier,
            paddingValues = PaddingValues(),
            navController = rememberNavController(),
            viewModel = viewModel()
        )
    }
}