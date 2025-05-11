package ie.setu.getit.ui.component.listings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ie.setu.getit.R
import ie.setu.getit.ui.theme.GetitTheme
import java.util.Date


@Composable
fun ListCard(
    id: String,
    uid: String,
    title: String,
    description: String,
    price: Int,
    location: String,
    listedON: Date,
    onDeleteClick: () -> Unit,
    navController: NavHostController,
    loggedUserId: String
) {
    var expanded by remember { mutableStateOf(false) }
    var showDeleteConfirmDialog by remember { mutableStateOf(false) }

    ListItem(
        headlineContent = {
            Text(title, fontWeight = FontWeight.Bold, maxLines = 1)
        },
        overlineContent = {
            Text("id: $id", fontStyle = FontStyle.Italic, color = Color.Gray)
        },
        supportingContent = {
            Column {
                Text(description, maxLines = 2, overflow = TextOverflow.Ellipsis)
                Spacer(Modifier.height(4.dp))
                Text("€ $price", fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.height(4.dp))
                Text(location)
                Spacer(Modifier.height(4.dp))
                Text(listedON.toString(), fontStyle = FontStyle.Italic, color = Color.Gray)
            }
        },
        trailingContent = {
            if (uid == loggedUserId) {
                Box {
                    IconButton(onClick = { expanded = true }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "More options")
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Update") },
                            onClick = {
                                expanded = false
                                navController.navigate("list/$id")
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Delete") },
                            onClick = {
                                expanded = false
                                showDeleteConfirmDialog = true
                            }
                        )
                    }
                }
                if (showDeleteConfirmDialog) {
                    ShowDeleteAlert(
                        onDismiss = { showDeleteConfirmDialog = false },
                        onDelete = onDeleteClick
                    )
                }
            }

        },
        modifier = Modifier
                    .padding(4.dp)
                    .clickable { navController.navigate("listing-detail/$id") },
    )
    HorizontalDivider(color = MaterialTheme.colorScheme.secondary)
}

@Composable
fun ShowDeleteAlert(
    onDismiss: () -> Unit,
    onDelete: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss ,
        title = { Text(stringResource(id = R.string.confirm_delete)) },
        text = { Text(stringResource(id = R.string.confirm_delete_message)) },
        confirmButton = {
            Button(
                onClick = { onDelete() }
            ) { Text("Yes") }
        },
        dismissButton = {
            Button(onClick = onDismiss) { Text("No") }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun ListCardPreview() {
    GetitTheme {
        ListCard(
            id = "12345",
            uid = "1",
            title = "Antique Bread Cutter",
            description = "1860's bread cutter. Cleaned and brought to usable state. Amazing old piece and craftsmanship",
            price = 300,
            location = "Waterford, Ireland",
            listedON = Date(),
            navController = rememberNavController(),
            onDeleteClick = { },
            loggedUserId = "1"
        )
    }
}