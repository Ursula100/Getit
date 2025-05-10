package ie.setu.getit

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ie.setu.getit.ui.component.navigation.AppNavDrawer
import ie.setu.getit.ui.component.navigation.Home
import ie.setu.getit.ui.component.navigation.Listings
import ie.setu.getit.ui.component.navigation.NavHostProvider
import ie.setu.getit.ui.component.navigation.allDestinations
import ie.setu.getit.ui.component.navigation.appNavDrawerDestinations
import ie.setu.getit.ui.theme.GetitTheme
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GetitTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GetitApp(modifier = Modifier)
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GetitApp(modifier: Modifier = Modifier, navController: NavHostController = rememberNavController()) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route ?: Home.route
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Match current route with registered AppDestination (handles "/{id}" cases)
    val currentScreen = allDestinations.find { destination ->
        currentRoute == destination.route || currentRoute.startsWith("${destination.route}/")
    } ?: Home

    val isTopLevelDestination = appNavDrawerDestinations.contains(currentScreen)

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppNavDrawer(
                navController = navController,
                currentScreen = currentScreen,
                closeDrawer = { scope.launch { drawerState.close() } }
            )
        }
    ) {
        Scaffold(
            modifier = modifier,
            topBar = {
                TopAppBar(
                    title = { Text(currentScreen.label, color = Color.White) },
                    colors = TopAppBarDefaults.largeTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    navigationIcon = {
                        if (isTopLevelDestination) {
                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                Icon(Icons.Filled.Menu, contentDescription = "Menu", tint = Color.White)
                            }
                        } else {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                            }
                        }
                    },
                    actions = {
                        if (currentScreen == Listings) {
                            IconButton(onClick = { /* Search logic */ }) {
                                Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.White)
                            }
                        }
                    }
                )
            },
            content = { paddingValues ->
                NavHostProvider(
                    modifier = Modifier,
                    navController = navController,
                    paddingValues = paddingValues
                )
            }
        )
    }
}