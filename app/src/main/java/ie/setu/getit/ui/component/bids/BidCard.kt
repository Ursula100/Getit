package ie.setu.getit.ui.component.bids

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ie.setu.getit.data.BidModel
import ie.setu.getit.data.BidStatus
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun BidCard(bid: BidModel, onClick: () -> Unit) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = if (bid.status == BidStatus.TOP)
                MaterialTheme.colorScheme.primaryContainer
            else
                MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Listing ID: ${bid.listingId}", style = MaterialTheme.typography.titleMedium)
            Text("Bid Amount: €${bid.amount}", style = MaterialTheme.typography.bodyLarge)
            Text(
                "Placed: ${SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault()).format(bid.bidTime)}",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = if (bid.status == BidStatus.TOP) "Status: Top Bid" else "Status: Lost",
                style = MaterialTheme.typography.labelMedium,
                color = if (bid.status == BidStatus.TOP)
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.error
            )
        }
    }
}