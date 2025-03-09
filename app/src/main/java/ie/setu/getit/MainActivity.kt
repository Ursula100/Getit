package ie.setu.getit

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import ie.setu.getit.data.ItemModel
import ie.setu.getit.ui.component.general.ScreenOptions
import ie.setu.getit.ui.component.navigation.AppNavDrawer
import ie.setu.getit.ui.screens.HomeScreen
import ie.setu.getit.ui.screens.ListItemScreen
import ie.setu.getit.ui.screens.ListingScreen
import ie.setu.getit.ui.theme.GetitTheme
import kotlinx.coroutines.launch

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
fun GetitApp(modifier: Modifier = Modifier) {
    val listings = remember { mutableStateListOf<ItemModel>() }
    var selectedScreenOption by remember { mutableStateOf<ScreenOptions?>(ScreenOptions.Home) }

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppNavDrawer(
                selectedScreen = selectedScreenOption,
                onScreenSelected = { selectedScreenOption = it },
                closeDrawer = { scope.launch { drawerState.close() } },
            )
        }
    ) {
        Scaffold(
            modifier = modifier,
            topBar = {
                TopAppBar(
                    title = { Text(screenTitle(selectedScreenOption), color = Color.White) },
                    colors = TopAppBarDefaults.largeTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    navigationIcon = {
                        if (selectedScreenOption == ScreenOptions.ListItem) {
                            IconButton(onClick = { selectedScreenOption = ScreenOptions.Listings }) {
                                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                            }
                        } else {
                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                Icon(Icons.Filled.Menu, contentDescription = "Menu", tint = Color.White)
                            }
                        }
                    },
                    actions = {
                        if (selectedScreenOption == ScreenOptions.Listings) {
                            IconButton(onClick = {}) {
                                Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.White)
                            }
                        }
                    }
                )
            },
            content = { paddingValues ->
                when (selectedScreenOption) {
                    ScreenOptions.Home -> HomeScreen(modifier, paddingValues)
                    ScreenOptions.Listings -> ListingScreen(modifier, listings, paddingValues)
                    ScreenOptions.ListItem -> ListItemScreen(modifier, listings, paddingValues)
                    else -> {}
                }
            },
            floatingActionButton = {
                if (selectedScreenOption == ScreenOptions.Listings) {
                    FloatingActionButton(
                        onClick = { selectedScreenOption = ScreenOptions.ListItem },
                        containerColor = MaterialTheme.colorScheme.surfaceContainer,
                        contentColor = MaterialTheme.colorScheme.primary
                    ) {
                        Icon(Icons.Filled.Add, contentDescription = "Add")
                    }
                }
            }
        )
    }
}

fun screenTitle(screen: ScreenOptions?): String {
    return when (screen) {
        ScreenOptions.Home -> "GetIt"
        ScreenOptions.Listings -> "My Listings"
        ScreenOptions.ListItem -> "New Listing"
        else -> ""
    }
}