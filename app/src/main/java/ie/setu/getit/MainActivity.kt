package ie.setu.getit

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import ie.setu.getit.data.ItemModel
import ie.setu.getit.ui.component.general.ScreenOptions
import ie.setu.getit.ui.screens.ListItemScreen
import ie.setu.getit.ui.screens.ListingScreen
import ie.setu.getit.ui.theme.GetitTheme

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
    var selectedScreenOption by remember { mutableStateOf<ScreenOptions?>(ScreenOptions.Listings) }

    Scaffold (
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    if (selectedScreenOption == ScreenOptions.Listings) {
                        Text("My Listings", color = Color.White)
                    }
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                navigationIcon = {
                    if(selectedScreenOption == ScreenOptions.ListItem) {
                        IconButton(onClick = { selectedScreenOption = ScreenOptions.Listings}) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "" ,// Add a valid content description
                                tint = Color.White
                            )
                        }
                    }
                },
                actions = {
                    if(selectedScreenOption == ScreenOptions.Listings) {
                        IconButton(onClick = {}) {
                            Icon(
                                Icons.Default.Search,
                                contentDescription = "", // Add a valid content description
                                tint = Color.White,
                            )
                        }
                    }
                }
            )
        },
        content = { paddingValues -> //Capture scaffold padding
            when(selectedScreenOption){
                ScreenOptions.Listings -> ListingScreen(modifier =  modifier, listings = listings, paddingValues = paddingValues)
                ScreenOptions.ListItem -> ListItemScreen(modifier =  modifier, listings = listings, paddingValues = paddingValues)
                else -> {}
            }
        },
        floatingActionButton = {
            if(selectedScreenOption == ScreenOptions.Listings){
                FloatingActionButton(
                    onClick = {selectedScreenOption = ScreenOptions.ListItem},
                    containerColor = MaterialTheme.colorScheme.surfaceContainer,
                    contentColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(
                        Icons.Filled.Add,
                        contentDescription = "" // Add a valid content description
                    )
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    GetitTheme {
        GetitApp(modifier = Modifier)
    }
}