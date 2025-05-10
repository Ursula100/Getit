package ie.setu.getit.ui.screens.bids

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import ie.setu.getit.ui.component.bids.BidCard
import ie.setu.getit.ui.component.navigation.ListingDetail

@Composable
fun BidsScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: BidsViewModel = hiltViewModel()
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("Top Bids", "Lost Bids")

    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        TabRow(selectedTabIndex = selectedTabIndex) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = { Text(title) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        val bids = if (selectedTabIndex == 0) viewModel.topBids else viewModel.lostBids

        if (bids.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No bids found.")
            }
        } else {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                bids.forEach { bid ->
                    BidCard(bid = bid) {
                        navController.navigate(ListingDetail.withId(bid.listingId))
                    }
                }
            }
        }
    }
}