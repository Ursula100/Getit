package ie.setu.getit

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import ie.setu.getit.firebase.service.AuthService
import ie.setu.getit.ui.component.navigation.*
import ie.setu.getit.ui.theme.GetitTheme
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var authService: AuthService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GetitTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GetitApp(modifier = Modifier, authService = authService)
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GetitApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    authService: AuthService
) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route ?: Home.route
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val isAuthScreen = currentRoute == "login" || currentRoute == "register"

    var isAuthenticated by remember { mutableStateOf<Boolean?>(null) }

    LaunchedEffect(Unit) {
        isAuthenticated = FirebaseAuth.getInstance().currentUser != null
    }

    // Block UI until Firebase finishes checking
    if (isAuthenticated == null) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Box(contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        return
    }

    // Navigate away if user not logged in
    LaunchedEffect(isAuthenticated, currentRoute) {
        if (!isAuthenticated!! && !isAuthScreen) {
            navController.navigate("login") {
                popUpTo(0) { inclusive = true }
            }
        }
    }

    // Determine screen metadata
    val currentScreen = allDestinations.find { destination ->
        currentRoute == destination.route || currentRoute.startsWith("${destination.route}/")
    } ?: Home

    val isTopLevelDestination = appNavDrawerDestinations.contains(currentScreen)

    if (isAuthScreen || !isAuthenticated!!) {
        // Auth screens – no TopBar or Drawer
        NavHostProvider(
            modifier = Modifier,
            navController = navController,
            paddingValues = PaddingValues()
        )
    } else {
        // All other screens with Scaffold and Drawer
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                AppNavDrawer(
                    navController = navController,
                    currentScreen = currentScreen,
                    closeDrawer = { scope.launch { drawerState.close() } },
                    authService = authService
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
}