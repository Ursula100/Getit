package ie.setu.getit.ui.component.listings

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListingsTopBar(
) {
    TopAppBar(
        title = {
            Text("My Listings")
        },
        navigationIcon = {
            IconButton(onClick = {}) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "" // Add a valid content description
                )
            }
        },
        actions = {
            IconButton(onClick = {}) {
                Icon(
                    Icons.Default.Search,
                    contentDescription = "" // Add a valid content description
                )
            }
        }
    )
}