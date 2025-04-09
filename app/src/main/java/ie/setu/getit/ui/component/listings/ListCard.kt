package ie.setu.getit.ui.component.listings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ie.setu.getit.ui.theme.GetitTheme
import java.util.Date

@Composable
fun ListCard(
    id: Int,
    title: String,
    description: String,
    price: Int,
    location: String,
    listedON: Date
){
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
            Icon(Icons.Default.MoreVert, contentDescription = "")
        },
        modifier = Modifier.padding(4.dp)
    )
    HorizontalDivider(color = MaterialTheme.colorScheme.secondary)
}

@Preview(showBackground = true)
@Composable
fun ListCardPreview() {
    GetitTheme {
        ListCard(
            id = 12345,
            title = "Antique Bread Cutter",
            description = "1860's bread cutter. Cleaned and brought to usable state. Amazing old piece and craftmanship",
            price = 300,
            location = "Waterford, Ireland",
            listedON = Date()
        )
    }
}