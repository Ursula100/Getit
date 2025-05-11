package ie.setu.getit.ui.screens.listing_details

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import ie.setu.getit.R
import ie.setu.getit.firebase.service.AuthService
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ListingDetailScreen(
    navController: NavHostController,
    modifier: Modifier,
    viewModel: ListingDetailViewModel = hiltViewModel(),
    authService: AuthService
) {
    val listing by viewModel.listing
    val bids = viewModel.bids
    val context = LocalContext.current

    val userId = authService.getCurrentUserId()!! /// trusting uid is always be available if user is logged in

    if (listing == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(14.dp)
            .padding( // Extra space
                start = 24.dp,
                end = 24.dp
            ),

        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item {
            Image(
                painter = painterResource(id = R.drawable.list_your_item),
                contentDescription = "Listing Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
                    .clip(RoundedCornerShape(12.dp))
            )
        }

        item {
            Column {
                Text("Title", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Medium)
                Text(listing!!.title, style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(8.dp))

                Text("Location", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Medium)
                Text(listing!!.location, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(8.dp))

                Text("Listed On", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Medium)
                Text(
                    SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(listing!!.listedOn),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        item {
            Column {
                Text("Description", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Medium)
                Text(listing!!.description, style = MaterialTheme.typography.bodyLarge)
            }
        }

        item {
            Column {
                Text("Condition", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Medium)
                Text(listing!!.condition.name.replace("_", " ").lowercase()
                    .replaceFirstChar { it.uppercase() }, style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(8.dp))

                Text("Categories", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Medium)
                Text(
                    listing!!.categories.joinToString { it.name.replace("_", " ").lowercase().replaceFirstChar { c -> c.uppercase() } },
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column{
                    Text("Starting Price", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Medium)
                    Text("€${listing!!.price}", style = MaterialTheme.typography.bodyLarge)
                }
                Column{
                    val topBid = viewModel.currentTopBid
                    Text("Top Bid", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Medium)
                    Text("€${topBid ?: "No bids yet"}", style = MaterialTheme.typography.bodyLarge)
                }
            }
        }

        item {
            if (listing!!.uid != userId) {
                var bidAmount by remember { mutableStateOf("") }

                Column {
                    Text("Place a Bid", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = bidAmount,
                        onValueChange = { bidAmount = it },
                        label = { Text("Enter your bid (€)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = {
                            val amount = bidAmount.toIntOrNull()
                            if (amount == null) {
                                Toast.makeText(context, "Enter a valid whole amount", Toast.LENGTH_SHORT).show()
                            }

                            val topBid = viewModel.currentTopBid
                            val minRequired = topBid ?: listing!!.price

                            if (amount != null) {
                                if (amount < minRequired || (topBid != null && amount == minRequired)) {
                                    Toast.makeText(
                                        context,
                                        "Bid must be higher than €$minRequired",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                else {
                                    viewModel.placeBid(amount)
                                    Toast.makeText(context, "Bid placed!", Toast.LENGTH_SHORT).show()
                                }
                            }
                            bidAmount = ""
                        },
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text("Place Bid")
                    }
                }
            }
        }

        if (bids.isNotEmpty()) {
            val top5Bids = viewModel.topFiveBids()
            item {
                Column {
                    Text("Bid History", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
            items(top5Bids) { bid ->
                Text(
                    text = "€${bid.amount} • ${SimpleDateFormat("dd MMM HH:mm", Locale.getDefault()).format(bid.bidTime)}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}