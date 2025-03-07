package ie.setu.getit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ie.setu.getit.data.ItemModel
import ie.setu.getit.ui.screens.ListScreen
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

@Composable
fun GetitApp(modifier: Modifier = Modifier) {
    val listings = remember { mutableStateListOf<ItemModel>() }
    ListScreen(modifier = modifier, listings = listings)
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    GetitTheme {
        GetitApp(modifier = Modifier)
    }
}