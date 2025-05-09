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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import ie.setu.getit.R
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ListingDetailScreen(
    navController: NavHostController,
    modifier: Modifier,
    viewModel: ListingDetailViewModel = hiltViewModel(),
) {
    val listing by viewModel.listing
    val bids = viewModel.bids
    val context = LocalContext.current

    if (listing == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    var bidAmount by remember { mutableStateOf("") }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        item {
            Image(
                painter = painterResource(id = R.drawable.list_your_item),
                contentDescription = "Listing Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
        }

        item {
            Column {
                Text(listing!!.title, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(4.dp))
                Text(listing!!.location, style = MaterialTheme.typography.bodyMedium)
                Spacer(Modifier.height(4.dp))
                Text(
                    "Listed on: ${SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(listing!!.listedOn)}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        item {
            Column {
                Text("Description", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.height(4.dp))
                Text(listing!!.description)
            }
        }

        item {
            Column {
                Text("Category", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.height(4.dp))
                Text(listing!!.categories.joinToString { it.name.replace("_", " ").replaceFirstChar { it.uppercase() } })
            }
        }

        item {
            Column {
                Text("Condition", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.height(4.dp))
                Text(listing!!.condition.name.replace("_", " ").replaceFirstChar { it.uppercase() })
            }
        }

        item {
            val topBid = viewModel.currentTopBid
            Column {
                Text("Starting Price: €${listing!!.price}", fontWeight = FontWeight.Medium)
                Spacer(Modifier.height(4.dp))
                Text("Top Bid: €${topBid ?: "No bids yet"}")
            }
        }

        item {
            Column {
                OutlinedTextField(
                    value = bidAmount,
                    onValueChange = { bidAmount = it },
                    label = { Text("Your Bid") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))
                Button(
                    onClick = {
                        val amount = bidAmount.toIntOrNull()
                        val baseAmount = viewModel.currentTopBid ?: listing!!.price
                        if (amount == null || amount < baseAmount) {
                            Toast.makeText(context, "Bid must be at least €$baseAmount", Toast.LENGTH_SHORT).show()
                        } else {
                            viewModel.placeBid(amount)
                            bidAmount = ""
                            Toast.makeText(context, "Bid placed: €$amount", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Place Bid")
                }
            }
        }

        if (bids.isNotEmpty()) {
            item {
                Text("Bid History", fontWeight = FontWeight.SemiBold)
            }
            items(bids) { bid ->
                Text("€${bid.amount} — ${SimpleDateFormat("HH:mm dd MMM", Locale.getDefault()).format(bid.bidTime)}")
            }
        }
    }
}