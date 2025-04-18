package ie.setu.getit.ui.component.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
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

val appNavDrawerDestinations = listOf(Home, Listings, ListItem, About)
val allDestinations = listOf(Home, Listings, ListItem, About)
