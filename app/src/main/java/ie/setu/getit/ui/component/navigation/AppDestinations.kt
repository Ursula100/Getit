package ie.setu.getit.ui.component.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonAdd
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
    override val label = "Explore Listings"
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
    override val label = "View Listing Details"
    override val route = "listing-detail"

    fun withId(id: Int) = "$route/$id"
}

object MyListings : AppDestination {
    override val icon = Icons.AutoMirrored.Filled.List
    override val label = "My Listings"
    override val route = "my-listings"
}

object Bids : AppDestination {
    override val icon = Icons.Filled.AttachMoney
    override val label = "My Bids"
    override val route = "bids"
}

object Login : AppDestination {
    override val icon = Icons.Default.Person
    override val label = "Login"
    override val route = "login"
}

object Register : AppDestination {
    override val icon = Icons.Default.PersonAdd
    override val label = "Register"
    override val route = "register"
}


val appNavDrawerDestinations = listOf(Home, Listings, MyListings, ListItem, Bids, About)
val allDestinations = listOf(Home, Listings, ListItem, About, ListingDetail, Bids, MyListings, Login, Register)
