package ie.setu.getit.ui.component.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

@Composable
fun AppNavDrawer(
    navController: NavHostController,
    currentScreen: AppDestination,
    closeDrawer: () -> Unit
) {
    ModalDrawerSheet(
        modifier = Modifier.requiredWidth(260.dp)
    ) {

        //initializing the default selected item
        var navigationSelectedItem by remember { mutableIntStateOf(0) }

        DrawerHeader()
        Spacer(modifier = Modifier.height(16.dp))

        appNavDrawerDestinations.forEachIndexed { index, navigationItem ->
            NavigationDrawerItem(
                selected = navigationItem == currentScreen,
                shape = MaterialTheme.shapes.medium,
                colors = NavigationDrawerItemDefaults.colors(
                    selectedContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f), // Change background color of selected item
                    unselectedContainerColor = Color.Transparent, // Background for unselected items
                    unselectedTextColor = MaterialTheme.colorScheme.onSurface, // Default text color
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant // Default icon color
                ),
                label = { Text(text = navigationItem.label) },
                icon = { Icon(navigationItem.icon, contentDescription = navigationItem.label, tint = MaterialTheme.colorScheme.primary) },
                onClick = {
                    navigationSelectedItem = index
                    navController.navigate(navigationItem.route){
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                    closeDrawer()
                },

            )
        }
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