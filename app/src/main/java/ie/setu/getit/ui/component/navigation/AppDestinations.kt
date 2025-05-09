package ie.setu.getit.ui.component.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.ui.graphics.vector.ImageVector

interface AppDestination {
    val icon: ImageVector
    val label: String
    val route: String
}

object ListItem : AppDestination {
    override val icon = Icons.Filled.AddCircle
    override val label = "List an Item"
    override val route = "list"

    // to navigate to edit a specific listing
    fun withId(id: Int) = "list/$id"
}

object Listings : AppDestination {
    override val icon = Icons.AutoMirrored.Filled.List
    override val label = "My Listings"
    override val route = "listings"
}

object Home : AppDestination {
    override val icon = Icons.Default.Home
    override val label = "GetIt"
    override val route = "home"
}

object About : AppDestination {
    override val icon = Icons.Default.Info
    override val label = "About"
    override val route = "about"
}

object ListingDetail : AppDestination {
    override val icon = Icons.Default.Info
    override val label = "View Listing"
    override val route = "listing-detail"

    fun withId(id: Int) = "$route/$id"
}

object Bids : AppDestination {
    override val icon = Icons.Filled.AttachMoney
    override val label = "My Bids"
    override val route = "bids"
}


val appNavDrawerDestinations = listOf(Home, Listings, ListItem, Bids, About)
val allDestinations = listOf(Home, Listings, ListItem, About, ListingDetail, Bids)
