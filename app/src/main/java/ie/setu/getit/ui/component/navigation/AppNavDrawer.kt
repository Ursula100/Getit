package ie.setu.getit.ui.component.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ie.setu.getit.R
import ie.setu.getit.ui.component.general.ScreenOptions

@Composable
fun AppNavDrawer(
    selectedScreen: ScreenOptions?,
    onScreenSelected: (ScreenOptions) -> Unit,
    closeDrawer: () -> Unit
) {
    ModalDrawerSheet(
        modifier = Modifier.requiredWidth(260.dp)
    ) {
        DrawerHeader()
        Spacer(modifier = Modifier.height(16.dp))

        DrawerItem("Home", Icons.Default.Home, selectedScreen == ScreenOptions.Home) {
            onScreenSelected(ScreenOptions.Home)
            closeDrawer()
        }
        DrawerItem("Listings", Icons.AutoMirrored.Filled.List, selectedScreen == ScreenOptions.Listings) {
            onScreenSelected(ScreenOptions.Listings)
            closeDrawer()
        }
        DrawerItem("About GetIt", Icons.Default.Info, selectedScreen == ScreenOptions.About) {
            onScreenSelected(ScreenOptions.About)
            closeDrawer()
        }
        /*DrawerItem("Profile", Icons.Default.Person, selectedScreen == ScreenOptions.Profile) {
            onScreenSelected(ScreenOptions.Profile)
            closeDrawer()
        }*/
    }
}

@Composable
fun DrawerHeader() {
    Column(
        verticalArrangement = Arrangement.Center,
        //horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(Color(0xFF10851A), Color(0xFF1C8C40)) // Example gradient colors
                )
            )
            .padding(16.dp)
    ) {
        Text(
            text = "GetIt",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge,
            color = Color.White
        )
    }
}

@Composable
fun DrawerItem(
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    selected: Boolean,
    onClick: () -> Unit
) {
    NavigationDrawerItem(
        label = { Text(label, style = MaterialTheme.typography.labelLarge) },
        icon = { Icon(icon, contentDescription = label, tint = MaterialTheme.colorScheme.primary) },
        selected = selected,
        onClick = onClick,
        shape = MaterialTheme.shapes.medium,
        colors = NavigationDrawerItemDefaults.colors(
            selectedContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f), // Change background color of selected item
            unselectedContainerColor = Color.Transparent, // Background for unselected items
            unselectedTextColor = MaterialTheme.colorScheme.onSurface, // Default text color
            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant // Default icon color
        )
    )
}