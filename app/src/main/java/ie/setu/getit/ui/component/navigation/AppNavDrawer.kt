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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import ie.setu.getit.firebase.service.AuthService

@Composable
fun AppNavDrawer(
    navController: NavHostController,
    currentScreen: AppDestination,
    closeDrawer: () -> Unit,
    authService: AuthService
) {
    val userName = authService.getCurrentUser()?.displayName

    var navigationSelectedItem by remember { mutableIntStateOf(0) }

    ModalDrawerSheet(modifier = Modifier.requiredWidth(260.dp)) {
        DrawerHeader(userName)

        Spacer(modifier = Modifier.height(16.dp))

        appNavDrawerDestinations.forEachIndexed { index, navigationItem ->
            NavigationDrawerItem(
                selected = navigationItem == currentScreen,
                shape = MaterialTheme.shapes.medium,
                colors = NavigationDrawerItemDefaults.colors(
                    selectedContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                    unselectedContainerColor = Color.Transparent
                ),
                label = { Text(navigationItem.label) },
                icon = {
                    Icon(
                        navigationItem.icon,
                        contentDescription = navigationItem.label,
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                onClick = {
                    navigationSelectedItem = index
                    navController.navigate(navigationItem.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                    closeDrawer()
                }
            )
        }

        Spacer(Modifier.weight(1f)) // Push logout button to bottom

        Divider()

        TextButton(
            onClick = {
                authService.logout()
                navController.navigate("login") {
                    popUpTo(0) { inclusive = true }
                }
                closeDrawer()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Logout", color = MaterialTheme.colorScheme.error)
        }
    }
}

@Composable
fun DrawerHeader(userName: String?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.horizontalGradient(
                    colors = listOf(Color(0xFF10851A), Color(0xFF1C8C40))
                )
            )
            .padding(16.dp)
    ) {
        Text(
            text = "GetIt",
            style = MaterialTheme.typography.titleLarge,
            color = Color.White
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = userName ?: "Guest",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White
        )
    }
}